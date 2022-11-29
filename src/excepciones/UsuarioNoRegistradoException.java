/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

/**
 *
 * @author SANTIAGO
 */
public class UsuarioNoRegistradoException extends RuntimeException {

    public UsuarioNoRegistradoException() {
        super("El usuario no está registrado.");
    }

}
