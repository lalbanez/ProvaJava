package br.com.prova.livraria.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.prova.livraria.dao.PopulaBanco;
import br.com.prova.livraria.dao.UsuarioDao;
import br.com.prova.livraria.modelo.Perfil;
import br.com.prova.livraria.modelo.Usuario;
import br.com.prova.livraria.util.CookieHelper;

@ManagedBean
@ViewScoped
public class LoginBean {

	private Usuario usuarioLogin = new Usuario();

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void init() {

	}

	public String efetuaLogin() {
		CookieHelper ch = new CookieHelper();
		PopulaBanco pb = new PopulaBanco();
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getExternalContext().getSessionMap().get("usuarioLogado") == null)
			pb.fillLista();

		System.out.println("fazendo login do usuario " + this.usuarioLogin.getEmail());
		System.out.println(this.usuarioLogin.toString());

		this.usuarioLogin = new UsuarioDao().existe(this.usuarioLogin);
		if (this.usuarioLogin != null) {
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuarioLogin);
			ch.setCookie("username", this.usuarioLogin.getEmail(), -1);
			ch.setCookie("password", this.usuarioLogin.getSenha(), -1);
			ch.setCookie("name", this.usuarioLogin.getNome(), -1);
			ch.setCookie("perfil", this.usuarioLogin.getPerfil().getDescricao(), -1);
			return "livro?faces-redirect=true";
		}

		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Usuário não encontrado"));

		pb.dropLista();

		return "login?faces-redirect=true";
	}

	public String deslogar() {
		CookieHelper ch = new CookieHelper();
		PopulaBanco pb = new PopulaBanco();
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		ch.setCookie("username", null, 0);
		ch.setCookie("password", null, 0);
		ch.setCookie("name", null, 0);
		ch.setCookie("perfil", null, 0);
		pb.dropLista();
		return "login?faces-redirect=true";
	}

	public String loginComCookie() {
		CookieHelper ch = new CookieHelper();
		FacesContext context = FacesContext.getCurrentInstance();
		PopulaBanco pb = new PopulaBanco();
		if (ch.getCookie("username") != null && ch.getCookie("password") != null && ch.getCookie("perfil") != null
				&& ch.getCookie("name") != null) {
			this.usuarioLogin.setEmail(ch.getCookie("username").getValue());
			this.usuarioLogin.setSenha(ch.getCookie("password").getValue());
			this.usuarioLogin.setNome(ch.getCookie("name").getValue());
			this.usuarioLogin.setPerfil(Perfil.valueOf(ch.getCookie("perfil").getValue().toUpperCase()));
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuarioLogin);
			pb.dropLista();
			pb.fillLista();
			context.getApplication().getNavigationHandler().handleNavigation(context, null,
					"livro?faces-redirect=true");
			return "livro?faces-redirect=true";
		}
		return "login?faces-redirect=true";
	}
}
