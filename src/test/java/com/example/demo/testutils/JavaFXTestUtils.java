package com.example.demo.testutils;

import java.util.concurrent.CountDownLatch;

import javafx.application.Platform;

public class JavaFXTestUtils {

    private static boolean javaFXInitialized = false;

    /**
     * Initializes the JavaFX platform if it hasn't been initialized already.
     * This method is safe to call multiple times.
     */
    public static void initializeJavaFX() {
        if (!javaFXInitialized) {
            try {
                Platform.startup(() -> {
                    // No-op, just start the JavaFX platform
                });
                javaFXInitialized = true;
            } catch (IllegalStateException e) {
                // JavaFX is already initialized, so no action is needed
            }
        }
    }

    public static void awaitFXTasks() {
        try {
            Thread.sleep(100); // Give the JavaFX thread some time to process
            Platform.runLater(() -> {});
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting for JavaFX tasks to complete", e);
        }
    }

    public static void waitForFxEvents() {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(latch::countDown);
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
