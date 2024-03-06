package org.mybatis.db.provider;


import org.mybatis.db.table.ColumnWithValue;
import org.mybatis.db.table.TableSchema;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-03-23 17:32:55
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-03-23     张李鑫                     1.0         1.0 Version
 */
public class TableOrFileProvider implements SqlProvider{
    /**
     * 获取插入语句
     *
     * @param tableSchema 表对象
     * @param data        数据对象
     * @return
     */
    protected String getInsertStatement(TableSchema tableSchema, Map<String, Object> data) {

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append(tableSchema.getTableName()).append(" (");

        StringBuilder values = new StringBuilder();
        values.append(" VALUES (");

        tableSchema.getModels().forEach(model -> {
            sql.append(model.getColumn()).append(",");
            values.append(getValueString(data.get(model.getColumn()), model.getType().getValue())).append(",");
        });

        sql.deleteCharAt(sql.length() - 1);
        values.deleteCharAt(values.length() - 1);

        sql.append(")").append(values).append(")");
        return sql.toString();
    }

    /**
     * 获取插入语句
     *
     * @param tableSchema {@link TableSchema}
     * @param list        {@link ColumnWithValue}
     * @return
     */
    protected String getInsertStatement(TableSchema tableSchema, List<ColumnWithValue> list) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append(tableSchema.getTableName()).append(" (");
        StringBuilder values = new StringBuilder();
        values.append(" VALUES (");
        //循环外部设置的key 如果在表中存在 则添加到sql中
        list.stream().filter(model -> tableSchema.getModelSet().contains(model.getSqlModel().getColumn())).forEach(model -> {
            sql.append(model.getSqlModel().getColumn()).append(",");
            values.append(getValueString(model.getValue(), model.getSqlModel().getType().getValue())).append(",");
        });
        sql.deleteCharAt(sql.length() - 1);
        values.deleteCharAt(values.length() - 1);
        sql.append(")").append(values).append(")");
        return sql.toString();
    }

//    protected String getValueString(Object value, byte type) {
//        if (value == null) {
//            return "null";
//        }
//        if (type == (MysqlColumn.CHAR.getValue()) || type == MysqlColumn.VARCHAR.getValue()) {
//            return "'" + value + "'";
//        } else if (type == MysqlColumn.DATETIME.getValue() || type == MysqlColumn.TIMESTAMP.getValue()) {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            return "'" + dateFormat.format(value) + "'";
//        } else {
//            return value.toString();
//        }
//    }
//    /**
//     * 构造List sql insert语句
//     * 由原生的 statement.executeUpdate(sql);来执行这句sql
//     * 场景:一般用于 table || excel 的场景无法建立字段映射
//     *
//     * @param values
//     * @return
//     */
//    public List<SQL> insertSelectiveBatch(List<List<String>> values, TableSchema tableSchema) {
//        List<SQL> resSql = new ArrayList<>();
//        //收集arr[i]所产生的所有sql返回
//        values.forEach(val -> resSql.add(insertSelective(val, tableSchema)));
//        return resSql;
//    }
//    /**
//     * 通过行数据构造当前行的insert语句
//     *
//     * @param val
//     * @return
//     */
//    public SQL insertSelective(List<String> val, TableSchema tableSchema) {
//        //前缀构造
//        SQL sql = sqlPrefix(tableSchema);
//        String sqlStr = val.toString();
//        sql.INTO_VALUES(sqlStr.substring(1, sqlStr.length() - 1));
//        return sql;
//    }
//
//    /**
//     * 转换sql成为String 产生的String 由原生jdbc执行
//     * inset into （a,b,c) values(a,v,c);
//     * inset into （a,b,c) values(a1,v1,c1);
//     *
//     * @param lists
//     * @return
//     */
//    public String sqlList2Str(List<SQL> lists) {
//        StringBuilder sb = new StringBuilder();
////        lists.forEach((sql) -> sb.append(sql).append(ending + lineFeed));
//        return sb.toString();
//    }
//
//    /**
//     * 转换数组成为List
//     *
//     * @param values 数组转换成list
//     * @return
//     */
//    public List<List<String>> array2List(List<String[]> values) {
//        List<List<String>> res = new ArrayList<>();
//        values.forEach((value) -> res.add(List.of(value)));
//        return res;
//    }
//
//
//    /**
//     * 构造前缀
//     */
//    public SQL sqlPrefix(TableSchema tableSchema) {
//        SQL sql = new SQL();
//        sql.INSERT_INTO(tableSchema.getTableName());
//        List<SqlModel> models = tableSchema.getModels();
//        models.forEach((model) -> sql.INTO_COLUMNS(model.getColumn()));
//        return sql;
//    }
//
//    @Override
//    String insertSelective(TableSchema sqlModel) {
//        return null;
//    }
}
