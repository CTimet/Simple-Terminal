package io.github.ctimetbukii.simpleterminal;

import lombok.Getter;
import io.github.ctimetbukii.simpleterminal.annotations.CommandProcessor;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class Terminal {
    private final TerminalOptions options;
    @Getter
    private Thread asyncThread;

    @Getter
    private Future<?> monitorInputFuture;

    private boolean run = true;

    /*
     * 当前上下文中的指令。未输入带上下文的指令时，该值应当为null。上下文结束时，该值应重新赋值为null
     */
    private String contextCommand = null;

    //命令处理器
    private HashMap<String, Consumer<String[]>> commandExecutors = new HashMap<>();

    public Terminal() {
        this(new TerminalOptions());
    }

    public Terminal(TerminalOptions options) {
        this.options = options;
    }

    /**
     * 启动终端监听
     */
    public void run() {
        //从锚定类路径中查找带有注解标记的方法/类
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages(options.getAnchorPackagePath())
                .setScanners(new TypeAnnotationsScanner(), new MethodAnnotationsScanner()));
        Set<Class<?>> commandProcessors = reflections.getTypesAnnotatedWith(CommandProcessor.class);


        if (options.isRunAsync()) {
            if (options.getRunExecutor() == null) {
                asyncThread = new Thread(this::monitorInput);
                asyncThread.start();
            } else {
                monitorInputFuture = options.getRunExecutor().submit(this::monitorInput);
            }
        } else {
            monitorInput();
        }
    }

    private void monitorInput() {
        try (Scanner scanner = new Scanner(options.getInputStream())){
            while (run && !Thread.currentThread().isInterrupted() && scanner.hasNextLine()) {
                String input = scanner.nextLine();

            }
        }
    }

    public void stop() {
        run = false;
        if (asyncThread != null) {
            asyncThread.interrupt();
        }
        if (monitorInputFuture != null) {
            monitorInputFuture.cancel(true);
        }
    }
}
