package org.mybatis.mybatisGenerator.annotation;

import java.lang.annotation.*;

/**
 * 生成代码注解
 * 可以用于逆向生成sql文件
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableGeneratorDoc {

    /**
     * 备注
     * @return
     */
    String remark() default "";

    /**
     * 字段名称
     * @return
     */
    String name() default "";

}
