package org.mybatis.db.table;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-04-03 15:48:02
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-04-03     张李鑫                     1.0         1.0 Version
 */
public class ColumnWithValue {
    private String value;
    private SqlModel sqlModel;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SqlModel getSqlModel() {
        return sqlModel;
    }

    public void setSqlModel(SqlModel sqlModel) {
        this.sqlModel = sqlModel;
    }
}
