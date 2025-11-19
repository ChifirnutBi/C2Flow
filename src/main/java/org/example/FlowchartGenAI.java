package org.example;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;

public class FlowchartGenAI {

    private static FlowchartGenAI instance;

    private static final Client CLIENT = new Client();

    private FlowchartGenAI() {

    }

    public static FlowchartGenAI getInstance() {
        if (instance == null) {
            instance = new FlowchartGenAI();
        }
        return instance;
    }

    public String generate(String model, String prompt, GenerateContentConfig config) {
        GenerateContentResponse response = CLIENT.models.generateContent(
                model,
                prompt,
                config
        );
        return response.text();
    }

}
