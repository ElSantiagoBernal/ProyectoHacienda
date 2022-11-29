/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloAnimales;

import controladores.HistorialEliminado;
import java.io.IOException;
import java.io.Serializable;
import util.LSE;
import util.Serializadora;

/**
 *
 * @author SANTIAGO
 */
public class Animal implements Serializable {

    public static final String MACHO = "Macho";
    public static final String HEMBRA = "Hembra";

    private String nombre;
    private int edad;
    private String peso;
    private String codigoString;
    private String genero;
    private String problemaRemisionVeterinario;
    Serializadora serializadora;
    LSE<HistoriaClinica> historiaClinica;

    public Animal(String genero, String nombre, int edad, String peso) {
//        serializadora = new Serializadora();
        historiaClinica = new LSE<>();
        this.genero = genero;
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        codigoString = null;
        problemaRemisionVeterinario = null;
    }

//    private LSE<HistoriaClinica> inicializarHistorialClinico() {
//        LSE<HistoriaClinica> historial;
//        try {
//            historial = serializadora.leerHistorialClinico();
//            return historial;
//        } catch (IOException | ClassNotFoundException ex) {
//            return new LSE<>();
//        }
//    }
//
//    private void escribirHistorialClinico() {
//        try {
//            serializadora.escribirHistorialClinico(historiaClinica);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }

    public HistoriaClinica recorrerListaHistoriaClinica(int index) {
        return historiaClinica.get(index);
    }

    public void añadirAlHistorialClinico(HistoriaClinica dato) {
        historiaClinica.add(dato);
////        escribirHistorialClinico();
    }

    public void eliminarHistorialClinico(HistoriaClinica dato) {
        for (int i = 0; i < historiaClinica.size(); i++) {
            if (historiaClinica.get(i).equals(dato)) {
                historiaClinica.delete(i);
            }
        }
//////        escribirHistorialClinico();
    }

    public int obtenerTamañoListaHistorial() {
        return historiaClinica.size();
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCodigoString() {
        return codigoString;
    }

    public void setCodigoString(String codigoString) {
        this.codigoString = codigoString;
    }

    public String getProblemaRemisionVeterinario() {
        return problemaRemisionVeterinario;
    }

    public void setProblemaRemisionVeterinario(String problemaRemisionVeterinario) {
        this.problemaRemisionVeterinario = problemaRemisionVeterinario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

}
