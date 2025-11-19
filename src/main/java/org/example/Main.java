package org.example;

import java.io.*;

import org.example.config.Config;
import org.example.config.ConfigManager;

public class Main {

    static String code;

    public static void main(String[] args) {

        FlowchartGenAI flowchartGenAI = FlowchartGenAI.getInstance();
        Config config = Config.getInstance();

        try {
            ConfigManager.loadConfig("config/config.json");
        } catch (IOException e) {
            System.out.println("Configuration file not found or corrupted. Trying a new one.");
            ConfigManager.createNewConfig();
        }


        System.out.println("C2Flow 0.0.1");

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

        System.out.println(code);
        System.out.println(flowchartGenAI.generate(config.genAI.model, config.genAI.generateTaskAndSolution + code, config.genAI.config));
        System.out.println(flowchartGenAI.generate(config.genAI.model, config.genAI.generateTableOfVariables + code, config.genAI.config));
    }
}