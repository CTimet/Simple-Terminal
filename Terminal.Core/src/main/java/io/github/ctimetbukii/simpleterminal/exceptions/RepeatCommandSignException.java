package io.github.ctimetbukii.simpleterminal.exceptions;

/**
 * Executor重复异常。按CommandSign区分CommandExecutor。当一个类中有多个patterns与types都相同的Executor时，抛出此异常
 */
public class RepeatCommandSignException extends RepeatException{
    public RepeatCommandSignException(Class<?> exceptionClass) {
        super("Repeat CommandSign(same patterns and types) in " + exceptionClass.getName());
    }
}
