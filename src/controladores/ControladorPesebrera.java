/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import excepciones.AnimalDeLaPesebreraNoRegistradoException;
import java.io.IOException;
import modeloAnimales.Caballo;
import modelosTerrenos.Pesebrera;
import util.Serializadora;

/**
 *
 * @author SANTIAGO
 */
public class ControladorPesebrera {

    Serializadora serializadora;
    Pesebrera[][] pesebreras;
    private int numeroAnimalesPesebreraRegistrados;
    private boolean instanciaPesebreras;

    public ControladorPesebrera() {
        serializadora = new Serializadora();
        instanciaPesebreras = true;
        pesebreras = inicializarPesebreras();
        numeroAnimalesPesebreraRegistrados = inicializarNumeroAnimalesPesebreras();
        if (instanciaPesebreras == false) {
            initPesebreras();
        }
    }

    private Pesebrera[][] inicializarPesebreras() {
        Pesebrera[][] matrizPesebrerases;
        try {
            matrizPesebrerases = serializadora.leerPesebreras();
            return matrizPesebrerases;
        } catch (IOException | ClassNotFoundException ex) {
            instanciaPesebreras = false;
            return new Pesebrera[4][6];
        }
    }

    private int inicializarNumeroAnimalesPesebreras() {
        int numAnimales;
        try {
            numAnimales = serializadora.leerNumeroAnimalesPesebreras();
            return numAnimales;
        } catch (IOException | ClassNotFoundException ex) {
            return 0;
        }
    }

    public void escribirPesebreras() {
        try {
            serializadora.escribirPesebreras(pesebreras);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void escribirNumeroAnimalesPesebreras() {
        try {
            serializadora.escribirNumeroAnimalesPesebreras(numeroAnimalesPesebreraRegistrados);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void initPesebreras() {
        for (int i = 0; i < pesebreras.length; i++) {
            for (int j = 0; j < pesebreras[i].length; j++) {
                pesebreras[i][j] = new Pesebrera();
            }
        }
    }

    private void sumarNumAnimalesPesebrera(Pesebrera pesebrera) {
        int numero = pesebrera.getNumeroAnimales();
        numero += 1;
        pesebrera.setNumeroAnimales(numero);
    }

    private void restarNumeroAnimalesPesebrera(Pesebrera pesebrera) {
        int numero = pesebrera.getNumeroAnimales();
        numero -= 1;
        pesebrera.setNumeroAnimales(numero);
    }

    private void sumarNumeroAnimalesRegistrados() {
        numeroAnimalesPesebreraRegistrados += 1;
    }

    private int obtenerNumeroAnimalesRegistrados() {
        return numeroAnimalesPesebreraRegistrados;
    }

    public Caballo buscarEnPesebrera(String codigoRegistro, int fila, int columna) {
        Pesebrera pesebrera = obtenerPesebrera(fila, columna);
        for (int i = 0; i < pesebrera.obtenerTamañoLista(); i++) {
            if (pesebrera.recorrerLista(i).getCodigoString().equals(codigoRegistro)) {
                return pesebrera.recorrerLista(i);
            }
        }
        return null;
    }

    public void agregarALaPesebrera(Caballo animal, int fila, int columna) {
        Pesebrera pesebrera = obtenerPesebrera(fila, columna);
        sumarNumeroAnimalesRegistrados();
        sumarNumAnimalesPesebrera(pesebrera);
        int code = obtenerNumeroAnimalesRegistrados();
        animal.setCodigoString("CA" + code);
        pesebrera.agregarCaballo(animal);
        pesebrera.setEstado(Pesebrera.OCUPADO);
        escribirNumeroAnimalesPesebreras();
        escribirPesebreras();
    }

    public void eliminarDeLaPesebrera(String codigo, int fila, int columna) {
        Pesebrera pesebrera = obtenerPesebrera(fila, columna);
        Caballo caballo = buscarEnPesebrera(codigo, fila, columna);
        if (caballo == null) {
            throw new AnimalDeLaPesebreraNoRegistradoException();
        }
        for (int i = 0; i < pesebrera.obtenerTamañoLista(); i++) {
            if (pesebrera.recorrerLista(i).getCodigoString().equals(codigo)) {
                restarNumeroAnimalesPesebrera(pesebrera);
                pesebrera.eliminarCaballo(i);
            }
        }
        escribirPesebreras();
    }
    public void agregarCaballoProvenientePila(Caballo caballo, int fila, int columna) {
        Pesebrera pesebrera = obtenerPesebrera(fila, columna);
        pesebrera.agregarCaballo(caballo);
        sumarNumAnimalesPesebrera(pesebrera);
        pesebrera.setEstado(Pesebrera.OCUPADO);
        escribirPesebreras();
    }

    public Pesebrera obtenerPesebrera(int fila, int columna) {
        return pesebreras[fila][columna];
    }

    public int retornarFilas() {
        return pesebreras.length;
    }

    public int retornalColumnas() {
        return pesebreras[0].length;
    }

}
