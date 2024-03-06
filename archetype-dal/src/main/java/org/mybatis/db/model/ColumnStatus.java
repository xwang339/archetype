package org.mybatis.db.model;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-03-24 11:25:08
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-03-24     张李鑫                     1.0         1.0 Version
 */
public enum ColumnStatus {
    ISNULL(" DEFAULT NULL ", (byte) 1),
    NOTNULL(" NOT NULL ", (byte) 0);

    private final String description;
    private final byte value;

    ColumnStatus(String description, byte value) {
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
