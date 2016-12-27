package br.com.prova.livraria.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.prova.livraria.dao.UsuarioDao;
import br.com.prova.livraria.modelo.Perfil;
import br.com.prova.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class UsuarioBean {

	private Usuario usuario = new Usuario();

	private Usuario usuarioSelecionado = new Usuario();

	private Integer usuarioId;

	private UsuarioDao daoU = new UsuarioDao();

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public void carregarUsuarioPelaId() {
		this.usuario = daoU.buscaPorId(usuarioId);
	}

	public Perfil[] getPerfil() {
		return Perfil.values();
	}

	public void gravar() {
		System.out.println("Gravando usuario " + this.usuario.getEmail());

		if (this.usuario.getId() == null) {
			daoU.pesist(this.usuario);
		} else {
			daoU.atualiza(this.usuario);
		}

		this.usuario = new Usuario();

	}

	public void excluir() {
		UsuarioDao.LSUsuario.remove(usuarioSelecionado);
	}

	public List<Usuario> getUsuarios() {
		return daoU.listaTodos();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		System.out.println(usuario.toString());
		this.usuario = usuario;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
		System.out.println(this.usuarioSelecionado.toString());
		
	}

}
