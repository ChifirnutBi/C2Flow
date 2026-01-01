package org.example;

import java.io.*;

import org.example.config.Config;
import org.example.config.ConfigManager;

public class Main {

    static String code;

    public static void main(String[] args) {

        Config config = Config.getInstance();

        System.out.println("C2Flow 0.0.2");

        try {
            ConfigManager.loadConfig("config/config.json");
        } catch (IOException e) {
            System.out.println("Configuration file not found or corrupted. Trying to create a new one.");
            ConfigManager.createNewConfig();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(config.paths.src));
        StringWriter stringWriter = new StringWriter()) {
            while (reader.ready()) {
                stringWriter.write(reader.readLine() + "\n");
            }
            code = stringWriter.toString();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Runnable r1 = () -> {
            ConcurrentFlowchartGenAI cfgAI = new ConcurrentFlowchartGenAI();
            System.out.println(cfgAI.generateWithRetry(config.genAI.model,
                    config.genAI.generateTaskAndSolution + code,
                    config.genAI.config, 60));
        };
        Runnable r2 = () -> {
            ConcurrentFlowchartGenAI cfgAI = new ConcurrentFlowchartGenAI();
            System.out.println(cfgAI.generateWithRetry(config.genAI.model,
                    config.genAI.generateTableOfVariables + code,
                    config.genAI.config, 60));
        };
        Runnable r3 = () -> {
            ConcurrentFlowchartGenAI cfgAI = new ConcurrentFlowchartGenAI();
            System.out.println(cfgAI.generateWithRetry(config.genAI.model,
                    config.genAI.generateFunctionSignaturesTable + code,
                    config.genAI.config, 60));
        };
        Runnable r4 = new Runnable() {
            @Override
            public void run() {
                ConcurrentFlowchartGenAI cfgAI = new ConcurrentFlowchartGenAI();
                System.out.println(cfgAI.generateWithRetry(config.genAI.model,
                        config.genAI.generateMathModel + code,
                        config.genAI.config, 60));
            }
        };
        Runnable r5 = () -> {
            ConcurrentFlowchartGenAI cfgAI = new ConcurrentFlowchartGenAI();
            System.out.println(cfgAI.generateWithRetry(config.genAI.model,
                    config.genAI.generateConclusion + code,
                    config.genAI.config, 60));
        };

        Thread thread1 = new Thread(r1);
        Thread thread2 = new Thread(r2);
        Thread thread3 = new Thread(r3);
        Thread thread4 = new Thread(r4);
        Thread thread5 = new Thread(r5);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }
}