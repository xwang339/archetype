package org.mybatis.db.table;



import org.mybatis.db.keyword.PostGreSqlKeyword;

import java.util.List;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-03-16 16:40:32
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-03-16     张李鑫                     1.0         1.0 Version
 */
public class PostgreSqlTableSchema extends TableSchema {

    /**
     * 前缀
     */
    public final  String prefix = "CREATE TABLE IF NOT EXISTS ";
    /**
     * 时间后缀
     */
    public final  String dateOrderFix = " timestamp without time zone DEFAULT CURRENT_TIMESTAMP \n ";
    /**
     * text后缀
     */
    public final  String textOrderFix = " text ";
    /**
     * 描述的前缀
     */
    public final  String commentOrderFix = " COMMENT ON COLUMN ";

//    @Override
    public String builderCreateSql() {
        StringBuilder comment = new StringBuilder();
        StringBuilder column = new StringBuilder();
        column.append(prefix);
        column.append(this.getTableName());
        List<SqlModel> models = this.getModels();
        models.forEach((model) -> {});
        return column.append("\n").append(comment).toString();
    }

    public PostgreSqlTableSchema(List<SqlModel> models, String tableName) {
        super(models, tableName);
        this.setKeywords(PostGreSqlKeyword.toSet());
    }

    @Override
    public String buildCreateTableSql() {
        return null;
    }

    @Override
    public String getCommentTableSql() {
        return null;
    }

    @Override
    public String updateOrInsertTableCommentSql(String tableName, String tableDesc) {
        return null;
    }

    @Override
    public String getCommentColumnSql() {
        return null;
    }

    @Override
    public String getUpdateColumnDocSql(SqlModel sqlModel) {
        return null;
    }


    public PostgreSqlTableSchema(List<SqlModel> models, String tableName, String doc) {
        super(models, tableName,doc);
        this.setKeywords(PostGreSqlKeyword.toSet());
    }

}
