package br.com.prova.livraria.dao;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.prova.livraria.modelo.Autor;
import br.com.prova.livraria.modelo.Livro;

public class LivroDao {

	public static ArrayList<Livro> LSLivro = new ArrayList<Livro>();

	public void pesist(Livro livro) {
		LSLivro.add(livro);
	}

	public void drop() {
		LSLivro.clear();
	}

	public List<Livro> listaTodos() {
		return LSLivro;
	}

	public Livro buscaPorId(Integer id) {
		for (Livro livro : LSLivro) {
			if (id == livro.getId()) {
				return livro;

			}
		}

		return null;
	}

	public void adiciona(Livro livro) {
		if (!verificaDuplicidade(livro)) {
			livro.setId(LSLivro.size() + 1);
			for (Autor a : livro.getAutores()) {
				a.setQtdLivros(a.getQtdLivros() + 1);
			}
			LSLivro.add(livro);
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Livro já Cadastrado", "Livro já Cadastrado"));
		}

	}

	private boolean verificaDuplicidade(Livro livro) {
		for (Livro l : LSLivro) {
			if (l.getTitulo().equals(livro.getTitulo()))
				return true;
		}
		return false;
	}

	public void atualiza(Livro livro) {
		for (Livro l : LSLivro) {
			if (livro.getId() == l.getId()) {

				l.setTitulo(livro.getTitulo());
			}
		}
	}

	public void remove(Livro livro) {
		LSLivro.remove(livro);
	}

}
