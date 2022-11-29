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
public class NoSePuedeGestionarInfoAdminException extends RuntimeException {
    
    public NoSePuedeGestionarInfoAdminException() {
        super("Podrás ver la información de otro administrador pero no gestionarla.");
    }
    
}
