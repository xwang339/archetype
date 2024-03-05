package org.mybatis.mybatisGenerator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.mybatisGenerator.MybatisConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AnnotationCommentsPlugin extends PluginAdapter {
    public AnnotationCommentsPlugin() {
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
            String annotation = "@ColumnGeneratorDoc(typeName = \"%s\"," +
                    "name = \"%s\", " +
                    "jdbcType = %d, " +
                    "isNull = %b, " +
                    "length = %d, " +
                    "Scale = %d, " +
                    "Identity = %b, " +
                    "type = 1, " +
                    "remark = \"%s\")";

            field.addJavaDocLine(
                    String.format(
                            annotation,
                            introspectedColumn.getJdbcTypeName(),
                            introspectedColumn.getActualColumnName(),
                            introspectedColumn.getJdbcType(),
                            introspectedColumn.isNullable(),
                            introspectedColumn.getLength(),
                            introspectedColumn.getScale(),
                            introspectedColumn.isIdentity(),
                            introspectedColumn.getRemarks()
                    ));
        }
        return true;
    }

}
