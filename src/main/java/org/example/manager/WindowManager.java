package org.example.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.simulacro.MainApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class WindowManager {

    private static final Logger LOG = LoggerFactory.getLogger(WindowManager.class);

    private static Stage primaryStage;

    public static void setStage(Stage stage) {
        LOG.info("Setting the primary stage to [{}]", stage);
        primaryStage = stage;
    }

    public static void showWindow(String title, String fxmlPath, String cssPath, int width, int height) {

        LOG.info("Showing new window --> [{}] in [{}]...", title, fxmlPath);

        if (primaryStage == null) {
            LOG.error("The primaryStage is not set. Call setPrimaryStage() before using showView().");
            throw new IllegalStateException("PrimaryStage is not configured");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxmlPath));
        Scene scene;

        try {
            scene = new Scene(fxmlLoader.load(), width, height);
            scene.getStylesheets().add(MainApplication.class.getResource(cssPath).toExternalForm());

            primaryStage.setTitle(title);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}