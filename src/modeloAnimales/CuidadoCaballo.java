/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloAnimales;

import java.io.Serializable;

/**
 *
 * @author SANTIAGO
 */
public class CuidadoCaballo implements Serializable{
    
    private String cuidado;
    private String fecha;

    public CuidadoCaballo(String cuidado, String fecha) {
        this.cuidado = cuidado;
        this.fecha = fecha;
    }

    public String getCuidado() {
        return cuidado;
    }

    public void setCuidado(String cuidado) {
        this.cuidado = cuidado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
