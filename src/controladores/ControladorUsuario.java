/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import excepciones.PersonaRegistradaConElMismoUsuarioException;
import excepciones.PersonaRegistradaConLaMismaIdException;
import excepciones.UsuarioNoRegistradoException;
import java.io.IOException;
import modeloPersonas.Usuario;
import util.LSE;
import util.Serializadora;

/**
 *
 * @author SANTIAGO
 */
public class ControladorUsuario {

    LSE<Usuario> usuarios;
    private Usuario usuarioLogueado;
    Serializadora serializadoraUsuarios;

    public ControladorUsuario() {
        serializadoraUsuarios = new Serializadora();
        usuarios = inicializarUsuarios();
        usuarioLogueado = null;
    }

    private LSE<Usuario> inicializarUsuarios() {
        LSE<Usuario> users;
        try {
            users = serializadoraUsuarios.leerUsuarios();
            return users;
        } catch (IOException | ClassNotFoundException ex) {
            return new LSE<>();
        }
    }
    
    public void escribirUsuarios() {
        try {
            serializadoraUsuarios.escribirUsuarios(usuarios);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int obtenerTamañoLista() {
        return usuarios.size();

    }

    public Usuario buscarUsuarioPorId(String id) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getIdentificacion().equals(id)) {
                return usuarios.get(i);
            }
        }
        return null;
    }

    public Usuario buscarUsuario(String usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsuario().equals(usuario)) {
                return usuarios.get(i);
            }
        }
        return null;
    }

    public void registrarUsuario(Usuario usuario) {
        Usuario userId = buscarUsuarioPorId(usuario.getIdentificacion());
        Usuario user = buscarUsuario(usuario.getUsuario());
        if (userId != null) {
            throw new PersonaRegistradaConLaMismaIdException();
        } else if (user != null) {
            throw new PersonaRegistradaConElMismoUsuarioException();
        }
        usuarios.add(usuario);
        escribirUsuarios();
    }

    public void editarInfoUsuario(String id, String nuevoNombre, String nuevoApellido, int nuevaEdad) {
        Usuario usuario = buscarUsuarioPorId(id);
        if (usuario == null) {
            throw new UsuarioNoRegistradoException();
        }
        usuario.setNombre(nuevoNombre);
        usuario.setApellido(nuevoApellido);
        usuario.setEdad(nuevaEdad);
        escribirUsuarios();
    }

    public void eliminarUsuario(String id) {
        Usuario usuario = buscarUsuarioPorId(id);
        if (usuario == null) {
            throw new UsuarioNoRegistradoException();
        }
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getIdentificacion().equals(id)) {
                usuarios.delete(i);
            }
        }
        escribirUsuarios();
    }

    public void setUsuarioLogueado(Usuario usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public Usuario recorrerLista(int i) {
        return usuarios.get(i);
    }

}
