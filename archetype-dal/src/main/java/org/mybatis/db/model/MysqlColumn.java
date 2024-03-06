package org.mybatis.db.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-03-23 15:56:26
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-03-23     张李鑫                     1.0         1.0 Version
 */
public enum MysqlColumn {
    TEXT("text(%s)", (byte) 1, true),
    INTEGER("int(%s)", (byte) 2, true),
    TIMESTAMP("timestamp", (byte) 3, false),
    DATETIME("datetime", (byte) 6, false),
    CHAR("CHAR(%s)", (byte) 5, true),
    VARCHAR("VARCHAR(%s)", (byte) 4, true);


    private final String suffix;
    private final byte value;
    private final boolean hasSuffix;

    public static final Map<Byte, MysqlColumn> enumMap = toMap();

    private MysqlColumn(String suffix, byte value, boolean hasSuffix) {
        this.suffix = suffix;
        this.value = value;
        this.hasSuffix = hasSuffix;
    }

    private static Map<Byte, MysqlColumn> toMap() {
        Map<Byte, MysqlColumn> statusMap = new LinkedHashMap<Byte, MysqlColumn>();
        for (MysqlColumn tmp : MysqlColumn.values()) {
            statusMap.put(tmp.value, tmp);
        }
        return statusMap;
    }

    public String getSuffix() {
        return suffix;
    }

    public byte getValue() {
        return value;
    }

    public boolean isHasSuffix() {
        return hasSuffix;
    }
}
