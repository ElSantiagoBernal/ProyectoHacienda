/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import controladores.ControladorLote;
import controladores.ControladorPesebrera;
import controladores.ControladorPocilga;
import controladores.ControladorUsuario;
import excepciones.AnimalDelGanadoNoRegistradoException;
import javax.swing.JOptionPane;
import modeloAnimales.AccionRealizadaAnimal;
import modeloAnimales.Caballo;
import modeloAnimales.Cerdo;
import modeloAnimales.Ganado;
import modeloPersonas.CriadorCerdo;
import modeloPersonas.EncargadoCaballo;
import modeloPersonas.EncargadoGanado;
import modeloPersonas.Usuario;
import modelosTerrenos.Lote;
import modelosTerrenos.Pesebrera;
import modelosTerrenos.Pocilga;

/**
 *
 * @author SANTIAGO
 */
public class VistaSeleccionGenero extends javax.swing.JFrame {

    /**
     * Creates new form VistaSeleccionGenero
     */
    VistaLogIn vistaLogIn;
    private int fila;
    private int columna;
    ControladorUsuario controladorUsuario;
    ControladorLote controladorLote;
    ControladorPesebrera controladorPesebrera;
    ControladorPocilga controladorPocilga;
    VistaEncargadoDelGanado vistaEncargadoDelGanado;
    VistaEncargadoDelCaballo vistaEncargadoDelCaballo;
    VistaCriadorDeCerdo vistaCriadorDeCerdo;

    public VistaSeleccionGenero(VistaLogIn vistaLogIn, int fila, int columna) {
        initComponents();
        setLocationRelativeTo(this);
        this.vistaLogIn = vistaLogIn;
        this.fila = fila;
        this.columna = columna;
        controladorUsuario = vistaLogIn.getControladorUsuario();
        controladorPesebrera = vistaLogIn.getControladorPesebrera();
        controladorPocilga = vistaLogIn.getControladorPocilga();
        controladorLote = vistaLogIn.getControladorLote();
        vistaEncargadoDelGanado = new VistaEncargadoDelGanado(vistaLogIn);
        vistaEncargadoDelCaballo = new VistaEncargadoDelCaballo(vistaLogIn);
        vistaCriadorDeCerdo = new VistaCriadorDeCerdo(vistaLogIn);
        validarUsuarioLogueado(controladorUsuario.getUsuarioLogueado());
        System.out.println("Fila " + fila + " Columna " + columna);
    }

    private void validarUsuarioLogueado(Usuario usuario) {
        if (usuario instanceof EncargadoCaballo) {
            txtTipoAnimal.setText("Caballos");
        } else if (usuario instanceof CriadorCerdo) {
            txtTipoAnimal.setText("Cerdos");
        } else {
            txtTipoAnimal.setText("Ganado");
        }
    }

//    private void abrirVentanaUsuario() {
//        Usuario usuario = controladorUsuario.getUsuarioLogueado();
//        if (usuario instanceof EncargadoCaballo) {
//            VistaEncargadoDelCaballo vista = new VistaEncargadoDelCaballo(vistaLogIn);
//            vista.setVisible(true);
//        } else if (usuario instanceof CriadorCerdo) {
//            VistaCriadorDeCerdo vista = new VistaCriadorDeCerdo(vistaLogIn);
//            vista.setVisible(true);
//        } else {
//            vistaEncargadoDelGanado.setVisible(true);
//        }
//    }
    private String solicitarNombreAnimal() {
        String nombre = JOptionPane.showInputDialog("Ingresa el nombre del animal");
        while (nombre.isEmpty()) {
            nombre = JOptionPane.showInputDialog("Ingresa el nombre del animal");
        }
        return nombre;
    }

    private String solicitarPesoAnimal() {
        String peso = JOptionPane.showInputDialog("Ingresa el peso del animal.");
        while (peso.isEmpty()) {
            peso = JOptionPane.showInputDialog("Ingresa el peso del animal.");
        }
        return peso;
    }

    private int solicitarEdadAnimal() {
        String edadString = JOptionPane.showInputDialog("Ingresa la edad del animal.");
        while (edadString.isEmpty()) {
            edadString = JOptionPane.showInputDialog("Ingresa la edad del animal.");
        }
        int edad = Integer.parseInt(edadString);
        return edad;
    }

    private void registrarGanado(String genero) {
        Ganado animal;
        if (genero.equals(Ganado.MACHO)) {
            animal = new Ganado(Ganado.MACHO, solicitarNombreAnimal(), solicitarEdadAnimal(), solicitarPesoAnimal());
        } else {
            animal = new Ganado(Ganado.HEMBRA, solicitarNombreAnimal(), solicitarEdadAnimal(), solicitarPesoAnimal());
        }
        AccionRealizadaAnimal accion = new AccionRealizadaAnimal(animal, AccionRealizadaAnimal.REGISTRAR);
        vistaLogIn.getControladorPila().pushAccionPilaUnoAnimales(accion);
        controladorLote.agregarAlGanado(animal, fila, columna);
        vistaEncargadoDelGanado.actualizarBotones();
    }

    private void registrarCaballo(String genero) {
        Caballo animal;
        if (genero.equals(Caballo.MACHO)) {
            animal = new Caballo(Caballo.MACHO, solicitarNombreAnimal(), solicitarEdadAnimal(), solicitarPesoAnimal());
        } else {
            animal = new Caballo(Caballo.HEMBRA, solicitarNombreAnimal(), solicitarEdadAnimal(), solicitarPesoAnimal());
        }
        AccionRealizadaAnimal accion = new AccionRealizadaAnimal(animal, AccionRealizadaAnimal.REGISTRAR);
        vistaLogIn.getControladorPila().pushAccionPilaUnoAnimales(accion);
        controladorPesebrera.agregarALaPesebrera(animal, fila, columna);;
        vistaEncargadoDelCaballo.actualizarBotones();
    }

    private void registrarCerdo(String genero) {
        Cerdo animal;
        if (genero.equals(Caballo.MACHO)) {
            animal = new Cerdo(Caballo.MACHO, solicitarNombreAnimal(), solicitarEdadAnimal(), solicitarPesoAnimal());
        } else {
            animal = new Cerdo(Caballo.HEMBRA, solicitarNombreAnimal(), solicitarEdadAnimal(), solicitarPesoAnimal());
        }
        AccionRealizadaAnimal accion = new AccionRealizadaAnimal(animal, AccionRealizadaAnimal.REGISTRAR);
        vistaLogIn.getControladorPila().pushAccionPilaUnoAnimales(accion);
        controladorPocilga.agregarALaPocilga(animal, fila, columna);
        vistaCriadorDeCerdo.actualizarBotones();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlSeleccionGenero = new javax.swing.JPanel();
        btnMacho = new javax.swing.JButton();
        btnHembra = new javax.swing.JButton();
        txtTipoAnimal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlSeleccionGenero.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Selección de genero", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        btnMacho.setText("Macho");
        btnMacho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMachoActionPerformed(evt);
            }
        });

        btnHembra.setText("Hembra");
        btnHembra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHembraActionPerformed(evt);
            }
        });

        txtTipoAnimal.setText("jLabel1");

        javax.swing.GroupLayout pnlSeleccionGeneroLayout = new javax.swing.GroupLayout(pnlSeleccionGenero);
        pnlSeleccionGenero.setLayout(pnlSeleccionGeneroLayout);
        pnlSeleccionGeneroLayout.setHorizontalGroup(
            pnlSeleccionGeneroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSeleccionGeneroLayout.createSequentialGroup()
                .addGroup(pnlSeleccionGeneroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSeleccionGeneroLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(btnMacho, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHembra, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlSeleccionGeneroLayout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(txtTipoAnimal)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        pnlSeleccionGeneroLayout.setVerticalGroup(
            pnlSeleccionGeneroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSeleccionGeneroLayout.createSequentialGroup()
                .addComponent(txtTipoAnimal)
                .addGap(55, 55, 55)
                .addGroup(pnlSeleccionGeneroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMacho, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHembra, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(79, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlSeleccionGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlSeleccionGenero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMachoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMachoActionPerformed
        // TODO add your handling code here:
        Usuario usuario = controladorUsuario.getUsuarioLogueado();
        if (usuario instanceof CriadorCerdo || VistaAdministrador.rolEscogido.equals("Cerdo")) {
            registrarCerdo(Cerdo.MACHO);
        } else if (usuario instanceof EncargadoCaballo || VistaAdministrador.rolEscogido.equals("Caballo")) {
            registrarCaballo(Caballo.MACHO);
        } else if (usuario instanceof EncargadoGanado || VistaAdministrador.rolEscogido.equals("Ganado")) {
            registrarGanado(Ganado.MACHO);
        }
        JOptionPane.showMessageDialog(null, "Se registró un Macho");
        VistaListaAnimales vista = new VistaListaAnimales(vistaLogIn, fila, columna);
        vista.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnMachoActionPerformed

    private void btnHembraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHembraActionPerformed
        // TODO add your handling code here:
        Usuario usuario = controladorUsuario.getUsuarioLogueado();
        if (usuario instanceof CriadorCerdo || VistaAdministrador.rolEscogido.equals("Cerdo")) {
            registrarCerdo(Cerdo.HEMBRA);
        } else if (usuario instanceof EncargadoCaballo || VistaAdministrador.rolEscogido.equals("Caballo")) {
            registrarCaballo(Caballo.HEMBRA);
        } else if (usuario instanceof EncargadoGanado || VistaAdministrador.rolEscogido.equals("Ganado")) {
            registrarGanado(Ganado.HEMBRA);
        }
        JOptionPane.showMessageDialog(null, "Se registró una Hembra");
        VistaListaAnimales vista = new VistaListaAnimales(vistaLogIn, fila, columna);
        vista.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnHembraActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHembra;
    private javax.swing.JButton btnMacho;
    private javax.swing.JPanel pnlSeleccionGenero;
    private javax.swing.JLabel txtTipoAnimal;
    // End of variables declaration//GEN-END:variables
}
