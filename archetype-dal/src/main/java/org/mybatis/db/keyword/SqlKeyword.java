package org.mybatis.db.keyword;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-04-03 13:19:50
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-04-03     张李鑫                     1.0         1.0 Version
 */
public enum SqlKeyword {
    SET("SET"),
    INSERT_KEYWORD(" INSERT INTO "),
    UPDATE(" update "),
    VALUES("VALUES ");

    private final String description;

    private SqlKeyword(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}