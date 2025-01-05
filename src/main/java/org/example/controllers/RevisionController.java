package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import org.example.manager.WindowManager;
import org.example.simulacro.Pregunta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RevisionController {

    private static final Logger LOG = LoggerFactory.getLogger(RevisionController.class);

    @FXML
    private Label labelEnunciado, labelOpcion1, labelOpcion2, labelOpcion3, labelOpcion4, labelNumeroPregunta;

    @FXML
    private RadioButton rb1, rb2, rb3, rb4;

    @FXML
    private Button botonAnterior, botonSiguiente;

    @FXML
    private HBox hBox1, hBox2, hBox3, hBox4;

    private static List<Pregunta> listaPreguntas;
    private static Map<Integer, Byte> soluciones;
    private static Map<Integer, Byte> respuestasSeleccionadas;

    private int indice = 0;

    public void initialize() {

        CorreccionController c1 = new CorreccionController();
        listaPreguntas = c1.getLISTA_PREGUNTAS();
        soluciones = c1.getSOLUCIONES();
        respuestasSeleccionadas = c1.getRESPUESTAS_SELECCIONADAS();

        LOG.debug("Iniciando REVISIÓN del examen, preguntas: [{}]", listaPreguntas.size());

        cargarPregunta(); // Carga la primera pregunta

        controlDeBotones();

        pintarFondosDeCorreccion();
    }

    private void pintarFondosDeCorreccion() {

        Byte respuestaCorrecta = soluciones.get(indice+1);
        Byte respuestaSeleccionada = respuestasSeleccionadas.get(indice+1);

        // Limpiar estilos anteriores de los labels
        limpiarEstilosLabels();

        // Se asigna el color a la respuesta correcta
        switch (respuestaCorrecta){
            case 1 -> hBox1.getStyleClass().add("correcta");
            case 2 -> hBox2.getStyleClass().add("correcta");
            case 3 -> hBox3.getStyleClass().add("correcta");
            case 4 -> hBox4.getStyleClass().add("correcta");
        }
        // Si la pergunta no se contestó
        if(respuestaSeleccionada == null){

            switch (respuestaCorrecta){
                case 1 -> hBox1.getStyleClass().add("no-contestada");
                case 2 -> hBox2.getStyleClass().add("no-contestada");
                case 3 -> hBox3.getStyleClass().add("no-contestada");
                case 4 -> hBox4.getStyleClass().add("no-contestada");
            }
        // Si se ha marcado alguna respuesta y esta NO ES LA CORRECTA
        } else if (!respuestaSeleccionada.equals(respuestaCorrecta)) {

            switch (respuestaSeleccionada){
                case 1 -> hBox1.getStyleClass().add("incorrecta");
                case 2 -> hBox2.getStyleClass().add("incorrecta");
                case 3 -> hBox3.getStyleClass().add("incorrecta");
                case 4 -> hBox4.getStyleClass().add("incorrecta");
            }

        }
    }

    private void limpiarEstilosLabels() {
        hBox1.getStyleClass().removeAll("correcta", "incorrecta", "no-contestada");
        hBox2.getStyleClass().removeAll("correcta", "incorrecta", "no-contestada");
        hBox3.getStyleClass().removeAll("correcta", "incorrecta", "no-contestada");
        hBox4.getStyleClass().removeAll("correcta", "incorrecta", "no-contestada");
    }

    private void cargarPregunta() {

        if (indice < 0 || indice >= listaPreguntas.size()) return;

        // Recupera la pregunta con posición del índice
        Pregunta pregunta = listaPreguntas.get(indice);

        LOG.debug("Cargando pregunta [{}] --> [{}]", indice +1, pregunta);

        // Cambia el enunciado de la interfaz
        labelEnunciado.setText(pregunta.getEnunciado());

        // Carga las opciones en los distintos labels
        Map<Byte, String> opciones = pregunta.getOpciones();
        cargarOpcionesEnLabels(opciones, labelOpcion1, labelOpcion2, labelOpcion3, labelOpcion4);

        // Cambia el label del nº de pregunta correspondiente
        labelNumeroPregunta.setText(String.format("%d/%d", indice + 1, listaPreguntas.size()));

        cargarRadioButton(indice);
    }

    private void cargarOpcionesEnLabels(Map<Byte, String> opciones, Label labelOpcion1, Label labelOpcion2, Label labelOpcion3, Label labelOpcion4) {

        List<String> strOpciones = new ArrayList<>();

        // Se implementan las opciones a la lista
        for(Map.Entry<Byte, String> mapEntry : opciones.entrySet()){
            strOpciones.add(mapEntry.getValue());
        }

        // Se cargan las opciones en los Labels
        labelOpcion1.setText(strOpciones.get(0));
        labelOpcion2.setText(strOpciones.get(1));
        labelOpcion3.setText(strOpciones.get(2));
        labelOpcion4.setText(strOpciones.get(3));
    }

    private void cargarRadioButton(int indice){

        Byte respuesta = respuestasSeleccionadas.get(indice+1);

        LOG.debug("Cargando RadioButtons correspondientes, [Pregunta: [{}], Solución: [{}]]", indice+1, respuesta);

        if (respuesta == null){
            rb1.setSelected(false);
            rb2.setSelected(false);
            rb3.setSelected(false);
            rb4.setSelected(false);
        } else {
            switch (respuesta) {
                case 1 -> {
                    rb1.setSelected(true);
                    rb2.setSelected(false);
                    rb3.setSelected(false);
                    rb4.setSelected(false);
                }
                case 2 -> {
                    rb1.setSelected(false);
                    rb2.setSelected(true);
                    rb3.setSelected(false);
                    rb4.setSelected(false);
                }
                case 3 -> {
                    rb1.setSelected(false);
                    rb2.setSelected(false);
                    rb3.setSelected(true);
                    rb4.setSelected(false);
                }
                case 4 -> {
                    rb1.setSelected(false);
                    rb2.setSelected(false);
                    rb3.setSelected(false);
                    rb4.setSelected(true);
                }
            }
        }
    }

    private void controlDeBotones() {
        botonAnterior.setDisable(indice == 0); // En caso de ser True (primera pregunta) se desabilita
        if (indice == listaPreguntas.size()-1)
            botonSiguiente.setText("Finalizar Revisión");
        else
            botonSiguiente.setText("Siguente ⮞");
    }

    @FXML
    public void onSiguienteClick() {

        LOG.debug("Botón 'Siguiente' pulsado");

        if (indice < listaPreguntas.size() - 1) {

            indice++;

            cargarPregunta();

            controlDeBotones();

            pintarFondosDeCorreccion();

        } else {
            LOG.debug("Regresando al 'Home'...");
            WindowManager.showWindow("SIMULACROS | Home", "/fxml/maincontroller.fxml", "/styles/main.css", 850, 380);
        }
    }

    @FXML
    public void onAnteriorClick() {

        LOG.debug("Botón 'Anterior' pulsado");

        if (indice > 0) {

            indice--;

            cargarPregunta();

            controlDeBotones();

            pintarFondosDeCorreccion();
        }

    }


}
