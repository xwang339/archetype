package com.lixin.dal.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * Description: 代码生成器
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-07-28 18:29:44
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-07-28     张李鑫                     1.0         1.0 Version
 */
public class CodeGenerator {
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator generator = new AutoGenerator();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(System.getProperty("user.dir") + "/");
        globalConfig.setAuthor("lixin");
        globalConfig.setOpen(false);
        globalConfig.setDateType(DateType.ONLY_DATE); // 只使用日期，不使用时间
        globalConfig.setIdType(IdType.AUTO); // 主键策略，这里使用自动增长

        generator.setGlobalConfig(globalConfig);

        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setController("");
        generator.setTemplate(templateConfig);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/chat");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("10201224w");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");

        generator.setDataSource(dataSourceConfig);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com"); // 设置基础包名
        packageConfig.setModuleName("lixin"); // 设置模块名
        generator.setPackageInfo(packageConfig);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel); // 数据库表名映射到实体的命名策略
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel); // 数据库字段映射到实体的命名策略
        strategyConfig.setEntityLombokModel(true); // 是否为lombok模型
        strategyConfig.setRestControllerStyle(false);
        strategyConfig.setLogicDeleteFieldName("deleted"); // 逻辑删除字段名
        strategyConfig.setVersionFieldName("version"); // 乐观锁字段名
        strategyConfig.setTablePrefix(packageConfig.getModuleName() + "_"); // 设置表名前缀，生成的实体类将去掉前缀

        generator.setStrategy(strategyConfig);

        // 代码生成器执行
        generator.execute();
    }

}
