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
import controladores.HistorialEliminado;
import excepciones.AnimalEnColaException;
import excepciones.CerdoMachoException;
import excepciones.CompletarCampoVacioException;
import excepciones.CriaCerdoException;
import excepciones.HembraConCriaException;
import excepciones.LoteLlenoException;
import excepciones.NoHayCriaQueLiberarException;
import excepciones.NoHayEspacioEnNingunaPocilgaException;
import excepciones.ObjetoTablaNoSeleccionadoException;
import excepciones.PesebreraLlenaException;
import excepciones.PocilgaLlenaException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modeloAnimales.AccionRealizadaAnimal;
import modeloAnimales.Animal;
import modeloAnimales.ArgumentoEliminacion;
import modeloAnimales.Caballo;
import modeloAnimales.Cerdo;
import modeloAnimales.Ganado;
import modeloAnimales.Venta;
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
public class VistaListaAnimales extends javax.swing.JFrame {

    /**
     * Creates new form VistaListaAnimales
     */
    VistaLogIn vistaLogIn;
    ControladorLote controladorLote;
    ControladorPesebrera controladorPesebrera;
    ControladorUsuario controladorUsuario;
    ControladorPocilga controladorPocilga;
    DefaultTableModel modeloAnimales;
    private int fila;
    private int columna;

    public VistaListaAnimales(VistaLogIn vistaLogIn, int fila, int columna) {
        initComponents();
        setLocationRelativeTo(this);
        controladorLote = vistaLogIn.getControladorLote();
        controladorPesebrera = vistaLogIn.getControladorPesebrera();
        controladorPocilga = vistaLogIn.getControladorPocilga();
        controladorUsuario = vistaLogIn.getControladorUsuario();
        this.vistaLogIn = vistaLogIn;
        validarColumnasTabla(controladorUsuario.getUsuarioLogueado());
        validarGestion();
        this.fila = fila;
        this.columna = columna;
        cargarTabla();
        validarAccionesUsuario();
    }

    private void validarColumnasTabla(Usuario usuario) {
        if (usuario instanceof EncargadoGanado || VistaAdministrador.rolEscogido.equals("Ganado")) {
            declararColumnasTablaGanado();
        } else if (usuario instanceof CriadorCerdo || VistaAdministrador.rolEscogido.equals("Cerdo")) {
            declararColumnasTablaCerdos();
        } else if (usuario instanceof EncargadoCaballo || VistaAdministrador.rolEscogido.equals("Caballo")) {
            declararColumnasTablaCaballos();
        }

    }

    private void validarGestion() {
        Usuario usuario = controladorUsuario.getUsuarioLogueado();
        if (usuario instanceof EncargadoGanado || VistaAdministrador.rolEscogido.equals("Ganado")) {
            txtGestion.setText("Gestión de ganado");
        } else if (usuario instanceof CriadorCerdo || VistaAdministrador.rolEscogido.equals("Cerdo")) {
            txtGestion.setText("Gestión de cerdos");
        } else if (usuario instanceof EncargadoCaballo || VistaAdministrador.rolEscogido.equals("Caballo")) {
            txtGestion.setText("Gestión de caballos");
        }
    }

    private void desactivarBotonesYTxt() {
        btnGestionarPremios.setVisible(false);
        btnRegistrarCrias.setVisible(false);
        btnCuidadosCaballos.setVisible(false);
        btnGenerarVenta.setVisible(false);
        btnLiberar.setVisible(false);
        btnEditarPrecio.setVisible(false);
        pnlAccionLote.setVisible(false);
        txtPrecioPorKg.setVisible(false);
        textoPrecioPorKg.setVisible(false);
    }

    private void validarVentaCerdos() {
        String codigo = (String) modeloAnimales.getValueAt(0, 0);
        Animal animal = validarBusquedaAnimales(codigo);
        if (animal instanceof Cerdo) {
            if (((Cerdo) animal).isHembraAmamantando() == false || animal.getGenero().equals(Animal.MACHO)) {
                btnGenerarVenta.setVisible(true);
            }
        }
    }

    private void validarAccionesUsuario() {
        desactivarBotonesYTxt();
        Usuario usuario = controladorUsuario.getUsuarioLogueado();
        if (usuario instanceof EncargadoCaballo || VistaAdministrador.rolEscogido.equals("Caballo")) {
            btnGestionarPremios.setVisible(true);
            btnCuidadosCaballos.setVisible(true);
        } else if (usuario instanceof CriadorCerdo || VistaAdministrador.rolEscogido.equals("Cerdo")) {
            Pocilga pocilga = controladorPocilga.obtenerPocilga(fila, columna);
            if (pocilga.isCrias()) {
                btnLiberar.setVisible(true);
            }
            btnRegistrarCrias.setVisible(true);
            btnGenerarVenta.setVisible(true);
            txtPrecioPorKg.setVisible(true);
            textoPrecioPorKg.setVisible(true);
            btnEditarPrecio.setVisible(true);
            llenarCampoPrecio();
        } else if (usuario instanceof EncargadoGanado || VistaAdministrador.rolEscogido.equals("Ganado")) {
            String genero = buscarGeneroGanado();
            if (genero.equals(Animal.HEMBRA)) {
                pnlAccionLote.setVisible(true);
            } else {
                btnGenerarVenta.setVisible(true);
            }
        }
    }

    private void cargarTabla() {
        Usuario usuario = controladorUsuario.getUsuarioLogueado();
        if (usuario instanceof EncargadoGanado || VistaAdministrador.rolEscogido.equals("Ganado")) {
            cargarTablaGanado();
        } else if (usuario instanceof CriadorCerdo || VistaAdministrador.rolEscogido.equals("Cerdo")) {
            cargarTablaCerdos();
        } else if (usuario instanceof EncargadoCaballo || VistaAdministrador.rolEscogido.equals("Caballo")) {
            cargarTablaCaballos();
        }
    }

    private Animal buscarEnCola(String codigo) {
        for (int i = 0; i < vistaLogIn.getControladorColaVeterinario().size(); i++) {
            Animal animal = vistaLogIn.getControladorColaVeterinario().get(i);
            if (animal.getCodigoString().equals(codigo)) {
                return animal;
            }
        }
        return null;
    }

    private String buscarGeneroGanado() {
        Lote lote = controladorLote.obtenerLote(fila, columna);
        String genero = null;
        for (int i = 0; i < lote.obtenerTamañoLista(); i++) {
            genero = lote.recorrerLista(i).getGenero();
            break;
        }
        return genero;
    }

    private String buscarGeneroCaballos() {
        Pesebrera pesebrera = controladorPesebrera.obtenerPesebrera(fila, columna);
        String genero = null;
        for (int i = 0; i < pesebrera.obtenerTamañoLista(); i++) {
            genero = pesebrera.recorrerLista(i).getGenero();
            break;
        }
        return genero;
    }

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

    private String solicitarGeneroAnimal() {
        String botones[] = {"Hembra", "Macho"};
        int generos = JOptionPane.showOptionDialog(null, "¿Cuál es el genero del animal?", "Elija",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, botones, botones[0]);
        String generoString = " ";
        if (generos == 0) {
            generoString = "Hembra";
        } else {
            generoString = "Macho";
        }
        return generoString;
    }

    private int solicitarNumeroCrias() {
        String criasString = JOptionPane.showInputDialog("Ingresa el numero de crias.");
        while (criasString.isEmpty()) {
            criasString = JOptionPane.showInputDialog("Ingresa el numero de crias.");
        }
        int crias = Integer.parseInt(criasString);
        return crias;
    }

    private void añadirAnimal() {
        AccionRealizadaAnimal accion = new AccionRealizadaAnimal(null, AccionRealizadaAnimal.REGISTRAR);
        Usuario usuario = controladorUsuario.getUsuarioLogueado();
        if (usuario instanceof EncargadoCaballo || VistaAdministrador.rolEscogido.equals("Caballo")) {
            validarPesebrera();
            Caballo caballo = new Caballo(buscarGeneroCaballos(), solicitarNombreAnimal(), solicitarEdadAnimal(), solicitarPesoAnimal());
            controladorPesebrera.agregarALaPesebrera(caballo, fila, columna);
            añadirTablaCaballo(caballo);
            accion.setAnimal(caballo);
        } else if (usuario instanceof EncargadoGanado || VistaAdministrador.rolEscogido.equals("Ganado")) {
            validarLote();
            Ganado animal = new Ganado(buscarGeneroGanado(), solicitarNombreAnimal(), solicitarEdadAnimal(), solicitarPesoAnimal());
            controladorLote.agregarAlGanado(animal, fila, columna);
            añadirTablaGanado(animal);
            accion.setAnimal(animal);
        } else if (usuario instanceof CriadorCerdo || VistaAdministrador.rolEscogido.equals("Cerdo")) {
            validarPocilga(fila, columna);
            Cerdo cerdo = new Cerdo(solicitarGeneroAnimal(), solicitarNombreAnimal(), solicitarEdadAnimal(), solicitarPesoAnimal());
            controladorPocilga.agregarALaPocilga(cerdo, fila, columna);
            añadirTablaCerdo(cerdo);
            accion.setAnimal(cerdo);
        }
        vistaLogIn.getControladorPila().pushAccionPilaUnoAnimales(accion);
    }

    private void añadirTablaGanado(Ganado animal) {
        String[] datos = new String[5];
        datos[0] = animal.getCodigoString();
        datos[1] = animal.getNombre();
        datos[2] = animal.getGenero();
        datos[3] = String.valueOf(animal.getCantidadLecheEnLitros());
        datos[3] = String.valueOf(animal.getEdad());
        datos[4] = animal.getPeso();
        modeloAnimales.addRow(datos);
    }

    private void añadirTablaCerdo(Cerdo animal) {
        String[] datos = new String[6];
        datos[0] = animal.getCodigoString();
        datos[1] = animal.getNombre();
        datos[2] = animal.getGenero();
        datos[3] = String.valueOf(animal.getEdad());
        datos[4] = animal.getPeso();
        if (animal.isHembraAmamantando()) {
            datos[5] = "Si";
        } else {
            datos[5] = "No";
        }
        modeloAnimales.addRow(datos);
    }

    private void añadirTablaCaballo(Caballo animal) {
        String[] datos = new String[6];
        datos[0] = animal.getCodigoString();
        datos[1] = animal.getNombre();
        datos[2] = animal.getGenero();
        datos[3] = String.valueOf(animal.getEdad());
        datos[4] = animal.getPeso();
        if (animal.obtenerTamañoListaPremios() > 0) {
            datos[5] = "Si";
        } else {
            datos[5] = "No";
        }
        modeloAnimales.addRow(datos);
    }

    private void eliminarGanadoTabla(String codigo) {
        for (int i = 0; i < modeloAnimales.getRowCount(); i++) {
            if (modeloAnimales.getValueAt(i, 0).equals(codigo)) {
                modeloAnimales.removeRow(i);
            }
        }
    }

    private void eliminarCaballoTabla(String codigo) {
        for (int i = 0; i < modeloAnimales.getRowCount(); i++) {
            if (modeloAnimales.getValueAt(i, 0).equals(codigo)) {
                modeloAnimales.removeRow(i);
            }
        }
    }

    private void eliminarCerdoTabla(String codigo) {
        for (int i = 0; i < modeloAnimales.getRowCount(); i++) {
            if (modeloAnimales.getValueAt(i, 0).equals(codigo)) {
                modeloAnimales.removeRow(i);
            }
        }
    }

    private void abrirVentanaUsuario() {
        Usuario usuario = controladorUsuario.getUsuarioLogueado();
        if (usuario instanceof EncargadoCaballo || VistaAdministrador.rolEscogido.equals("Caballo")) {
            VistaEncargadoDelCaballo vista = new VistaEncargadoDelCaballo(vistaLogIn);
            vista.setVisible(true);
        } else if (usuario instanceof CriadorCerdo || VistaAdministrador.rolEscogido.equals("Cerdo")) {
            VistaCriadorDeCerdo vista = new VistaCriadorDeCerdo(vistaLogIn);
            vista.setVisible(true);
        } else if (usuario instanceof EncargadoGanado || VistaAdministrador.rolEscogido.equals("Ganado")) {
            VistaEncargadoDelGanado vista = new VistaEncargadoDelGanado(vistaLogIn);
            vista.setVisible(true);
        }
    }

    private void declararColumnasTablaGanado() {
        modeloAnimales = new DefaultTableModel();
        modeloAnimales.addColumn("Código");
        modeloAnimales.addColumn("Nombre");
        modeloAnimales.addColumn("Genero");
        modeloAnimales.addColumn("Edad");
        modeloAnimales.addColumn("Peso");
        this.tblAnimales.setModel(modeloAnimales);
    }

    private void declararColumnasTablaCerdos() {
        modeloAnimales = new DefaultTableModel();
        modeloAnimales.addColumn("Código");
        modeloAnimales.addColumn("Nombre");
        modeloAnimales.addColumn("Genero");
        modeloAnimales.addColumn("Edad");
        modeloAnimales.addColumn("Peso");
        modeloAnimales.addColumn("Amamantando");
        this.tblAnimales.setModel(modeloAnimales);
    }

    private void declararColumnasTablaCaballos() {
        modeloAnimales = new DefaultTableModel();
        modeloAnimales.addColumn("Código");
        modeloAnimales.addColumn("Nombre");
        modeloAnimales.addColumn("Genero");
        modeloAnimales.addColumn("Edad");
        modeloAnimales.addColumn("Peso");
        modeloAnimales.addColumn("Premios");
        this.tblAnimales.setModel(modeloAnimales);
    }

    private void cargarTablaGanado() {
        Lote lote = controladorLote.obtenerLote(fila, columna);
        for (int i = 0; i < lote.obtenerTamañoLista(); i++) {
            Ganado ganado = lote.recorrerLista(i);
            String[] datos = new String[5];
            datos[0] = ganado.getCodigoString();
            datos[1] = ganado.getNombre();
            datos[2] = ganado.getGenero();
            datos[3] = String.valueOf(ganado.getEdad());
            datos[4] = ganado.getPeso();
            modeloAnimales.addRow(datos);
        }
    }

    private void cargarTablaCerdos() {
        Pocilga pocilga = controladorPocilga.obtenerPocilga(fila, columna);
        Cerdo cerdo = null;
        for (int i = 0; i < pocilga.obtenerTamañoLista(); i++) {
            cerdo = pocilga.recorrerLista(i);
            String[] datos = new String[6];
            datos[0] = cerdo.getCodigoString();
            datos[1] = cerdo.getNombre();
            datos[2] = cerdo.getGenero();
            datos[3] = String.valueOf(cerdo.getEdad());
            datos[4] = cerdo.getPeso();
            if (cerdo.isHembraAmamantando()) {
                datos[5] = "Si";
            } else {
                datos[5] = "No";
            }
            ocultarColumnas();
            modeloAnimales.addRow(datos);
        }
    }

    private void cargarTablaCaballos() {
        Pesebrera pesebrera = controladorPesebrera.obtenerPesebrera(fila, columna);
        for (int i = 0; i < pesebrera.obtenerTamañoLista(); i++) {
            Caballo caballo = pesebrera.recorrerLista(i);
            String[] datos = new String[6];
            datos[0] = caballo.getCodigoString();
            datos[1] = caballo.getNombre();
            datos[2] = caballo.getGenero();
            datos[3] = String.valueOf(caballo.getEdad());
            datos[4] = caballo.getPeso();
            if (caballo.obtenerTamañoListaPremios() > 0) {
                datos[5] = "Si";
            } else {
                datos[5] = "No";
            }
            modeloAnimales.addRow(datos);
        }
    }

    private void pasarCerdoOtraPocilga(String codigo) {
        for (int i = 0; i < controladorPocilga.retornarFilas(); i++) {
            for (int j = 0; j < controladorPocilga.retornarColumnas(); j++) {
                Pocilga otraPocilga = controladorPocilga.obtenerPocilga(i, j);
                if (i == 1 && j == 7) {
                    System.out.println("Entro a la ultima");
                    if (otraPocilga.getEstado().equals(Pocilga.OCUPADO) || otraPocilga.isCrias() == true) {
                        throw new NoHayEspacioEnNingunaPocilgaException();
                    }
                }
                Cerdo cerdo = controladorPocilga.buscarEnPocilga(codigo, fila, columna);
                if (cerdo.getGenero().equals("Cria")) {
                    if (cerdo.getNombre().isEmpty()) {
                        cerdo.setNombre(solicitarNombreAnimal());
                    }
                    cerdo.setGenero(solicitarGeneroAnimal());
                    cerdo.setPeso(solicitarPesoAnimal());
                }
                if (otraPocilga.getEstado().equals(Pocilga.DISPONIBLE) || otraPocilga.isCrias() == false) {
                    if (otraPocilga.getNumeroAnimales() != 4) {
                        controladorPocilga.agregarCerdoProvenienteOtraPocilga(cerdo, i, j);
                        return;
                    }
                }
            }
        }
    }

    private void eliminarCerdoMenosHembra(String codigo) {
        for (int i = 0; i < modeloAnimales.getRowCount(); i++) {
            String code = (String) modeloAnimales.getValueAt(i, 0);
            if (code != codigo) {
                pasarCerdoOtraPocilga(code);
                controladorPocilga.eliminarDeLaPocilga(code, fila, columna);
            }
        }
    }

    private void liberarCrias() {
        for (int i = 0; i < modeloAnimales.getRowCount(); i++) {
            String genero = (String) modeloAnimales.getValueAt(i, 2);
            String code = (String) modeloAnimales.getValueAt(i, 0);
            if (genero.equals("Cria")) {
                pasarCerdoOtraPocilga(code);
                controladorPocilga.eliminarDeLaPocilga(code, fila, columna);
            }
        }
    }

    private void eliminarObjetosTabla() {
        modeloAnimales.setRowCount(0);
    }

    private void validarLote() {
        Lote lote = controladorLote.obtenerLote(fila, columna);
        if (lote.getNumeroAnimales() == 20) {
            throw new LoteLlenoException();
        }
    }

    private void validarPesebrera() {
        Pesebrera pesebrera = controladorPesebrera.obtenerPesebrera(fila, columna);
        if (pesebrera.getNumeroAnimales() == 2) {
            throw new PesebreraLlenaException();
        }
    }

    private void validarPocilga(int fil, int column) {
        Pocilga pocilga = controladorPocilga.obtenerPocilga(fil, column);
        if (pocilga.getNumeroAnimales() == 4) {
            throw new PocilgaLlenaException();
        }
    }

    private Animal validarBusquedaAnimales(String codigo) {
        Usuario usuario = controladorUsuario.getUsuarioLogueado();
        Animal animal = null;
        if (usuario instanceof EncargadoGanado || VistaAdministrador.rolEscogido.equals("Ganado")) {
            Ganado ganado = controladorLote.buscarEnElGanado(codigo, fila, columna);
            return ganado;
        } else if (usuario instanceof CriadorCerdo || VistaAdministrador.rolEscogido.equals("Cerdo")) {
            animal = controladorPocilga.buscarEnPocilga(codigo, fila, columna);
        } else if (usuario instanceof EncargadoCaballo || VistaAdministrador.rolEscogido.equals("Caballo")) {
            animal = controladorPesebrera.buscarEnPesebrera(codigo, fila, columna);
        }
        return animal;
    }

    private void validarHembraAmamantando(Animal cerdo) {
        if (cerdo instanceof Cerdo) {
            if (((Cerdo) cerdo).isHembraAmamantando()) {
                throw new HembraConCriaException();
            }
        }

    }

    private void validarLiberacionCrias() {
        int cantidad = 0;
        for (int i = 0; i < modeloAnimales.getRowCount(); i++) {
            String genero = (String) modeloAnimales.getValueAt(i, 2);
            if (genero.equals("Cria")) {
                cantidad += 1;
            }
        }
        if (cantidad == 0) {
            throw new NoHayCriaQueLiberarException();
        }
    }

    private void validarAgregarCerdos() {
        Usuario usuario = controladorUsuario.getUsuarioLogueado();
        if (usuario instanceof CriadorCerdo || VistaAdministrador.rolEscogido.equals("Cerdo")) {
            Pocilga pocilga = controladorPocilga.obtenerPocilga(fila, columna);
            if (pocilga.isCrias()) {
                throw new HembraConCriaException();
            }
        }
    }

    private void cambiarEstadoHembra() {
        Usuario usuario = controladorUsuario.getUsuarioLogueado();
        if (usuario instanceof CriadorCerdo || VistaAdministrador.rolEscogido.equals("Cerdo")) {
            for (int i = 0; i < modeloAnimales.getRowCount(); i++) {
                String codigo = (String) modeloAnimales.getValueAt(i, 0);
                Cerdo cerdo = controladorPocilga.buscarEnPocilga(codigo, fila, columna);
                if (cerdo.isHembraAmamantando()) {
                    cerdo.setHembraAmamantando(false);
                    Pocilga pocilga = controladorPocilga.obtenerPocilga(fila, columna);
                    pocilga.setCrias(false);
                    controladorPocilga.escribirPocilgas();
                }
            }
        }

    }

    private void ocultarColumnas() {
        tblAnimales.getColumnModel().getColumn(5).setMaxWidth(0);
        tblAnimales.getColumnModel().getColumn(5).setMinWidth(0);
        tblAnimales.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(0);
        tblAnimales.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(0);
        tblAnimales.getTableHeader().getColumnModel().getColumn(5).setMinWidth(0);
    }

    private void editarLecheVacaTabla(int seleccion, double leche) {
        modeloAnimales.setValueAt(leche, seleccion, 3);
    }

    private void editarInfoAnimalTabla(int seleccion, String nombre, String nuevoPeso, int nuevaEdad) {
        modeloAnimales.setValueAt(nombre, seleccion, 1);
        modeloAnimales.setValueAt(nuevaEdad, seleccion, 3);
        modeloAnimales.setValueAt(nuevoPeso, seleccion, 4);
    }

    private void limpiarCampos() {
        txtNombreAnimal.setText(null);
        txtEdad.setText(null);
    }

    private void eliminarAnimal(Animal animal) {
        if (animal instanceof Cerdo) {
            if (controladorPocilga.obtenerPocilga(fila, columna).obtenerTamañoLista() == 1) {
                controladorPocilga.eliminarDeLaPocilga(animal.getCodigoString(), fila, columna);
                controladorPocilga.obtenerPocilga(fila, columna).setEstado(Pocilga.DISPONIBLE);
            } else {
                controladorPocilga.eliminarDeLaPocilga(animal.getCodigoString(), fila, columna);
            }
            eliminarCerdoTabla(animal.getCodigoString());
            controladorPocilga.escribirPocilgas();
        } else if (animal instanceof Caballo) {
            if (controladorPesebrera.obtenerPesebrera(fila, columna).obtenerTamañoLista() == 1) {
                controladorPesebrera.eliminarDeLaPesebrera(animal.getCodigoString(), fila, columna);
                controladorPesebrera.obtenerPesebrera(fila, columna).setEstado(Pesebrera.DISPONIBLE);
            } else {
                controladorPocilga.eliminarDeLaPocilga(animal.getCodigoString(), fila, columna);
            }
            eliminarCaballoTabla(animal.getCodigoString());
            controladorPesebrera.escribirPesebreras();
        } else {
            if (controladorLote.obtenerLote(fila, columna).obtenerTamañoLista() == 1) {
                controladorLote.eliminarDelGanado(animal.getCodigoString(), fila, columna);
                controladorLote.obtenerLote(fila, columna).setEstado(Pesebrera.DISPONIBLE);
            } else {
                controladorLote.eliminarDelGanado(animal.getCodigoString(), fila, columna);
            }
            eliminarGanadoTabla(animal.getCodigoString());
            controladorLote.escribirLotes();
        }
    }

    private void editarInfoAnimal(Animal animal, String nuevoNombre, int nuevaEdad, String nuevoPeso) {
        animal.setNombre(nuevoNombre);
        animal.setPeso(nuevoPeso);
        animal.setEdad(nuevaEdad);
        if (animal instanceof Cerdo) {
            controladorPocilga.escribirPocilgas();
        } else if (animal instanceof Ganado) {
            controladorLote.escribirLotes();
        } else {
            controladorPesebrera.escribirPesebreras();
        }
    }

    private void agregarAnimalProvenientePila(Animal animal) {
        if (animal instanceof Cerdo) {
            controladorPocilga.agregarCerdoProvenientePila((Cerdo) animal, fila, columna);
        } else if (animal instanceof Caballo) {
            controladorPesebrera.agregarCaballoProvenientePila((Caballo) animal, fila, columna);
        } else {
            controladorLote.agregarGanadoProvenientePila((Ganado) animal, fila, columna);
        }
    }

    private void limpiarCampoPrecio() {
        txtPrecioPorKg.setText(null);
    }

    private void llenarCampoPrecio() {
        txtPrecioPorKg.setText(controladorPocilga.getPrecioPorkg());
    }

    private void añadirToroVendidoEmpleado() {
        Usuario usuario = vistaLogIn.getControladorUsuario().getUsuarioLogueado();
        if (usuario instanceof EncargadoGanado) {
            ((EncargadoGanado) usuario).añadirToroVendido();
            vistaLogIn.getControladorUsuario().escribirUsuarios();
        }
    }

    private void eliminarToroVendido() {
        Usuario usuario = vistaLogIn.getControladorUsuario().getUsuarioLogueado();
        if (usuario instanceof EncargadoGanado) {
            ((EncargadoGanado) usuario).restarToroVendido();
            vistaLogIn.getControladorUsuario().escribirUsuarios();
        }
    }

    private void añadirCerdoVendidoEmpleado() {
        Usuario usuario = vistaLogIn.getControladorUsuario().getUsuarioLogueado();
        if (usuario instanceof CriadorCerdo) {
            ((CriadorCerdo) usuario).añadirVenta();
            vistaLogIn.getControladorUsuario().escribirUsuarios();
        }
    }

    private void eliminarCerdoVendidoEmpleado() {
        Usuario usuario = vistaLogIn.getControladorUsuario().getUsuarioLogueado();
        if (usuario instanceof CriadorCerdo) {
            ((CriadorCerdo) usuario).restarVenta();
            vistaLogIn.getControladorUsuario().escribirUsuarios();
        }
    }

//    private void realizarAccionAdelantePila() {
//        Usuario usuario = controladorUsuario.getUsuarioLogueado();
//        if (usuario instanceof EncargadoCaballo || VistaAdministrador.rolEscogido.equals("Caballo")) {
//
//        } else if (usuario instanceof EncargadoGanado || VistaAdministrador.rolEscogido.equals("Ganado")) {
//
//        } else if (usuario instanceof CriadorCerdo || VistaAdministrador.rolEscogido.equals("Cerdo")) {
//            realizarAccionAdelantePilaCerdos();
//        }
//
//    }
    private void realizarAccionAdelantePilaAnimales() {
//        if (vistaLogIn.getControladorPila().pilaDosIsEmpty() == false) {
//            AccionRealizadaAnimal accion = vistaLogIn.getControladorPila().popAccionPilaDos();
////            ArgumentoEliminacion argumentoEliminacion = vistaLogIn.getHistorialEliminado().getArgumentoEliminacion();
//            if (accion.getAccion().equals(AccionRealizadaAnimal.ELIMINAR)) {
//                agregarAnimalProvenientePila(accion.getAnimal());
//                System.out.println("codigo animal: " + accion.getAnimal().getCodigoString());
////                argumentoEliminacion = vistaLogIn.getHistorialEliminado().obtenerArgumento(accion.getAnimal().getCodigoString());
////                vistaLogIn.getHistorialEliminado().setArgumentoEliminacion(argumentoEliminacion);
////                vistaLogIn.getHistorialEliminado().eliminarAnimalListaEliminados(accion.getAnimal().getCodigoString());
//                accion.setAccion(AccionRealizadaAnimal.REGISTRAR);
////                vistaLogIn.getControladorPila().pushAccionPilaUno(accion);
//                añadirTablaCerdo((Cerdo) accion.getAnimal());
//            } else if (accion.getAccion().equals(AccionRealizadaAnimal.REGISTRAR)) {
//                eliminarAnimal(accion.getAnimal());
////                if (argumentoEliminacion != null) {
////                    vistaLogIn.getHistorialEliminado().agregarAnimalEliminado(argumentoEliminacion);
////                }
//                accion.setAccion(AccionRealizadaAnimal.ELIMINAR);
////                vistaLogIn.getControladorPila().pushAccionPilaUno(accion);
//                eliminarObjetosTabla();
//                cargarTabla();
//            }
//            vistaLogIn.getControladorPila().pushAccionPilaUno(accion);
//        } else {
//            JOptionPane.showMessageDialog(null, "No hay pila adelante");
//        }
    }

//    private void realizarAccionAtrasPila() {
//        Usuario usuario = controladorUsuario.getUsuarioLogueado();
//        if (usuario instanceof EncargadoCaballo || VistaAdministrador.rolEscogido.equals("Caballo")) {
//            
//        } else if (usuario instanceof EncargadoGanado || VistaAdministrador.rolEscogido.equals("Ganado")) {
//            
//        } else if (usuario instanceof CriadorCerdo || VistaAdministrador.rolEscogido.equals("Cerdo")) {
//////            realizarAccionAtrasPilaCerdos();
//        }
//        
//    }
//    private void realizarAccionAtrasPilaAimales() {
//        if (vistaLogIn.getControladorPila().pilaUnoIsEmpty() == false) {
//            AccionRealizadaAnimal accion = vistaLogIn.getControladorPila().popAccionPilaUno();
//            if (vistaLogIn.getControladorPila().pilaUnoIsEmpty()) {
//                System.out.println("Pila uno vacia.");
//            }
////            ArgumentoEliminacion argumentoEliminacion = vistaLogIn.getHistorialEliminado().getArgumentoEliminacion();
//            if (accion.getAccion().equals(AccionRealizadaAnimal.REGISTRAR)) {
//                eliminarAnimal(accion.getAnimal());
////                if (argumentoEliminacion != null) {
////                    vistaLogIn.getHistorialEliminado().agregarAnimalEliminado(argumentoEliminacion);
////                }
//                accion.setAccion(AccionRealizadaAnimal.ELIMINAR);
////                vistaLogIn.getControladorPila().pushAccionPilaDos(accion);
//                eliminarObjetosTabla();
//                cargarTabla();
//            } else if (accion.getAccion().equals(AccionRealizadaAnimal.ELIMINAR)) {
//                agregarAnimalProvenientePila(accion.getAnimal());
////                argumentoEliminacion = vistaLogIn.getHistorialEliminado().obtenerArgumento(accion.getAnimal().getCodigoString());
////                vistaLogIn.getHistorialEliminado().setArgumentoEliminacion(argumentoEliminacion);
////                vistaLogIn.getHistorialEliminado().eliminarAnimalListaEliminados(accion.getAnimal().getCodigoString());
//                accion.setAccion(AccionRealizadaAnimal.REGISTRAR);
////                vistaLogIn.getControladorPila().pushAccionPilaDos(accion);
//                añadirTablaCerdo((Cerdo) accion.getAnimal());
//            } else if (accion.getAccion().equals(AccionRealizadaAnimal.EDITAR)) {
//
//            }
//
//            vistaLogIn.getControladorPila().pushAccionPilaDos(accion);
//        } else {
//            JOptionPane.showMessageDialog(null, "No hay nada en la pila");
//        }
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtGestion = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAnimales = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnAñadir = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnRemitir = new javax.swing.JButton();
        txtNombreAnimal = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnEditar = new javax.swing.JButton();
        Jlabel2 = new javax.swing.JLabel();
        txtEdad = new javax.swing.JTextField();
        btnHistoriaClinica = new javax.swing.JButton();
        btnRegistrarCrias = new javax.swing.JButton();
        btnGestionarPremios = new javax.swing.JButton();
        btnGenerarVenta = new javax.swing.JButton();
        btnCuidadosCaballos = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtPeso = new javax.swing.JTextField();
        btnLiberar = new javax.swing.JButton();
        pnlAccionLote = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        txtPrecioPorKg = new javax.swing.JTextField();
        textoPrecioPorKg = new javax.swing.JLabel();
        btnEditarPrecio = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        btnAtras = new javax.swing.JButton();
        btnAdelante = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtGestion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtGestion.setText("Gestión de");

        tblAnimales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblAnimales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAnimalesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAnimales);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Acciones animales", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        btnAñadir.setText("Añadir");
        btnAñadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnRemitir.setText("Remitir");
        btnRemitir.setActionCommand("Remitir al \nveterinario");
        btnRemitir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemitirActionPerformed(evt);
            }
        });

        txtNombreAnimal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombreAnimal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNombreAnimalMouseClicked(evt);
            }
        });
        txtNombreAnimal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreAnimalKeyTyped(evt);
            }
        });

        jLabel2.setText("Nombre");

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        Jlabel2.setText("Edad");

        txtEdad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEdad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtEdadMouseClicked(evt);
            }
        });
        txtEdad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEdadKeyTyped(evt);
            }
        });

        btnHistoriaClinica.setText("Historia clínica");
        btnHistoriaClinica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistoriaClinicaActionPerformed(evt);
            }
        });

        btnRegistrarCrias.setText("Registrar crias");
        btnRegistrarCrias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarCriasActionPerformed(evt);
            }
        });

        btnGestionarPremios.setText("Gestionar premios");
        btnGestionarPremios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGestionarPremiosActionPerformed(evt);
            }
        });

        btnGenerarVenta.setText("Generar venta");
        btnGenerarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVentaActionPerformed(evt);
            }
        });

        btnCuidadosCaballos.setText("Cuidados");
        btnCuidadosCaballos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCuidadosCaballosActionPerformed(evt);
            }
        });

        jLabel1.setText("Peso");

        txtPeso.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPeso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPesoMouseClicked(evt);
            }
        });
        txtPeso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesoActionPerformed(evt);
            }
        });
        txtPeso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPesoKeyTyped(evt);
            }
        });

        btnLiberar.setText("Liberar crias");
        btnLiberar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLiberarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnAñadir)
                        .addGap(13, 13, 13)
                        .addComponent(btnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnRegistrarCrias)
                        .addGap(6, 6, 6)
                        .addComponent(btnGenerarVenta))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(btnRemitir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHistoriaClinica)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPeso, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreAnimal, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Jlabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnCuidadosCaballos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGestionarPremios)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnLiberar)
                .addGap(79, 79, 79))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreAnimal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(Jlabel2)
                    .addComponent(txtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtPeso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditar)
                    .addComponent(btnAñadir)
                    .addComponent(btnEliminar))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemitir)
                    .addComponent(btnHistoriaClinica))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCuidadosCaballos)
                    .addComponent(btnGestionarPremios))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrarCrias)
                    .addComponent(btnGenerarVenta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(btnLiberar))
        );

        pnlAccionLote.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lote", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jButton1.setText("Pasar a ordeño");

        javax.swing.GroupLayout pnlAccionLoteLayout = new javax.swing.GroupLayout(pnlAccionLote);
        pnlAccionLote.setLayout(pnlAccionLoteLayout);
        pnlAccionLoteLayout.setHorizontalGroup(
            pnlAccionLoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAccionLoteLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(68, 68, 68))
        );
        pnlAccionLoteLayout.setVerticalGroup(
            pnlAccionLoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAccionLoteLayout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        txtPrecioPorKg.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecioPorKg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPrecioPorKgMouseClicked(evt);
            }
        });
        txtPrecioPorKg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioPorKgKeyTyped(evt);
            }
        });

        textoPrecioPorKg.setText("Precio por kg");

        btnEditarPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarPrecioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlAccionLote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 10, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtGestion, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(textoPrecioPorKg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPrecioPorKg, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditarPrecio)
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGestion, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecioPorKg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoPrecioPorKg)
                    .addComponent(btnEditarPrecio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlAccionLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        btnVolver.setText("volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        btnAtras.setText("Atras");
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });

        btnAdelante.setText("Adelante");
        btnAdelante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdelanteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(265, 265, 265)
                        .addComponent(btnAtras)
                        .addGap(41, 41, 41)
                        .addComponent(btnAdelante)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVolver)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVolver)
                    .addComponent(btnAtras)
                    .addComponent(btnAdelante))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAñadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirActionPerformed
        // TODO add your handling code here:
        try {
            validarAgregarCerdos();
            añadirAnimal();
            JOptionPane.showMessageDialog(null, "Se registro el animal.");
        } catch (LoteLlenoException | PesebreraLlenaException | PocilgaLlenaException
                | HembraConCriaException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnAñadirActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        try {
            int seleccion = tblAnimales.getSelectedRow();
            if (seleccion < 0) {
                throw new ObjetoTablaNoSeleccionadoException();
            }
            String codigo = (String) tblAnimales.getValueAt(seleccion, 0);
            Animal animal = validarBusquedaAnimales(codigo);
            eliminarAnimal(animal);
            AccionRealizadaAnimal accion = new AccionRealizadaAnimal(animal, AccionRealizadaAnimal.ELIMINAR);
            vistaLogIn.getControladorPila().pushAccionPilaUnoAnimales(accion);
            VistaRazonEliminacion vista = new VistaRazonEliminacion(vistaLogIn, animal);
            vista.setVisible(true);
            limpiarCampos();
        } catch (ObjetoTablaNoSeleccionadoException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tblAnimalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAnimalesMouseClicked
        // TODO add your handling code here:
        int seleccion = tblAnimales.getSelectedRow();
        String nombre = (String) tblAnimales.getValueAt(seleccion, 1);
//        String codigo = (String) tblAnimales.getValueAt(seleccion, 0);
        String edad = (String) tblAnimales.getValueAt(seleccion, 3);
        String peso = (String) tblAnimales.getValueAt(seleccion, 4);
//        Animal animal = validarBusquedaAnimales(codigo);
        txtNombreAnimal.setText(nombre);
        txtEdad.setText(edad);
        txtPeso.setText(peso);
    }//GEN-LAST:event_tblAnimalesMouseClicked

    private void btnRemitirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemitirActionPerformed
        // TODO add your handling code here:
        try {
            int seleccion = tblAnimales.getSelectedRow();
            if (seleccion < 0) {
                throw new ObjetoTablaNoSeleccionadoException();
            }
            String codigo = (String) modeloAnimales.getValueAt(seleccion, 0);
            Animal cola = buscarEnCola(codigo);
            if (cola == null) {
                Animal animal = validarBusquedaAnimales(codigo);
                if (animal != null) {
                    String problema = JOptionPane.showInputDialog("Problema por el cual se remite.");
                    while (problema == null) {
                        problema = JOptionPane.showInputDialog("Problema por el cual se remite.");
                    }
                    animal.setProblemaRemisionVeterinario(problema);
                    vistaLogIn.getControladorColaVeterinario().encolar(animal);
                    System.out.println(vistaLogIn.getControladorColaVeterinario().size());
                    JOptionPane.showMessageDialog(null, "Se remitió el animal al veterinario.");
                } else {
                    JOptionPane.showMessageDialog(null, "El animal no esta registrado");
                }
            } else {
                throw new AnimalEnColaException();
            }
        } catch (ObjetoTablaNoSeleccionadoException | AnimalEnColaException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnRemitirActionPerformed

    private void btnGestionarPremiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGestionarPremiosActionPerformed
        // TODO add your handling code here:
        try {
            int seleccion = tblAnimales.getSelectedRow();
            if (seleccion < 0) {
                throw new ObjetoTablaNoSeleccionadoException();
            }
            String codigo = (String) modeloAnimales.getValueAt(seleccion, 0);
            Animal animal = validarBusquedaAnimales(codigo);
            VistaRegistroPremiosCaballo vista = new VistaRegistroPremiosCaballo(vistaLogIn, (Caballo) animal, fila, columna);
            vista.setVisible(true);
            this.dispose();
        } catch (ObjetoTablaNoSeleccionadoException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }//GEN-LAST:event_btnGestionarPremiosActionPerformed

    private void btnRegistrarCriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarCriasActionPerformed
        // TODO add your handling code here:
        try {
            int seleccion = tblAnimales.getSelectedRow();
            if (seleccion < 0) {
                throw new ObjetoTablaNoSeleccionadoException();
            }
            String codigo = (String) modeloAnimales.getValueAt(seleccion, 0);
            Animal animal = validarBusquedaAnimales(codigo);
            if (animal.getGenero().equals(Animal.MACHO)) {
                throw new CerdoMachoException();
            } else if (animal.getGenero().equals("Cria")) {
                throw new CriaCerdoException();
            }
            Pocilga pocilga = controladorPocilga.obtenerPocilga(fila, columna);
            pocilga.setCrias(true);
            int numeroCrias = solicitarNumeroCrias();
            eliminarCerdoMenosHembra(codigo);
            eliminarObjetosTabla();
            controladorPocilga.añadirCriasCerdo(codigo, numeroCrias, fila, columna);
            cargarTabla();
            JOptionPane.showMessageDialog(null, "Se agregaron las crias.");
            btnLiberar.setVisible(true);
            vistaLogIn.getControladorPila().limpiarPilas();
        } catch (ObjetoTablaNoSeleccionadoException | CerdoMachoException | CriaCerdoException
                | NoHayEspacioEnNingunaPocilgaException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }//GEN-LAST:event_btnRegistrarCriasActionPerformed

    private void btnGenerarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarVentaActionPerformed
        // TODO add your handling code here:
        try {
            int seleccion = tblAnimales.getSelectedRow();
            if (seleccion < 0) {
                throw new ObjetoTablaNoSeleccionadoException();
            }
            if (txtPrecioPorKg.isVisible()) {
                if (controladorPocilga.getPrecioPorkg() == null) {
                    JOptionPane.showMessageDialog(null, "Debes diligenciar el precio por kg.");
                    return;
                }
            }
            String codigo = (String) modeloAnimales.getValueAt(seleccion, 0);
            Animal animal = validarBusquedaAnimales(codigo);
            validarHembraAmamantando(animal);
            eliminarAnimal(animal);
            ArgumentoEliminacion argumentoEliminacion = new ArgumentoEliminacion(animal, "Venta");
            cambiarEstadoHembra();
            vistaLogIn.getHistorialEliminado().agregarAnimalEliminado(argumentoEliminacion);
            AccionRealizadaAnimal accion = new AccionRealizadaAnimal(animal, AccionRealizadaAnimal.VENDER);
            vistaLogIn.getControladorPila().pushAccionPilaUnoAnimales(accion);
            VistaRegistroVentas vista = new VistaRegistroVentas(vistaLogIn, animal, fila, columna);
            vista.setVisible(true);
        } catch (ObjetoTablaNoSeleccionadoException | HembraConCriaException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }


    }//GEN-LAST:event_btnGenerarVentaActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        try {
            int seleccion = tblAnimales.getSelectedRow();
            if (seleccion < 0) {
                throw new ObjetoTablaNoSeleccionadoException();
            } else if (txtNombreAnimal.getText().isEmpty() || txtEdad.getText().isEmpty()
                    || txtPeso.getText().isEmpty()) {
                throw new CompletarCampoVacioException();
            }
            String nuevoNombre = txtNombreAnimal.getText();
            String nuevoPeso = txtPeso.getText();
            int nuevaEdad = Integer.parseInt(txtEdad.getText());
            String codigo = (String) modeloAnimales.getValueAt(seleccion, 0);
            Animal animal = validarBusquedaAnimales(codigo);
//            AccionRealizadaAnimal accion = new AccionRealizadaAnimal(animal, AccionRealizadaAnimal.EDITAR);
//            vistaLogIn.getControladorPila().pushAccionPilaUnoAnimales(accion);
            editarInfoAnimal(animal, nuevoNombre, nuevaEdad, nuevoPeso);
            editarInfoAnimalTabla(seleccion, nuevoNombre, nuevoPeso, nuevaEdad);
//            vistaLogIn.getControladorPila().pushPilaAnimalesEditados(animal);
            JOptionPane.showMessageDialog(null, "Se editó la información del animal");
        } catch (ObjetoTablaNoSeleccionadoException | CompletarCampoVacioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void txtNombreAnimalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombreAnimalMouseClicked
        // TODO add your handling code here:
        txtNombreAnimal.setText(null);
    }//GEN-LAST:event_txtNombreAnimalMouseClicked

    private void txtEdadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEdadMouseClicked
        // TODO add your handling code here:
        txtEdad.setText(null);
    }//GEN-LAST:event_txtEdadMouseClicked

    private void btnHistoriaClinicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistoriaClinicaActionPerformed
        // TODO add your handling code here:
        try {
            int seleccion = tblAnimales.getSelectedRow();
            if (seleccion < 0) {
                throw new ObjetoTablaNoSeleccionadoException();
            }
            String codigo = (String) modeloAnimales.getValueAt(seleccion, 0);
            Animal animal = validarBusquedaAnimales(codigo);
            VistaHistorialClinico vista = new VistaHistorialClinico(vistaLogIn, animal, fila, columna);
            vista.setVisible(true);
            this.dispose();
        } catch (ObjetoTablaNoSeleccionadoException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }//GEN-LAST:event_btnHistoriaClinicaActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        // TODO add your handling code here:
        abrirVentanaUsuario();
        this.dispose();
        vistaLogIn.getControladorPila().limpiarPilas();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnCuidadosCaballosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCuidadosCaballosActionPerformed
        // TODO add your handling code here:
        try {
            int seleccion = tblAnimales.getSelectedRow();
            if (seleccion < 0) {
                throw new ObjetoTablaNoSeleccionadoException();
            }
            String codigo = (String) modeloAnimales.getValueAt(seleccion, 0);
            Animal animal = validarBusquedaAnimales(codigo);
            VistaCuidadosCaballo vista = new VistaCuidadosCaballo(vistaLogIn, (Caballo) animal, fila, columna);
            vista.setVisible(true);
            this.dispose();
            vistaLogIn.getControladorPila().limpiarPilas();
        } catch (ObjetoTablaNoSeleccionadoException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnCuidadosCaballosActionPerformed

    private void txtPesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesoActionPerformed

    private void txtPesoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPesoMouseClicked
        // TODO add your handling code here:
        txtPeso.setText(null);
    }//GEN-LAST:event_txtPesoMouseClicked

    private void btnLiberarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLiberarActionPerformed
        // TODO add your handling code here:
        try {
            validarLiberacionCrias();
            liberarCrias();
            eliminarObjetosTabla();
            cargarTabla();
            cambiarEstadoHembra();
            JOptionPane.showMessageDialog(null, "Se liberaron la crias.");
            btnLiberar.setVisible(false);
        } catch (NoHayCriaQueLiberarException | NoHayEspacioEnNingunaPocilgaException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnLiberarActionPerformed

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        // TODO add your handling code here:
        if (vistaLogIn.getControladorPila().pilaUnoAnimalesIsEmpty() == false) {
            AccionRealizadaAnimal accion = vistaLogIn.getControladorPila().popAccionPilaUnoAnimales();
            System.out.println("Accion: " + accion.getAccion());
            if (accion.getAccion().equals(AccionRealizadaAnimal.REGISTRAR)) {
                eliminarAnimal(accion.getAnimal());
                accion.setAccion(AccionRealizadaAnimal.ELIMINAR);
            } else if (accion.getAccion().equals(AccionRealizadaAnimal.ELIMINAR)) {
                agregarAnimalProvenientePila(accion.getAnimal());
                accion.setAccion(AccionRealizadaAnimal.REGISTRAR);
                ArgumentoEliminacion argumentoEliminacion = vistaLogIn.getHistorialEliminado().obtenerArgumento(accion.getAnimal().getCodigoString());
                vistaLogIn.getControladorPila().pushPilaArgumentoEliminacion(argumentoEliminacion);
                vistaLogIn.getHistorialEliminado().eliminarAnimalListaEliminados(accion.getAnimal().getCodigoString());
                eliminarObjetosTabla();
                cargarTabla();
            } else if (accion.getAccion().equals(AccionRealizadaAnimal.VENDER)) {
                agregarAnimalProvenientePila(accion.getAnimal());
                accion.setAccion(AccionRealizadaAnimal.DESVENDER);
                ArgumentoEliminacion argumentoEliminacion = vistaLogIn.getHistorialEliminado().obtenerArgumento(accion.getAnimal().getCodigoString());
                vistaLogIn.getControladorPila().pushPilaArgumentoEliminacion(argumentoEliminacion);
                vistaLogIn.getHistorialEliminado().eliminarAnimalListaEliminados(accion.getAnimal().getCodigoString());
                Venta venta = vistaLogIn.getHistorialVenta().obtenerVenta(accion.getAnimal().getCodigoString());
                vistaLogIn.getControladorPila().pushPilaVentasDos(venta);
                vistaLogIn.getHistorialVenta().eliminarAnimalListaVendidos(accion.getAnimal().getCodigoString());
                eliminarToroVendido();
                eliminarCerdoVendidoEmpleado();
                eliminarObjetosTabla();
                cargarTabla();
            }
            vistaLogIn.getControladorPila().pushAccionPilaDosAnimales(accion);
        } else {
            JOptionPane.showMessageDialog(null, "No se puede retroceder más.");
        }
    }//GEN-LAST:event_btnAtrasActionPerformed

    private void btnAdelanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdelanteActionPerformed
        // TODO add your handling code here:
        if (vistaLogIn.getControladorPila().pilaDosAnimalesIsEmpty() == false) {
            AccionRealizadaAnimal accion = vistaLogIn.getControladorPila().popAccionPilaDosAnimales();
            System.out.println("Accion: " + accion.getAccion());
            if (accion.getAccion().equals(AccionRealizadaAnimal.ELIMINAR)) {
                agregarAnimalProvenientePila(accion.getAnimal());
                accion.setAccion(AccionRealizadaAnimal.REGISTRAR);
                eliminarObjetosTabla();
                cargarTabla();
            } else if (accion.getAccion().equals(AccionRealizadaAnimal.REGISTRAR)) {
                eliminarAnimal(accion.getAnimal());
                accion.setAccion(AccionRealizadaAnimal.ELIMINAR);
                vistaLogIn.getHistorialEliminado().agregarAnimalEliminado(vistaLogIn.getControladorPila().popPilaArgumentoEliminacion());
            } else if (accion.getAccion().equals(AccionRealizadaAnimal.DESVENDER)) {
                eliminarAnimal(accion.getAnimal());
                accion.setAccion(AccionRealizadaAnimal.VENDER);
                Venta venta = vistaLogIn.getControladorPila().popPilaVentasDos();
                ArgumentoEliminacion argumentoEliminacion = vistaLogIn.getControladorPila().popPilaArgumentoEliminacion();
                vistaLogIn.getHistorialEliminado().agregarAnimalEliminado(argumentoEliminacion);
                vistaLogIn.getHistorialVenta().agregarVentaHistorial(venta);
                añadirToroVendidoEmpleado();
                añadirCerdoVendidoEmpleado();
                eliminarObjetosTabla();
                cargarTabla();
            }

            if (accion.getAnimal() instanceof Cerdo) {

            } else if (accion.getAnimal() instanceof Caballo) {

            } else {

            }
            vistaLogIn.getControladorPila().pushAccionPilaUnoAnimales(accion);
        } else {
            JOptionPane.showMessageDialog(null, "No se puede adelantar más.");
        }
    }//GEN-LAST:event_btnAdelanteActionPerformed

    private void btnEditarPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarPrecioActionPerformed
        // TODO add your handling code here:
        if (txtPrecioPorKg.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debes completar el campo.");
            return;
        }
        String precio = txtPrecioPorKg.getText();
        controladorPocilga.setPrecioPorkg(precio);
        limpiarCampoPrecio();
        llenarCampoPrecio();
    }//GEN-LAST:event_btnEditarPrecioActionPerformed

    private void txtPrecioPorKgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPrecioPorKgMouseClicked
        // TODO add your handling code here:
        txtPrecioPorKg.setText(null);
    }//GEN-LAST:event_txtPrecioPorKgMouseClicked

    private void txtNombreAnimalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreAnimalKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNombreAnimalKeyTyped

    private void txtEdadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEdadKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (c < '0' || c > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_txtEdadKeyTyped

    private void txtPesoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesoKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (c < '0' || c > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_txtPesoKeyTyped

    private void txtPrecioPorKgKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioPorKgKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (c < '0' || c > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_txtPrecioPorKgKeyTyped

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Jlabel2;
    private javax.swing.JButton btnAdelante;
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnAñadir;
    private javax.swing.JButton btnCuidadosCaballos;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEditarPrecio;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGenerarVenta;
    private javax.swing.JButton btnGestionarPremios;
    private javax.swing.JButton btnHistoriaClinica;
    private javax.swing.JButton btnLiberar;
    private javax.swing.JButton btnRegistrarCrias;
    private javax.swing.JButton btnRemitir;
    private javax.swing.JButton btnVolver;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlAccionLote;
    private javax.swing.JTable tblAnimales;
    private javax.swing.JLabel textoPrecioPorKg;
    private javax.swing.JTextField txtEdad;
    private javax.swing.JLabel txtGestion;
    private javax.swing.JTextField txtNombreAnimal;
    private javax.swing.JTextField txtPeso;
    private javax.swing.JTextField txtPrecioPorKg;
    // End of variables declaration//GEN-END:variables
}
