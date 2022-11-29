/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloAnimales;

import java.io.Serializable;

/**
 *
 * @author SANTIAGO
 */
public class ArgumentoEliminacion implements Serializable{
    
    private Animal animal;
    private String argumento;

    public ArgumentoEliminacion(Animal animal, String argumento) {
        this.argumento = argumento;
        this.animal = animal;
    }

    public String getArgumento() {
        return argumento;
    }

    public void setArgumento(String argumento) {
        this.argumento = argumento;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
    
    
    
}
