/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import modeloAnimales.AccionRealizadaAnimal;
import modeloAnimales.Animal;
import modeloAnimales.ArgumentoEliminacion;
import modeloAnimales.CuidadoCaballo;
import modeloAnimales.HistoriaClinica;
import modeloAnimales.PremioCaballo;
import modeloAnimales.Venta;
import util.Pila;
import vistas.VistaLogIn;

/**
 *
 * @author SANTIAGO
 */
public class ControladorPila {

    Pila<AccionRealizadaAnimal> pilaUnoAnimales;
    Pila<AccionRealizadaAnimal> pilaDosAnimales;
    Pila<ArgumentoEliminacion> pilaArgumentoEliminacion;
    Pila<CuidadoCaballo> pilaCuidadosCaballoUno;
    Pila<CuidadoCaballo> pilaCuidadosCaballoDos;
    Pila<PremioCaballo> pilaPremiosUno;
    Pila<PremioCaballo> pilaPremiosDos;
    Pila<HistoriaClinica> pilaHistoriaClinicaUno;
    Pila<HistoriaClinica> pilaHistoriaClinicaDos;
    Pila<Venta> pilaVentasUno;
    Pila<Venta> pilaVentasDos;
    Pila<Animal> pilaAnimalVendidoUno;
    Pila<Animal> pilaAnimalVendidoDos;

    public ControladorPila() {
        pilaUnoAnimales = new Pila<>();
        pilaDosAnimales = new Pila<>();
        pilaArgumentoEliminacion = new Pila<>();
        pilaCuidadosCaballoUno = new Pila<>();
        pilaCuidadosCaballoDos = new Pila<>();
        pilaPremiosUno = new Pila<>();
        pilaPremiosDos = new Pila<>();
        pilaHistoriaClinicaUno = new Pila<>();
        pilaHistoriaClinicaDos = new Pila<>();
        pilaVentasUno = new Pila<>();
        pilaVentasDos = new Pila<>();
        pilaAnimalVendidoUno = new Pila<>();
        pilaAnimalVendidoDos = new Pila<>();
    }

    public void pushAccionPilaUnoAnimales(AccionRealizadaAnimal accionRealizadaAnimal) {
        pilaUnoAnimales.push(accionRealizadaAnimal);
    }

    public void pushAccionPilaDosAnimales(AccionRealizadaAnimal accionRealizadaAnimal) {
        pilaDosAnimales.push(accionRealizadaAnimal);
    }

    public AccionRealizadaAnimal popAccionPilaUnoAnimales() {
        AccionRealizadaAnimal accion = pilaUnoAnimales.pop();
        return accion;
    }

    public AccionRealizadaAnimal popAccionPilaDosAnimales() {
        AccionRealizadaAnimal accion = pilaDosAnimales.pop();
        return accion;
    }

    public boolean pilaUnoAnimalesIsEmpty() {
        return pilaUnoAnimales.isEmpty();
    }

    public boolean pilaDosAnimalesIsEmpty() {
        return pilaDosAnimales.isEmpty();
    }

    public void limpiarPilas() {
        pilaUnoAnimales.reset();
        pilaDosAnimales.reset();
        pilaArgumentoEliminacion.reset();
    }

    public void pushPilaArgumentoEliminacion(ArgumentoEliminacion argumentoEliminacion) {
        pilaArgumentoEliminacion.push(argumentoEliminacion);
    }

    public ArgumentoEliminacion popPilaArgumentoEliminacion() {
        ArgumentoEliminacion dato = pilaArgumentoEliminacion.pop();
        return dato;
    }

    public boolean pilaArgumentoEliminacionIsempty() {
        return pilaArgumentoEliminacion.isEmpty();
    }

    public void pushPilaCuidadosUno(CuidadoCaballo cuidadoCaballo) {
        pilaCuidadosCaballoUno.push(cuidadoCaballo);
    }

    public CuidadoCaballo popPilaCuidadosUno() {
        CuidadoCaballo cuidadoCaballo = pilaCuidadosCaballoUno.pop();
        return cuidadoCaballo;
    }

    public boolean pilaCuidadosUnoIsEmpty() {
        return pilaCuidadosCaballoUno.isEmpty();
    }

    public void pushPilaCuidadosDos(CuidadoCaballo cuidadoCaballo) {
        pilaCuidadosCaballoDos.push(cuidadoCaballo);
    }

    public CuidadoCaballo popPilaCuidadosDos() {
        CuidadoCaballo cuidadoCaballo = pilaCuidadosCaballoDos.pop();
        return cuidadoCaballo;
    }

    public boolean pilaCuidadosDosIsEmpty() {
        return pilaCuidadosCaballoDos.isEmpty();
    }

    public void pushPilaPremiosUno(PremioCaballo premioCaballo) {
        pilaPremiosUno.push(premioCaballo);
    }

    public PremioCaballo popPilaPremiosUno() {
        PremioCaballo premioCaballo = pilaPremiosUno.pop();
        return premioCaballo;
    }

    public boolean pilaPremiosUnoIsEmpty() {
        return pilaPremiosUno.isEmpty();
    }

    public void pushPilaPremiosDos(PremioCaballo premioCaballo) {
        pilaPremiosDos.push(premioCaballo);
    }

    public PremioCaballo popPilaPremiosDos() {
        PremioCaballo premioCaballo = pilaPremiosDos.pop();
        return premioCaballo;
    }

    public boolean pilaPremiosDosIsEmpty() {
        return pilaPremiosDos.isEmpty();
    }

    public void pushPilaHistoriaClinicaUno(HistoriaClinica historiaClinica) {
        pilaHistoriaClinicaUno.push(historiaClinica);
    }

    public HistoriaClinica popPilaHistoriaClinicaUno() {
        HistoriaClinica historiaClinica = pilaHistoriaClinicaUno.pop();
        return historiaClinica;
    }

    public void pushPilaHistoriaClinicaDos(HistoriaClinica historiaClinica) {
        pilaHistoriaClinicaDos.push(historiaClinica);
    }

    public HistoriaClinica popPilaHistoriaClinicaDos() {
        HistoriaClinica historiaClinica = pilaHistoriaClinicaDos.pop();
        return historiaClinica;
    }
    
    public void pushPilaAnimalesVendidosUno(Animal animal) {
        pilaAnimalVendidoUno.push(animal);
    }

    public Animal popPilaAnimalesVendidosUno() {
        Animal animal = pilaAnimalVendidoUno.pop();
        return animal;
    }
    
    public void pushPilaAnimalesVendidosDos(Animal animal) {
        pilaAnimalVendidoDos.push(animal);
    }

    public Animal popPilaAnimalesVendidosDos() {
        Animal animal = pilaAnimalVendidoDos.pop();
        return animal;
    }
    
    public void pushPilaVentasUno(Venta venta) {
        pilaVentasUno.push(venta);
    }

    public Venta popPilaVentasUno() {
        Venta venta = pilaVentasUno.pop();
        return venta;
    }
    
    public void pushPilaVentasDos(Venta venta) {
        pilaVentasDos.push(venta);
    }

    public Venta popPilaVentasDos() {
        Venta venta = pilaVentasDos.pop();
        return venta;
    }

}
