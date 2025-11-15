package org.example;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;

public class FlowchartGenAI {

    final static String MODEL = "gemini-2.5-flash";
    final static GenerateContentConfig CONFIG = null;

    private static FlowchartGenAI instance;

    private final Client client;
    private final String model;
    private final GenerateContentConfig config;

    private FlowchartGenAI() {
        this.client = new Client(); // берёт ключ из GEMINI_API_KEY
        this.model = "gemini-2.5-flash";
        this.config = null; // или свой конфиг
    }

    public static FlowchartGenAI getInstance() {
        if (instance == null) {
            instance = new FlowchartGenAI();
        }
        return instance;
    }

    public String generate(String prompt) {
        GenerateContentResponse response = client.models.generateContent(
                model,
                prompt,
                config
        );
        return response.text();
    }

    public static void main(String[] args) {
        // key from GOOGLE_API_KEY
        Client client = new Client();

        // пример запроса
        GenerateContentResponse response = client.models.generateContent(
                model,                // модель
                "промпт",                                // твой промпт
                null                                     // можно оставить null
        );

        // выводим результат
        System.out.println(response.text());
    }
}
