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
public class AnimalDeLaPocilgaNoRegistradoException extends RuntimeException {
    
    public AnimalDeLaPocilgaNoRegistradoException() {
        super("El animal no esta registrdo en la pocilga.");
    }
    
}
