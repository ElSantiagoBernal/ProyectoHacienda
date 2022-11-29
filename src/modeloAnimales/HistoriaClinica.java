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
public class HistoriaClinica implements Serializable {

    private String diagnostico;
    private String fecha;

    public HistoriaClinica(String fecha, String diagnostico) {
        this.fecha = fecha;
        this.diagnostico = diagnostico;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
