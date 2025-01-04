package org.example.simulacro;


import javafx.application.Application;
import javafx.stage.Stage;
import org.example.manager.FileManager;
import org.example.manager.WindowManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MainApplication extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(MainApplication.class);

    @Override
    public void start(Stage stage) throws IOException {
        LOG.info("Initializing application...");
        boolean ficheroCargado = FileManager.cargarFichero();

        if(!ficheroCargado){
            LOG.warn("No se ha cargado el fichero, cerrando la aplicación...");
            System.exit(0);
        }
        WindowManager.setStage(stage);
        WindowManager.showWindow("SIMULACROS | Home", "/fxml/maincontroller.fxml", "/styles/main.css", 850, 380);
    }

    @Override
    public void stop(){
        LOG.info("Close the application...");
        // Volcar fichero .dat
        FileManager.volcarFichero();
    }

    public static void main(String[] args) {
        launch();
    }

}
