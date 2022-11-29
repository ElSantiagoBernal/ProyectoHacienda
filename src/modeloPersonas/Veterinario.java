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
public class Veterinario extends Usuario {

    private int numeroAnimalesAtendidos;

    public Veterinario(String usuario, String contraseña, String nombre, String apellido, String identificacion, int edad) {
        super(usuario, contraseña, nombre, apellido, identificacion, edad);
        numeroAnimalesAtendidos = 0;
    }

    public int getNumeroAnimalesAtendidos() {
        return numeroAnimalesAtendidos;
    }

    public void setNumeroAnimalesAtendidos(int numeroAnimalesAtendidos) {
        this.numeroAnimalesAtendidos = numeroAnimalesAtendidos;
    }

    public void añadirAnimalAtendido() {
        numeroAnimalesAtendidos += 1;
    }
    
    public void restarAnimalAtendido() {
        numeroAnimalesAtendidos -= 1;
    }

}
