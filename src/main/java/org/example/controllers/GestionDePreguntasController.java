package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import org.example.manager.FileManager;
import org.example.simulacro.Pregunta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class GestionDePreguntasController {

    private static final Logger LOG = LoggerFactory.getLogger(GestionDePreguntasController.class);

    private final List<Pregunta> LISTA_PREGUNTAS = new ArrayList<>(FileManager.getPreguntasList());

    @FXML
    TilePane tilePaneBotones;

    @FXML
    public void initialize() {

        LOG.debug("Iniciando el controlador 'Gestión de Preguntas', preguntas recuperadas: [{}]", LISTA_PREGUNTAS.size());

        tilePaneBotones.getChildren().clear();

        for (int i = 0; i < LISTA_PREGUNTAS.size(); i++) {
            Button boton = new Button(String.valueOf(i+1));  // O cualquier otro texto o estilo
            boton.setPrefWidth(56);  // Aseguramos que el botón sea cuadrado (ajustamos a tu preferencia)
            boton.setPrefHeight(56);  // De nuevo, cuadrado
            boton.setFocusTraversable(false);
            tilePaneBotones.getChildren().add(boton);  // Agregar el botón al TilePane
        }

    }

    private List<Pregunta> creaPreguntas(int num){

        List<Pregunta> listaPreguntas = new ArrayList<>();
        while(num > 0){
            listaPreguntas.add(new Pregunta());
            num--;
        }
        return listaPreguntas;
    }

}
