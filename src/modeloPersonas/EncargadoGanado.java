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
public class EncargadoGanado extends Usuario {

    private int numeroTorosVendidos;

    public EncargadoGanado(String usuario, String contraseña, String nombre, String apellido, String identificacion, int edad) {
        super(usuario, contraseña, nombre, apellido, identificacion, edad);
        numeroTorosVendidos = 0;
    }

    public int getNumeroTorosVendidos() {
        return numeroTorosVendidos;
    }

    public void setNumeroTorosVendidos(int numeroTorosVendidos) {
        this.numeroTorosVendidos = numeroTorosVendidos;
    }

    public void añadirToroVendido() {
        numeroTorosVendidos += 1;
    }

    public void restarToroVendido() {
        numeroTorosVendidos -= 1;
    }

}
