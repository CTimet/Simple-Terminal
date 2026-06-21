package io.github.ctimetbukii.simpleterminal.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记一个类为某个指令的处理器。被标记的类中必须含有且仅有一个被CommandExecutor注解标记的方法。
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandProcessor {
    /**
     * 命令
     */
    String value();

    /**
     * 同时匹配大写指令和小写指令。默认为true。 <br/>
     * 例如，该值为true时，mycommand，MYCOMMAND，MyCommand，mYCOMMand等都会被@CommandProcessor("mycommand")匹配。
     * 为false时则只有mycommand能被匹配
     */
    boolean matchUpperAndLowerCase() default true;
}
