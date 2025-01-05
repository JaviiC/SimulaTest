package org.example.controllers;

import javafx.fxml.FXML;
import org.example.manager.FileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.manager.WindowManager;

public class MainController {

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

    @FXML
    public void onNuevaPreguntaClick(){
        // Redirige a la ventana de implementación de una Nueva Pregunta
        WindowManager.showWindow("SIMULACROS | Nueva Pregunta", "/fxml/registrarpreguntacontroller.fxml", "/styles/main.css", 850, 525);
    }

    @FXML
    public void onVisualizarPreguntasClick(){
        FileManager.visualizarPreguntas();
    }

    @FXML
    public void onIniciarSimulacroClick(){
        if(!FileManager.getPreguntasList().isEmpty())
            // Redirige al Simulacro de todas las Preguntas Registradas
            WindowManager.showWindow("SIMULACROS | Simulacro", "/fxml/preguntascontroller.fxml", "/styles/main.css", 850, 375);
    }

}
