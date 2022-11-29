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
public class Administrador extends Usuario {

    private int numeroUsuariosRegistrados;

    public Administrador(String usuario, String contraseña, String nombre, String apellido, String identificacion, int edad) {
        super(usuario, contraseña, nombre, apellido, identificacion, edad);
        numeroUsuariosRegistrados = 0;
    }

    public int getNumeroUsuariosRegistrados() {
        return numeroUsuariosRegistrados;
    }

    public void aumentarNumeroUsuariosRegistrados() {
        numeroUsuariosRegistrados += 1;
    }

    
    

}
