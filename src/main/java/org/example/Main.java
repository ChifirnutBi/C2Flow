package org.example;

import java.io.*;
import org.apache.poi.xwpf.usermodel.*;
import org.example.config.Config;
import org.example.config.ConfigLoader;

public class Main {

    static String code;


    public static void main(String[] args) {

        FlowchartGenAI flowchartGenAI = FlowchartGenAI.getInstance();
        Config config;

        try {
            config = ConfigLoader.loadConfig("config/config.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
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

        System.out.println(flowchartGenAI.generate(FlowchartGenAI.generateTaskAndSolution + code));
        System.out.println(flowchartGenAI.generate(FlowchartGenAI.generateTableOfVariables + code));
    }
}