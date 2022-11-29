/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import excepciones.AnimalDeLaPocilgaNoRegistradoException;
import java.io.IOException;
import modeloAnimales.Cerdo;
import modelosTerrenos.Pocilga;
import util.Serializadora;

/**
 *
 * @author SANTIAGO
 */
public class ControladorPocilga {

    Serializadora serializadora;
    Pocilga[][] pocilgas;
    private int numeroAnimalesRegistrados;
    private boolean instanciaPocilgas;
    private String precioPorkg;

    public ControladorPocilga() {
        instanciaPocilgas = true;
        serializadora = new Serializadora();
        pocilgas = inicializarPocilgas();
        numeroAnimalesRegistrados = inicializarNumeroAnimalesPocilgas();
        precioPorkg = inicializarPrecioPorKg();
        if (instanciaPocilgas == false) {
            initPocilgas();
        }
    }

    private Pocilga[][] inicializarPocilgas() {
        Pocilga[][] matrizPocilgas;
        try {
            matrizPocilgas = serializadora.leerPocilgas();
            return matrizPocilgas;
        } catch (IOException | ClassNotFoundException ex) {
            instanciaPocilgas = false;
            return new Pocilga[2][8];
        }
    }

    private int inicializarNumeroAnimalesPocilgas() {
        int numAnimales;
        try {
            numAnimales = serializadora.leerNumeroAnimalesPocilgas();
            return numAnimales;
        } catch (IOException | ClassNotFoundException ex) {
            return 0;
        }
    }

    private String inicializarPrecioPorKg() {
        String precio;
        try {
            precio = serializadora.leerPrecioPorKgCerdo();
            return precio;
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }

    public void escribirPrecio() {
        try {
            serializadora.escribirPrecioPorKgCerdo(precioPorkg);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void escribirPocilgas() {
        try {
            serializadora.escribirPocilgas(pocilgas);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void escribirNumeroAnimalesPocilgas() {
        try {
            serializadora.escribirNumeroAnimalesPocilgas(numeroAnimalesRegistrados);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void initPocilgas() {
        for (int i = 0; i < pocilgas.length; i++) {
            for (int j = 0; j < pocilgas[i].length; j++) {
                pocilgas[i][j] = new Pocilga();
            }
        }
    }

    private void sumarNumeroAnimalesPocilga(Pocilga pocilga) {
        int numero = pocilga.getNumeroAnimales();
        numero += 1;
        pocilga.setNumeroAnimales(numero);
    }

    private void restarNumeroAnimalesPocilga(Pocilga pocilga) {
        int numero = pocilga.getNumeroAnimales();
        numero -= 1;
        pocilga.setNumeroAnimales(numero);
    }

    private void sumarNumeroAnimalesRegistrados() {
        numeroAnimalesRegistrados += 1;
    }

    private int obtenerNumeroAnimalesRegistrados() {
        return numeroAnimalesRegistrados;
    }

    public void generarPocilgaAlAzar() {
        int fila = (int) Math.floor(Math.random() * ((pocilgas.length - 1) - 0 + 1) + 0);
        int columna = (int) Math.floor(Math.random() * ((pocilgas[0].length - 1) - 0 + 1) + 0);
        System.out.println(fila + "," + columna);
    }

    public Cerdo buscarEnPocilga(String codigoRegistro, int fila, int columna) {
        Pocilga pocilga = obtenerPocilga(fila, columna);
        for (int i = 0; i < pocilga.obtenerTamañoLista(); i++) {
            if (pocilga.recorrerLista(i).getCodigoString().equals(codigoRegistro)) {
                return pocilga.recorrerLista(i);
            }
        }
        return null;
    }

    public void agregarALaPocilga(Cerdo animal, int fila, int columna) {
        Pocilga pocilga = obtenerPocilga(fila, columna);
        sumarNumeroAnimalesRegistrados();
        sumarNumeroAnimalesPocilga(pocilga);
        int code = obtenerNumeroAnimalesRegistrados();
        animal.setCodigoString("CE" + code);
        pocilga.agregarCerdo(animal);
        pocilga.setEstado(Pocilga.OCUPADO);
        escribirNumeroAnimalesPocilgas();
        escribirPocilgas();
    }

    public void eliminarDeLaPocilga(String codigo, int fila, int columna) {
        Pocilga pocilga = obtenerPocilga(fila, columna);
        Cerdo Cerdo = buscarEnPocilga(codigo, fila, columna);
        if (Cerdo == null) {
            throw new AnimalDeLaPocilgaNoRegistradoException();
        }
        for (int i = 0; i < pocilga.obtenerTamañoLista(); i++) {
            if (pocilga.recorrerLista(i).getCodigoString().equals(codigo)) {
                restarNumeroAnimalesPocilga(pocilga);
                pocilga.eliminarCerdo(i);
            }
        }
        escribirPocilgas();
    }

    public void agregarCerdoProvenienteOtraPocilga(Cerdo cerdo, int fila, int columna) {
        Pocilga pocilga = obtenerPocilga(fila, columna);
        pocilga.agregarCerdo(cerdo);
        sumarNumeroAnimalesPocilga(pocilga);
        pocilga.setEstado(Pocilga.OCUPADO);
        escribirPocilgas();
    }

    public void agregarCerdoProvenientePila(Cerdo cerdo, int fila, int columna) {
        Pocilga pocilga = obtenerPocilga(fila, columna);
        pocilga.agregarCerdo(cerdo);
        sumarNumeroAnimalesPocilga(pocilga);
        pocilga.setEstado(Pocilga.OCUPADO);
        escribirPocilgas();
    }

    public void añadirCriasCerdo(String codigo, int numeroCerdos, int fila, int columna) {
        Cerdo cerdo = buscarEnPocilga(codigo, fila, columna);
        for (int i = 0; i < numeroCerdos; i++) {
            Cerdo cria = new Cerdo("Cria", "", 0, " ");
            agregarALaPocilga(cria, fila, columna);
        }
        cerdo.setHembraAmamantando(true);
        escribirPocilgas();
    }

    public Pocilga obtenerPocilga(int fila, int columna) {
        return pocilgas[fila][columna];
    }

    public int retornarFilas() {
        return pocilgas.length;
    }

    public int retornarColumnas() {
        return pocilgas[0].length;
    }

    public String getPrecioPorkg() {
        return precioPorkg;
    }

    public void setPrecioPorkg(String precioPorkg) {
        this.precioPorkg = precioPorkg;
        escribirPrecio();
    }

}
