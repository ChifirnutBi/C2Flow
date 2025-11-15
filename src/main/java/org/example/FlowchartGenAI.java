package org.example;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;

public class FlowchartGenAI {

    final static String model = "gemini-2.5-flash";
    final static GenerateContentConfig config = null;

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
