package org.mybatis.db.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-03-27 16:13:32
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-03-27     张李鑫                     1.0         1.0 Version
 */
public enum PrimaryKey {

    IS_PRIMARY_KEY_NOT_AUTOINCREMENT(true, false),
    IS_PRIMARY_KEY_AUTOINCREMENT(true, true),
    NOT_PRIMARYKEY(false, false);

    private final boolean isPrimaryKey;
    private final boolean isAutoIncrement;


    private PrimaryKey(boolean isPrimaryKey, boolean isAutoIncrement) {
        this.isPrimaryKey = isPrimaryKey;
        this.isAutoIncrement = isAutoIncrement;
    }

    public static List<PrimaryKey> toList() {
        return new ArrayList<>(Arrays.asList(PrimaryKey.values()));
    }


    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }
}
