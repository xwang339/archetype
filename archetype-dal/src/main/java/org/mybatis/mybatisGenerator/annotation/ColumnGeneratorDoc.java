package org.mybatis.mybatisGenerator.annotation;

import java.lang.annotation.*;

/**
 * 生成代码注解
 * 可以用于逆向生成sql文件
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface ColumnGeneratorDoc {

    /**
     * 备注
     * @return
     */
    String remark() default "";

    boolean isPrimaryKey() default false;

    boolean isAuto() default  false;

    /**
     * 字段名称
     * @return
     */
    String name() default "";

    /**
     * 字段类型名称
     * @return
     */
    String typeName() default "";

    /**
     * 字段类型枚举 todo:没有整理源码每个枚举分别代表什么 先简单的用name逆向
     * @return
     */
    int jdbcType();

    /**
     * 字段是否可以为空
     * @return
     */
    boolean isNull();

    /**
     * 注解的类型 1字段注解 2类注解
     * @return
     */
    int type();

    /**
     * 字段长度
     * @return
     */
    int length();

    /**
     *  不知道是什么 从introspectedColumn获取到的下面两个属性 暂时先留着后续可能有用
     * @return
     */
    int Scale();

    boolean Identity();


}
