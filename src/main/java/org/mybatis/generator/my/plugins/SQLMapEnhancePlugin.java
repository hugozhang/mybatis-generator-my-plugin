package org.mybatis.generator.my.plugins;

import java.sql.Types;
import java.util.List;

import com.google.common.base.CaseFormat;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.config.TableConfiguration;

public class SQLMapEnhancePlugin extends PluginAdapter {

    private String domainObjectName(String tableName) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,tableName);
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        //以下是为了简化generateConfig.xml
        super.initialized(introspectedTable);
        TableConfiguration tableConfiguration = introspectedTable.getTableConfiguration();
        tableConfiguration.setAllColumnDelimitingEnabled(true);
        tableConfiguration.setSelectByExampleStatementEnabled(false);
        tableConfiguration.setUpdateByExampleStatementEnabled(false);
        tableConfiguration.setDeleteByExampleStatementEnabled(false);
        tableConfiguration.setDomainObjectName(domainObjectName(tableConfiguration.getTableName()));

        //generateKey 会影响主键id的返回
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        if (primaryKeyColumns.size() != 1) {
            throw new RuntimeException("Primary key must only one,but found " + introspectedTable.getPrimaryKeyColumns().size() + ",table name is " + tableConfiguration.getTableName());
        }
        IntrospectedColumn primaryKeyColumn = primaryKeyColumns.get(0);
        GeneratedKey generatedKey = new GeneratedKey(primaryKeyColumn.getActualColumnName(),"JDBC",true,null);
        tableConfiguration.setGeneratedKey(generatedKey);

        //tinyint(1) 默认会转成byte
        List<IntrospectedColumn> baseColumns = introspectedTable.getBaseColumns();
        for ( IntrospectedColumn column : baseColumns ) {
            if (column.getJdbcType() != Types.TINYINT) continue;
            column.setFullyQualifiedJavaType(new FullyQualifiedJavaType("java.lang.Boolean"));
        }
        //text 默认会转会Bob
        List<IntrospectedColumn> blobColumns = introspectedTable.getBLOBColumns();
        for ( IntrospectedColumn column : blobColumns ) {
            if (column.getJdbcType() != Types.LONGVARCHAR) continue;
            baseColumns.add(column);
        }
        introspectedTable.getBLOBColumns().clear();
    }

    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        sqlMap.setMergeable(false);
        return super.sqlMapGenerated(sqlMap,introspectedTable);
//
//        try {
//            Field field = sqlMap.getClass().getDeclaredField("isMergeable");
//            field.setAccessible(true);
//            field.setBoolean(sqlMap, false);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return true;
    }


    
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

}
