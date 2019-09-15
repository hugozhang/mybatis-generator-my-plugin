package org.mybatis.generator.my.plugins;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;

public class ModelEnhancedPluginV2 extends PluginAdapter {

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        CommentGenerator commentGenerator = context.getCommentGenerator();
        FullyQualifiedJavaType InnerEnumType = new FullyQualifiedJavaType("Column");
        InnerEnum innerEnum = new InnerEnum(InnerEnumType);
        innerEnum.setVisibility(JavaVisibility.PUBLIC);
        innerEnum.setStatic(true);
        commentGenerator.addEnumComment(innerEnum, introspectedTable);
        topLevelClass.addInnerEnum(innerEnum);

        for (IntrospectedColumn introspectedColumn : introspectedTable.getNonBLOBColumns()) {
            innerEnum.addEnumConstant(introspectedColumn.getActualColumnName());
        }

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("asc");
        method.setReturnType(FullyQualifiedJavaType.getStringInstance());
        method.addBodyLine("return this.name() + \" ASC\";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        innerEnum.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("desc");
        method.setReturnType(FullyQualifiedJavaType.getStringInstance());
        method.addBodyLine("return this.name() + \" DESC\";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        innerEnum.addMethod(method);

//        method = new Method();
//        method.setVisibility(JavaVisibility.PRIVATE);
//        method.setName("column");
//        method.setReturnType(FullyQualifiedJavaType.getStringInstance());
//        method.addBodyLine("StringBuilder buffer = new StringBuilder();");
//        method.addBodyLine("char[] charArray = this.name().toCharArray();");
//        method.addBodyLine("for(char ch : charArray) {");
//        method.addBodyLine("if(Character.isUpperCase(ch)){");
//        method.addBodyLine("buffer.append(\"_\");");
//        method.addBodyLine("buffer.append(Character.toLowerCase(ch));");
//        method.addBodyLine("} else {");
//        method.addBodyLine("buffer.append(ch);");
//        method.addBodyLine("}");
//        method.addBodyLine("}");
//        method.addBodyLine("return buffer.toString();");
//        commentGenerator.addGeneralMethodComment(method, introspectedTable);
//        innerEnum.addMethod(method);
        return true;
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

}
