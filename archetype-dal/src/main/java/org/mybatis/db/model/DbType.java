package org.mybatis.db.model;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-03-16 15:57:40
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-03-16     张李鑫                     1.0         1.0 Version
 */
public enum DbType {
    MYSQL("mysql",(byte)1),
    POSTGRESQL("postgresql",(byte)0);

    private final String description;
    private final byte value;

    DbType(String description, byte value) {
        this.description = description;
        this.value = value;
    }
    public String getDescription() {
        return description;
    }

    public byte getValue() {
        return value;
    }
}
