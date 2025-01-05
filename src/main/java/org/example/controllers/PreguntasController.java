package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import org.example.manager.FileManager;
import org.example.manager.WindowManager;
import org.example.simulacro.Pregunta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PreguntasController {

    private static final Logger LOG = LoggerFactory.getLogger(PreguntasController.class);

    @FXML
    private Label labelEnunciado, labelOpcion1, labelOpcion2, labelOpcion3, labelOpcion4, labelNumeroPregunta;

    @FXML
    private ToggleGroup grupoBotones;

    @FXML
    private RadioButton rb1, rb2, rb3, rb4;

    @FXML
    private Button botonAnterior, botonSiguiente;

    private static Map<Integer, Byte> soluciones;
    private static Map<Integer, Byte> respuestasSeleccionadas;
    private static List<Pregunta> listaPreguntas;

    private int indicePreguntaActual = 0;

    public void initialize() {

        listaPreguntas = new ArrayList<>(FileManager.getPreguntasList());

        LOG.debug("Iniciando simulacro de examen, preguntas: [{}]", listaPreguntas.size());

        // Se inicializan los mapas de las Soluciones y las Respuestas Seleccionadas que vaya seleccionando el usuario
        respuestasSeleccionadas = new LinkedHashMap<>();
        soluciones = new LinkedHashMap<>();

        desordenarPreguntas();

        cargarPregunta(indicePreguntaActual); // Carga la primera pregunta

        controlDeBotones();

        cargarSoluciones(); // Se carga el mapa de soluciones
    }

    private void cargarSoluciones(){
        LOG.debug("Cargando soluciones...");
        for (int i = 0; i < listaPreguntas.size(); i++) {
            soluciones.put(i+1, listaPreguntas.get(i).getSolucion());
        }
        LOG.trace("Mapa de Soluciones: [{}]", soluciones);
    }

    private void cargarPregunta(int indice) {
        if (indice < 0 || indice >= listaPreguntas.size()) return;

        // Recupera la pregunta con posición del índice
        Pregunta pregunta = listaPreguntas.get(indice);

        // Cambia el enunciado de la interfaz
        labelEnunciado.setText(pregunta.getEnunciado());

        Map<Byte, String> opciones = pregunta.getOpciones();
        cargarOpcionesEnLabels(opciones, labelOpcion1, labelOpcion2, labelOpcion3, labelOpcion4);

        labelNumeroPregunta.setText(String.format("%d/%d",indicePreguntaActual + 1, listaPreguntas.size()));

        cargarRadioButton(indice);
    }

    private void cargarRadioButton(int indice){

        Byte respuesta = respuestasSeleccionadas.get(indice+1);

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

    private void desordenarPreguntas() {

        LOG.debug("Desordenando las claves y todas las preguntas...");

        Collections.shuffle(listaPreguntas);

        for(Pregunta pregunta : listaPreguntas){

            LOG.trace("Modificando el orden de la pregunta [{}]...", pregunta);

            Byte antiguaPosicionCorrecta = pregunta.getSolucion(); // Posición
            // Quiero recuperar la clave del mapa que está en esta posición
            Byte idOpcionCorrecta = obtenerClaveEnPosicion(pregunta.getOpciones(), antiguaPosicionCorrecta);

            Byte nuevaPosicionCorrecta = null;

            // Lista de las claves
            List<Byte> listaClaves = new ArrayList<>(pregunta.getOpciones().keySet());
            // Se desordenan las claves
            Collections.shuffle(listaClaves);

            // Crear un nuevo mapa con las claves desordenadas
            Map<Byte, String> opcionesDesordenadas = new LinkedHashMap<>();
            for (Byte clave : listaClaves) {
                opcionesDesordenadas.put(clave, pregunta.getOpciones().get(clave));
            }

            // Se cambia la posición de la solución a la misma que la opción correcta en base al ID de la opción correcta
            for (int i = 0; i < listaClaves.size(); i++) {
                if(idOpcionCorrecta == (byte)listaClaves.get(i))
                    nuevaPosicionCorrecta = (byte)(i+1);
            }
            // Actualizar las opciones de la pregunta con el nuevo mapa desordenado
            pregunta.setOpciones(opcionesDesordenadas);

            LOG.debug("Se ha modificado el orden de las opciones [{}]", pregunta);

            pregunta.setSolucion(nuevaPosicionCorrecta);

            LOG.trace("Se ha modificado la solución correcta a la pregunta [{}]", pregunta);
        }

    }

    private Byte obtenerClaveEnPosicion(Map<Byte, String> opciones, Byte antiguaPosicionCorrecta) {
        LOG.debug("Obteniendo la clave de la opción en la posición [{}]..", antiguaPosicionCorrecta);
        Byte index = 1;
        for (Map.Entry<Byte, String> mapEntry : opciones.entrySet()){
            if(index.equals(antiguaPosicionCorrecta)){
                LOG.info("Devolviendo clave en la posición [{}], Key --> [{}]", antiguaPosicionCorrecta, mapEntry.getKey());
                return mapEntry.getKey();
            }
            else
                index++;
        }
        throw new NullPointerException("Se ha producido un error al obtener la Clave en la posición " + antiguaPosicionCorrecta);
    }

    private void controlDeBotones() {
        botonAnterior.setDisable(indicePreguntaActual == 0); // En caso de ser True (primera pregunta) se desabilita
        if (indicePreguntaActual == listaPreguntas.size()-1)
            botonSiguiente.setText("Corregir");
        else
            botonSiguiente.setText("Siguente ⮞");
    }

    private Byte obtenerRespuestaSeleccionada() {

        RadioButton selected = (RadioButton) grupoBotones.getSelectedToggle();

        if (selected == null)
            return null;

        return switch (selected.getId()) {
            case "rb1" -> 1;
            case "rb2" -> 2;
            case "rb3" -> 3;
            case "rb4" -> 4;
            default -> throw new IllegalStateException("ID desconocido: " + selected.getId());
        };

    }

    private void correccionDeSimulacro() {
        LOG.debug("Procediendo a la corrección del simulacro...");
        WindowManager.showWindow("SIMULACROS | Home", "/fxml/correccioncontroller.fxml", "/styles/main.css", 725, 365);

    }

    public static Map<Integer, Byte> getSoluciones(){
        return soluciones;
    }

    public static Map<Integer, Byte> getRespuestasSeleccionadas(){
        return respuestasSeleccionadas;
    }

    public static List<Pregunta> getListaPreguntas(){
        return listaPreguntas;
    }

    @FXML
    public void onSiguienteClick() {

        LOG.debug("Botón 'Siguiente' pulsado");

        if (indicePreguntaActual < listaPreguntas.size() - 1) {

            respuestasSeleccionadas.put(indicePreguntaActual + 1, obtenerRespuestaSeleccionada());

            indicePreguntaActual++;

            cargarPregunta(indicePreguntaActual);

            controlDeBotones();

        } else {
            respuestasSeleccionadas.put(indicePreguntaActual + 1, obtenerRespuestaSeleccionada());
            correccionDeSimulacro();
        }
    }

    @FXML
    public void onAnteriorClick() {

        LOG.debug("Botón 'Anterior' pulsado");

        if (indicePreguntaActual > 0) {

            // Añade la respuesta marcada al mapa de respuestas <Nº pregunta, Solución>
            respuestasSeleccionadas.put(indicePreguntaActual + 1, obtenerRespuestaSeleccionada());

            indicePreguntaActual--;
            // Se carga la Pregunta según el índice de la lisa de preguntas
            cargarPregunta(indicePreguntaActual);
            // Se gestiona el comportamiento de los botones "Siguiente" y "Anterior"
            controlDeBotones();
        }

    }

}
