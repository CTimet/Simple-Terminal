package io.github.ctimetbukii.simpleterminaldemo.hardest.processors;

import io.github.ctimetbukii.simpleterminal.annotations.CommandExecutor;
import io.github.ctimetbukii.simpleterminal.annotations.CommandProcessor;
import io.github.ctimetbukii.simpleterminal.annotations.ContextCommandExecutor;



@CommandProcessor(value = "send", matchUpperAndLowerCase = false, hasContext = true)
public class SendContextCommandProcessor {
    @CommandExecutor
    public void send(String content) {
        System.out.println("Send '" + content + "' to whom?");
    }

    /*
     *
     */
    @ContextCommandExecutor(stage = 1)
    public void acceptUser(String user) {
        System.out.println("Send to '" + user + "' done.");
        System.out.println("Your feeling rate: ");
    }

    @ContextCommandExecutor(stage = 2)
    public void acceptUserFeelingRate(int rate) {

    }
}
