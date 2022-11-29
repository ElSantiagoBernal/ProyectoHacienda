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
public class AnimalEnColaException extends RuntimeException {
    
    public AnimalEnColaException() {
        super("El animal ya está en cola y a la espera de ser atendido.");
    }
    
}
