/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.IOException;
import modeloAnimales.Animal;
import util.Cola;
import util.Serializadora;

/**
 *
 * @author SANTIAGO
 */
public class ControladorColaVeterinario {

    Serializadora serializadora;
    Cola<Animal> cola;

    public ControladorColaVeterinario() {
        serializadora = new Serializadora();
        cola = inicializarColaVeterinario();
    }

    private Cola<Animal> inicializarColaVeterinario() {
        Cola<Animal> col;
        try {
            col = serializadora.leerColaVeterinario();
            return col;
        } catch (IOException | ClassNotFoundException ex) {
            return new Cola<>();
        }
    }

    public void escribirColaVeterinario() {
        try {
            serializadora.escribirColaVeterinario(cola);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void encolar(Animal animal) {
        cola.encolar(animal);
        escribirColaVeterinario();
    }

    public Animal desencolar() {
        Animal animal = cola.desencolar();
        escribirColaVeterinario();
        return animal;
    }

    public void ponerFrente(Animal animal) {
        cola.ponerFrente(animal);
        escribirColaVeterinario();
    }

    public Animal frente() {
        return cola.frente();
    }

    public int size() {
        return cola.size();
    }

    public Animal get(int index) {
        return cola.get(index);
    }

    public void isEmpty() {
        cola.isEmpty();
    }

}
