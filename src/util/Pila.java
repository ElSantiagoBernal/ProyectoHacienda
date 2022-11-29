/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import excepciones.PilaSinObjetoException;

/**
 *
 * @author SANTIAGO
 */
public class Pila<S> {

    Nodo<S> primero;

    public Pila() {
        primero = null;
    }

    public void push(S dato) {
        Nodo<S> nuevo = new Nodo(dato);
        Nodo<S> auxiliar = primero;
        nuevo.setNodoSiguiente(auxiliar);
        primero = nuevo;
    }

    public S pop() {
        Nodo<S> auxiliar = primero;
        primero = primero.getNodoSiguiente();
        return auxiliar.getDato();
    }

    public void reset() {
        primero = null;
    }

    public boolean isEmpty() {
        if (primero == null) {
            return true;
        }
        return false;
    }

}
