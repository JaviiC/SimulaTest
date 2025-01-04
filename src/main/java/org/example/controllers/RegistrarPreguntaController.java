package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.manager.FileManager;
import org.example.simulacro.Pregunta;
import org.example.manager.WindowManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

public class RegistrarPreguntaController {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrarPreguntaController.class);

    @FXML
    TextField textFieldPregunta, textFieldOpcion1, textFieldOpcion2, textFieldOpcion3, textFieldOpcion4;

    @FXML
    ToggleGroup questionGroup;

    @FXML
    RadioButton rb1, rb2, rb3, rb4; // IDs

    @FXML
    Button buttonGuardar;

    @FXML
    public void onGuardarClick(){

        LOG.info("Se ha pulsado el botón de 'Guardar'");

        if (camposValidos()){

            String enunciado = textFieldPregunta.getText();
            String op1 = textFieldOpcion1.getText();
            String op2 = textFieldOpcion2.getText();
            String op3 = textFieldOpcion3.getText();
            String op4 = textFieldOpcion4.getText();

            Byte numOpcion = obtenerOpcionCorrecta();

            Map<Byte, String> opciones = new LinkedHashMap<>();
            opciones.put((byte)1, op1);
            opciones.put((byte)2, op2);
            opciones.put((byte)3, op3);
            opciones.put((byte)4, op4);

            Pregunta pregunta = new Pregunta(enunciado, opciones, numOpcion);

            FileManager.guardarPregunta(pregunta);

            WindowManager.showWindow("SIMULACROS | Home", "/fxml/maincontroller.fxml", "/styles/main.css", 850, 380);

        } else {
            // Informar de que hay que rellenar TODOS los campos
        }

    }

    private Byte obtenerOpcionCorrecta() {

        LOG.debug("Obteniendo la opción correcta a la pregunta [{}]...", textFieldPregunta.getText());
        RadioButton selected = (RadioButton) questionGroup.getSelectedToggle();

        return switch (selected.getId()) {
            case "rb1" -> 1;
            case "rb2" -> 2;
            case "rb3" -> 3;
            case "rb4" -> 4;
            default -> throw new IllegalStateException("ID desconocido: " + selected.getId());
        };

    }

    private boolean camposValidos(){
        return !textFieldPregunta.getText().isEmpty()
                && !textFieldOpcion1.getText().isEmpty()
                && !textFieldOpcion2.getText().isEmpty()
                && !textFieldOpcion3.getText().isEmpty()
                && !textFieldOpcion4.getText().isEmpty();
    }

}
