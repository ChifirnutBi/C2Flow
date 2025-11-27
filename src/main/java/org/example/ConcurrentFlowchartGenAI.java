package org.example;

import com.google.genai.Client;
import com.google.genai.errors.ApiException;
import com.google.genai.errors.GenAiIOException;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class ConcurrentFlowchartGenAI {

    private final Client CLIENT;

    public ConcurrentFlowchartGenAI() {
        this.CLIENT = new Client();
    }

    public ConcurrentFlowchartGenAI(String apiKey) {
        try {
            Constructor<Client> ctor = Client.class.getDeclaredConstructor(
                    Optional.class, Optional.class, Optional.class, Optional.class,
                    Optional.class, Optional.class, Optional.class, Optional.class
            );
            ctor.setAccessible(true);

            this.CLIENT = ctor.newInstance(
                    Optional.of(apiKey),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.of(false),
                    Optional.empty(),
                    Optional.empty()
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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