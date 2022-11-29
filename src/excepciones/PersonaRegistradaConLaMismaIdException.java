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
public class PersonaRegistradaConLaMismaIdException extends RuntimeException {

    public PersonaRegistradaConLaMismaIdException() {
        super("Ya hay una persona registrada con la misma identificación.");
    }

}
