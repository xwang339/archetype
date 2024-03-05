package org.mybatis.mybatisGenerator.internal;

import com.lixin.utils.constants.Constants;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.mybatisGenerator.MybatisConstants;

import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * 修改mybatis 部分生成的代码增加部分信息
 * todo:通过生成的信息逆向生成库表
 * todo:日期相关的后期可以尝试做生成追加
 */
public class ArchetypeGenerator extends DefaultCommentGenerator {

    private boolean suppressAllComments;

    /**
     * If suppressAllComments is true, this option is ignored.
     */
    private boolean addRemarkComments;
    private boolean useLombok;


    public ArchetypeGenerator() {
        super();
    }

    /**
     * 字段注释
     *
     * @param field              the field
     * @param introspectedTable  the introspected table
     * @param introspectedColumn the introspected column
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (this.suppressAllComments) {
            return;
        }

        field.addJavaDocLine(MybatisConstants.PREFIX); //$NON-NLS-1$

        String remarks = introspectedColumn.getRemarks();
        if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
            field.addJavaDocLine(" * Database Column Remarks:" + remarks); //$NON-NLS-1$
        }
        field.addJavaDocLine(" * " + introspectedColumn);
        field.addJavaDocLine(" * this column typeName: " + introspectedColumn.getJdbcTypeName());
        addJavadocTag(field, false);
        field.addJavaDocLine(MybatisConstants.SUFFIX); //$NON-NLS-1$
    }

    /**
     * @param properties All properties from the configuration
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        suppressAllComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));

        addRemarkComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));
        super.addConfigurationProperties(properties);
    }

    /**
     * 生成的example的字段注释
     * 就添加日期足够了
     *
     * @param field             the field
     * @param introspectedTable the introspected table
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        field.addJavaDocLine(MybatisConstants.PREFIX); //$NON-NLS-1$
        addJavadocTag(field, false);
        field.addJavaDocLine(MybatisConstants.SUFFIX); //$NON-NLS-1$
    }


    /**
     * 模型class的注释
     *
     * @param topLevelClass     the top level class
     * @param introspectedTable the introspected table
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments || !addRemarkComments) {
            return;
        }
        topLevelClass.addJavaDocLine(MybatisConstants.PREFIX); //$NON-NLS-1$
        String remarks = introspectedTable.getRemarks();
        if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
            topLevelClass.addJavaDocLine(" * Database Table Remarks:" + remarks); //$NON-NLS-1$
        }
        topLevelClass.addJavaDocLine(" * table name:" + introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(MybatisConstants.SUFFIX);
    }

    /**
     * example mapper 生成的注释
     *
     * @param method            the method
     * @param introspectedTable the introspected table
     */
    @Override
    public void addGeneralMethodComment(Method method,
                                        IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        method.addJavaDocLine(MybatisConstants.PREFIX); //$NON-NLS-1$
        addJavadocTag(method, false);
        method.addJavaDocLine(MybatisConstants.SUFFIX); //$NON-NLS-1$
    }

    /**
     * xml mapper注释 标识生成日期
     *
     * @param xmlElement the xml element
     */
    @Override
    public void addComment(XmlElement xmlElement) {
        if (suppressAllComments) {
            return;
        }

        xmlElement.addElement(new TextElement("<!--")); //$NON-NLS-1$

        StringBuilder sb = new StringBuilder();
        sb.append("  WARNING - "); //$NON-NLS-1$
        sb.append(MergeConstants.NEW_ELEMENT_TAG);

        String s = getDateString();
        if (s != null) {
            sb.setLength(0);
            sb.append("  This element was generated on "); //$NON-NLS-1$
            sb.append(s);
            sb.append('.');
            xmlElement.addElement(new TextElement(sb.toString()));
        }
        xmlElement.addElement(new TextElement("-->")); //$NON-NLS-1$
    }


    /**
     * GeneratedCriteria 类注释
     *
     * @param innerClass        the inner class
     * @param introspectedTable the introspected table
     */
    @Override
    public void addClassComment(InnerClass innerClass,
                                IntrospectedTable introspectedTable) {
        super.addClassComment(innerClass, introspectedTable);
    }

    @Override
    public void addGetterComment(Method method,
                                 IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        if (suppressAllComments || useLombok) {
            return;
        }
        super.addGetterComment(method, introspectedTable, introspectedColumn);
    }


}
