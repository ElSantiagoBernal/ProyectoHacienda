/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelosTerrenos;

import java.io.Serializable;
import modeloAnimales.Cerdo;
import util.LSE;

/**
 *
 * @author SANTIAGO
 */
public class Pocilga implements Serializable{
    
    public static final String DISPONIBLE = "Disponible";
    public static final String OCUPADO = "Ocupado";
    
    LSE<Cerdo> ListaCerdos;
    private String estado;
    private int numeroAnimales;
    boolean crias;

    public Pocilga() {
        ListaCerdos = new LSE<>();
        estado = DISPONIBLE;
        numeroAnimales = 0;
        crias = false;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public int obtenerTamañoLista (){
        return ListaCerdos.size();
    }
    
    public Cerdo recorrerLista (int index){
        return ListaCerdos.get(index);
    }
    
    public void agregarCerdo (Cerdo cerdo){
        ListaCerdos.add(cerdo);
    }
    
    public void eliminarCerdo (int index){
        ListaCerdos.delete(index);
    }

    public int getNumeroAnimales() {
        return numeroAnimales;
    }

    public void setNumeroAnimales(int numeroAnimales) {
        this.numeroAnimales = numeroAnimales;
    }

    public boolean isCrias() {
        return crias;
    }

    public void setCrias(boolean crias) {
        this.crias = crias;
    }
    
    
    
}
