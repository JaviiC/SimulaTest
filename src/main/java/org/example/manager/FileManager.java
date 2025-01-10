package org.example.manager;

import org.example.controllers.RegistrarPreguntaController;
import org.example.simulacro.Pregunta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class FileManager {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrarPreguntaController.class);

    private static final File FICHERO_BINARIO = new File("preguntas.dat");
    private static List<Pregunta> preguntasList = null;

    public static boolean cargarFichero(){

        LOG.debug("Cargando datos del fichero [{}]...", FICHERO_BINARIO.getName());

        // Si el fichero está vacío carga una lista vacía
        if(FICHERO_BINARIO.length() == 0) {
            LOG.debug("El fichero está vacío, cargando una lista de preguntas en blanco");
            preguntasList = new ArrayList<>();
            return true;
        }

        try (FileInputStream fis = new FileInputStream(FICHERO_BINARIO);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ObjectInputStream ois = new ObjectInputStream(bis)){

            preguntasList = (List<Pregunta>) ois.readObject();
            LOG.info("Preguntas recuperadas del fichero: [{}]", preguntasList.size());
            return true;

        } catch (FileNotFoundException e) {
            LOG.error("Error, no se ha encontrado el fichero [{}] --> [{}]", FICHERO_BINARIO.getName(), e.getMessage());
            return false;
        } catch (IOException e) {
            LOG.error("Error al cargar el fichero --> [{}]", e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
            LOG.error("Error al leer el fichero --> [{}]", e.getMessage());
            return false;
        }

    }

    public static void volcarFichero(){

        LOG.debug("Procediendo al volcado de la lista. Tamaño de la lista: [{}]", preguntasList.size());

        try (FileOutputStream fos = new FileOutputStream(FICHERO_BINARIO);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream ois = new ObjectOutputStream(bos)){

            ois.writeObject(preguntasList);
            LOG.info("Se ha realizado el volcado de [{}] pregunta/s en el fichero [{}]", preguntasList.size(), FICHERO_BINARIO.getName());

        } catch (FileNotFoundException e) {
            LOG.error("Error, no se ha encontrado el fichero [{}] --> [{}]", FICHERO_BINARIO.getName(), e.getMessage());
        } catch (IOException e) {
            LOG.error("Error en el volcado del fichero [{}] --> [{}]", FICHERO_BINARIO.getName(), e.getMessage());
        }

    }

    public static void guardarPregunta(Pregunta pregunta){
        LOG.debug("Guardando la pregunta [{}] en el registro...", pregunta);
        preguntasList.add(pregunta);
        LOG.info("Pregunta guardada");
    }

    public static List<Pregunta> getPreguntasList(){
        return preguntasList;
    }

    public static void eliminarPregunta(int idPregunta) {

        LOG.debug("Procediendo a eliminar la pregunta [{}]", idPregunta);
        if(preguntasList.size() < idPregunta){
            LOG.warn("Error al eliminar, la pregunta [{}] no existe...", idPregunta);
            return;
        }

        LOG.info("Pregunta [{}] ELIMINADA", preguntasList.get(idPregunta-1));
        preguntasList.remove(idPregunta-1);
    }

    public static int getIdPregunta(Pregunta pregunta) {
        return preguntasList.indexOf(pregunta);
    }
}
