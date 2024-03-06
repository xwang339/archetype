package org.mybatis.db.table;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Description: table 的描述信息 table由每一个列信息组成
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-03-16 16:30:42
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-03-16     张李鑫                     1.0         1.0 Version
 */
public abstract class TableSchema {

    /**
     * 关键字
     */
    private Set<String> keywords;
    /**
     * 表名称
     */
    private String tableName;
    /**
     * 表单描述
     */
    private String tableDesc;
    /**
     * 表字段
     */
    private List<SqlModel> models;
    /**
     * 表拓展字段
     */
    private List<SqlModel> extraModel;
    /**
     * 当前表的主键
     */
    private SqlModel primaryKey;
    /**
     * 是否已经合并数据
     */
    private Boolean isMerge;

    private String engine;
    private String character;

    private HashSet<String> modelSet;

    public TableSchema(List<SqlModel> models, String tableName) {
        this(models, tableName, "");
    }

    public TableSchema(List<SqlModel> models, String tableName, String tableDesc) {
        Objects.requireNonNull(models, "models cannot be null");
        Objects.requireNonNull(tableName, "tableName cannot be null");
        //默认引擎
        this.engine = "InnoDB ";
        //默认字符集
        this.character = "DEFAULT CHARSET=utf8;";
        this.isMerge = false;
        this.models = models;
        this.tableDesc = tableDesc;
        this.tableName = tableName;
        this.modelSet = new HashSet<>();
        //设置字段名称
        this.models.forEach(model -> modelSet.add(model.getColumn()));
        //设置主键
        for (SqlModel model : models) {
            if (model.getPrimaryKey().isPrimaryKey()) {
                setPrimaryKey(model);
                return;
            }
        }
    }


    public HashSet<String> getModelSet() {
        return modelSet;
    }

    public void setModelSet(HashSet<String> modelSet) {
        this.modelSet = modelSet;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 获取当前表的模型 如果没有合并过 先合并在返回
     *
     * @return
     */
    public List<SqlModel> getModels() {
        if (isMerge) {
            mergeModel();
        }
        return models;
    }

    /**
     * 合并表单字段以及逻辑
     */
    public void mergeModel() {
        models.addAll(extraModel);
        isMerge = true;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }

    public void setModels(List<SqlModel> models) {
        isMerge = false;
        this.models = models;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }

    public SqlModel getPrimaryKey() {
        return primaryKey;
    }

    public List<SqlModel> getExtraModel() {
        return extraModel;
    }

    public void setExtraModel(List<SqlModel> extraModel) {
        isMerge = false;
        this.extraModel = extraModel;
    }

    public Boolean getMerge() {
        return isMerge;
    }

    public void setMerge(Boolean merge) {
        isMerge = merge;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setPrimaryKey(SqlModel primaryKey) {
        this.primaryKey = primaryKey;
    }


    /**
     * 获取创建表语句
     *
     * @return
     */
    public abstract String buildCreateTableSql();

    /**
     * 创建备注语句
     *
     * @return
     */
    public abstract String getCommentTableSql();

    public abstract String updateOrInsertTableCommentSql(String tableName, String tableDesc);

    /**
     * 获取字段的备注sql
     *
     * @return
     */
    public abstract String getCommentColumnSql();

    /**
     * 更新字段sql
     *
     * @param sqlModel
     * @return
     */
    public abstract String getUpdateColumnDocSql(SqlModel sqlModel);

}
