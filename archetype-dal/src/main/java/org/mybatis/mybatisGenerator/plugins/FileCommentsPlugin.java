package org.mybatis.mybatisGenerator.plugins;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.mybatisGenerator.MybatisConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileCommentsPlugin extends PluginAdapter {
    public FileCommentsPlugin() {
    }

    public boolean validate(List<String> warnings) {
        return true;
    }

    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        if (Boolean.parseBoolean(this.properties.getProperty("enable"))) {
            this.addComment(interfaze, "clientGenerated");
        }
        return true;
    }

    public boolean providerGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        if (Boolean.parseBoolean(this.properties.getProperty("enable"))) {
            this.addComment(topLevelClass, "providerGenerated");
        }
        return true;
    }

    /**
     * 实体对象注释
     *
     * @param topLevelClass     the generated base record class
     * @param introspectedTable The class containing information about the table as
     *                          introspected from the database
     * @return
     */
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (Boolean.parseBoolean(this.properties.getProperty("enable"))) {
            this.addComment(topLevelClass, "modelBaseRecordClassGenerated");
        }
        return true;
    }

    /**
     * example对象以及blobs对象注释
     *
     * @param topLevelClass     the generated example class
     * @param introspectedTable The class containing information about the table as
     *                          introspected from the database
     * @return
     */
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (Boolean.parseBoolean(this.properties.getProperty("enable"))) {
            this.addComment(topLevelClass, "modelExampleClassGenerated");
        }
        return true;
    }

    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (Boolean.parseBoolean(this.properties.getProperty("enable"))) {
            this.addComment(topLevelClass, "modelRecordWithBLOBsClassGenerated");
        }
        return true;
    }

    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (Boolean.parseBoolean(this.properties.getProperty("enable"))) {
            this.addComment(topLevelClass, "modelPrimaryKeyClassGenerated");
        }
        return true;
    }

    private void addComment(JavaElement javaElement, String tag) {
        List<String> javaDocLines = javaElement.getJavaDocLines();
        //假如是空的集合就先插入头尾 然后像后面插入
        if (javaDocLines.isEmpty()) {
            javaDocLines.add(MybatisConstants.PREFIX);
            javaDocLines.add(MybatisConstants.SUFFIX);
        }
        int index = javaDocLines.size() - 1;
        javaDocLines.add(index," * " + tag);
        SimpleDateFormat dateFormat = this.getDateFormat();
        Date date = new Date();
        javaDocLines.add(index," * @date " + dateFormat.format(date));
        javaDocLines.add(index," * @author " + this.properties.getProperty("author", "lixin"));
    }

    private SimpleDateFormat getDateFormat() {
        String dateFormatString = this.properties.getProperty("dateFormat");
        return new SimpleDateFormat(StringUtility.stringHasValue(dateFormatString) ? dateFormatString : "yyyy-MM-dd HH:mm:ss");
    }


}
