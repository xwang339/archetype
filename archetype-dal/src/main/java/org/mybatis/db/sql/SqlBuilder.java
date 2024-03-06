package org.mybatis.db.sql;


import org.mybatis.db.model.ModeType;
import org.mybatis.db.provider.SqlProvider;
import org.mybatis.db.table.SqlModel;
import org.mybatis.db.table.TableSchema;
import org.mybatis.db.util.CreateUtils;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-03-16 16:00:13
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-03-16     张李鑫                     1.0         1.0 Version
 */
public class SqlBuilder {

    /**
     * 换行符号
     */
    final String lineFeed = " \n";


    /**
     * 表结构
     */
    private TableSchema tableSchema;
    /**
     * sql构造器
     */
    private SqlProvider sqlProvider;


    /**
     * 获取创建表语句
     *
     * @return
     */
    public String buildCreateTableSql() {
        return this.tableSchema.buildCreateTableSql();
    }

    /**
     * 创建备注语句
     *
     * @return
     */
    public String getCommentSql() {
        return this.tableSchema.getCommentColumnSql() + this.tableSchema.getCommentTableSql();
    }

    /**
     * 更新单个字段
     *
     * @return
     */
    public String getUpdateColumnDoc(SqlModel sqlModel) {
        return this.tableSchema.getUpdateColumnDocSql(sqlModel);
    }

    /**
     * 更新表备注
     *
     * @return
     */
    public String getUpdateTableDoc(String tableName, String tableDoc) {
        return this.tableSchema.updateOrInsertTableCommentSql(tableName, tableDoc);
    }

    /**
     * 获取建表语句以及备注语句
     *
     * @return
     */
    public String getCreateTableAndCommentSql() {
        return buildCreateTableSql() + lineFeed + getCommentSql();
    }

    public SqlBuilder(TableSchema tableSchema, ModeType modeType) {
        this.tableSchema = tableSchema;
        this.sqlProvider = CreateUtils.builderSqlProvider(modeType);
    }


    public SqlBuilder(TableSchema tableSchema) {
        this.tableSchema = tableSchema;
        this.sqlProvider = CreateUtils.builderSqlProvider(ModeType.BEAN);
    }

    public SqlProvider getSqlProvider() {
        return sqlProvider;
    }

    public void setSqlProvider(SqlProvider sqlProvider) {
        this.sqlProvider = sqlProvider;
    }


    public TableSchema getTable() {
        return tableSchema;
    }

    public void setTable(TableSchema tableSchema) {
        this.tableSchema = tableSchema;
    }

}
