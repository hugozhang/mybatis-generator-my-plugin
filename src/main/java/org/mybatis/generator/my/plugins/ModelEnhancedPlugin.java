package org.mybatis.generator.my.plugins;

import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class ModelEnhancedPlugin extends PluginAdapter {

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        CommentGenerator commentGenerator = context.getCommentGenerator();
        FullyQualifiedJavaType InnerEnumType = new FullyQualifiedJavaType("Column");
        InnerEnum innerEnum = new InnerEnum(InnerEnumType);
        innerEnum.setVisibility(JavaVisibility.PUBLIC);
        innerEnum.setStatic(true);
        commentGenerator.addEnumComment(innerEnum, introspectedTable);
        topLevelClass.addInnerEnum(innerEnum);

        for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()) {
            innerEnum.addEnumConstant(introspectedColumn.getJavaProperty());
        }

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("asc");
        method.setReturnType(FullyQualifiedJavaType.getStringInstance());
        method.addBodyLine("return column() + \" ASC\";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        innerEnum.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("desc");
        method.setReturnType(FullyQualifiedJavaType.getStringInstance());
        method.addBodyLine("return column() + \" DESC\";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        innerEnum.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PRIVATE);
        method.setName("column");
        method.setReturnType(FullyQualifiedJavaType.getStringInstance());
        method.addBodyLine("StringBuilder buffer = new StringBuilder();");
        method.addBodyLine("char[] charArray = this.name().toCharArray();");
        method.addBodyLine("for(char ch : charArray) {");
        method.addBodyLine("if(Character.isUpperCase(ch)){");
        method.addBodyLine("buffer.append(\"_\");");
        method.addBodyLine("buffer.append(Character.toLowerCase(ch));");
        method.addBodyLine("} else {");
        method.addBodyLine("buffer.append(ch);");
        method.addBodyLine("}");
        method.addBodyLine("}");
        method.addBodyLine("return buffer.toString();");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        innerEnum.addMethod(method);
        return true;
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

}