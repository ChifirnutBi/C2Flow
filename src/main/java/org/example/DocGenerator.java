package org.example;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.example.config.Config;
import org.example.config.ConfigManager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DocGenerator {

    XWPFDocument doc;

    public DocGenerator() throws IOException {
        doc = new XWPFDocument();
    }

    //Create file using template
    public DocGenerator(String fileName) {
        Config config = Config.getInstance();

        try {
            doc = new XWPFDocument(new FileInputStream(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (XWPFParagraph p : doc.getParagraphs()) {
            String paraText = p.getText();

            String newText = paraText
                .replace("{UNIVERSITY}", config.studentInfo.title)
                .replace("{LAB_NUMBER}", config.studentInfo.labNumber)
                .replace("{COURSE}", config.studentInfo.course)
                .replace("{TOPIC}", config.studentInfo.topic)
                .replace("{VARIANT}", config.studentInfo.variant)
                .replace("{GROUP}", config.studentInfo.group)
                .replace("{STUDENT_NAME}", config.studentInfo.studentName)
                .replace("{TEACHER_NAME}", config.studentInfo.teacherName)
                .replace("{CITY}", config.studentInfo.city)
                .replace("{YEAR}", config.studentInfo.year)
                .replace("{TOPIC_UPPER_CASE}", config.studentInfo.topic.toUpperCase()
                .replace("{LAB_GOAL}", config.studentInfo.labGoal));

            if (!paraText.equals(newText)) {
                int runCount = p.getRuns().size();
                for (int i = runCount - 1; i >= 0; i--) {
                    p.removeRun(i);
                }
                XWPFRun run = p.createRun();
                run.setText(newText);
                run.setFontFamily("Times New Roman");
                run.setFontSize(14);
            }
        }
    }

    public void save(String outputFileName) {
        try (FileOutputStream out = new FileOutputStream(outputFileName)) {
            doc.write(out);
        } catch (IOException e) {
            throw new RuntimeException("Cannot save file", e);
        }
    }

    public void generateLabFromTemplate(String templateName, HashMap<String, String> map) {
        List<String> missingPlaceholders = new ArrayList<>();

        for (XWPFParagraph p : doc.getParagraphs()) {
            String paraText = p.getText();

            String newText = paraText;
            boolean changed = false;

            for (String placeholder : map.keySet()) {
                if (paraText.contains(placeholder)) {
                    newText = newText.replace(placeholder, map.get(placeholder));
                    changed = true;
                } else {
                    missingPlaceholders.add(placeholder);
                }
            }

            if (!paraText.equals(newText)) {
                int runCount = p.getRuns().size();
                for (int i = runCount - 1; i >= 0; i--) {
                    p.removeRun(i);
                }

                XWPFRun run = p.createRun();
                run.setText(newText);
                run.setFontFamily("Times New Roman");
                run.setFontSize(14);
            }
        }

        if (!missingPlaceholders.isEmpty()) {
            System.out.println("NOT FOUND PLACEHOLDERS:");
            missingPlaceholders.stream().distinct().forEach(ph -> {
                System.out.println(" - " + ph);
            });
        }
    }
}
