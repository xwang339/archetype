package org.mybatis.db.table;

import org.mybatis.db.model.MysqlColumn;
import org.mybatis.db.model.PrimaryKey;

import java.util.List;

/**
 * Description: 单列字段的描述类
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-03-16 16:26:14
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-03-16     张李鑫                     1.0         1.0 Version
 */
public class SqlModel {

    /**
     * 字段名称
     */
    private String column;

    /**
     * 描述
     */
    private String comment;
    /**
     * 字段类型
     */
    private MysqlColumn type;

    /**
     * 字段长度
     */
    private int length;

    private boolean isNull;

    /**
     * 是否是主键
     */
    private PrimaryKey primaryKey;


    public SqlModel() {
    }

    public SqlModel(String column, String comment, Byte type, Integer length, boolean isNull, boolean isPrimaryKey, boolean isAuto) {
        this.column = column;
        this.comment = comment;
        setType(type);
        this.length = length;
        this.isNull = isNull;
        setPrimaryKey(isPrimaryKey, isAuto);

    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public MysqlColumn getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = MysqlColumn.enumMap.get(type);
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public boolean isNull() {
        return isNull;
    }

    public void setNull(boolean aNull) {
        isNull = aNull;
    }

    public PrimaryKey getPrimaryKey() {
        return primaryKey;
    }

    /**
     * 如果设置了主键必须设置主键不是null
     *
     * @param primaryKey 是否是主键
     */
    public void setPrimaryKey(boolean primaryKey, boolean isAuto) {
        List<PrimaryKey> primaryKeys = PrimaryKey.toList();
        for (PrimaryKey key : primaryKeys) {
            if (key.isAutoIncrement() == isAuto && key.isPrimaryKey() == primaryKey) {
                this.primaryKey = key;
                return;
            }
        }
        throw new RuntimeException("params is error");
    }
}
