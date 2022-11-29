/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;

/**
 *
 * @author SANTIAGO
 */
public class LSE<S> implements Serializable{

    private Nodo<S> primero;
    private int size;

    public LSE() {
        this.primero = null;
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public void add(S dato) {
        Nodo<S> nuevo = new Nodo(dato);
        if (primero == null) {
            primero = nuevo;
        } else {
            Nodo<S> auxiliar = primero;
            while (auxiliar.getNodoSiguiente() != null) {
                auxiliar = auxiliar.getNodoSiguiente();
            }
            auxiliar.setNodoSiguiente(nuevo);
        }
        size++;
    }

    public S get(int index) {
        if (index < size && index >= 0) {
            Nodo<S> auxiliar = null;
            if (index == 0) {
                return primero.getDato();
            } else {
                auxiliar = primero;
                int contador = 0;
                while (contador < index) {
                    auxiliar = auxiliar.getNodoSiguiente();
                    contador++;
                }
            }
            return auxiliar.getDato();
        } else {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
    }

    public void delete(int index) {
        if (index < 0 && index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            primero = primero.getNodoSiguiente();
        } else {
            Nodo<S> auxiliar = primero;
            Nodo<S> nodoEliminar;
            int contador = 0;
            while (contador < index - 1) {
                auxiliar = auxiliar.getNodoSiguiente();
                contador++;
            }
            nodoEliminar = auxiliar.getNodoSiguiente();
            auxiliar.setNodoSiguiente(nodoEliminar.getNodoSiguiente());
        }
        size--;
    }

//    public void delete(S dato) {
//        Nodo<S> auxiliar = primero;
//        while (auxiliar != dato) {
//            
//        }
//    }

}
