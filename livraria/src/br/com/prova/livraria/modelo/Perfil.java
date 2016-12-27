package br.com.prova.livraria.modelo;

public enum Perfil {
	ADMINISTRADOR("Administrador"), USUARIO("Usuario");

	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	private Perfil(String descricao) {
		this.descricao = descricao;
	}

}
