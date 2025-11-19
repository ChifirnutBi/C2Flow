package org.example.config;

public class Config {

    public GenAIConfig genAI = new GenAIConfig();
    public StudentInfo student = new StudentInfo();
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
        public String config;
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
        public static final String CONFIG_FILE_PATH = "config/config.json";
        public String tmpDir;

    }
}
