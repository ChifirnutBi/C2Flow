package org.example;

import java.io.*;

import org.example.config.Config;
import org.example.config.ConfigManager;

public class Main {

    static String code;

    public static void main(String[] args) {

        FlowchartGenAI flowchartGenAI = FlowchartGenAI.getInstance();
        Config config = Config.getInstance();

        System.out.println("C2Flow 0.0.1");

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

        System.out.println(code);
        System.out.println(flowchartGenAI.generate(config.genAI.model, config.genAI.generateTaskAndSolution + code, config.genAI.config));
        System.out.println(flowchartGenAI.generate(config.genAI.model, config.genAI.generateTableOfVariables + code, config.genAI.config));
        System.out.println(flowchartGenAI.generate(config.genAI.model, config.genAI.generateFunctionSignaturesTable + code, config.genAI.config));
        System.out.println(flowchartGenAI.generate(config.genAI.model, config.genAI.generateMathModel+ code, config.genAI.config));
        System.out.println(flowchartGenAI.generate(config.genAI.model, config.genAI.generateConclusion + code, config.genAI.config));

    }
}