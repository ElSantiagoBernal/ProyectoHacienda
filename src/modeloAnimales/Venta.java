/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloAnimales;

import java.io.Serializable;
import modeloPersonas.Persona;

/**
 *
 * @author SANTIAGO
 */
public class Venta implements Serializable {

    private Animal animalVendido;
    private Persona datosComprador;
    private double precioAnimal;
    private String fecha;

    public Venta(Animal animalVendido, Persona datosComprador, double precioAnimal, String fecha) {
        this.animalVendido = animalVendido;
        this.datosComprador = datosComprador;
        this.precioAnimal = precioAnimal;
        this.fecha = fecha;
    }

    public Persona getDatosComprador() {
        return datosComprador;
    }

    public void setDatosComprador(Persona datosComprador) {
        this.datosComprador = datosComprador;
    }

    public double getPrecioAnimal() {
        return precioAnimal;
    }

    public void setPrecioAnimal(double precioAnimal) {
        this.precioAnimal = precioAnimal;
    }

    public Animal getAnimalVendido() {
        return animalVendido;
    }

    public void setAnimalVendido(Animal animalVendido) {
        this.animalVendido = animalVendido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
