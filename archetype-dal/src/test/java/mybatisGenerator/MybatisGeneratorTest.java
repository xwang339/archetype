package mybatisGenerator;

import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * mybatis 代码生成器 也可以用maven插件的方式去生成代码
 */
public class MybatisGeneratorTest {

    @Test(expected = InvalidConfigurationException.class)
    public void testGenerateMyBatis3WithInvalidConfig() throws Exception {
        List<String> warnings = new ArrayList<String>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(this.getClass().getClassLoader().getResourceAsStream("generatorConfig.xml"));

        DefaultShellCallback shellCallback = new DefaultShellCallback(true);
        try {
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
            myBatisGenerator.generate(null, null, null, true);
            if (!warnings.isEmpty()) {
                for (String warning : warnings) {
                    System.out.println(warning);
                }
            }
        } catch (InvalidConfigurationException e) {
            assertEquals(2, e.getErrors().size());
            throw e;
        }
    }



}
