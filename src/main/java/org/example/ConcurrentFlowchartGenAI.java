package org.example;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;

public class ConcurrentFlowchartGenAI {

    private static final Client CLIENT = new Client();

    public String generate(String model, String prompt, GenerateContentConfig config) {
        return CLIENT.models.generateContent(model, prompt, config).text();
    }
}