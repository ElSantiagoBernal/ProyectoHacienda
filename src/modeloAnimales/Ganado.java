/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloAnimales;

/**
 *
 * @author SANTIAGO
 */
public class Ganado extends Animal {

    private double cantidadLecheEnLitros;

    public Ganado(String genero, String nombre, int edad, String peso) {
        super(genero, nombre, edad, peso);
        cantidadLecheEnLitros = 0;
    }

    public double getCantidadLecheEnLitros() {
        return cantidadLecheEnLitros;
    }

    public void setCantidadLecheEnLitros(double cantidadLecheEnLitros) {
        this.cantidadLecheEnLitros = cantidadLecheEnLitros;
    }

}
