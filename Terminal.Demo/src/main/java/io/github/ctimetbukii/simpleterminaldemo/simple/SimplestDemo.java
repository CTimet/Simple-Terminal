package io.github.ctimetbukii.simpleterminaldemo.simple;

import io.github.ctimetbukii.simpleterminal.Terminal;
import io.github.ctimetbukii.simpleterminal.TerminalBuilder;

public class SimplestDemo {
    public static void main(String[] args) {
        Terminal terminal = TerminalBuilder.builder().build();
        terminal.run();
    }
}
