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
public class PremioCaballo implements Serializable {

    private String premio;
    private String fecha;

    public PremioCaballo(String premio, String fecha) {
        this.premio = premio;
        this.fecha = fecha;
    }

    public String getPremio() {
        return premio;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
