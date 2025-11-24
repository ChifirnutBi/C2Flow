package org.example.config;

import com.google.genai.types.GenerateContentConfig;

public class Config {

    public GenAIConfig genAI = new GenAIConfig();
    public StudentInfo studentInfo = new StudentInfo();
    public PathsConfig paths = new PathsConfig();

    private static Config instance;

    private Config() {}

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public static class GenAIConfig {
        public String model;
        public GenerateContentConfig config;
        public String generateTaskAndSolution;
        public String generateTableOfVariables;
        public String generateFunctionSignaturesTable;
        public String generateMathModel;
        public String generateConclusion;
    }

    public static class StudentInfo {
        public String title;
        public String labNumber;
        public String course;
        public String topic;
        public String variant;
        public String group;
        public String studentName;
        public String teacherName;
        public String city;
        public String year;
    }

    public static class PathsConfig {
        public String src;
        public String docOutputDir;
        public String templatePath;
        public String tmpDir;
    }
}
