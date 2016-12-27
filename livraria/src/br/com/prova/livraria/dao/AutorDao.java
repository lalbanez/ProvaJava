package br.com.prova.livraria.dao;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.prova.livraria.modelo.Autor;
import br.com.prova.livraria.modelo.Livro;

public class AutorDao {

	public static ArrayList<Autor> LSAutor = new ArrayList<Autor>();

	public void pesist(Autor autor) {
		LSAutor.add(autor);
	}

	public void drop() {
		LSAutor.clear();
	}

	public List<Autor> listaTodos() {
		return LSAutor;
	}

	public Autor buscaPorId(Integer autorId) {

		for (Autor autor : LSAutor) {
			if (autorId == autor.getId()) {
				return autor;
			}
		}

		return null;

	}

	public void remove(Autor autor) {
		LSAutor.remove(autor);

	}

	public void adiciona(Autor autor) {
		if (!verificaDuplicidade(autor)) {
			autor.setId(LSAutor.size() + 1);
			autor.setQtdLivros(0);
			LSAutor.add(autor);
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Autor já Cadastrado", "Autor já Cadastrado"));
		}

	}

	private boolean verificaDuplicidade(Autor autor) {
		for (Autor a : LSAutor) {
			if (a.getNome().equals(autor.getNome()))
				return true;
		}
		return false;
	}

	public void atualiza(Autor autor) {
		LSAutor.set(autor.getId() - 1, autor);
	}

	public void contarLivros() {
		for (Autor a : AutorDao.LSAutor) {
			for (Livro l : LivroDao.LSLivro) {
				for (Autor al : l.getAutores()) {
					if (a.getNome().equals(al.getNome())) {
						a.setQtdLivros(a.getQtdLivros() + 1);
					}
				}
			}
		}
	}
}
