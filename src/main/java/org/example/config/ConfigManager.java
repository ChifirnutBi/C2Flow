package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    public static void loadConfig(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Config loaded = mapper.readValue(new File(filePath), Config.class);

        Config instance = Config.getInstance();
        instance.genAI = loaded.genAI;
        instance.studentInfo = loaded.studentInfo;
        instance.paths = loaded.paths;
    }

    public static void saveConfig(Config config, String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), config);
    }
}