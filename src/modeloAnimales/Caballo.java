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
public class Caballo extends Animal {

    LSE<PremioCaballo> premios;
    LSE<CuidadoCaballo> cuidados;

    public Caballo(String genero, String nombre, int edad, String peso) {
        super(genero, nombre, edad, peso);
        premios = new LSE<>();
        cuidados = new LSE<>();
    }

    public void agregarPremioAlCaballo(PremioCaballo premioCaballo) {
        premios.add(premioCaballo);
    }

    public void eliminarPremio(PremioCaballo premioCaballo) {
        for (int i = 0; i < premios.size(); i++) {
            if (premios.get(i).equals(premioCaballo)) {
                premios.delete(i);
            }
        }
    }

    public int obtenerTamañoListaPremios() {
        return premios.size();
    }

    public PremioCaballo recorrerListaPremios(int index) {
        return premios.get(index);
    }

    public void agregarCuidadoAlCaballo(CuidadoCaballo cuidadoCaballo) {
        cuidados.add(cuidadoCaballo);
    }

    public void eliminarCuidado(CuidadoCaballo cuidadoCaballo) {
        for (int i = 0; i < cuidados.size(); i++) {
            if (cuidados.get(i).equals(cuidadoCaballo)) {
                cuidados.delete(i);
            }
        }
    }

    public int obtenerTamañoListaCuidados() {
        return cuidados.size();
    }

    public CuidadoCaballo recorrerListaCuidados(int index) {
        return cuidados.get(index);
    }

}
