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
        LOG.trace("Se ha pulsado el botón de 'Nueva Pregunta'");

        // Redirige a la ventana de implementación de una Nueva Pregunta
        WindowManager.showWindow("SIMULATEST | Nueva Pregunta", "/fxml/registrarpreguntacontroller.fxml", "/styles/main.css", 850, 525);
    }

    @FXML
    public void onGestionarPreguntasClick(){
        LOG.trace("Se ha pulsado el botón de 'Gestión de Preguntas'");

        // Redirige a la ventana de eliminación de Preguntas
        WindowManager.showWindow("SIMULATEST | Preguntas", "/fxml/gestiondepreguntascontroller.fxml", "/styles/main.css", 780, 560);
    }

    @FXML
    public void onIniciarSimulacroClick(){
        LOG.trace("Se ha pulsado el botón de 'Inicio de Simulacro'");

        if(!FileManager.getPreguntasList().isEmpty())
            // Redirige al Simulacro de todas las Preguntas Registradas
            WindowManager.showWindow("SIMULATEST | Simulacro", "/fxml/preguntascontroller.fxml", "/styles/main.css", 850, 375);
    }

}
