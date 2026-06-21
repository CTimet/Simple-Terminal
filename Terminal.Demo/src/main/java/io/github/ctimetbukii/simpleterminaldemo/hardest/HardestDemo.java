package io.github.ctimetbukii.simpleterminaldemo.hardest;

import io.github.ctimetbukii.simpleterminal.Terminal;
import io.github.ctimetbukii.simpleterminal.TerminalBuilder;

public class HardestDemo {
    public static void main(String[] args) {
        Terminal terminal = TerminalBuilder.builder()
                .build();
        terminal.run();
    }
}
