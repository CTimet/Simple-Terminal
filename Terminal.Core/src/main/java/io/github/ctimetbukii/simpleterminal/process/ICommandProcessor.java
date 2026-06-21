package io.github.ctimetbukii.simpleterminal.process;

public interface ICommandProcessor {
    /**
     * 处理命令
     * @param args 传入的命令参数，若当前指令是含上下文的命令，则保护用户的全部输入，若不包含上下文，则不包含顶部命令。 <br/>
     *             例如，执行 mycommand 1 2 3，若该指令hasContext=true，则此处传入{"mycommand","1","2","3"}。
     *             若该指令hasContext=false，则此处传入{"1","2","3"} 。 <br/>
     *             此时子命令被视为命令参数。
     * @param contextStage 上下文阶段。对于没有上下文的指令，该值可以忽略
     */
    public void accept(String[] args, int contextStage);
}
