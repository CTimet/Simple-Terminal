package io.github.ctimetbukii.simpleterminal.exceptions;

/**
 * 用于在某些在正常用法上根本不应该走到的分支上的异常
 */
public class IllogicalPanicException extends RuntimeException {
    public IllogicalPanicException(String message) {
        super(message);
    }
}
