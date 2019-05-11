package org.mybatis.generator.my.plugins;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
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
        String comment = introspectedTable.getRemarks();
        if(StringUtils.isBlank(comment) ){ return; }
        // 转义,避免出现单引号或者双引号的冲突
        comment = StringEscapeUtils.escapeJava(comment);
        topLevelClass.addJavaDocLine("@ApiModel"+"(\""+comment+"\")");
    }

    /**在这里添加字段注解**/
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        String remark = introspectedColumn.getRemarks();
        if( StringUtils.isNotBlank(remark) ){
            remark = StringEscapeUtils.escapeJava(remark);
        }
        field.addJavaDocLine("@ApiModelProperty"+"(\""+remark+"\")");
    }

}