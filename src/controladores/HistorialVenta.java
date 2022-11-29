/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.IOException;
import modeloAnimales.Venta;
import util.LSE;
import util.Serializadora;

/**
 *
 * @author SANTIAGO
 */
public class HistorialVenta {

    Serializadora serializadora;
    LSE<Venta> ventas;

    public HistorialVenta() {
        serializadora = new Serializadora();
        ventas = inicializarHistorialVentas();
    }

    private LSE<Venta> inicializarHistorialVentas() {
        LSE<Venta> historial;
        try {
            historial = serializadora.leerHistorialVentas();
            return historial;
        } catch (IOException | ClassNotFoundException ex) {
            return new LSE<>();
        }
    }

    public void escribirHistorialVentas() {
        try {
            serializadora.escribirHistorialVentas(ventas);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int obtenerTamañoLista() {
        return ventas.size();
    }

    public Venta recorrerLista(int index) {
        return ventas.get(index);
    }

    public void agregarVentaHistorial(Venta venta) {
        ventas.add(venta);
        escribirHistorialVentas();
    }

    public void eliminarAnimalListaVendidos(String codigo) {
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).getAnimalVendido().getCodigoString().equals(codigo)) {
                ventas.delete(i);
            }
        }
        escribirHistorialVentas();
    }

    public Venta obtenerVenta(String codigo) {
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).getAnimalVendido().getCodigoString().equals(codigo)) {
                return ventas.get(i);
            }
        }
        return null;
    }

}
