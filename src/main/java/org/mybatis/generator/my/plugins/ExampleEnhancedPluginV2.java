package org.mybatis.generator.my.plugins;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;

public class ExampleEnhancedPluginV2 extends PluginAdapter {

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        CommentGenerator commentGenerator = context.getCommentGenerator();
        List<InnerClass> innerClasses = topLevelClass.getInnerClasses();
        for (InnerClass innerClass : innerClasses) {
            if ("Criteria".equals(innerClass.getType().getShortName())) {
                Field field = new Field();
                field.setVisibility(JavaVisibility.PRIVATE);
                field.setType(topLevelClass.getType());
                field.setName("where");
                commentGenerator.addFieldComment(field, introspectedTable);
                innerClass.addField(field);

                List<Method> methods = innerClass.getMethods();
                for (Method method : methods) {
                    if (method.isConstructor()) {
                        method.addParameter(new Parameter(topLevelClass.getType(), "where"));
                        method.addBodyLine("this.where = where;");
                        commentGenerator.addGeneralMethodComment(method, introspectedTable);
                    }
                }

                Method method = new Method();
                method.setVisibility(JavaVisibility.PUBLIC);
                method.setName("where");
                method.setReturnType(topLevelClass.getType());
                method.addBodyLine("return this.where;");

                commentGenerator.addGeneralMethodComment(method, introspectedTable);
                innerClass.addMethod(method);
            }
        }

        List<Method> methods = topLevelClass.getMethods();
        for (Method method : methods) {
            if (!"createCriteriaInternal".equals(method.getName())) continue;
            method.getBodyLines().set(0, "Criteria criteria = new Criteria(this);");
        }

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("orderBy");
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "orderByClauses", true));
        method.setReturnType(topLevelClass.getType());
        method.addBodyLine("StringBuilder buffer = new StringBuilder();");
        method.addBodyLine("if(orderByClauses == null) throw new RuntimeException(\"order by field cannot be null\");");
        method.addBodyLine("for(String field : orderByClauses) {");
        method.addBodyLine("if(field == null || field.trim().length() == 0) throw new RuntimeException(\"order by field cannot be null\");");
        method.addBodyLine("buffer.append(field);");
        method.addBodyLine("buffer.append(\",\");");
        method.addBodyLine("}");
        method.addBodyLine("if(buffer.length() == 0) return this;");
        method.addBodyLine("this.setOrderByClause(buffer.substring(0, buffer.length() - 1));");
        method.addBodyLine("return this;");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("limit");
        method.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), "pageNo", false));
        method.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), "pageSize", false));
        method.setReturnType(topLevelClass.getType());
        method.addBodyLine("this.size = pageSize;");
        method.addBodyLine("this.startOffSet = (pageNo - 1) * pageSize;");
        method.addBodyLine("return this;");

        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);

        return true;
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    public static void main(String[] args) {
        String value = "%null  %";

        String newVaule = value.replaceAll("%", "");

        System.out.println(newVaule);

    }

}
