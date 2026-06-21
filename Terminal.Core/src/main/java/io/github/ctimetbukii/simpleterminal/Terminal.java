package io.github.ctimetbukii.simpleterminal;

import io.github.ctimetbukii.simpleterminal.annotations.ArgsCastFatalExecutor;
import io.github.ctimetbukii.simpleterminal.annotations.ArgsPatternsIllegalExecutor;
import io.github.ctimetbukii.simpleterminal.annotations.CommandExecutor;
import io.github.ctimetbukii.simpleterminal.exceptions.RepeatArgsCastFatalExecutorException;
import io.github.ctimetbukii.simpleterminal.exceptions.RepeatArgsPatternsIllegalExecutor;
import io.github.ctimetbukii.simpleterminal.exceptions.RepeatCommandExecutorException;
import io.github.ctimetbukii.simpleterminal.process.NormalExecutor;
import io.github.ctimetbukii.simpleterminal.process.PatternsIllegalExecutor;
import io.github.ctimetbukii.simpleterminal.process.TypesIllegalExecutor;
import lombok.Getter;
import io.github.ctimetbukii.simpleterminal.annotations.CommandProcessor;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Future;

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
    private HashMap<String, NormalExecutor> commandExecutors = new HashMap<>();

    //参数格式错误的命令处理器
    private HashMap<String, PatternsIllegalExecutor> patternsIllegalExecutors = new HashMap<>();

    //参数类型错误的命令处理器
    private HashMap<String, TypesIllegalExecutor> typesIllegalExecutors = new HashMap<>();

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

        //从这些类中扫描CommandExecutor
        commandProcessors.forEach(cls -> {
            String command = cls.getAnnotation(CommandProcessor.class).value();
            Method[] methods = cls.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(CommandExecutor.class)) {
                    //注册该类的命令处理器。
                    if (commandExecutors.containsKey(command)) {
                        //命令处理器已经被注册过，说明是重复注册，代表一个类中注册了两个CommandExecutor，抛出异常
                        throw new RepeatCommandExecutorException(cls);
                    }
                    commandExecutors.put(command, new NormalExecutor(method));
                    continue;
                }
                if (method.isAnnotationPresent(ArgsCastFatalExecutor.class)) {
                    if (typesIllegalExecutors.containsKey(command)) {
                        throw new RepeatArgsCastFatalExecutorException(cls);
                    }
                    typesIllegalExecutors.put(command, new TypesIllegalExecutor(method));
                    continue;
                }
                if (method.isAnnotationPresent(ArgsPatternsIllegalExecutor.class)) {
                    if (patternsIllegalExecutors.containsKey(command)) {
                        throw new RepeatArgsPatternsIllegalExecutor(cls);
                    }
                    patternsIllegalExecutors.put(command, new PatternsIllegalExecutor(method));
                    //continue; //no need to continue
                }
            }
        });


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
                String[] inputs = scanner.nextLine().split(" ");
                //拿到顶部命令
                String topCommand = inputs[0];
                //检查命令是否存在
                if (commandExecutors.containsKey(topCommand)) {
                    //命令存在，把参数传入commandExecutor处理
                    commandExecutors.get(topCommand).accept(Arrays.copyOfRange(inputs, 1, inputs.length));
                } else {
                    //命令不存在，传入commandDoesn'tExistProcessor处理
                    options.getCommandDoesntExistProcessor().accept(topCommand);
                }
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
