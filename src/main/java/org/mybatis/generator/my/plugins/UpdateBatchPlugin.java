package org.mybatis.generator.my.plugins;

import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public class UpdateBatchPlugin extends PluginAdapter{

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        batchUpdate(document,introspectedTable);
        return true;
    }
    
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        FullyQualifiedJavaType listType = FullyQualifiedJavaType.getNewListInstance();
        listType.addTypeArgument(introspectedTable.getRules().calculateAllFieldsClass());
        
        Method method = new Method();
        method.setVisibility(JavaVisibility.DEFAULT);
        method.setName("updateBatchSelective");
        method.addParameter(new Parameter(listType, "list"));
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        interfaze.addMethod(method);
        
        return true;
    }

    private void batchUpdate(Document document,IntrospectedTable introspectedTable) {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        XmlElement batchUpdateEl = new XmlElement("update");
        batchUpdateEl.addAttribute(new Attribute("id", "updateBatchSelective"));
        batchUpdateEl.addAttribute(new Attribute("parameterType", "java.util.List"));
        
        XmlElement foreachEl = new XmlElement("foreach");
        foreachEl.addAttribute(new Attribute("collection", "list"));
        foreachEl.addAttribute(new Attribute("item", "item"));
        foreachEl.addAttribute(new Attribute("index", "index"));
        foreachEl.addAttribute(new Attribute("separator", ";"));
        foreachEl.addAttribute(new Attribute("open", ""));
        foreachEl.addAttribute(new Attribute("close", ""));
        
        foreachEl.addElement(new TextElement("update " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        XmlElement setEl = new XmlElement("set");
        for(IntrospectedColumn column : introspectedTable.getAllColumns()) {
            
            if(column.isAutoIncrement()) continue;
            XmlElement ifEl = new XmlElement("if");
            ifEl.addAttribute(new Attribute("test", column.getJavaProperty("item.") + " != null"));
            
            StringBuilder sb = new StringBuilder();
            sb.append(MyBatis3FormattingUtilities
                    .getAliasedEscapedColumnName(column));
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtilities.getParameterClause(
                    column, "item."));
            sb.append(',');
            ifEl.addElement(new TextElement(sb.toString()));
            setEl.addElement(ifEl);
            sb.setLength(0);
        }
        foreachEl.addElement(setEl);
        if(introspectedTable.getPrimaryKeyColumns().size() != 1) {
            throw new RuntimeException("primary key must at least one");
        }
        IntrospectedColumn primaryKeyColumn = introspectedTable.getPrimaryKeyColumns().get(0);
        StringBuilder sb = new StringBuilder();
        sb.append("where " + MyBatis3FormattingUtilities
                .getAliasedEscapedColumnName(primaryKeyColumn));
        sb.append(" = ");
        sb.append(MyBatis3FormattingUtilities.getParameterClause(
                primaryKeyColumn, "item."));
        foreachEl.addElement(new TextElement(sb.toString()));
        batchUpdateEl.addElement(foreachEl);
        
        commentGenerator.addComment(foreachEl);
        commentGenerator.addComment(batchUpdateEl);
        document.getRootElement().addElement(batchUpdateEl);
    }
    
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

}
