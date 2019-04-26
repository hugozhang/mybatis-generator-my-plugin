package org.mybatis.generator.my.plugins;

import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class InsertBatchPlugin extends PluginAdapter {

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        batchInsert(document, introspectedTable);
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        /*CommentGenerator commentGenerator = context.getCommentGenerator();
        FullyQualifiedJavaType listType = FullyQualifiedJavaType.getNewListInstance();
        listType.addTypeArgument(introspectedTable.getRules().calculateAllFieldsClass());

        Method method = new Method();
        method.setVisibility(JavaVisibility.DEFAULT);
        method.setName("insertBatch");
        method.addParameter(new Parameter(listType, "list"));
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        interfaze.addMethod(method);*/

        return true;
    }

    private void batchInsert(Document document, IntrospectedTable introspectedTable) {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        XmlElement batchInsertEl = new XmlElement("insert");
        batchInsertEl.addAttribute(new Attribute("id", "insertBatch"));
        batchInsertEl.addAttribute(new Attribute("parameterType", "java.util.List"));
        batchInsertEl.addAttribute(new Attribute("useGeneratedKeys", "true"));

        if (introspectedTable.getPrimaryKeyColumns().size() != 1) {
            StringBuilder sb = new StringBuilder();
            for (IntrospectedColumn column : introspectedTable.getPrimaryKeyColumns()) {
                sb.append(column.getActualColumnName());
                sb.append(",");
            }
            throw new RuntimeException("Primary key must only one,but found "
                    + introspectedTable.getPrimaryKeyColumns().size() + " : " + sb.toString());
        }

        batchInsertEl.addAttribute(
                new Attribute("keyProperty", introspectedTable.getPrimaryKeyColumns().get(0).getJavaProperty()));

        batchInsertEl
                .addElement(new TextElement("insert into " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));

        StringBuilder buffer = new StringBuilder("(");
        for (IntrospectedColumn column : introspectedTable.getAllColumns()) {
            if (column.isAutoIncrement()) continue;
            buffer.append(column.getActualColumnName());
            buffer.append(",");
        }
        buffer.deleteCharAt(buffer.length() - 1);
        buffer.append(") values");
        batchInsertEl.addElement(new TextElement(buffer.toString()));

        XmlElement foreachEl = new XmlElement("foreach");
        foreachEl.addAttribute(new Attribute("collection", "list"));
        foreachEl.addAttribute(new Attribute("item", "item"));
        foreachEl.addAttribute(new Attribute("index", "index"));
        foreachEl.addAttribute(new Attribute("separator", ","));

        buffer = new StringBuilder("(");
        for (IntrospectedColumn column : introspectedTable.getAllColumns()) {
            if (column.isAutoIncrement()) continue;
            buffer.append("#{item." + column.getJavaProperty() + "}");
            buffer.append(",");
        }
        buffer.deleteCharAt(buffer.length() - 1);
        buffer.append(")");
        foreachEl.addElement(new TextElement(buffer.toString()));

        batchInsertEl.addElement(foreachEl);
        commentGenerator.addComment(foreachEl);
        commentGenerator.addComment(batchInsertEl);
        document.getRootElement().addElement(batchInsertEl);
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

}
