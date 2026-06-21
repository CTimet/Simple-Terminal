package io.github.ctimetbukii.simpleterminal.exceptions;

public class RepeatArgsPatternsIllegalExecutor extends RuntimeException {
    public RepeatArgsPatternsIllegalExecutor(Class<?> excetpionCls) {
        super("Repeat ArgsPatternsIllegalExecutor in " + excetpionCls.getName());
    }
}
