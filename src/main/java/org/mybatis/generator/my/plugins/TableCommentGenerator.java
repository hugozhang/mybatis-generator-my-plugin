package org.mybatis.generator.my.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.DefaultCommentGenerator;

/**
 * 表注释生成到类
 * 功能 :
 * 表注释生成到domain属性中
 * @author : Bruce(刘正航) 上午12:49 2018/3/25
 */
public class TableCommentGenerator extends DefaultCommentGenerator {

    /**在这里import类和类注解**/
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addJavaDocLine("");
        topLevelClass.addImportedType("io.swagger.annotations.ApiModel");
        topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty");
        topLevelClass.addJavaDocLine("");
        topLevelClass.addJavaDocLine("@ApiModel"+"(\""+introspectedTable.getRemarks()+"\")");
    }

    /**在这里添加字段注解**/
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        field.addJavaDocLine("@ApiModelProperty"+"(\""+introspectedColumn.getRemarks()+"\")");
    }

}