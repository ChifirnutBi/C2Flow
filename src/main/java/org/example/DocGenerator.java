package org.example;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileInputStream;
import java.io.IOException;

public class DocGenerator {

    public DocGenerator() throws IOException {
        XWPFDocument doc = new XWPFDocument();
    }

    //Create file using template
    public DocGenerator(String fileName, String university, String workTitle, String student, String group, String teacher, String year) {
        XWPFDocument doc;
        try {
            doc = new XWPFDocument(new FileInputStream(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (XWPFParagraph p : doc.getParagraphs())

        {
            for (XWPFRun r : p.getRuns()) {
                String text = r.getText(0);
                if (text == null) continue;

                text = text.replace("{UNIVERSITY}", university)
                        .replace("{WORK_TITLE}", workTitle)
                        .replace("{STUDENT}", student)
                        .replace("{GROUP}", group)
                        .replace("{TEACHER}", teacher)
                        .replace("{YEAR}", year);

                r.setText(text, 0);
            }
        }
    }
}
