package org.example;

import com.google.genai.Client;
import com.google.genai.errors.ApiException;
import com.google.genai.errors.GenAiIOException;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;

import java.io.IOException;

public class ConcurrentFlowchartGenAI {

    private static final Client CLIENT = new Client();

    public String generate(String model, String prompt, GenerateContentConfig config) {
        return CLIENT.models.generateContent(model, prompt, config).text();
    }
    public String generateWithRetry(String model, String prompt, GenerateContentConfig config, int maxAttempts) {
        for (int i = 0; i < maxAttempts; i++) {
            try {
                return this.generate(model, prompt, config);
            } catch (ApiException | GenAiIOException e) {
                System.err.printf(
                        "Exception in %s: \"%s\" (%d/%d)%n",
                        Thread.currentThread().getName(),
                        ((Throwable)e).getMessage(),
                        i + 1, maxAttempts
                );
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }

        }
        throw new RuntimeException("All " + maxAttempts + " attempts failed");
    }
}