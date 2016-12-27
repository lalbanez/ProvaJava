package br.com.prova.livraria.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.prova.livraria.dao.AutorDao;
import br.com.prova.livraria.modelo.Autor;

@ManagedBean
@ViewScoped
public class AutorBean {

	private Autor autor = new Autor();

	private Autor autorSelecionado = new Autor();

	private Integer autorId;

	private AutorDao daoA = new AutorDao();

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public void carregarAutorPelaId() {
		this.autor = daoA.buscaPorId(autorId);
	}

	public void gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if (this.autor.getId() == null) {
			daoA.adiciona(this.autor);
		} else {
			daoA.atualiza(this.autor);
		}

		this.autor = new Autor();

	}

	public void remover() {
		System.out.println("Removendo autor " + autorSelecionado.getNome());
		daoA.remove(autorSelecionado);
	}

	public List<Autor> getAutores() {
		return daoA.listaTodos();
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Autor getAutorSelecionado() {
		return autorSelecionado;
	}

	public void setAutorSelecionado(Autor autorSelecionado) {
		this.autorSelecionado = autorSelecionado;
	}

}
