package org.mybatis.db.provider;


import org.mybatis.db.model.MysqlColumn;

import java.text.SimpleDateFormat;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-03-23 17:07:40
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-03-23     张李鑫                     1.0         1.0 Version
 */
public interface  SqlProvider {

    /**
     * 获取值的方法
     * @param value 值
     * @param type 数据类型
     * @return
     */
    default String getValueString(Object value, int type) {
        if (type == (MysqlColumn.CHAR.getValue()) || type == MysqlColumn.VARCHAR.getValue()) {
            return "'" + value + "'";
        } else if (type == MysqlColumn.TIMESTAMP.getValue()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return "'" + dateFormat.format(value) + "'";
        } else {
            return value.toString();
        }
    }

}
