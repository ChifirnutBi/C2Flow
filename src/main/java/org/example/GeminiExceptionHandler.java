package org.example;

import com.google.genai.errors.ApiException;
import com.google.genai.errors.GenAiIOException;

import java.io.IOException;

public class GeminiExceptionHandler implements Thread.UncaughtExceptionHandler {

    private final int maxRetries;

    public GeminiExceptionHandler(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (isRetryable(e)) {
            int attempt = 1;
            while (attempt <= maxRetries) {
                System.err.println("Exception in " + t.getName() + " \"" + e.getMessage() + "\", trying again... " + attempt + "/" + maxRetries);
                try {
                    Thread.sleep(2000);
                    // todo retry
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
                attempt++;
            }
            if (attempt > maxRetries) {
                System.err.println(t.getName() + " retries exceeded.");
            }
        }
    }

    private boolean isRetryable(Throwable e) {
        return e instanceof IOException
                || e instanceof ApiException;
    }
}
