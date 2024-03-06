package org.mybatis.mybatisGenerator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.mybatisGenerator.MybatisConstants;
import org.mybatis.mybatisGenerator.annotation.TableGeneratorDoc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class AnnotationCommentsPlugin extends PluginAdapter {
    private final FullyQualifiedJavaType TableGeneratorDoc;
    private final FullyQualifiedJavaType ColumnGeneratorDoc;
    public AnnotationCommentsPlugin() {
        TableGeneratorDoc = new FullyQualifiedJavaType("org.mybatis.mybatisGenerator.annotation.TableGeneratorDoc");
        ColumnGeneratorDoc = new FullyQualifiedJavaType("org.mybatis.mybatisGenerator.annotation.ColumnGeneratorDoc"); //$NON-NLS-1$
    }

    public boolean validate(List<String> warnings) {
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
            String annotation = "@TableGeneratorDoc(remark = \"%s\",name = \"%s\")";
            topLevelClass.addImportedType(TableGeneratorDoc);
            topLevelClass.addJavaDocLine(String.format(annotation, introspectedTable.getRemarks(), introspectedTable.getFullyQualifiedTable()));
        }
        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field,
                                       TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable,
                                       Plugin.ModelClassType modelClassType) {

        if (Boolean.parseBoolean(this.properties.getProperty("enable"))) {
            List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
            boolean isPrimaryKey = primaryKeyColumns.contains(introspectedColumn);

            String format = buildBaseAnnotation(introspectedColumn);
            //添加主键相关注释
            if (primaryKeyColumns.contains(introspectedColumn)) {
                format += ",isPrimaryKey = %b,isAuto = %b";
                format = String.format(format, isPrimaryKey, introspectedColumn.isAutoIncrement());

            }
            format+=")";
            field.addJavaDocLine(format);
            topLevelClass.addImportedType(ColumnGeneratorDoc);
        }
        return true;
    }

    /**
     * 构造基础的注解
     * @param introspectedColumn
     * @return
     */
    private static String buildBaseAnnotation(IntrospectedColumn introspectedColumn) {
        String annotation = "@ColumnGeneratorDoc(typeName = \"%s\"," +
                "name = \"%s\", " +
                "jdbcType = %d, " +
                "isNull = %b, " +
                "length = %d, " +
                "Scale = %d, " +
                "Identity = %b, " +
                "type = 1, " +
                "remark = \"%s\"";

        return  String.format(
                annotation,
                introspectedColumn.getJdbcTypeName(),
                introspectedColumn.getActualColumnName(),
                introspectedColumn.getJdbcType(),
                introspectedColumn.isNullable(),
                introspectedColumn.getLength(),
                introspectedColumn.getScale(),
                introspectedColumn.isIdentity(),
                introspectedColumn.getRemarks()
        );
    }

}
