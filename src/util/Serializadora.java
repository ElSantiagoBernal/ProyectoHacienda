/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import modeloAnimales.Animal;
import modeloAnimales.ArgumentoEliminacion;
import modeloAnimales.HistoriaClinica;
import modeloAnimales.Venta;
import modeloPersonas.Usuario;
import modelosTerrenos.Lote;
import modelosTerrenos.Pesebrera;
import modelosTerrenos.Pocilga;

/**
 *
 * @author SANTIAGO
 */
public class Serializadora {

    private ObjectOutputStream escritorObjetos;
    private ObjectInputStream lectorObjetos;

    public void escribirUsuarios(LSE<Usuario> usuarios) throws FileNotFoundException, IOException {
        FileOutputStream file = new FileOutputStream("usuarios.dat");
        escritorObjetos = new ObjectOutputStream(file);
        escritorObjetos.writeObject(usuarios);
    }

    public LSE<Usuario> leerUsuarios() throws FileNotFoundException, IOException, ClassNotFoundException {
        LSE<Usuario> usuarios;
        FileInputStream file = new FileInputStream("usuarios.dat");
        lectorObjetos = new ObjectInputStream(file);
        usuarios = (LSE<Usuario>) lectorObjetos.readObject();
        return usuarios;
    }

    public void escribirPocilgas(Pocilga[][] pocilgas) throws FileNotFoundException, IOException {
        FileOutputStream file = new FileOutputStream("matrizPocilgas.dat");
        escritorObjetos = new ObjectOutputStream(file);
        escritorObjetos.writeObject(pocilgas);
    }

    public Pocilga[][] leerPocilgas() throws FileNotFoundException, IOException, ClassNotFoundException {
        Pocilga[][] pocilgas = null;
        FileInputStream file = new FileInputStream("matrizPocilgas.dat");
        lectorObjetos = new ObjectInputStream(file);
        pocilgas = (Pocilga[][]) lectorObjetos.readObject();
        return pocilgas;
    }

    public void escribirNumeroAnimalesPocilgas(int numeroAnimales) throws FileNotFoundException, IOException {
        FileOutputStream file = new FileOutputStream("numeroAnimalesPocilgas.dat");
        escritorObjetos = new ObjectOutputStream(file);
        escritorObjetos.writeObject(numeroAnimales);
    }

    public int leerNumeroAnimalesPocilgas() throws FileNotFoundException, IOException, ClassNotFoundException {
        int numeroAnimalesPocilgas;
        FileInputStream file = new FileInputStream("numeroAnimalesPocilgas.dat");
        lectorObjetos = new ObjectInputStream(file);
        numeroAnimalesPocilgas = (int) lectorObjetos.readObject();
        return numeroAnimalesPocilgas;
    }

    public void escribirLotes(Lote[][] lotes) throws FileNotFoundException, IOException {
        FileOutputStream file = new FileOutputStream("matrizLotes.dat");
        escritorObjetos = new ObjectOutputStream(file);
        escritorObjetos.writeObject(lotes);
    }

    public Lote[][] leerLotes() throws FileNotFoundException, IOException, ClassNotFoundException {
        Lote[][] lotes = null;
        FileInputStream file = new FileInputStream("matrizLotes.dat");
        lectorObjetos = new ObjectInputStream(file);
        lotes = (Lote[][]) lectorObjetos.readObject();
        return lotes;
    }

    public void escribirNumeroAnimalesLotes(int numeroAnimales) throws FileNotFoundException, IOException {
        FileOutputStream file = new FileOutputStream("numeroAnimalesLotes.dat");
        escritorObjetos = new ObjectOutputStream(file);
        escritorObjetos.writeObject(numeroAnimales);
    }

    public int leerNumeroAnimalesLotes() throws FileNotFoundException, IOException, ClassNotFoundException {
        int numeroAnimalesLotes;
        FileInputStream file = new FileInputStream("numeroAnimalesLotes.dat");
        lectorObjetos = new ObjectInputStream(file);
        numeroAnimalesLotes = (int) lectorObjetos.readObject();
        return numeroAnimalesLotes;
    }

    public void escribirPesebreras(Pesebrera[][] pesebreras) throws FileNotFoundException, IOException {
        FileOutputStream file = new FileOutputStream("matrizPesebreras.dat");
        escritorObjetos = new ObjectOutputStream(file);
        escritorObjetos.writeObject(pesebreras);
    }

    public Pesebrera[][] leerPesebreras() throws FileNotFoundException, IOException, ClassNotFoundException {
        Pesebrera[][] pesebreras = null;
        FileInputStream file = new FileInputStream("matrizPesebreras.dat");
        lectorObjetos = new ObjectInputStream(file);
        pesebreras = (Pesebrera[][]) lectorObjetos.readObject();
        return pesebreras;
    }

    public void escribirNumeroAnimalesPesebreras(int numeroAnimales) throws FileNotFoundException, IOException {
        FileOutputStream file = new FileOutputStream("numeroAnimalesPesebreras.dat");
        escritorObjetos = new ObjectOutputStream(file);
        escritorObjetos.writeObject(numeroAnimales);
    }

    public int leerNumeroAnimalesPesebreras() throws FileNotFoundException, IOException, ClassNotFoundException {
        int numeroAnimalesPesebreras;
        FileInputStream file = new FileInputStream("numeroAnimalesPesebreras.dat");
        lectorObjetos = new ObjectInputStream(file);
        numeroAnimalesPesebreras = (int) lectorObjetos.readObject();
        return numeroAnimalesPesebreras;
    }

    public void escribirHistorialEliminados(LSE<ArgumentoEliminacion> eliminados) throws FileNotFoundException, IOException {
        FileOutputStream file = new FileOutputStream("historialEliminados.dat");
        escritorObjetos = new ObjectOutputStream(file);
        escritorObjetos.writeObject(eliminados);
    }

    public LSE<ArgumentoEliminacion> leerHistorialEliminados() throws FileNotFoundException, IOException, ClassNotFoundException {
        LSE<ArgumentoEliminacion> eliminados;
        FileInputStream file = new FileInputStream("historialEliminados.dat");
        lectorObjetos = new ObjectInputStream(file);
        eliminados = (LSE<ArgumentoEliminacion>) lectorObjetos.readObject();
        return eliminados;
    }

    public void escribirHistorialVentas(LSE<Venta> ventas) throws FileNotFoundException, IOException {
        FileOutputStream file = new FileOutputStream("historialVentas.dat");
        escritorObjetos = new ObjectOutputStream(file);
        escritorObjetos.writeObject(ventas);
    }

    public LSE<Venta> leerHistorialVentas() throws FileNotFoundException, IOException, ClassNotFoundException {
        LSE<Venta> ventas;
        FileInputStream file = new FileInputStream("historialVentas.dat");
        lectorObjetos = new ObjectInputStream(file);
        ventas = (LSE<Venta>) lectorObjetos.readObject();
        return ventas;
    }

    public void escribirColaVeterinario(Cola<Animal> cola) throws FileNotFoundException, IOException {
        FileOutputStream file = new FileOutputStream("colaVeterinario.dat");
        escritorObjetos = new ObjectOutputStream(file);
        escritorObjetos.writeObject(cola);
    }

    public Cola<Animal> leerColaVeterinario() throws FileNotFoundException, IOException, ClassNotFoundException {
        Cola<Animal> cola;
        FileInputStream file = new FileInputStream("colaVeterinario.dat");
        lectorObjetos = new ObjectInputStream(file);
        cola = (Cola<Animal>) lectorObjetos.readObject();
        return cola;
    }
    
    public void escribirPrecioPorKgCerdo(String precio) throws FileNotFoundException, IOException {
        FileOutputStream file = new FileOutputStream("precioKg.dat");
        escritorObjetos = new ObjectOutputStream(file);
        escritorObjetos.writeObject(precio);
    }

    public String leerPrecioPorKgCerdo() throws FileNotFoundException, IOException, ClassNotFoundException {
        String precio;
        FileInputStream file = new FileInputStream("precioKg.dat");
        lectorObjetos = new ObjectInputStream(file);
        precio = (String) lectorObjetos.readObject();
        return precio;
    }

//////    public void escribirHistorialClinico(LSE<HistoriaClinica> clinico) throws FileNotFoundException, IOException {
//////        FileOutputStream file = new FileOutputStream("historialClinico.dat");
//////        escritorObjetos = new ObjectOutputStream(file);
//////        escritorObjetos.writeObject(clinico);
//////    }
//////
//////    public LSE<HistoriaClinica> leerHistorialClinico() throws FileNotFoundException, IOException, ClassNotFoundException {
//////        LSE<HistoriaClinica> clinico;
//////        FileInputStream file = new FileInputStream("historialClinico.dat");
//////        lectorObjetos = new ObjectInputStream(file);
//////        clinico = (LSE<HistoriaClinica>) lectorObjetos.readObject();
//////        return clinico;
//////    }

}
