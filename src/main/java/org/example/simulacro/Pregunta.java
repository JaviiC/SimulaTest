package org.example.simulacro;

import java.io.Serializable;
import java.util.Map;

public class Pregunta implements Serializable {

    private String enunciado;
    private Map<Byte, String> opciones;
    private Byte solucion;

    public Pregunta(String enunciado, Map<Byte, String> opciones, Byte solucion) {
        this.enunciado = enunciado;
        this.opciones = opciones;
        this.solucion = solucion;
    }

    public Pregunta() {
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public Map<Byte, String> getOpciones() {
        return opciones;
    }

    public void setOpciones(Map<Byte, String> opciones) {
        this.opciones = opciones;
    }

    public Byte getSolucion() {
        return solucion;
    }

    public void setSolucion(Byte correct) {
        this.solucion = correct;
    }

    @Override
    public String toString() {
        return " {Enunciado: '" + this.enunciado +
                "'; Opciones [ " + opciones + " ]" +
                "; Solución: " + this.solucion + "} ";
    }

}
