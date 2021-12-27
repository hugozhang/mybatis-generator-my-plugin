package org.mybatis.generator.my.plugins;

import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;


/**
 * 定义一个id序列，作为主键，不支持复合主键
 */
public class InsertBatchPlugin extends PluginAdapter {

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        batchInsert(document, introspectedTable);
        batchInsertSelective(document,introspectedTable);
        return true;
    }

    private void batchInsertSelective(Document document, IntrospectedTable introspectedTable) {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        XmlElement batchInsertEl = new XmlElement("insert");
        batchInsertEl.addAttribute(new Attribute("id", "insertBatchSelective"));
        batchInsertEl.addAttribute(new Attribute("parameterType", "java.util.List"));
        batchInsertEl.addAttribute(new Attribute("useGeneratedKeys", "true"));

        if (introspectedTable.getPrimaryKeyColumns().size() != 1) {
            throw new RuntimeException("Primary key must only one,but found " + introspectedTable.getPrimaryKeyColumns().size() + ",table is " + introspectedTable.getFullyQualifiedTableNameAtRuntime());
        }

        batchInsertEl.addAttribute(new Attribute("keyProperty", introspectedTable.getPrimaryKeyColumns().get(0).getJavaProperty()));

        XmlElement foreachEl = new XmlElement("foreach");
        foreachEl.addAttribute(new Attribute("collection", "list"));
        foreachEl.addAttribute(new Attribute("item", "item"));
        foreachEl.addAttribute(new Attribute("index", "index"));
        foreachEl.addAttribute(new Attribute("separator", ";"));

        foreachEl.addElement(new TextElement("insert into " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));

        XmlElement insertTrimElement = new XmlElement("trim");
        insertTrimElement.addAttribute(new Attribute("prefix", "("));
        insertTrimElement.addAttribute(new Attribute("suffix", ")"));
        insertTrimElement.addAttribute(new Attribute("suffixOverrides", ","));
        foreachEl.addElement(insertTrimElement);

        XmlElement valuesTrimElement = new XmlElement("trim");
        valuesTrimElement.addAttribute(new Attribute("prefix", "values ("));
        valuesTrimElement.addAttribute(new Attribute("suffix", ")"));
        valuesTrimElement.addAttribute(new Attribute("suffixOverrides", ","));
        foreachEl.addElement(valuesTrimElement);

        StringBuilder sb = new StringBuilder();
        String prefix = "item.";
        for (IntrospectedColumn introspectedColumn : ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns())) {

            if (introspectedColumn.isSequenceColumn()
                    || introspectedColumn.getFullyQualifiedJavaType().isPrimitive()) {
                // if it is a sequence column, it is not optional
                // This is required for MyBatis3 because MyBatis3 parses
                // and calculates the SQL before executing the selectKey

                // if it is primitive, we cannot do a null check
                sb.setLength(0);
                sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
                sb.append(',');
                insertTrimElement.addElement(new TextElement(sb.toString()));

                sb.setLength(0);
                sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
                sb.append(',');
                valuesTrimElement.addElement(new TextElement(sb.toString()));

                continue;
            }

            sb.setLength(0);
            sb.append(prefix + introspectedColumn.getJavaProperty());
            sb.append(" != null");
            XmlElement insertNotNullElement = new XmlElement("if");
            insertNotNullElement.addAttribute(new Attribute("test", sb.toString()));

            sb.setLength(0);
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(',');
            insertNotNullElement.addElement(new TextElement(sb.toString()));
            insertTrimElement.addElement(insertNotNullElement);

            sb.setLength(0);
            sb.append(prefix + introspectedColumn.getJavaProperty());
            sb.append(" != null");
            XmlElement valuesNotNullElement = new XmlElement("if");
            valuesNotNullElement.addAttribute(new Attribute("test", sb.toString()));
            sb.setLength(0);
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn,prefix));
            sb.append(',');
            valuesNotNullElement.addElement(new TextElement(sb.toString()));
            valuesTrimElement.addElement(valuesNotNullElement);
        }

        batchInsertEl.addElement(foreachEl);
        commentGenerator.addComment(foreachEl);
        commentGenerator.addComment(batchInsertEl);
        document.getRootElement().addElement(batchInsertEl);
    }

    private void batchInsert(Document document, IntrospectedTable introspectedTable) {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        XmlElement batchInsertEl = new XmlElement("insert");
        batchInsertEl.addAttribute(new Attribute("id", "insertBatch"));
        batchInsertEl.addAttribute(new Attribute("parameterType", "java.util.List"));
        batchInsertEl.addAttribute(new Attribute("useGeneratedKeys", "true"));

        if (introspectedTable.getPrimaryKeyColumns().size() != 1) {
            throw new RuntimeException("Primary key must only one,but found " + introspectedTable.getPrimaryKeyColumns().size() + ",table is " + introspectedTable.getFullyQualifiedTableNameAtRuntime() );
        }

        batchInsertEl.addAttribute(new Attribute("keyProperty", introspectedTable.getPrimaryKeyColumns().get(0).getJavaProperty()));
        batchInsertEl.addElement(new TextElement("insert into " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));

        StringBuilder buffer = new StringBuilder("(");
        for (IntrospectedColumn column : ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns())) {
            buffer.append(MyBatis3FormattingUtilities.getEscapedColumnName(column));
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
        for (IntrospectedColumn column : ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns())) {
            buffer.append(MyBatis3FormattingUtilities.getParameterClause(column,"item."));
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
