/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.IOException;
import modeloAnimales.Animal;
import modeloAnimales.ArgumentoEliminacion;
import util.LSE;
import util.Serializadora;

/**
 *
 * @author SANTIAGO
 */
public class HistorialEliminado {

    Serializadora serializadora;
    private ArgumentoEliminacion argumentoEliminacion;
    LSE<ArgumentoEliminacion> animalesEliminados;

    public HistorialEliminado() {
        serializadora = new Serializadora();
        animalesEliminados = inicializarHistorialEliminacion();
        argumentoEliminacion = null;
    }

    private LSE<ArgumentoEliminacion> inicializarHistorialEliminacion() {
        LSE<ArgumentoEliminacion> eliminados;
        try {
            eliminados = serializadora.leerHistorialEliminados();
            return eliminados;
        } catch (IOException | ClassNotFoundException ex) {
            return new LSE<>();
        }
    }

    public void escribirHistorialEliminados() {
        try {
            serializadora.escribirHistorialEliminados(animalesEliminados);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int obtenerTamañoLista() {
        return animalesEliminados.size();
    }

    public ArgumentoEliminacion recorrerLista(int index) {
        return animalesEliminados.get(index);
    }

    public void agregarAnimalEliminado(ArgumentoEliminacion argumento) {
        animalesEliminados.add(argumento);
        escribirHistorialEliminados();
    }

    public void eliminarAnimalListaEliminados(String codigo) {
        for (int i = 0; i < animalesEliminados.size(); i++) {
            if (animalesEliminados.get(i).getAnimal().getCodigoString().equals(codigo)) {
                animalesEliminados.delete(i);
            }
        }
        escribirHistorialEliminados();
    }

    public ArgumentoEliminacion obtenerArgumento(String codigo) {
        for (int i = 0; i < animalesEliminados.size(); i++) {
            if (animalesEliminados.get(i).getAnimal().getCodigoString().equals(codigo)) {
                return animalesEliminados.get(i);
            }
        }
        return null;
    }

    public ArgumentoEliminacion getArgumentoEliminacion() {
        return argumentoEliminacion;
    }

    public void setArgumentoEliminacion(ArgumentoEliminacion argumentoEliminacion) {
        this.argumentoEliminacion = argumentoEliminacion;
    }

}
