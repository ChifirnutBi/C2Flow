package org.example;

import java.io.*;
import org.apache.poi.xwpf.usermodel.*;

public class Main {

    static String src;
    static String code;

    static {
        //TODO: code format here
    }

    public static void main(String[] args) {

        FlowchartGenAI flowchartGenAI = FlowchartGenAI.getInstance();

        System.out.println("C2Flow 0.0.1");
        System.out.println("Enter path to your file: ");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            src = reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(src));
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