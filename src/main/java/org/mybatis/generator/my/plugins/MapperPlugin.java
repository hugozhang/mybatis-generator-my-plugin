package org.mybatis.generator.my.plugins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class MapperPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        MapperGeneratorExt mapperGeneratorExt = new MapperGeneratorExt();
        mapperGeneratorExt.setContext(context);
        mapperGeneratorExt.setIntrospectedTable(introspectedTable);
        List<CompilationUnit> units = mapperGeneratorExt.getCompilationUnits();
        List<GeneratedJavaFile> generatedFile = new ArrayList<GeneratedJavaFile>();
        GeneratedJavaFile genJavaFile = null;
        for (Iterator<CompilationUnit> iterator = units.iterator(); iterator.hasNext(); generatedFile.add(genJavaFile)) {
            CompilationUnit unit =  iterator.next();
            genJavaFile = new GeneratedJavaFile(unit, context.getJavaClientGeneratorConfiguration().getTargetProject(),
                    context.getProperty("javaFileEncoding"), context.getJavaFormatter());
        }
        return generatedFile;
    }

}
