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
public class ObjetoTablaNoSeleccionadoException extends RuntimeException {
    
    public ObjetoTablaNoSeleccionadoException() {
        super("No se ha seleccionado nungun objeto de la tabla");
    }
    
}
