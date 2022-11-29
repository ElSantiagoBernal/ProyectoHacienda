/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloPersonas;

/**
 *
 * @author SANTIAGO
 */
public class CriadorCerdo extends Usuario {

    private int numeroDeVentas;

    public CriadorCerdo(String usuario, String contraseña, String nombre, String apellido, String identificacion, int edad) {
        super(usuario, contraseña, nombre, apellido, identificacion, edad);
        numeroDeVentas = 0;
    }

    public int getNumeroDeVentas() {
        return numeroDeVentas;
    }

    public void setNumeroDeVentas(int numeroDeVentas) {
        this.numeroDeVentas = numeroDeVentas;
    }

    public void añadirVenta() {
        numeroDeVentas += 1;
    }
    
     public void restarVenta() {
        numeroDeVentas -= 1;
    }

}
