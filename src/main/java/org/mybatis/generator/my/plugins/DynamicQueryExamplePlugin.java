package org.mybatis.generator.my.plugins;

import static org.mybatis.generator.internal.util.JavaBeansUtil.getGetterMethodName;
import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public class DynamicQueryExamplePlugin extends PluginAdapter {

    /**
     * @see org.mybatis.generator.codegen.mybatis3.model.ExampleGenerator
     */
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        // clear
        topLevelClass.getAnnotations().clear();
        topLevelClass.getFields().clear();
        topLevelClass.getInitializationBlocks().clear();
        topLevelClass.getInnerClasses().clear();
        topLevelClass.getMethods().clear();
        // new
        CommentGenerator commentGenerator = context.getCommentGenerator();

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getExampleType());
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);

        // add default constructor
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
        method.setName(type.getShortName());
        method.addBodyLine("oredCriteria = new ArrayList<Criteria>();"); 

        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);

        // add field, getter, setter for orderby clause
        Field field = new Field();
        field.setVisibility(JavaVisibility.PROTECTED);
        field.setType(FullyQualifiedJavaType.getStringInstance());
        field.setName("orderByClause"); 
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("setOrderByClause"); 
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "orderByClause")); 
        method.addBodyLine("this.orderByClause = orderByClause;"); 
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getStringInstance());
        method.setName("getOrderByClause"); 
        method.addBodyLine("return orderByClause;"); 
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);

        // add field, getter, setter for distinct
        field = new Field();
        field.setVisibility(JavaVisibility.PROTECTED);
        field.setType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
        field.setName("distinct"); 
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("setDistinct"); 
        method.addParameter(new Parameter(FullyQualifiedJavaType.getBooleanPrimitiveInstance(), "distinct")); 
        method.addBodyLine("this.distinct = distinct;"); 
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
        method.setName("isDistinct"); 
        method.addBodyLine("return distinct;"); 
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);

        // add field and methods for the list of ored criteria
        field = new Field();
        field.setVisibility(JavaVisibility.PROTECTED);

        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("java.util.List<Criteria>"); 
        field.setType(fqjt);
        field.setName("oredCriteria"); 
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(fqjt);
        method.setName("getOredCriteria"); 
        method.addBodyLine("return oredCriteria;"); 
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("or"); 
        method.addParameter(new Parameter(FullyQualifiedJavaType.getCriteriaInstance(), "criteria")); 
        method.addBodyLine("oredCriteria.add(criteria);"); 
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("or"); 
        method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
        method.addBodyLine("Criteria criteria = createCriteriaInternal();"); 
        method.addBodyLine("oredCriteria.add(criteria);"); 
        method.addBodyLine("return criteria;"); 
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("createCriteria"); 
        method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
        method.addBodyLine("Criteria criteria = createCriteriaInternal();"); 
        method.addBodyLine("if (oredCriteria.size() == 0) {"); 
        method.addBodyLine("oredCriteria.add(criteria);"); 
        method.addBodyLine("}"); 
        method.addBodyLine("return criteria;"); 
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setName("createCriteriaInternal"); 
        method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
        method.addBodyLine("Criteria criteria = new Criteria();");
        method.addBodyLine("return criteria;");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("clear"); 
        method.addBodyLine("oredCriteria.clear();"); 
        method.addBodyLine("orderByClause = null;"); 
        method.addBodyLine("distinct = false;"); 
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);

        // now generate the inner class that holds the AND conditions
        topLevelClass.addInnerClass(getGeneratedCriteriaInnerClass(topLevelClass, introspectedTable));
        topLevelClass.addInnerClass(getCriteriaInnerClass(introspectedTable));
        topLevelClass.addInnerClass(getCriterionInnerClass(introspectedTable));

        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
    }

    private static Method getGetter(Field field) {
        Method method = new Method();
        method.setName(getGetterMethodName(field.getName(), field.getType()));
        method.setReturnType(field.getType());
        method.setVisibility(JavaVisibility.PUBLIC);
        StringBuilder sb = new StringBuilder();
        sb.append("return "); 
        sb.append(field.getName());
        sb.append(';');
        method.addBodyLine(sb.toString());
        return method;
    }

    private InnerClass getCriterionInnerClass(IntrospectedTable introspectedTable) {
        Field field;
        Method method;

        InnerClass answer = new InnerClass(new FullyQualifiedJavaType("Criterion")); 
        answer.setVisibility(JavaVisibility.PUBLIC);
        answer.setStatic(true);
        context.getCommentGenerator().addClassComment(answer, introspectedTable);

        field = new Field();
        field.setName("condition"); 
        field.setType(FullyQualifiedJavaType.getStringInstance());
        field.setVisibility(JavaVisibility.PRIVATE);
        answer.addField(field);
        answer.addMethod(getGetter(field));

        field = new Field();
        field.setName("value"); 
        field.setType(FullyQualifiedJavaType.getObjectInstance());
        field.setVisibility(JavaVisibility.PRIVATE);
        answer.addField(field);
        answer.addMethod(getGetter(field));

        field = new Field();
        field.setName("secondValue"); 
        field.setType(FullyQualifiedJavaType.getObjectInstance());
        field.setVisibility(JavaVisibility.PRIVATE);
        answer.addField(field);
        answer.addMethod(getGetter(field));

        field = new Field();
        field.setName("noValue"); 
        field.setType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
        field.setVisibility(JavaVisibility.PRIVATE);
        answer.addField(field);
        answer.addMethod(getGetter(field));

        field = new Field();
        field.setName("singleValue"); 
        field.setType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
        field.setVisibility(JavaVisibility.PRIVATE);
        answer.addField(field);
        answer.addMethod(getGetter(field));

        field = new Field();
        field.setName("betweenValue"); 
        field.setType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
        field.setVisibility(JavaVisibility.PRIVATE);
        answer.addField(field);
        answer.addMethod(getGetter(field));

        field = new Field();
        field.setName("likeListValue");
        field.setType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
        field.setVisibility(JavaVisibility.PRIVATE);
        answer.addField(field);
        answer.addMethod(getGetter(field));
        
        field = new Field();
        field.setName("listValue"); 
        field.setType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
        field.setVisibility(JavaVisibility.PRIVATE);
        answer.addField(field);
        answer.addMethod(getGetter(field));

        field = new Field();
        field.setName("typeHandler"); 
        field.setType(FullyQualifiedJavaType.getStringInstance());
        field.setVisibility(JavaVisibility.PRIVATE);
        answer.addField(field);
        answer.addMethod(getGetter(field));

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setName("Criterion"); 
        method.setConstructor(true);
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "condition")); 
        method.addBodyLine("super();"); 
        method.addBodyLine("this.condition = condition;"); 
        method.addBodyLine("this.typeHandler = null;"); 
        answer.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setName("Criterion"); 
        method.setConstructor(true);
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "condition")); 
        method.addParameter(new Parameter(FullyQualifiedJavaType.getObjectInstance(), "value")); 
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "typeHandler")); 
        method.addBodyLine("super();"); 
        method.addBodyLine("this.condition = condition;"); 
        method.addBodyLine("this.value = value;"); 
        method.addBodyLine("this.typeHandler = typeHandler;"); 
        
        method.addBodyLine("if (value instanceof List<?>) {"); 
        
        method.addBodyLine("List<?> l = (List<?>)value;"); 
        method.addBodyLine("if(!l.isEmpty()) {"); 
        method.addBodyLine("if(condition.contains(\"like\")) {"); 
        method.addBodyLine("this.likeListValue = true;");
        method.addBodyLine("} else if(condition.contains(\"in\")) {"); 
        method.addBodyLine("this.listValue = true;"); 
        method.addBodyLine("}"); 
        method.addBodyLine("}"); 
        method.addBodyLine("} else {"); 
        method.addBodyLine("this.singleValue = true;"); 
        method.addBodyLine("if(value == null || value.toString().trim().length() == 0) {"); 
        method.addBodyLine("this.noValue = true;"); 
        method.addBodyLine("}"); 
        method.addBodyLine("}"); 
        answer.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setName("Criterion"); 
        method.setConstructor(true);
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "condition")); 
        method.addParameter(new Parameter(FullyQualifiedJavaType.getObjectInstance(), "value")); 
        method.addBodyLine("this(condition, value, null);"); 
        answer.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setName("Criterion"); 
        method.setConstructor(true);
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "condition")); 
        method.addParameter(new Parameter(FullyQualifiedJavaType.getObjectInstance(), "value")); 
        method.addParameter(new Parameter(FullyQualifiedJavaType.getObjectInstance(), "secondValue")); 
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "typeHandler")); 
        method.addBodyLine("super();"); 
        method.addBodyLine("this.condition = condition;"); 
        method.addBodyLine("this.value = value;"); 
        method.addBodyLine("this.secondValue = secondValue;"); 
        method.addBodyLine("this.typeHandler = typeHandler;"); 
        method.addBodyLine("this.betweenValue = true;"); 
        answer.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setName("Criterion"); 
        method.setConstructor(true);
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "condition")); 
        method.addParameter(new Parameter(FullyQualifiedJavaType.getObjectInstance(), "value")); 
        method.addParameter(new Parameter(FullyQualifiedJavaType.getObjectInstance(), "secondValue")); 
        method.addBodyLine("this(condition, value, secondValue, null);"); 
        answer.addMethod(method);

        return answer;
    }

    private InnerClass getCriteriaInnerClass(IntrospectedTable introspectedTable) {
        Method method;

        InnerClass answer = new InnerClass(FullyQualifiedJavaType.getCriteriaInstance());

        answer.setVisibility(JavaVisibility.PUBLIC);
        answer.setStatic(true);
        answer.setSuperClass(FullyQualifiedJavaType.getGeneratedCriteriaInstance());

        context.getCommentGenerator().addClassComment(answer, introspectedTable, true);

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setName("Criteria"); 
        method.setConstructor(true);
        method.addBodyLine("super();"); 
        answer.addMethod(method);

        return answer;
    }

    private InnerClass getGeneratedCriteriaInnerClass(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {
        Field field;
        Method method;

        InnerClass answer = new InnerClass(FullyQualifiedJavaType.getGeneratedCriteriaInstance());

        answer.setVisibility(JavaVisibility.PROTECTED);
        answer.setStatic(true);
        answer.setAbstract(true);
        context.getCommentGenerator().addClassComment(answer, introspectedTable);

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setName("GeneratedCriteria"); 
        method.setConstructor(true);
        method.addBodyLine("super();"); 
        method.addBodyLine("criteria = new ArrayList<Criterion>();"); 
        answer.addMethod(method);

        List<String> criteriaLists = new ArrayList<String>();
        criteriaLists.add("criteria"); 

        for (IntrospectedColumn introspectedColumn : introspectedTable.getNonBLOBColumns()) {
            if (stringHasValue(introspectedColumn.getTypeHandler())) {
                String name = addtypeHandledObjectsAndMethods(introspectedColumn, method, answer);
                criteriaLists.add(name);
            }
        }

        // now generate the isValid method
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("isValid"); 
        method.setReturnType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
        StringBuilder sb = new StringBuilder();
        Iterator<String> strIter = criteriaLists.iterator();
        sb.append("return "); 
        sb.append(strIter.next());
        sb.append(".size() > 0"); 
        if (!strIter.hasNext()) {
            sb.append(';');
        }
        method.addBodyLine(sb.toString());
        while (strIter.hasNext()) {
            sb.setLength(0);
            OutputUtilities.javaIndent(sb, 1);
            sb.append("|| "); 
            sb.append(strIter.next());
            sb.append(".size() > 0"); 
            if (!strIter.hasNext()) {
                sb.append(';');
            }
            method.addBodyLine(sb.toString());
        }
        answer.addMethod(method);

        // now generate the getAllCriteria method
        if (criteriaLists.size() > 1) {
            field = new Field();
            field.setName("allCriteria"); 
            field.setType(new FullyQualifiedJavaType("List<Criterion>")); 
            field.setVisibility(JavaVisibility.PROTECTED);
            answer.addField(field);
        }

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("getAllCriteria"); 
        method.setReturnType(new FullyQualifiedJavaType("List<Criterion>")); 
        if (criteriaLists.size() < 2) {
            method.addBodyLine("return criteria;"); 
        } else {
            method.addBodyLine("if (allCriteria == null) {"); 
            method.addBodyLine("allCriteria = new ArrayList<Criterion>();"); 

            strIter = criteriaLists.iterator();
            while (strIter.hasNext()) {
                method.addBodyLine(String.format("allCriteria.addAll(%s);", strIter.next())); 
            }

            method.addBodyLine("}"); 
            method.addBodyLine("return allCriteria;"); 
        }
        answer.addMethod(method);

        // now we need to generate the methods that will be used in the SqlMap
        // to generate the dynamic where clause
        topLevelClass.addImportedType(FullyQualifiedJavaType.getNewListInstance());
        topLevelClass.addImportedType(FullyQualifiedJavaType.getNewArrayListInstance());

        field = new Field();
        field.setVisibility(JavaVisibility.PROTECTED);
        FullyQualifiedJavaType listOfCriterion = new FullyQualifiedJavaType("java.util.List<Criterion>"); 
        field.setType(listOfCriterion);
        field.setName("criteria"); 
        answer.addField(field);

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(field.getType());
        method.setName(getGetterMethodName(field.getName(), field.getType()));
        method.addBodyLine("return criteria;"); 
        answer.addMethod(method);

        // now add the methods for simplifying the individual field set methods
        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setName("addCriterion"); 
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "condition")); 

        /*
         * ===不要异常 ===== method.addBodyLine("if (condition == null) {");  method.addBodyLine("throw new RuntimeException(\"Value for condition cannot be null\");");
         *  method.addBodyLine("}"); 
         */

        method.addBodyLine("criteria.add(new Criterion(condition));"); 
        if (criteriaLists.size() > 1) {
            method.addBodyLine("allCriteria = null;"); 
        }
        answer.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setName("addCriterion"); 
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "condition")); 
        method.addParameter(new Parameter(FullyQualifiedJavaType.getObjectInstance(), "value")); 
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "property")); 

        /*
         * ===不要异常 ===== method.addBodyLine("if (value == null) {");  method.addBodyLine("throw new RuntimeException(\"Value for \" + property + \" cannot be null\");"
         * );  method.addBodyLine("}"); 
         */
        method.addBodyLine("criteria.add(new Criterion(condition, value));"); 
        if (criteriaLists.size() > 1) {
            method.addBodyLine("allCriteria = null;"); 
        }
        answer.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setName("addCriterion"); 
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "condition")); 
        method.addParameter(new Parameter(FullyQualifiedJavaType.getObjectInstance(), "value1")); 
        method.addParameter(new Parameter(FullyQualifiedJavaType.getObjectInstance(), "value2")); 
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "property")); 
        method.addBodyLine("if (value1 == null || value2 == null) {"); 
        method.addBodyLine("throw new RuntimeException(\"Between values for \" + property + \" cannot be null\");"); 
        method.addBodyLine("}"); 
        method.addBodyLine("criteria.add(new Criterion(condition, value1, value2));"); 
        if (criteriaLists.size() > 1) {
            method.addBodyLine("allCriteria = null;"); 
        }
        answer.addMethod(method);

        FullyQualifiedJavaType listOfDates = new FullyQualifiedJavaType("java.util.List<java.util.Date>"); 

        if (introspectedTable.hasJDBCDateColumns()) {
            topLevelClass.addImportedType(FullyQualifiedJavaType.getDateInstance());
            topLevelClass.addImportedType(FullyQualifiedJavaType.getNewIteratorInstance());
            method = new Method();
            method.setVisibility(JavaVisibility.PROTECTED);
            method.setName("addCriterionForJDBCDate"); 
            method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "condition")); 
            method.addParameter(new Parameter(FullyQualifiedJavaType.getDateInstance(), "value")); 
            method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "property")); 
            /*
             * ===不要异常 ===== method.addBodyLine("if (value == null) {");  method.addBodyLine(
             * "throw new RuntimeException(\"Value for \" + property + \" cannot be null\");");  method.addBodyLine("}"); 
             */
            method.addBodyLine("addCriterion(condition, new java.sql.Date(value.getTime()), property);"); 
            answer.addMethod(method);

            method = new Method();
            method.setVisibility(JavaVisibility.PROTECTED);
            method.setName("addCriterionForJDBCDate"); 
            method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "condition")); 
            method.addParameter(new Parameter(listOfDates, "values")); 
            method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "property")); 
            method.addBodyLine("if (values == null || values.size() == 0) {"); 
            method.addBodyLine(
                    "throw new RuntimeException(\"Value list for \" + property + \" cannot be null or empty\");"); 
            method.addBodyLine("}"); 
            method.addBodyLine("List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();"); 
            method.addBodyLine("Iterator<Date> iter = values.iterator();"); 
            method.addBodyLine("while (iter.hasNext()) {"); 
            method.addBodyLine("dateList.add(new java.sql.Date(iter.next().getTime()));"); 
            method.addBodyLine("}"); 
            method.addBodyLine("addCriterion(condition, dateList, property);"); 
            answer.addMethod(method);

            method = new Method();
            method.setVisibility(JavaVisibility.PROTECTED);
            method.setName("addCriterionForJDBCDate"); 
            method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "condition")); 
            method.addParameter(new Parameter(FullyQualifiedJavaType.getDateInstance(), "value1")); 
            method.addParameter(new Parameter(FullyQualifiedJavaType.getDateInstance(), "value2")); 
            method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "property")); 
            method.addBodyLine("if (value1 == null || value2 == null) {"); 
            method.addBodyLine("throw new RuntimeException(\"Between values for \" + property + \" cannot be null\");"); 
            method.addBodyLine("}"); 
            method.addBodyLine(
                    "addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);"); 
            answer.addMethod(method);
        }

        if (introspectedTable.hasJDBCTimeColumns()) {
            topLevelClass.addImportedType(FullyQualifiedJavaType.getDateInstance());
            topLevelClass.addImportedType(FullyQualifiedJavaType.getNewIteratorInstance());
            method = new Method();
            method.setVisibility(JavaVisibility.PROTECTED);
            method.setName("addCriterionForJDBCTime"); 
            method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "condition")); 
            method.addParameter(new Parameter(FullyQualifiedJavaType.getDateInstance(), "value")); 
            method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "property")); 
            /*
             * ===不要异常 ===== method.addBodyLine("if (value == null) {");  method.addBodyLine(
             * "throw new RuntimeException(\"Value for \" + property + \" cannot be null\");");  method.addBodyLine("}"); 
             */
            method.addBodyLine("addCriterion(condition, new java.sql.Time(value.getTime()), property);"); 
            answer.addMethod(method);

            method = new Method();
            method.setVisibility(JavaVisibility.PROTECTED);
            method.setName("addCriterionForJDBCTime"); 
            method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "condition")); 
            method.addParameter(new Parameter(listOfDates, "values")); 
            method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "property")); 
            method.addBodyLine("if (values == null || values.size() == 0) {"); 
            method.addBodyLine(
                    "throw new RuntimeException(\"Value list for \" + property + \" cannot be null or empty\");"); 
            method.addBodyLine("}"); 
            method.addBodyLine("List<java.sql.Time> timeList = new ArrayList<java.sql.Time>();"); 
            method.addBodyLine("Iterator<Date> iter = values.iterator();"); 
            method.addBodyLine("while (iter.hasNext()) {"); 
            method.addBodyLine("timeList.add(new java.sql.Time(iter.next().getTime()));"); 
            method.addBodyLine("}"); 
            method.addBodyLine("addCriterion(condition, timeList, property);"); 
            answer.addMethod(method);

            method = new Method();
            method.setVisibility(JavaVisibility.PROTECTED);
            method.setName("addCriterionForJDBCTime"); 
            method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "condition")); 
            method.addParameter(new Parameter(FullyQualifiedJavaType.getDateInstance(), "value1")); 
            method.addParameter(new Parameter(FullyQualifiedJavaType.getDateInstance(), "value2")); 
            method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "property")); 
            method.addBodyLine("if (value1 == null || value2 == null) {"); 
            method.addBodyLine("throw new RuntimeException(\"Between values for \" + property + \" cannot be null\");"); 
            method.addBodyLine("}"); 
            method.addBodyLine(
                    "addCriterion(condition, new java.sql.Time(value1.getTime()), new java.sql.Time(value2.getTime()), property);"); 
            answer.addMethod(method);
        }

        for (IntrospectedColumn introspectedColumn : introspectedTable.getNonBLOBColumns()) {
            topLevelClass.addImportedType(introspectedColumn.getFullyQualifiedJavaType());

            // here we need to add the individual methods for setting the
            // conditions for a field
            answer.addMethod(getSetNullMethod(introspectedColumn));
            answer.addMethod(getSetNotNullMethod(introspectedColumn));
            answer.addMethod(getSetEqualMethod(introspectedColumn));
            answer.addMethod(getSetNotEqualMethod(introspectedColumn));
            answer.addMethod(getSetGreaterThanMethod(introspectedColumn));
            answer.addMethod(getSetGreaterThenOrEqualMethod(introspectedColumn));
            answer.addMethod(getSetLessThanMethod(introspectedColumn));
            answer.addMethod(getSetLessThanOrEqualMethod(introspectedColumn));

            if (introspectedColumn.isJdbcCharacterColumn()) {
                answer.addMethod(getSetLikeMethod(introspectedColumn));
                answer.addMethod(getSetNotLikeMethod(introspectedColumn));
            }
            
            if (introspectedColumn.isStringColumn()) {
                
                method = new Method();
                method.setVisibility(JavaVisibility.PUBLIC);
                sb.setLength(0);
                sb.append(introspectedColumn.getJavaProperty());
                sb.insert(0, Character.toUpperCase(sb.charAt(0)));
                
                sb = new StringBuilder();
                sb.append(introspectedColumn.getJavaProperty());
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
                sb.insert(0, "and");
                sb.append("LikeList");
                method.setName(sb.toString());
                
                FullyQualifiedJavaType type = FullyQualifiedJavaType.getNewListInstance();
                if (introspectedColumn.getFullyQualifiedJavaType().isPrimitive()) {
                    type.addTypeArgument(introspectedColumn.getFullyQualifiedJavaType().getPrimitiveTypeWrapper());
                } else {
                    type.addTypeArgument(introspectedColumn.getFullyQualifiedJavaType());
                }
                method.addParameter(new Parameter(type, "values"));
                method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
                method.addBodyLine("addCriterion(\"" + introspectedColumn.getActualColumnName()+" like\""+", values, "+"\""+introspectedColumn.getJavaProperty()+"\");");
                method.addBodyLine("return (Criteria) this;");
                answer.addMethod(method);
            }

            answer.addMethod(getSetInOrNotInMethod(introspectedColumn, true));
            answer.addMethod(getSetInOrNotInMethod(introspectedColumn, false));
            answer.addMethod(getSetBetweenOrNotBetweenMethod(introspectedColumn, true));
            answer.addMethod(getSetBetweenOrNotBetweenMethod(introspectedColumn, false));
        }

        return answer;
    }

    private Method getSetNullMethod(IntrospectedColumn introspectedColumn) {
        return getNoValueMethod(introspectedColumn, "IsNull", "is null");  
    }

    private Method getSetNotNullMethod(IntrospectedColumn introspectedColumn) {
        return getNoValueMethod(introspectedColumn, "IsNotNull", "is not null");  
    }

    private Method getSetEqualMethod(IntrospectedColumn introspectedColumn) {
        return getSingleValueMethod(introspectedColumn, "EqualTo", "=");  
    }

    private Method getSetNotEqualMethod(IntrospectedColumn introspectedColumn) {
        return getSingleValueMethod(introspectedColumn, "NotEqualTo", "<>");  
    }

    private Method getSetGreaterThanMethod(IntrospectedColumn introspectedColumn) {
        return getSingleValueMethod(introspectedColumn, "GreaterThan", ">");  
    }

    private Method getSetGreaterThenOrEqualMethod(IntrospectedColumn introspectedColumn) {
        return getSingleValueMethod(introspectedColumn, "GreaterThanOrEqualTo", ">=");  
    }

    private Method getSetLessThanMethod(IntrospectedColumn introspectedColumn) {
        return getSingleValueMethod(introspectedColumn, "LessThan", "<");  
    }

    private Method getSetLessThanOrEqualMethod(IntrospectedColumn introspectedColumn) {
        return getSingleValueMethod(introspectedColumn, "LessThanOrEqualTo", "<=");  
    }

    private Method getSetLikeMethod(IntrospectedColumn introspectedColumn) {
        return getSingleValueMethod(introspectedColumn, "Like", "like");  
    }

    private Method getSetNotLikeMethod(IntrospectedColumn introspectedColumn) {
        return getSingleValueMethod(introspectedColumn, "NotLike", "not like");  
    }

    private Method getSingleValueMethod(IntrospectedColumn introspectedColumn, String nameFragment, String operator) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addParameter(new Parameter(introspectedColumn.getFullyQualifiedJavaType(), "value")); 
        StringBuilder sb = new StringBuilder();
        sb.append(introspectedColumn.getJavaProperty());
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        sb.insert(0, "and"); 
        sb.append(nameFragment);
        method.setName(sb.toString());
        method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
        sb.setLength(0);

        //检查like  or not like 的情况
        if(operator.contains("like")) {
            sb.append("if(value == null || value.trim().length() == 0 ) return (Criteria) this;");
            method.addBodyLine(sb.toString());
        }
        
        sb.setLength(0);
        
        if (introspectedColumn.isJDBCDateColumn()) {
            sb.append("addCriterionForJDBCDate(\"");
        } else if (introspectedColumn.isJDBCTimeColumn()) {
            sb.append("addCriterionForJDBCTime(\"");
        } else if (stringHasValue(introspectedColumn.getTypeHandler())) {
            sb.append("add");
            sb.append(introspectedColumn.getJavaProperty());
            sb.setCharAt(3, Character.toUpperCase(sb.charAt(3)));
            sb.append("Criterion(\"");
        } else {
            sb.append("addCriterion(\"");
        }

        sb.append(MyBatis3FormattingUtilities.getAliasedActualColumnName(introspectedColumn));
        sb.append(' ');
        sb.append(operator);
        sb.append("\", "); 
        
        //检查like  or not like 的情况
        if(operator.contains("like")) {
            sb.append("\"");
            sb.append("%");
            sb.append("\"");
            sb.append("+");
            sb.append("value");
            sb.append("+");
            sb.append("\"");
            sb.append("%");
            sb.append("\"");
        } else {
            sb.append("value"); 
        }
        
        sb.append(", \""); 
        sb.append(introspectedColumn.getJavaProperty());
        sb.append("\");"); 
        method.addBodyLine(sb.toString());
        method.addBodyLine("return (Criteria) this;"); 

        return method;
    }

    /**
     * Generates methods that set between and not between conditions
     * 
     * @param introspectedColumn
     * @param betweenMethod
     * @return a generated method for the between or not between method
     */
    private Method getSetBetweenOrNotBetweenMethod(IntrospectedColumn introspectedColumn, boolean betweenMethod) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();

        method.addParameter(new Parameter(type, "value1")); 
        method.addParameter(new Parameter(type, "value2")); 
        StringBuilder sb = new StringBuilder();
        sb.append(introspectedColumn.getJavaProperty());
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        sb.insert(0, "and"); 
        if (betweenMethod) {
            sb.append("Between"); 
        } else {
            sb.append("NotBetween"); 
        }
        method.setName(sb.toString());
        method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
        sb.setLength(0);

        if (introspectedColumn.isJDBCDateColumn()) {
            sb.append("addCriterionForJDBCDate(\""); 
        } else if (introspectedColumn.isJDBCTimeColumn()) {
            sb.append("addCriterionForJDBCTime(\""); 
        } else if (stringHasValue(introspectedColumn.getTypeHandler())) {
            sb.append("add"); 
            sb.append(introspectedColumn.getJavaProperty());
            sb.setCharAt(3, Character.toUpperCase(sb.charAt(3)));
            sb.append("Criterion(\""); 
        } else {
            sb.append("addCriterion(\""); 
        }

        sb.append(MyBatis3FormattingUtilities.getAliasedActualColumnName(introspectedColumn));
        if (betweenMethod) {
            sb.append(" between"); 
        } else {
            sb.append(" not between"); 
        }
        sb.append("\", "); 
        sb.append("value1, value2"); 
        sb.append(", \""); 
        sb.append(introspectedColumn.getJavaProperty());
        sb.append("\");"); 
        method.addBodyLine(sb.toString());
        method.addBodyLine("return (Criteria) this;"); 

        return method;
    }

    /**
     * 
     * @param introspectedColumn
     * @param inMethod if true generates an "in" method, else generates a "not in" method
     * @return a generated method for the in or not in method
     */
    private Method getSetInOrNotInMethod(IntrospectedColumn introspectedColumn, boolean inMethod) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType type = FullyQualifiedJavaType.getNewListInstance();
        if (introspectedColumn.getFullyQualifiedJavaType().isPrimitive()) {
            type.addTypeArgument(introspectedColumn.getFullyQualifiedJavaType().getPrimitiveTypeWrapper());
        } else {
            type.addTypeArgument(introspectedColumn.getFullyQualifiedJavaType());
        }

        method.addParameter(new Parameter(type, "values")); 
        StringBuilder sb = new StringBuilder();
        sb.append(introspectedColumn.getJavaProperty());
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        sb.insert(0, "and"); 
        if (inMethod) {
            sb.append("In"); 
        } else {
            sb.append("NotIn"); 
        }
        method.setName(sb.toString());
        method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
        sb.setLength(0);

        if (introspectedColumn.isJDBCDateColumn()) {
            sb.append("addCriterionForJDBCDate(\""); 
        } else if (introspectedColumn.isJDBCTimeColumn()) {
            sb.append("addCriterionForJDBCTime(\""); 
        } else if (stringHasValue(introspectedColumn.getTypeHandler())) {
            sb.append("add"); 
            sb.append(introspectedColumn.getJavaProperty());
            sb.setCharAt(3, Character.toUpperCase(sb.charAt(3)));
            sb.append("Criterion(\""); 
        } else {
            sb.append("addCriterion(\""); 
        }

        sb.append(MyBatis3FormattingUtilities.getAliasedActualColumnName(introspectedColumn));
        if (inMethod) {
            sb.append(" in"); 
        } else {
            sb.append(" not in"); 
        }
        sb.append("\", values, \""); 
        sb.append(introspectedColumn.getJavaProperty());
        sb.append("\");"); 
        method.addBodyLine(sb.toString());
        method.addBodyLine("return (Criteria) this;"); 

        return method;
    }

    private Method getNoValueMethod(IntrospectedColumn introspectedColumn, String nameFragment, String operator) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        StringBuilder sb = new StringBuilder();
        sb.append(introspectedColumn.getJavaProperty());
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        sb.insert(0, "and"); 
        sb.append(nameFragment);
        method.setName(sb.toString());
        method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
        sb.setLength(0);
        sb.append("addCriterion(\""); 
        sb.append(MyBatis3FormattingUtilities.getAliasedActualColumnName(introspectedColumn));
        sb.append(' ');
        sb.append(operator);
        sb.append("\");"); 
        method.addBodyLine(sb.toString());
        method.addBodyLine("return (Criteria) this;"); 

        return method;
    }

    /**
     * This method adds all the extra methods and fields required to support a user defined type handler on some column.
     * 
     * @param introspectedColumn
     * @param constructor
     * @param innerClass
     * @return the name of the List added to the class by this method
     */
    private String addtypeHandledObjectsAndMethods(IntrospectedColumn introspectedColumn, Method constructor,
            InnerClass innerClass) {
        String answer;
        StringBuilder sb = new StringBuilder();

        // add new private field and public accessor in the class
        sb.setLength(0);
        sb.append(introspectedColumn.getJavaProperty());
        sb.append("Criteria"); 
        answer = sb.toString();

        Field field = new Field();
        field.setVisibility(JavaVisibility.PROTECTED);
        field.setType(new FullyQualifiedJavaType("java.util.List<Criterion>")); 
        field.setName(answer);
        innerClass.addField(field);

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(field.getType());
        method.setName(getGetterMethodName(field.getName(), field.getType()));
        sb.insert(0, "return "); 
        sb.append(';');
        method.addBodyLine(sb.toString());
        innerClass.addMethod(method);

        // add constructor initialization
        sb.setLength(0);
        sb.append(field.getName());
        sb.append(" = new ArrayList<Criterion>();");  ;
        constructor.addBodyLine(sb.toString());

        // now add the methods for simplifying the individual field set methods
        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        sb.setLength(0);
        sb.append("add"); 
        sb.append(introspectedColumn.getJavaProperty());
        sb.setCharAt(3, Character.toUpperCase(sb.charAt(3)));
        sb.append("Criterion"); 

        method.setName(sb.toString());
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "condition")); 
        method.addParameter(new Parameter(FullyQualifiedJavaType.getObjectInstance(), "value")); 
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "property")); 
        method.addBodyLine("if (value == null) {"); 
        method.addBodyLine("throw new RuntimeException(\"Value for \" + property + \" cannot be null\");"); 
        method.addBodyLine("}"); 

        method.addBodyLine(String.format("%s.add(new Criterion(condition, value, \"%s\"));", 
                field.getName(), introspectedColumn.getTypeHandler()));
        method.addBodyLine("allCriteria = null;"); 
        innerClass.addMethod(method);

        sb.setLength(0);
        sb.append("add"); 
        sb.append(introspectedColumn.getJavaProperty());
        sb.setCharAt(3, Character.toUpperCase(sb.charAt(3)));
        sb.append("Criterion"); 

        method = new Method();
        method.setVisibility(JavaVisibility.PROTECTED);
        method.setName(sb.toString());
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "condition")); 
        method.addParameter(new Parameter(introspectedColumn.getFullyQualifiedJavaType(), "value1")); 
        method.addParameter(new Parameter(introspectedColumn.getFullyQualifiedJavaType(), "value2")); 
        method.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "property")); 
        if (!introspectedColumn.getFullyQualifiedJavaType().isPrimitive()) {
            method.addBodyLine("if (value1 == null || value2 == null) {"); 
            method.addBodyLine("throw new RuntimeException(\"Between values for \" + property + \" cannot be null\");"); 
            method.addBodyLine("}"); 
        }

        method.addBodyLine(String.format("%s.add(new Criterion(condition, value1, value2, \"%s\"));", 
                field.getName(), introspectedColumn.getTypeHandler()));

        method.addBodyLine("allCriteria = null;"); 
        innerClass.addMethod(method);

        return answer;
    }

    /**
     * @see org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.ExampleWhereClauseElementGenerator
     */
    @Override
    public boolean sqlMapExampleWhereClauseElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {

        element.getElements().clear();

        XmlElement whereElement = new XmlElement("where"); 
        element.addElement(whereElement);

        XmlElement outerForEachElement = new XmlElement("foreach"); 
        outerForEachElement.addAttribute(new Attribute("collection", "oredCriteria"));  
        outerForEachElement.addAttribute(new Attribute("item", "criteria"));  
        outerForEachElement.addAttribute(new Attribute("separator", "or"));  
        whereElement.addElement(outerForEachElement);

        XmlElement ifElement = new XmlElement("if"); 
        ifElement.addAttribute(new Attribute("test", "criteria.valid"));  
        outerForEachElement.addElement(ifElement);

        XmlElement trimElement = new XmlElement("trim"); 
        trimElement.addAttribute(new Attribute("prefix", "("));  
        trimElement.addAttribute(new Attribute("suffix", ")"));  
        trimElement.addAttribute(new Attribute("prefixOverrides", "and"));  

        ifElement.addElement(trimElement);

        trimElement.addElement(getMiddleForEachElement(null));

        return super.sqlMapExampleWhereClauseElementGenerated(element, introspectedTable);
    }

    private XmlElement getMiddleForEachElement(IntrospectedColumn introspectedColumn) {
        StringBuilder sb = new StringBuilder();
        String criteriaAttribute;
        boolean typeHandled;
        String typeHandlerString;
        if (introspectedColumn == null) {
            criteriaAttribute = "criteria.criteria"; 
            typeHandled = false;
            typeHandlerString = null;
        } else {
            sb.setLength(0);
            sb.append("criteria."); 
            sb.append(introspectedColumn.getJavaProperty());
            sb.append("Criteria"); 
            criteriaAttribute = sb.toString();

            typeHandled = true;

            sb.setLength(0);
            sb.append(",typeHandler="); 
            sb.append(introspectedColumn.getTypeHandler());
            typeHandlerString = sb.toString();
        }

        XmlElement middleForEachElement = new XmlElement("foreach"); 
        middleForEachElement.addAttribute(new Attribute("collection", criteriaAttribute)); 
        middleForEachElement.addAttribute(new Attribute("item", "criterion"));  

        XmlElement chooseElement = new XmlElement("choose"); 
        middleForEachElement.addElement(chooseElement);

        XmlElement when = new XmlElement("when"); 
        /*
         * when.addAttribute(new Attribute("test", "criterion.noValue"));   when.addElement(new TextElement("and ${criterion.condition}")); 
         * chooseElement.addElement(when);
         */

        when = new XmlElement("when"); 
        when.addAttribute(new Attribute("test", "criterion.singleValue and !criterion.noValue"));  
        sb.setLength(0);
        sb.append("and ${criterion.condition} #{criterion.value"); 
        if (typeHandled) {
            sb.append(typeHandlerString);
        }
        sb.append('}');
        when.addElement(new TextElement(sb.toString()));
        chooseElement.addElement(when);

        when = new XmlElement("when"); 
        when.addAttribute(new Attribute("test", "criterion.betweenValue"));  
        sb.setLength(0);
        sb.append("and ${criterion.condition} #{criterion.value"); 
        if (typeHandled) {
            sb.append(typeHandlerString);
        }
        sb.append("} and #{criterion.secondValue"); 
        if (typeHandled) {
            sb.append(typeHandlerString);
        }
        sb.append('}');
        when.addElement(new TextElement(sb.toString()));
        chooseElement.addElement(when);

        when = new XmlElement("when"); 
        when.addAttribute(new Attribute("test", "criterion.listValue"));  
        when.addElement(new TextElement("and ${criterion.condition}")); 
        XmlElement innerForEach = new XmlElement("foreach"); 
        innerForEach.addAttribute(new Attribute("collection", "criterion.value"));  
        innerForEach.addAttribute(new Attribute("item", "listItem"));  
        innerForEach.addAttribute(new Attribute("open", "("));  
        innerForEach.addAttribute(new Attribute("close", ")"));  
        innerForEach.addAttribute(new Attribute("separator", ","));  
        sb.setLength(0);
        sb.append("#{listItem"); 
        if (typeHandled) {
            sb.append(typeHandlerString);
        }
        sb.append('}');
        innerForEach.addElement(new TextElement(sb.toString()));
        when.addElement(innerForEach);
        chooseElement.addElement(when);
        
        //再加一个when 解决  where a like '123%' or a like '234%'的语句
        when = new XmlElement("when"); 
        when.addAttribute(new Attribute("test", "criterion.likeListValue"));  
        when.addElement(new TextElement("and"));
        
        
        XmlElement lastForEach = new XmlElement("foreach"); 
        lastForEach.addAttribute(new Attribute("collection", "criterion.value"));  
        lastForEach.addAttribute(new Attribute("item", "listItem"));  
        lastForEach.addAttribute(new Attribute("open", "("));  
        lastForEach.addAttribute(new Attribute("close", ")"));  
        lastForEach.addAttribute(new Attribute("separator", "or"));  
        sb.setLength(0);
        sb.append("${criterion.condition} concat(#{listItem},'%')"); 
        lastForEach.addElement(new TextElement(sb.toString()));
        when.addElement(lastForEach);
        chooseElement.addElement(when);

        return middleForEachElement;
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

}
