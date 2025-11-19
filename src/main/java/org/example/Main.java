package org.example;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

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

            config = Config.getInstance();

            config.genAI.model = "gemini-2.5-flash";
            config.genAI.config = null;

            System.out.println("Enter the title page (multiple lines possible, end with a empty line):");
            Scanner scanner = null;
            StringBuilder titleBuilder = new StringBuilder();
            while (true) {
                String line = scanner.nextLine();
                if (line.isEmpty()) break;
                if (!titleBuilder.isEmpty()) titleBuilder.append("\n");
                titleBuilder.append(line);
            }
            config.studentInfo.title = titleBuilder.toString();
            System.out.println("Enter lab number:");
            config.studentInfo.labNumber = scanner.nextLine();
            System.out.println("Enter course name:");
            config.studentInfo.course = scanner.nextLine();
            System.out.println("Enter topic name:");
            config.studentInfo.topic = scanner.nextLine();
            System.out.println("Enter your variant:");
            config.studentInfo.variant = scanner.nextLine();
            System.out.println("Enter your group:");
            config.studentInfo.group = scanner.nextLine();
            System.out.println("Enter your name:");
            config.studentInfo.studentName = scanner.nextLine();
            System.out.println("Enter teacher`s name:");
            config.studentInfo.teacherName = scanner.nextLine();
            System.out.println("Enter your city:");
            config.studentInfo.city = scanner.nextLine();
            config.studentInfo.year = String.valueOf(LocalDate.now().getYear());

            System.out.println("Enter path to your file with code:");
            config.paths.src = scanner.nextLine();
            config.paths.docOutputDir = "output/";
            config.paths.templatePath = "template.docx";
            config.paths.tmpDir = "tmp/";

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