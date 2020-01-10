package org.mybatis.generator.my.plugins;

import com.google.common.base.CaseFormat;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.my.mapper.Mapper;
import org.mybatis.generator.my.service.AbstractMybatisService;
import org.mybatis.generator.my.service.MybatisService;

import java.util.ArrayList;
import java.util.List;

public class ServicePlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }


    private String domainObjectName(String tableName) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,tableName);
    }

    private static String firstLetterLower(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,tableName);
    }

    public static void main(String[] args) {
        System.out.println(firstLetterLower("Table"));
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> generatedFile = new ArrayList();
        GeneratedJavaFile genJavaFile;
        genJavaFile = new GeneratedJavaFile(generateService(introspectedTable),
                context.getProperty("serviceTargetProject"),
                context.getProperty("javaFileEncoding"),
                context.getJavaFormatter());
        generatedFile.add(genJavaFile);

        genJavaFile = new GeneratedJavaFile(generateServiceImpl(introspectedTable),
                context.getProperty("serviceImplTargetProject"),
                context.getProperty("javaFileEncoding"),
                context.getJavaFormatter());
        generatedFile.add(genJavaFile);
        return generatedFile;
    }

    private CompilationUnit generateServiceImpl(IntrospectedTable introspectedTable) {

        FullyQualifiedJavaType importSupper = new FullyQualifiedJavaType(context.getProperty("servicePackage") + "." + domainObjectName(introspectedTable.getFullyQualifiedTableNameAtRuntime()) + "Service");
        FullyQualifiedJavaType supper = new FullyQualifiedJavaType(domainObjectName(introspectedTable.getFullyQualifiedTableNameAtRuntime()) + "Service");

        TopLevelClass serviceInterfaceImpl = new TopLevelClass(context.getProperty("serviceImplPackage") + "." + domainObjectName(introspectedTable.getFullyQualifiedTableNameAtRuntime()) + "ServiceImpl");
        serviceInterfaceImpl.setVisibility(JavaVisibility.PUBLIC);
        serviceInterfaceImpl.addAnnotation("@Service");
        serviceInterfaceImpl.addImportedType("org.springframework.stereotype.Service");
        context.getCommentGenerator().addModelClassComment(serviceInterfaceImpl,introspectedTable);

        FullyQualifiedJavaType service = new FullyQualifiedJavaType(AbstractMybatisService.class.getSimpleName());
        FullyQualifiedJavaType service2 = new FullyQualifiedJavaType(AbstractMybatisService.class.getName());
        FullyQualifiedJavaType example = new FullyQualifiedJavaType(introspectedTable.getExampleType());
        FullyQualifiedJavaType entity = new FullyQualifiedJavaType(context.getJavaModelGeneratorConfiguration().getTargetPackage() + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName());
        service.addTypeArgument(entity);
        service.addTypeArgument(example);
        serviceInterfaceImpl.setSuperClass(service);

        serviceInterfaceImpl.addImportedType(service2);
        serviceInterfaceImpl.addImportedType(example);
        serviceInterfaceImpl.addImportedType(entity);

        serviceInterfaceImpl.addImportedType(importSupper);
        serviceInterfaceImpl.addSuperInterface(supper);

        FullyQualifiedJavaType mapperJavaType = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());
        Field field = new Field(firstLetterLower(mapperJavaType.getShortName()), mapperJavaType);
        field.setVisibility(JavaVisibility.PRIVATE);
        field.addAnnotation("@Resource");
        field.addJavaDocLine("");
        serviceInterfaceImpl.addField(field);
        serviceInterfaceImpl.addImportedType("javax.annotation.Resource");
        serviceInterfaceImpl.addImportedType(mapperJavaType);

        Method getMapper = new Method("getMapper");
        getMapper.setVisibility(JavaVisibility.PUBLIC);
        getMapper.addAnnotation("@Override");
        getMapper.addBodyLine("return " + firstLetterLower(mapperJavaType.getShortName()) + ";");
        serviceInterfaceImpl.addMethod(getMapper);

        FullyQualifiedJavaType returnMapper = new FullyQualifiedJavaType(Mapper.class.getSimpleName());
        returnMapper.addTypeArgument(entity);
        returnMapper.addTypeArgument(example);
        getMapper.setReturnType(returnMapper);

        FullyQualifiedJavaType importMapper = new FullyQualifiedJavaType(Mapper.class.getName());
        serviceInterfaceImpl.addImportedType(importMapper);

        return serviceInterfaceImpl;
    }

    private CompilationUnit generateService(IntrospectedTable introspectedTable) {

        Interface serviceInterface = new Interface(context.getProperty("servicePackage") + "." + domainObjectName(introspectedTable.getFullyQualifiedTableNameAtRuntime()) + "Service");
        serviceInterface.setVisibility(JavaVisibility.PUBLIC);
        context.getCommentGenerator().addJavaFileComment(serviceInterface);

        FullyQualifiedJavaType service = new FullyQualifiedJavaType(MybatisService.class.getSimpleName());
        FullyQualifiedJavaType service2 = new FullyQualifiedJavaType(MybatisService.class.getName());
        FullyQualifiedJavaType example = new FullyQualifiedJavaType(introspectedTable.getExampleType());
        FullyQualifiedJavaType entity = new FullyQualifiedJavaType(context.getJavaModelGeneratorConfiguration().getTargetPackage() + "." + introspectedTable.getFullyQualifiedTable().getDomainObjectName());
        service.addTypeArgument(entity);
        service.addTypeArgument(example);
        serviceInterface.addSuperInterface(service);

        serviceInterface.addImportedType(service2);
        serviceInterface.addImportedType(example);
        serviceInterface.addImportedType(entity);
        return serviceInterface;
    }
}
