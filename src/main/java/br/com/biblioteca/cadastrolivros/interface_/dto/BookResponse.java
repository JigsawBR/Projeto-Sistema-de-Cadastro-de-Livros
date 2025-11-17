package br.com.biblioteca.cadastrolivros.interface_.dto;

public class BookResponse {
	private Long id;
	private String titulo;
	private String autor;
	private String isbn;
	private Integer anoPublicacao;
	private Integer quantidadeEstoque;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getAnoPublicacao() {
		return anoPublicacao;
	}

	public void setAnoPublicacao(Integer anoPublicacao) {
		this.anoPublicacao = anoPublicacao;
	}

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}
}


