package br.com.biblioteca.cadastrolivros.domain.exception;

public class IsbnChangeNotAllowedException extends RuntimeException {
    public IsbnChangeNotAllowedException() {
        super("Não é permitido alterar o ISBN de um livro existente");
    }
}
