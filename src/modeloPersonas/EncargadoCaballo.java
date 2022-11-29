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
public class EncargadoCaballo extends Usuario {

    int numeroCuidadosRealizados;

    public EncargadoCaballo(String usuario, String contraseña, String nombre, String apellido, String identificacion, int edad) {
        super(usuario, contraseña, nombre, apellido, identificacion, edad);
        numeroCuidadosRealizados = 0;
    }

    public int getNumeroCuidadosRealizados() {
        return numeroCuidadosRealizados;
    }
    
    public void cuidadosRealizados(int cuidados) {
        numeroCuidadosRealizados += cuidados;
    }
    
    public void añadirCuidado() {
        numeroCuidadosRealizados += 1;
    }
    
    public void restarCuidadoo() {
        numeroCuidadosRealizados -= 1;
    }

}
