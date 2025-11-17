package br.com.biblioteca.cadastrolivros.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "books", uniqueConstraints = @UniqueConstraint(columnNames = "isbn"))
public class BookEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String titulo;

	@Column(nullable = false)
	private String autor;

	@Column(nullable = false, unique = true, length = 32)
	private String isbn;

	@Column(nullable = false)
	private Integer anoPublicacao;

	@Column(nullable = false)
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


