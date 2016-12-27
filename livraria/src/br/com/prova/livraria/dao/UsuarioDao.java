package br.com.prova.livraria.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.prova.livraria.modelo.Usuario;

public class UsuarioDao {

	public static ArrayList<Usuario> LSUsuario = new ArrayList<Usuario>();

	public Usuario existe(Usuario usuario) {

		for (Usuario u : LSUsuario) {
			if (u.getEmail().equals(usuario.getEmail()) && u.getSenha().equals(usuario.getSenha())) {
				return u;
			}
		}
		return null;
	}

	public void pesist(Usuario usuario) {
		usuario.setId(LSUsuario.size() + 1);
		LSUsuario.add(usuario);
	}

	public void atualiza(Usuario usuario) {
		LSUsuario.set(usuario.getId() - 1, usuario);
	}

	public void drop() {
		LSUsuario.clear();
	}

	public Usuario buscaPorId(Integer usuarioId) {

		for (Usuario usuario : LSUsuario) {
			if (usuarioId == usuario.getId()) {
				return usuario;
			}
		}

		return null;

	}

	public List<Usuario> listaTodos() {
		return LSUsuario;
	}
}
