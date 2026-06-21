package io.github.ctimetbukii.simpleterminal.exceptions;

public class RepeatArgsCastFatalExecutorException extends RepeatException {
    public RepeatArgsCastFatalExecutorException(Class<?> exceptionCls) {
        super("Repeat ArgsCastFatalExecutor in " + exceptionCls.getName());
    }
}
