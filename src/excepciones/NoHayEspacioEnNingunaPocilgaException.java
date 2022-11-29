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
public class NoHayEspacioEnNingunaPocilgaException extends RuntimeException {
    
    public NoHayEspacioEnNingunaPocilgaException() {
        super("No hay espacio en ninguna pocilga. Debes eliminar animales de otra pocilga.");
    }
    
}
