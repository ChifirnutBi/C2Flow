package org.example;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.example.config.Config;
import org.example.config.ConfigManager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
}
