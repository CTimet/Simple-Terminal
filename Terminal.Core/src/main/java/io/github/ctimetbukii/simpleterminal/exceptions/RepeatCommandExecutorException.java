package io.github.ctimetbukii.simpleterminal.exceptions;

/**
 * CommandExecutor重复异常。一个类中只能至多注册一个CommandExecutor。当注册多个时，抛出此异常
 */
public class RepeatCommandExecutorException extends RepeatException{
    public RepeatCommandExecutorException(Class<?> exceptionClass) {
        super("Repeat CommandExecutor in " + exceptionClass.getName());
    }
}
