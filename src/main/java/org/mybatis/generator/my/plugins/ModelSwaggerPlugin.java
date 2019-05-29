package org.mybatis.generator.my.plugins;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加swagger注解到domain
 * 功能 : 
 * 1.增加swagger注解
 * @author : Bruce(刘正航) 20:05 2019-05-11
 */
public class ModelSwaggerPlugin extends PluginAdapter {

    /**包引入**/
    private final List<String> imports = new ArrayList<>();

    {
        imports.add("io.swagger.annotations.ApiModel");
        imports.add("io.swagger.annotations.ApiModelProperty");
    }

    /**注释**/
    private static final String ANNOTATION_DOMAIN = "@ApiModel";
    private static final String ANNOTATION_FIELD = "@ApiModelProperty";

    @Override
    public boolean validate(final List<String> list) {
        return true;
    }
    /**生成实体**/
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        for (String needImport : imports) {
            topLevelClass.addImportedType(needImport);
        }
        if( StringUtils.isBlank(introspectedTable.getRemarks()) ){ return true; }
        topLevelClass.addAnnotation(ANNOTATION_DOMAIN +"(\""+ escape(introspectedTable.getRemarks())+"\")");
        return true;
    }
    /**生成字段**/
    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        if( StringUtils.isBlank(introspectedColumn.getRemarks()) ){ return true; }
        field.addAnnotation(ANNOTATION_FIELD +"(\""+escape(introspectedColumn.getRemarks())+"\")");
        return true;
    }

    /**双引号转换**/
    private String escape(String source){
        return source.replaceAll("\"","\\\\\"");
    }

}
