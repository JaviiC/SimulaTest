package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.manager.WindowManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CorreccionController {

    private static final Logger LOG = LoggerFactory.getLogger(CorreccionController.class);

    // Integer = nº pregunta || Byte = nº respuesta seleccionada / nº respuesta correcta
    private final Map<Integer, Byte> RESPUESTAS_SELECCIONADAS = PreguntasController.getRespuestasSeleccionadas();

    private final Map<Integer, Byte> SOLUCIONES = PreguntasController.getSoluciones();

    private final int PREGUNTAS_TOTALES = SOLUCIONES.size();

    private double puntuacionTotal = 0.0, aciertos = 0.0, fallos = 0.0, sinContestar = 0.0;

    @FXML
    Label labelAciertos, labelTotal, labelPuntuacion;

    @FXML
    Button buttonAceptar;

    public void initialize(){

        LOG.trace("Mostrando las respuestas seleccionadas: [{}]", RESPUESTAS_SELECCIONADAS);
        LOG.trace("Mostrando las soluciones a las preguntas: [{}]", SOLUCIONES);

        calculaEstadisticas();
        LOG.info("RESULTADOS OBTENIDOS --> Aciertos: [{}], Fallos: [{}], Sin contestar: [{}]", aciertos, fallos, sinContestar);

        labelAciertos.setText(String.valueOf((int)aciertos));
        labelTotal.setText(String.valueOf(SOLUCIONES.size()));
        labelPuntuacion.setText(String.format("%.2f",puntuacionTotal));
    }

    private void calculaEstadisticas() {

        LOG.debug("Obteniendo estadísticas del Simulacro, preguntas: [{}]", SOLUCIONES.size());

        for (int i = 1; i <= SOLUCIONES.size(); i++) {
            if (RESPUESTAS_SELECCIONADAS.get(i) == null) {
                System.out.println("Pregunta " + i + " --> NaN");
                sinContestar++;
            }else if(RESPUESTAS_SELECCIONADAS.get(i).equals(SOLUCIONES.get(i))) {
                System.out.println("Pregunta " + i + " --> OK");
                aciertos++;
            } else{
                System.out.println("Pregunta " + i + " --> X");
                fallos++;
            }
        }
        puntuacionTotal = aciertos / PREGUNTAS_TOTALES * 10.0;
    }

    @FXML
    public void onAceptarClick(){
        WindowManager.showWindow("SIMULACROS | Home", "/fxml/maincontroller.fxml", "/styles/main.css", 850, 380);
    }

    @FXML
    public void onRevisarClick(){
        // Hacer alguna ventana de revisión
    }

}
