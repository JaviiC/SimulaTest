package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.manager.WindowManager;
import org.example.simulacro.Pregunta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class CorreccionController {

    private static final Logger LOG = LoggerFactory.getLogger(CorreccionController.class);

    // Integer = nº pregunta || Byte = nº respuesta seleccionada / nº respuesta correcta
    private final Map<Integer, Byte> RESPUESTAS_SELECCIONADAS = PreguntasController.getRespuestasSeleccionadas();

    private final Map<Integer, Byte> SOLUCIONES = PreguntasController.getSoluciones();

    private final List<Pregunta> LISTA_PREGUNTAS = PreguntasController.getListaPreguntas();

    private final int PREGUNTAS_TOTALES = SOLUCIONES.size();

    private double puntuacionTotal = 0.0, aciertos = 0.0, fallos = 0.0, sinContestar = 0.0;

    @FXML
    Label labelAciertos, labelTotal, labelPuntuacion;

    @FXML
    Button botonAceptar, botonRevisar;

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
                LOG.info("Pregunta " + i + " --> NaN");
                sinContestar++;
            }else if(RESPUESTAS_SELECCIONADAS.get(i).equals(SOLUCIONES.get(i))) {
                LOG.info("Pregunta " + i + " --> OK");
                aciertos++;
            } else{
                LOG.info("Pregunta " + i + " --> X");
                fallos++;
            }
        }
        puntuacionTotal = aciertos / PREGUNTAS_TOTALES * 10.0;
    }

    public Map<Integer, Byte> getRESPUESTAS_SELECCIONADAS() {
        return RESPUESTAS_SELECCIONADAS;
    }

    public Map<Integer, Byte> getSOLUCIONES() {
        return SOLUCIONES;
    }

    public List<Pregunta> getLISTA_PREGUNTAS() {
        return LISTA_PREGUNTAS;
    }

    @FXML
    public void onAceptarClick(){
        LOG.debug("Se ha pulsado el botón 'Aceptar'");
        WindowManager.showWindow("SIMULACROS | Home", "/fxml/maincontroller.fxml", "/styles/main.css", 850, 380);
    }

    @FXML
    public void onRevisarClick(){
        LOG.debug("Se ha pulsado el botón 'Revisar'");
        WindowManager.showWindow("SIMULACROS | Home", "/fxml/revisioncontroller.fxml", "/styles/review.css", 850, 380);
    }

}
