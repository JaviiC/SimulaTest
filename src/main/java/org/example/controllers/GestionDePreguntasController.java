package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.manager.FileManager;
import org.example.manager.WindowManager;
import org.example.simulacro.Pregunta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class GestionDePreguntasController {

    private static final Logger LOG = LoggerFactory.getLogger(GestionDePreguntasController.class);

    private static Integer idBotonActual = -1;

    private static List<Pregunta> LISTA_PREGUNTAS;

    @FXML
    TilePane tilePaneBotones;

    @FXML
    public void initialize() {

        LISTA_PREGUNTAS = new ArrayList<>(FileManager.getPreguntasList());

        LOG.debug("Iniciando el controlador 'Gestión de Preguntas', preguntas recuperadas: [{}]", LISTA_PREGUNTAS.size());

        tilePaneBotones.getChildren().clear();

        for (int i = 0; i < LISTA_PREGUNTAS.size(); i++) {
            Button boton = new Button(String.valueOf(i+1));  // O cualquier otro texto o estilo
            boton.setId(String.valueOf(i+1));

            boton.setPrefWidth(56);  // Aseguramos que el botón sea cuadrado (ajustamos a tu preferencia)
            boton.setPrefHeight(56);  // De nuevo, cuadrado
            boton.setFocusTraversable(false);

            boton.setStyle(
                            "-fx-background-radius: 10;" + // Bordes redondeados
                            "-fx-border-color: black;" + // Borde negro
                            "-fx-border-width: 0.5;" + // Grosor del borde
                            "-fx-border-radius: 10;" // Bordes redondeados para el borde
            );

            boton.setFont(Font.font("Arial", FontWeight.BOLD, 16)); // Texto más grande y en negrita
            boton.setTextFill(Color.BLACK); // Texto negro

            // ASIGNAR EVENTO
            boton.setOnAction(event ->  {
                idBotonActual = Integer.parseInt(boton.getId());
                WindowManager.showWindow("SIMULATEST | Eliminación de Pregunta " + idBotonActual + "/" + LISTA_PREGUNTAS.size(), "/fxml/eliminacioncontroller.fxml", "/styles/main.css", 850, 375);
            });

            tilePaneBotones.getChildren().add(boton);  // Agregar el botón al TilePane
            LOG.debug("Botón asignado al TilePane");
        }

    }

    public static int getIdBotonActual(){
        return idBotonActual;
    }

    public static Pregunta getPreguntaActual(){
        if(idBotonActual == -1)
            throw new NullPointerException("Error al recuperar la pregunta del Controlador de Gestión, no hay ningún botón asignado.");

        return LISTA_PREGUNTAS.get(idBotonActual-1);
    }

    @FXML
    public void onInicioClick(){
        LOG.trace("Se ha pulsado el botón 'INICIO'");
        WindowManager.showWindow("SIMULATEST | Home", "/fxml/maincontroller.fxml", "/styles/main.css", 850, 380);
    }

}
