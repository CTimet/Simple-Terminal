package io.github.ctimetbukii.simpleterminal.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记一个方法为某个含上下文的指令的处理器
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContextCommandExecutor {
    /**
     * 第几轮指令输入。0代表初次指令输入。从1开始递增代表上下文输入次数。例如: <br/> <br/>
     * >send 666  <br/>
     * To whom? <br/>
     * >CTimet bukii <br/>
     * Send '666' to CTimet bukii, done. <br/>
     *  <br/>
     *  在这个例子中。send 输入被stage=0的方法截获。而CTimet bukii输入则被stage=1的方法截获。
     *  CTimet bukii是在send这个含有上下文的指令中的第一次上下文输入
     */
    int stage();
}
