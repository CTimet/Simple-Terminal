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
     * 是否含有子命令。若该值为true，则patterns与types值被放弃，传入的第一个参数被视为子命令，剩余参数传入子命令的处理器。
     */
    boolean hasSubCommand();
}
