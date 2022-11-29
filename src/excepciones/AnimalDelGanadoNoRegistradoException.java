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
public class AnimalDelGanadoNoRegistradoException extends RuntimeException {
    
    public AnimalDelGanadoNoRegistradoException() {
        super("El animal no esta registrado en el ganado");
    }
    
}
