/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloAnimales;

import util.LSE;

/**
 *
 * @author SANTIAGO
 */
public class Cerdo extends Animal {

    private boolean hembraAmamantando;

    public Cerdo(String genero, String nombre, int edad, String peso) {
        super(genero, nombre, edad, peso);
        hembraAmamantando = false;
    }


    public boolean isHembraAmamantando() {
        return hembraAmamantando;
    }

    public void setHembraAmamantando(boolean hembraAmamantando) {
        this.hembraAmamantando = hembraAmamantando;
    }


}
