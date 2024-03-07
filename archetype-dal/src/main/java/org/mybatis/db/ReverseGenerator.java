package org.mybatis.db;

import org.mybatis.db.model.DbType;
import org.mybatis.db.sql.SqlBuilder;
import org.mybatis.db.table.SqlModel;
import org.mybatis.db.table.TableSchema;
import org.mybatis.db.util.CreateUtils;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.mybatisGenerator.annotation.ColumnGeneratorDoc;
import org.mybatis.mybatisGenerator.annotation.TableGeneratorDoc;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

public class ReverseGenerator {

    /**
     * 沿用mybatis的配置文件
     * 根据mybatis生成的饿文件去做逆向
     */
    private Configuration configuration;

    private ShellCallback shellCallback;

    private List<String> classList;

    private final HashMap<TableGeneratorDoc, List<ColumnGeneratorDoc>> annotationHashMap;


    public ReverseGenerator(Configuration configuration) throws InvalidConfigurationException {
        if (configuration == null) {
            throw new IllegalArgumentException(getString("RuntimeError.2")); //$NON-NLS-1$
        }
        this.shellCallback = new DefaultShellCallback(false);
        this.configuration = configuration;
        this.configuration.validate();
        this.classList = new ArrayList<>();
        this.annotationHashMap = new HashMap<>();
    }


    public List<String> reverse(boolean writeFiles, DbType dbType) throws Exception {
        List<Context> contextsToRun = configuration.getContexts();
        getClassPath(contextsToRun);
        collectMetadata();
        List<TableSchema> tableSchemas = convert(dbType);
        List<String> result = new ArrayList<>();
        tableSchemas.forEach((tableSchema) -> result.add( new SqlBuilder(tableSchema).getCreateTableAndCommentSql()));
        if (writeFiles) {
            //todo
        }
        return result;
    }

    /**
     * 把注解信息转换成class
     */
    private List<TableSchema> convert(DbType dbType) {
        List<TableSchema> tableSchemas = new ArrayList<>();
        annotationHashMap.forEach((tableGeneratorDoc, columnGeneratorDocs) -> {
            List<SqlModel> sqlModels = columnGeneratorDocs.stream().map(this::buildSqlModel).collect(Collectors.toList());
            tableSchemas.add(CreateUtils.createTable(dbType, sqlModels, tableGeneratorDoc.name(), tableGeneratorDoc.remark()));
        });
        return tableSchemas;
    }

    private SqlModel buildSqlModel(ColumnGeneratorDoc column) {
        if (column == null) {
            return null;
        }
        return new SqlModel(column.name(),
                column.remark(),
                column.jdbcType(),
                column.length(),
                column.isNull(),
                column.isPrimaryKey(),
                column.isAuto()
        );
    }

    /**
     * 获取配置文件路径配置信息 拼接成class path
     *
     * @param contextsToRun
     * @throws ShellException
     */
    private void getClassPath(List<Context> contextsToRun) throws ShellException {
        for (Context context : contextsToRun) {
            JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = context.getJavaModelGeneratorConfiguration();
            File directory = shellCallback.getDirectory(javaModelGeneratorConfiguration.getTargetProject(), javaModelGeneratorConfiguration.getTargetPackage());
            File[] files = directory.listFiles();
            if (files == null || files.length == 0) {
                break;
            }
            for (File file : files) {
                classList.add(javaModelGeneratorConfiguration.getTargetPackage() + "." + file.getName().replace(".java", ""));
            }
        }
    }

    /**
     * 收集元数据信息
     * 注解信息包含表信息 字段信息
     *
     * @throws ClassNotFoundException
     */
    private void collectMetadata() throws ClassNotFoundException {
        for (String path : classList) {
            Class<?> aClass = Class.forName(path);
            TableGeneratorDoc tableGeneratorDoc = aClass.getAnnotation(TableGeneratorDoc.class);
            if (tableGeneratorDoc != null) {
                annotationHashMap.computeIfAbsent(tableGeneratorDoc, (k) -> new ArrayList<>());
                Arrays.stream(aClass.getDeclaredFields()).forEach((field) -> {
                    ColumnGeneratorDoc annotations = field.getAnnotation(ColumnGeneratorDoc.class);
                    if (annotations != null) {
                        annotationHashMap.get(tableGeneratorDoc).add(annotations);
                    }
                });
            }
        }
    }


}
