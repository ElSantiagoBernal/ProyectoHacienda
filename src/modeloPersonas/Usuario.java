/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloPersonas;

import java.io.Serializable;

/**
 *
 * @author SANTIAGO
 */
public class Usuario extends Persona {
    
    private String usuario;
    private String contraseña;
    
    public Usuario(String usuario, String contraseña, String nombre, String apellido, String identificacion, int edad) {
        super(nombre, apellido, identificacion, edad);
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    
    
}
