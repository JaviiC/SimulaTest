package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.manager.FileManager;
import org.example.manager.WindowManager;
import org.example.simulacro.Pregunta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class EliminacionController {

    private static final Logger LOG = LoggerFactory.getLogger(GestionDePreguntasController.class);

    @FXML
    Label labelEnunciado, labelOpcion1, labelOpcion2, labelOpcion3, labelOpcion4, labelNumeroPregunta;

    @FXML
    Button botonCancelar, botonEliminar;

    public void initialize() {
        LOG.debug("Iniciando el controlador de Eliminación de Pregunta...");

        Pregunta pregunta = GestionDePreguntasController.getPreguntaActual();

        labelEnunciado.setText(pregunta.getEnunciado());

        Map<Byte, String> opciones = pregunta.getOpciones();
        labelOpcion1.setText(opciones.get((byte)1));
        labelOpcion2.setText(opciones.get((byte)2));
        labelOpcion3.setText(opciones.get((byte)3));
        labelOpcion4.setText(opciones.get((byte)4));
    }

    @FXML
    public void onCancelarClick(){
        LOG.trace("Se ha pulsado el botón 'Cancelar'");
        WindowManager.showWindow("SIMULATEST | Preguntas", "/fxml/gestiondepreguntascontroller.fxml", "/styles/main.css", 780, 560);
    }

    @FXML
    public void onEliminarClick(){
        LOG.trace("Se ha pulsado el botón de 'Eliminar Pregunta'");

        FileManager.eliminarPregunta(GestionDePreguntasController.getIdBotonActual());

        WindowManager.showWindow("SIMULATEST | Home", "/fxml/maincontroller.fxml", "/styles/main.css", 850, 380);
    }

}
