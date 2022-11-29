/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelosTerrenos;

import java.io.Serializable;
import modeloAnimales.Caballo;
import util.LSE;

/**
 *
 * @author SANTIAGO
 */
public class Pesebrera implements Serializable{

    public static final String DISPONIBLE = "Disponible";
    public static final String OCUPADO = "Ocupado";

    LSE<Caballo> ListaCaballos;
    private String estado;
    private int numeroAnimales;

    public Pesebrera() {
        ListaCaballos = new LSE<>();
        estado = DISPONIBLE;
        numeroAnimales = 0;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int obtenerTamañoLista() {
        return ListaCaballos.size();
    }

    public Caballo recorrerLista(int index) {
        return ListaCaballos.get(index);
    }
    
    public void agregarCaballo (Caballo caballo){
        ListaCaballos.add(caballo);
    }
    
    public void eliminarCaballo (int index){
        ListaCaballos.delete(index);
    }

    public int getNumeroAnimales() {
        return numeroAnimales;
    }

    public void setNumeroAnimales(int numeroAnimales) {
        this.numeroAnimales = numeroAnimales;
    }



}
