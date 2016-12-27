package br.com.prova.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.prova.livraria.dao.AutorDao;
import br.com.prova.livraria.dao.LivroDao;
import br.com.prova.livraria.modelo.Autor;
import br.com.prova.livraria.modelo.Livro;

@ManagedBean
@ViewScoped
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Livro livro = new Livro();

	private Livro livroSelecionado = new Livro();
	
	private boolean showAutor = false;

	private Integer autorId;

	private List<Livro> livros;
	private LivroDao daoL = new LivroDao();
	private AutorDao daoA = new AutorDao();

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public Livro getLivro() {
		return livro;
	}

	public List<Livro> getLivros() {

		if (this.livros == null) {
			this.livros = daoL.listaTodos();
		}

		return livros;
	}

	public List<Autor> getAutores() {
		return daoA.listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public void carregarLivroPelaId() {
		this.livro = daoL.buscaPorId(this.livro.getId());
	}

	public void gravarAutor() {
		Autor autor = daoA.buscaPorId(this.autorId);
		for(Autor a : this.livro.getAutores()){
			if(autor.getId().equals(a.getId())){
				return;
			}
		}
		this.livro.adicionaAutor(autor);
		System.out.println("Escrito por: " + autor.getNome());
	}

	public void gravar() {
		FacesContext context = FacesContext.getCurrentInstance();
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			context.addMessage("autor",
					new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}

		if (this.livro.getId() == null) {
			daoL.adiciona(this.livro);
			this.livros = daoL.listaTodos();
			this.showAutor = false;
			
			context.addMessage("livro", new FacesMessage(FacesMessage.SEVERITY_INFO,"Livro adicionado com sucesso","Livro adicionado com sucesso"));
		} else {
			daoL.atualiza(this.livro);
			this.showAutor = false;
			context.addMessage("livro", new FacesMessage(FacesMessage.SEVERITY_INFO,"Livro alterado com sucesso","Livro alterado com sucesso"));
		}

		this.livro = new Livro();
	}

	public void remover() {
		System.out.println("Removendo livro " + livroSelecionado.getTitulo());
		daoL.remove(livroSelecionado);
	}

	public void removerAutorDoLivro(Autor autor) {
		this.livro.removeAutor(autor);
	}

	public void carregar(Livro livro) {
		System.out.println("Carregando livro");
		this.livro = livro;
	}

	public String formAutor() {
		System.out.println("Chamada do formulário do Autor.");
		return "autor?faces-redirect=true";
	}
	
	public void exibeDadosAutor(){
		showAutor = true;
	}
	

	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("ISBN deveria começar com 1"));
		}

	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Livro getLivroSelecionado() {
		return livroSelecionado;
	}

	public void setLivroSelecionado(Livro livroSelecionado) {
		this.livroSelecionado = livroSelecionado;
	}

	public boolean isShowAutor() {
		return showAutor;
	}

	public void setShowAutor(boolean showAutor) {
		this.showAutor = showAutor;
	}
	
	

}
