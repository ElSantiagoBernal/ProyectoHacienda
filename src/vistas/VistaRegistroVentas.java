/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import excepciones.CompletarCampoVacioException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import modeloAnimales.Animal;
import modeloAnimales.Caballo;
import modeloAnimales.Cerdo;
import modeloAnimales.Ganado;
import modeloAnimales.Venta;
import modeloPersonas.CriadorCerdo;
import modeloPersonas.EncargadoGanado;
import modeloPersonas.Persona;
import modeloPersonas.Usuario;

/**
 *
 * @author SANTIAGO
 */
public class VistaRegistroVentas extends javax.swing.JFrame {

    /**
     * Creates new form VistaRegistroVentas
     */
    VistaLogIn vistaLogIn;
    Animal animal;
    int fila;
    int columna;
    
    public VistaRegistroVentas(VistaLogIn vistaLogIn, Animal animal, int fila, int columna) {
        initComponents();
        setLocationRelativeTo(this);
        this.vistaLogIn = vistaLogIn;
        this.animal = animal;
        this.fila = fila;
        this.columna = columna;
    }
    
    public static String obtenerFechaActual() {
        Date fecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/YYYY");
        return formato.format(fecha);
    }
    
    private Double CalcularPrecioCerdo() {
        String pesoString = JOptionPane.showInputDialog("Escriba el peso del animal.");
        while (pesoString.isEmpty()) {
            pesoString = JOptionPane.showInputDialog("Escriba el peso del animal.");
        }
//        String precioKgString = JOptionPane.showInputDialog("Escriba el peso por kilo.");
//        while (precioKgString.isEmpty()) {
//            precioKgString = JOptionPane.showInputDialog("Escriba el peso por kilo.");
//        }
        Double peso = Double.valueOf(pesoString);
        Double precioKg = Double.valueOf(vistaLogIn.getControladorPocilga().getPrecioPorkg());
        Double precioCerdo = peso * precioKg;
        return precioCerdo;
    }
    
    private Double obtenerPrecioAnimal() {
        Double precio;
        if (animal instanceof Cerdo) {
            precio = CalcularPrecioCerdo();
        } else {
            String precioString = JOptionPane.showInputDialog("Precio del animal.");
            while (precioString.isEmpty()) {
                precioString = JOptionPane.showInputDialog("Precio del animal.");
            }
            precio = Double.valueOf(precioString);
        }
        return precio;
    }
    
    private void añadirToroVendidoAlEmpleado() {
        Usuario usuario = vistaLogIn.getControladorUsuario().getUsuarioLogueado();
        if (usuario instanceof EncargadoGanado) {
            if (animal instanceof Ganado) {
                if (animal.getGenero().equals(Animal.MACHO)) {
                    ((EncargadoGanado) usuario).añadirToroVendido();
                    vistaLogIn.getControladorUsuario().escribirUsuarios();
                }
            }
            
        }
    }
    
    private void añadirCerdoVendidoAlEmpleado() {
        Usuario usuario = vistaLogIn.getControladorUsuario().getUsuarioLogueado();
        if (usuario instanceof CriadorCerdo) {
            if (animal instanceof Cerdo) {
                ((CriadorCerdo) usuario).añadirVenta();
                vistaLogIn.getControladorUsuario().escribirUsuarios();
                
            }
            
        }
    }
    
    private void abrirVentanaUsuario() {
        if (animal instanceof Cerdo) {
            if (vistaLogIn.getControladorPocilga().obtenerPocilga(fila, columna).obtenerTamañoLista() < 1) {
                VistaCriadorDeCerdo vista = new VistaCriadorDeCerdo(vistaLogIn);
                vista.setVisible(true);
            } else {
                VistaListaAnimales vista = new VistaListaAnimales(vistaLogIn, fila, columna);
                vista.setVisible(true);
            }
        } else if (animal instanceof Caballo) {
            if (vistaLogIn.getControladorPesebrera().obtenerPesebrera(fila, columna).obtenerTamañoLista() < 1) {
                VistaEncargadoDelCaballo vista = new VistaEncargadoDelCaballo(vistaLogIn);
                vista.setVisible(true);
            } else {
                VistaListaAnimales vista = new VistaListaAnimales(vistaLogIn, fila, columna);
                vista.setVisible(true);
            }
        } else {
            if (vistaLogIn.getControladorLote().obtenerLote(fila, columna).obtenerTamañoLista() < 1) {
                VistaEncargadoDelGanado vista = new VistaEncargadoDelGanado(vistaLogIn);
                vista.setVisible(true);
            } else {
                VistaListaAnimales vista = new VistaListaAnimales(vistaLogIn, fila, columna);
                vista.setVisible(true);
            }
            
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

        jPanel1 = new javax.swing.JPanel();
        txtNombreComprador = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        btnGenerarVenta = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos comprador", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        txtNombreComprador.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombreComprador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNombreCompradorMouseClicked(evt);
            }
        });
        txtNombreComprador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreCompradorKeyTyped(evt);
            }
        });

        jLabel1.setText("Nombre(s)");

        jLabel2.setText("Apellido(s)");

        txtApellido.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtApellido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtApellidoMouseClicked(evt);
            }
        });
        txtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoKeyTyped(evt);
            }
        });

        jLabel4.setText("Telefono");

        txtTelefono.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTelefono.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTelefonoMouseClicked(evt);
            }
        });
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });

        btnGenerarVenta.setText("Generar venta");
        btnGenerarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVentaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNombreComprador, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(btnGenerarVenta)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombreComprador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(btnGenerarVenta)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGenerarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarVentaActionPerformed
        // TODO add your handling code here:
        try {
            if (txtNombreComprador.getText().isEmpty() || txtApellido.getText().isEmpty()
                    || txtTelefono.getText().isEmpty()) {
                throw new CompletarCampoVacioException();
            }
            String nombre = txtNombreComprador.getText();
            String apellido = txtApellido.getText();
            String telefono = txtTelefono.getText();
            Persona comprador = new Persona(nombre, apellido, "", 0);
            comprador.setTelefono(telefono);
            Venta venta = new Venta(animal, comprador, obtenerPrecioAnimal(), obtenerFechaActual());
            vistaLogIn.getHistorialVenta().agregarVentaHistorial(venta);
            añadirToroVendidoAlEmpleado();
            añadirCerdoVendidoAlEmpleado();
            System.out.println(vistaLogIn.getHistorialVenta().obtenerTamañoLista());
            JOptionPane.showMessageDialog(null, "Se registró la venta del animal.");
            this.dispose();
        } catch (CompletarCampoVacioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        

    }//GEN-LAST:event_btnGenerarVentaActionPerformed

    private void txtNombreCompradorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombreCompradorMouseClicked
        // TODO add your handling code here:
        txtNombreComprador.setText(null);
    }//GEN-LAST:event_txtNombreCompradorMouseClicked

    private void txtNombreCompradorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreCompradorKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNombreCompradorKeyTyped

    private void txtApellidoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtApellidoMouseClicked
        // TODO add your handling code here:
        txtApellido.setText(null);
    }//GEN-LAST:event_txtApellidoMouseClicked

    private void txtApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtApellidoKeyTyped

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (c < '0' || c > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void txtTelefonoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTelefonoMouseClicked
        // TODO add your handling code here:
        txtTelefono.setText(null);
    }//GEN-LAST:event_txtTelefonoMouseClicked

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerarVenta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtNombreComprador;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
