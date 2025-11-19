package org.example;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;

public class ConcurrentFlowchartGenAI {

    private static final Client CLIENT = new Client();

    private String response;

    public String getResponse() {
        return response;
    }

    public void generate(String model, String prompt, GenerateContentConfig config) {
        GenerateContentResponse response = CLIENT.models.generateContent(
                model,
                prompt,
                config
        );
        this.response = response.text();
    }

}
