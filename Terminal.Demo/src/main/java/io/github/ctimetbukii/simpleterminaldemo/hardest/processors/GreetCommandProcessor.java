package io.github.ctimetbukii.simpleterminaldemo.hardest.processors;

import io.github.ctimetbukii.simpleterminal.annotations.CommandExecutor;
import io.github.ctimetbukii.simpleterminal.annotations.CommandProcessor;

@CommandProcessor("greet")
public class GreetCommandProcessor {
    @CommandExecutor(types = {String.class})
    public void greet(String user) {
        System.out.println("Hello, " + user);
    }
}
