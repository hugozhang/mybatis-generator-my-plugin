package org.mybatis.generator.my.plugins.v2;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.my.where.Example;

import java.util.List;

public class DynamicQueryExamplePluginV2 extends PluginAdapter {

    /**
     * @see org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.ExampleWhereClauseElementGenerator
     */
    @Override
    public boolean sqlMapExampleWhereClauseElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {

        element.getElements().clear();

        XmlElement whereElement = new XmlElement("where");
        element.addElement(whereElement);

        XmlElement outerForEachElement = new XmlElement("foreach");
        outerForEachElement.addAttribute(new Attribute("collection", "criteriaList"));
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

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        introspectedTable.setExampleType(Example.class.getName());
        return false;
    }

    public static void main(String[] args) {
        System.out.println(Example.class.getName());
    }

}
