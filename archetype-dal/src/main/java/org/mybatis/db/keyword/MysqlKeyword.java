package org.mybatis.db.keyword;

import java.util.HashSet;
import java.util.Set;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-03-16 16:48:53
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-03-16     张李鑫                     1.0         1.0 Version
 */
public enum MysqlKeyword {
    LIMIT("LIMIT");

    private String description;

    public static Set<String> toSet() {
        HashSet<String> set = new HashSet<String>();
        for (MysqlKeyword tmp : MysqlKeyword.values()) {
            set.add(tmp.description);
        }
        return set;
    }

    private MysqlKeyword(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
