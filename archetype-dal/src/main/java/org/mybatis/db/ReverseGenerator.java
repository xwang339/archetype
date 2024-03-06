package org.mybatis.db;

import org.mybatis.db.model.DbType;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.exception.InvalidConfigurationException;

import java.util.List;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

public class ReverseGenerator {

    /**
     * 沿用mybatis的配置文件
     * 根据mybatis生成的饿文件去做逆向
     */
    private Configuration configuration;


    public ReverseGenerator(Configuration configuration) throws InvalidConfigurationException {
        if (configuration == null) {
            throw new IllegalArgumentException(getString("RuntimeError.2")); //$NON-NLS-1$
        }
        this.configuration = configuration;
        this.configuration.validate();
    }


    public void reverse(boolean writeFiles, DbType dbType){
        List<Context> contextsToRun=configuration.getContexts();
    }

}
