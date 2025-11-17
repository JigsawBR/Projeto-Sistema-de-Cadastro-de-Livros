package br.com.biblioteca.cadastrolivros.domain.repository;

import br.com.biblioteca.cadastrolivros.domain.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
	Book save(Book book);
	Optional<Book> findById(Long id);
	Optional<Book> findByIsbn(String isbn);
	List<Book> findAll();
	boolean existsByIsbn(String isbn);
	void deleteById(Long id);
	boolean existsById(Long id);
}


