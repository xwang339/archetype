package org.mybatis.db.sql;

import org.mybatis.db.model.ModeType;
import org.mybatis.db.table.TableSchema;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;


public class SqlExecutor extends SqlBuilder {

    /**
     * 数据库连接对象
     */
    private DataSource dataSource;

    public SqlExecutor(TableSchema tableSchema, ModeType modeType) {
        super(tableSchema, modeType);
    }

    public SqlExecutor(TableSchema tableSchema, ModeType modeType, DataSource dataSource) {
        super(tableSchema, modeType);
        this.dataSource = dataSource;
    }
    public SqlExecutor(TableSchema tableSchema) {
        super(tableSchema);
    }


    public SqlExecutor(TableSchema tableSchema, DataSource dataSource) {
        super(tableSchema);
        this.dataSource = dataSource;
    }


    public void execute(String sql) {
        if (dataSource == null) {
            throw new RuntimeException("dataSource is null");
        }
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 获取执行语句并执行建表语句
     */
    public void executeCreateTableSql() {
        execute(getCreateTableAndCommentSql());
    }

}
