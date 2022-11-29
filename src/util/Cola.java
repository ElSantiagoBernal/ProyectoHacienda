/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import excepciones.ColaVaciaException;
import java.io.Serializable;
import javax.swing.JOptionPane;

/**
 *
 * @author SANTIAGO
 */
public class Cola<W> implements Serializable {

    private Nodo<W> primero;
    private int size;

    public Cola() {
        primero = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public void encolar(W dato) {
        Nodo<W> nuevo = new Nodo(dato);
        if (primero == null) {
            primero = nuevo;
        } else {
            Nodo<W> auxiliar = primero;
            while (auxiliar.getNodoSiguiente() != null) {
                auxiliar = auxiliar.getNodoSiguiente();
            }
            auxiliar.setNodoSiguiente(nuevo);
        }
        size++;
    }

    public W desencolar() {
        isEmpty();
        Nodo<W> nodoDesencolar = primero;
        primero = primero.getNodoSiguiente();
        size--;
        return nodoDesencolar.getDato();
    }

    public W frente() {
        isEmpty();
        return primero.getDato();
    }

    public void ponerFrente(W dato) {
        Nodo<W> nuevo = new Nodo(dato);
        nuevo.setNodoSiguiente(primero);
        primero = nuevo;
    }

    public void isEmpty() {
        if (primero == null) {
            throw new ColaVaciaException();
        }
    }

    public W get(int index) {
        if (index < size && index >= 0) {
            Nodo<W> auxiliar = null;
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
}
