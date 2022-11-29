/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloAnimales;

/**
 *
 * @author SANTIAGO
 */
public class AccionRealizadaAnimal {

    public static final String REGISTRAR = "Registrar";
    public static final String ELIMINAR = "Eliminar";
    public static final String EDITAR = "Editar";
    public static final String DESEDITAR = "Deseditar";
    public static final String VENDER = "Vender";
    public static final String DESVENDER = "Desvender";
    
   private  Animal animal;
    private String accion;

    public AccionRealizadaAnimal(Animal animal, String accion) {
        this.animal = animal;
        this.accion = accion;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

}
