package org.example;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.example.config.Config;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class DocGenerator implements Closeable {

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

        HashMap<String, String> placeholders = new HashMap<>();

        placeholders.put("{UNIVERSITY}", config.studentInfo.title);
        placeholders.put("{LAB_NUMBER}", config.studentInfo.labNumber);
        placeholders.put("{COURSE}", config.studentInfo.course);
        placeholders.put("{TOPIC}", config.studentInfo.topic);
        placeholders.put("{VARIANT}", config.studentInfo.variant);
        placeholders.put("{GROUP}", config.studentInfo.group);
        placeholders.put("{STUDENT_NAME}", config.studentInfo.studentName);
        placeholders.put("{TEACHER_NAME}", config.studentInfo.teacherName);
        placeholders.put("{CITY}", config.studentInfo.city);
        placeholders.put("{YEAR}", config.studentInfo.year);
        placeholders.put("{TOPIC_UPPER_CASE}", config.studentInfo.topic.toUpperCase());
        placeholders.put("{LAB_GOAL}", config.studentInfo.labGoal);

        Set<String> missingPlaceholders = replacePlaceholders(placeholders);

        if (!missingPlaceholders.isEmpty()) {
            System.out.println("NOT FOUND PLACEHOLDERS:");
            missingPlaceholders.stream().distinct().forEach(ph -> {
                System.out.println(" - " + ph);
            });
        };
    }

    public Set<String> replacePlaceholders(HashMap<String, String> placeholders) {
        Set<String> foundPlaceholders = new HashSet<>();

        for (XWPFParagraph p : doc.getParagraphs()) {
            String paraText = p.getText();
            String newText = paraText;
            boolean changed = false;

            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                String placeholder = entry.getKey();
                if (paraText.contains(placeholder)) {
                    newText = newText.replace(placeholder, entry.getValue());
                    changed = true;
                    foundPlaceholders.add(placeholder);
                }
            }

            if (changed) {
                p.getRuns().clear();
                XWPFRun run = p.createRun();
                run.setText(newText);
                run.setFontFamily("Times New Roman");
                run.setFontSize(14);
            }
        }

        Set<String> missingPlaceholders = new HashSet<>(placeholders.keySet());
        missingPlaceholders.removeAll(foundPlaceholders);

        return missingPlaceholders;
    }

    public void save(String outputFileName) {
        try (FileOutputStream out = new FileOutputStream(outputFileName)) {
            doc.write(out);
        } catch (IOException e) {
            throw new RuntimeException("Cannot save file", e);
        }
    }

    public void close() throws IOException {
        doc.close();
    }
}
