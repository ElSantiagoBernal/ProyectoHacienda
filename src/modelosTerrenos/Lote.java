/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelosTerrenos;

import controladores.ControladorLote;
import excepciones.AnimalDelGanadoNoRegistradoException;
import java.io.Serializable;
import modeloAnimales.Ganado;
import util.LSE;
import vistas.VistaLogIn;

/**
 *
 * @author SANTIAGO
 */
public class Lote implements Serializable{

    public static final String DISPONIBLE = "Disponible";
    public static final String OCUPADO = "Ocupado";
    public static final String ORDENO = "Ordeño";
    

    LSE<Ganado> ListaGanado;
    private String estado;
    private int numeroAnimales;

    public Lote() {
        ListaGanado = new LSE<>();
        estado = DISPONIBLE;
        numeroAnimales = 0;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Ganado recorrerLista(int index) {
        return ListaGanado.get(index);
    }

    public int obtenerTamañoLista() {
        return ListaGanado.size();
    }

    public void agregarAnimalAlGanado(Ganado ganado) {
        ListaGanado.add(ganado);
    }

    public void eliminarAnimalGanado(int index) {
        ListaGanado.delete(index);
    }
    
    

//    public Ganado buscarEnElGanado(String codigoRegistro) {
//        for (int i = 0; i < ListaGanado.size(); i++) {
//            if (ListaGanado.get(i).getCodigoString().equals(codigoRegistro)) {
//                return ListaGanado.get(i);
//            }
//        }
//        return null;
//    }
//
//    public void añadirAlGanado(Ganado animal) {
//        animal.setCodigoString("G" + code);
//        ListaGanado.add(animal);
//        estado = OCUPADO;
//    }
//
//    public void eliminarDelGanado(String codigo) {
//        Ganado ganado = buscarEnElGanado(codigo);
//        if (ganado == null) {
//            throw new AnimalDelGanadoNoRegistradoException();
//        }
//        for (int i = 0; i < ListaGanado.size(); i++) {
//            if (ListaGanado.get(i).getCodigoString().equals(codigo)) {
//                ListaGanado.delete(i);
//            }
//        }
//    }

    public int getNumeroAnimales() {
        return numeroAnimales;
    }

    public void setNumeroAnimales(int numeroAnimales) {
        this.numeroAnimales = numeroAnimales;
    }
}
