package br.com.biblioteca.cadastrolivros.domain.exception;

public class BookNotFoundException extends RuntimeException {
	public BookNotFoundException(Long id) {
		super("Livro não encontrado. ID: " + id);
	}

	public BookNotFoundException(String isbn) {
		super("Livro não encontrado. ISBN: " + isbn);
	}
}


