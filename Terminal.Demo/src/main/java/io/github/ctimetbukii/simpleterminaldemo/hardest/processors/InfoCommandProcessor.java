package io.github.ctimetbukii.simpleterminaldemo.hardest.processors;

import io.github.ctimetbukii.simpleterminal.annotations.CommandExecutor;
import io.github.ctimetbukii.simpleterminal.annotations.CommandProcessor;
import io.github.ctimetbukii.simpleterminal.annotations.SubCommandExecutor;

@CommandProcessor("info")
public class InfoCommandProcessor {
    @CommandExecutor
    public void info() {

    }

    @SubCommandExecutor("cpu")
    public void infoCpu() {

    }
}
