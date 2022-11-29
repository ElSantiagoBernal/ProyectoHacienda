/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import excepciones.AnimalDelGanadoNoRegistradoException;
import java.io.IOException;
import modeloAnimales.Ganado;
import modelosTerrenos.Lote;
import util.Serializadora;

/**
 *
 * @author SANTIAGO
 */
public class ControladorLote {

    Serializadora serializadora;
    Lote[][] lotes;
    private int numeroAnimalesRegistrados;
    private boolean instanciaLotes;

    public ControladorLote() {
        serializadora = new Serializadora();
        instanciaLotes = true;
        lotes = inicializarLotes();
        numeroAnimalesRegistrados = inicializarNumeroAnimalesLotes();
        if (instanciaLotes == false) {
            initLotes();
        }
    }

    private Lote[][] inicializarLotes() {
        Lote[][] matrizLotes;
        try {
            matrizLotes = serializadora.leerLotes();
            return matrizLotes;
        } catch (IOException | ClassNotFoundException ex) {
            instanciaLotes = false;
            return new Lote[3][4];
        }
    }

    private int inicializarNumeroAnimalesLotes() {
        int numAnimales;
        try {
            numAnimales = serializadora.leerNumeroAnimalesLotes();
            return numAnimales;
        } catch (IOException | ClassNotFoundException ex) {
            return 0;
        }
    }

    public void escribirLotes() {
        try {
            serializadora.escribirLotes(lotes);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void escribirNumeroAnimalesLotes() {
        try {
            serializadora.escribirNumeroAnimalesLotes(numeroAnimalesRegistrados);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void initLotes() {
        for (int i = 0; i < lotes.length; i++) {
            for (int j = 0; j < lotes[i].length; j++) {
                lotes[i][j] = new Lote();
            }
        }
    }

    private void sumarNumeroAnimalesLote(Lote lote) {
        int numero = lote.getNumeroAnimales();
        numero += 1;
        lote.setNumeroAnimales(numero);
    }

    private void restarNumeroAnimalesLote(Lote lote) {
        int numero = lote.getNumeroAnimales();
        numero -= 1;
        lote.setNumeroAnimales(numero);
    }

    private void sumarNumeroAnimalesRegistrados() {
        numeroAnimalesRegistrados += 1;
//        System.out.println("animales registrados " + numeroAnimalesRegistrados);
    }

    private int obtenerNumeroAnimalesRegistrados() {
        return numeroAnimalesRegistrados;
    }

    public Ganado buscarEnElGanado(String codigoRegistro, int fila, int columna) {
        Lote lote = obtenerLote(fila, columna);
        for (int i = 0; i < lote.obtenerTamañoLista(); i++) {
            if (lote.recorrerLista(i).getCodigoString().equals(codigoRegistro)) {
                return lote.recorrerLista(i);
            }
        }
        return null;
    }

    public void agregarAlGanado(Ganado animal, int fila, int columna) {
        Lote lote = obtenerLote(fila, columna);
        sumarNumeroAnimalesRegistrados();
        sumarNumeroAnimalesLote(lote);
        int code = obtenerNumeroAnimalesRegistrados();
        String codigo = "G" + code;
        animal.setCodigoString(codigo);
        lote.agregarAnimalAlGanado(animal);
        lote.setEstado(Lote.OCUPADO);
        escribirNumeroAnimalesLotes();
        escribirLotes();
    }

    public void eliminarDelGanado(String codigo, int fila, int columna) {
        Lote lote = obtenerLote(fila, columna);
        Ganado ganado = buscarEnElGanado(codigo, fila, columna);
        if (ganado == null) {
            throw new AnimalDelGanadoNoRegistradoException();
        }
        for (int i = 0; i < lote.obtenerTamañoLista(); i++) {
            if (lote.recorrerLista(i).getCodigoString().equals(codigo)) {
                restarNumeroAnimalesLote(lote);
                lote.eliminarAnimalGanado(i);
            }
        }
        escribirLotes();
    }
    
    public void agregarGanadoProvenientePila(Ganado ganado, int fila, int columna) {
        Lote lote = obtenerLote(fila, columna);
        lote.agregarAnimalAlGanado(ganado);
        sumarNumeroAnimalesLote(lote);
        lote.setEstado(Lote.OCUPADO);
        escribirLotes();
    }

    public Lote obtenerLote(int fila, int columna) {
        return lotes[fila][columna];
    }

    public int retornarFilas() {
        return lotes.length;
    }

    public int retornarColumnas() {
        return lotes[0].length;
    }

}
