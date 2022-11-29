/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import controladores.ControladorLote;
import controladores.ControladorUsuario;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import modeloPersonas.Administrador;
import modeloPersonas.Usuario;
import modelosTerrenos.Lote;

/**
 *
 * @author SANTIAGO
 */
public class VistaEncargadoDelGanado extends javax.swing.JFrame implements ActionListener {

    /**
     * Creates new form VistaEncargadoDelGanado
     */
    VistaLogIn vistaLogIn;
    ControladorLote controladorLote;
    ControladorUsuario controladorUsuario;
    JButton[][] botones;

    public VistaEncargadoDelGanado(VistaLogIn vistaLogIn) {
        initComponents();
        setLocationRelativeTo(this);
        controladorLote = vistaLogIn.getControladorLote();
        controladorUsuario = vistaLogIn.getControladorUsuario();
        this.vistaLogIn = vistaLogIn;
        botones = new JButton[controladorLote.retornarFilas()][controladorLote.retornarColumnas()];
        cargarBotones();
        btnVolver.setVisible(false);
        validarUsuarioLogeado();
    }

    private void validarUsuarioLogeado() {
        Usuario usuarioLogeado = controladorUsuario.getUsuarioLogueado();
        if (usuarioLogeado instanceof Administrador) {
            btnVolver.setVisible(true);
        }
    }

    private void cargarBotones() {
        int ancho = 60;
        int alto = 60;
        int separado = 60;
        int texto = 1;
        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[i].length; j++) {
                Lote lote = controladorLote.obtenerLote(i, j);
                botones[i][j] = new JButton();
                validarEspaciosBotones(texto, i, j, ancho, alto, separado);
                if (lote.getEstado().equals(Lote.OCUPADO)) {
                    botones[i][j].setBackground(Color.GREEN);
                }
                botones[i][j].setText(String.valueOf(texto));
                botones[i][j].addActionListener(this);
                panelBotones.add(botones[i][j]);
                texto++;
            }
        }
        panelBotones.repaint();
    }

    public void actualizarBotones() {
        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[i].length; j++) {
                Lote lote = controladorLote.obtenerLote(i, j);
                if (lote.getEstado().equals(Lote.OCUPADO)) {
                    botones[i][j].setBackground(Color.GREEN);
                }
            }
        }
    }

    private void validarEspaciosBotones(int texto, int i, int j, int ancho, int alto, int separado) {
        switch (texto) {
            case 1:
                botones[i][j].setBounds(
                        (j * ancho + separado),
                        (i * alto + separado),
                        ancho, alto);
                break;
            case 2:
                botones[i][j].setBounds(
                        ((j + 1) * ancho + separado),
                        (i * alto + separado),
                        ancho, alto);
                break;
            case 3:
                botones[i][j].setBounds(
                        ((j + 2) * ancho + separado),
                        (i * alto + separado),
                        ancho, alto);
                break;
            case 4:
                botones[i][j].setBounds(
                        ((j + 3) * ancho + separado),
                        (i * alto + separado),
                        ancho, alto);
                break;
            case 5:
                botones[i][j].setBounds(
                        (j * ancho + separado),
                        ((i + 1) * alto + separado),
                        ancho, alto);
                break;
            case 6:
                botones[i][j].setBounds(
                        ((j + 1) * ancho + separado),
                        ((i + 1) * alto + separado),
                        ancho, alto);
                break;
            case 7:
                botones[i][j].setBounds(
                        ((j + 2) * ancho + separado),
                        ((i + 1) * alto + separado),
                        ancho, alto);
                break;
            case 8:
                botones[i][j].setBounds(
                        ((j + 3) * ancho + separado),
                        ((i + 1) * alto + separado),
                        ancho, alto);
                break;
            case 9:
                botones[i][j].setBounds(
                        (j * ancho + separado),
                        ((i + 2) * alto + separado),
                        ancho, alto);
                break;
            case 10:
                botones[i][j].setBounds(
                        ((j + 1) * ancho + separado),
                        ((i + 2) * alto + separado),
                        ancho, alto);
                break;
            case 11:
                botones[i][j].setBounds(
                        ((j + 2) * ancho + separado),
                        ((i + 2) * alto + separado),
                        ancho, alto);
                break;
            case 12:
                botones[i][j].setBounds(
                        ((j + 3) * ancho + separado),
                        ((i + 2) * alto + separado),
                        ancho, alto);
                break;
            default:
                break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBotones = new javax.swing.JPanel();
        btnHistorial = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        btnVentas = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelBotones.setBackground(new java.awt.Color(0, 0, 0, 150));
        panelBotones.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Encargado del ganado", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        btnHistorial.setText("Eliminados");
        btnHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistorialActionPerformed(evt);
            }
        });

        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        btnVentas.setText("Ventas");
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });

        btnCerrarSesion.setText("Cerrar sesión");
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBotonesLayout = new javax.swing.GroupLayout(panelBotones);
        panelBotones.setLayout(panelBotonesLayout);
        panelBotonesLayout.setHorizontalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnVolver)
                .addGap(70, 70, 70)
                .addComponent(btnHistorial)
                .addGap(46, 46, 46)
                .addComponent(btnVentas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(btnCerrarSesion)
                .addContainerGap())
        );
        panelBotonesLayout.setVerticalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBotonesLayout.createSequentialGroup()
                .addContainerGap(393, Short.MAX_VALUE)
                .addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCerrarSesion)
                    .addComponent(btnVentas)
                    .addComponent(btnHistorial)
                    .addComponent(btnVolver))
                .addContainerGap())
        );

        getContentPane().add(panelBotones, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 11, 510, 450));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imágenes/Ganado.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 540, 470));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        // TODO add your handling code here:
        vistaLogIn.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        // TODO add your handling code here:
        VistaAdministrador vista = new VistaAdministrador(vistaLogIn);
        vista.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistorialActionPerformed
        // TODO add your handling code here:
        VistaHistorialEliminados vista = new VistaHistorialEliminados(vistaLogIn);
        vista.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnHistorialActionPerformed

    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        // TODO add your handling code here:
        VistaHistorialVendidos vista = new VistaHistorialVendidos(vistaLogIn);
        vista.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVentasActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnHistorial;
    private javax.swing.JButton btnVentas;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel panelBotones;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent ae) {
        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[i].length; j++) {
                if (ae.getSource().equals(botones[i][j])) {
                    Lote lote = controladorLote.obtenerLote(i, j);
                    if (lote.getEstado().equals(Lote.DISPONIBLE)) {
                        VistaSeleccionGenero vista = new VistaSeleccionGenero(vistaLogIn, i, j);
                        vista.setVisible(true);
                    } else {
                        VistaListaAnimales vista = new VistaListaAnimales(vistaLogIn, i, j);
                        vista.setVisible(true);
                    }
                    this.dispose();
                }
            }
        }
    }
}
