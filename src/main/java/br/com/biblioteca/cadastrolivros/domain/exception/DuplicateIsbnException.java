package br.com.biblioteca.cadastrolivros.domain.exception;

public class DuplicateIsbnException extends RuntimeException {
	public DuplicateIsbnException(String isbn) {
		super("ISBN já existente: " + isbn);
	}
}


