package br.com.biblioteca.cadastrolivros.application.service;

import br.com.biblioteca.cadastrolivros.domain.exception.BookNotFoundException;
import br.com.biblioteca.cadastrolivros.domain.exception.DuplicateIsbnException;
import br.com.biblioteca.cadastrolivros.domain.exception.IsbnChangeNotAllowedException;
import br.com.biblioteca.cadastrolivros.domain.model.Book;
import br.com.biblioteca.cadastrolivros.domain.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

	private final BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Transactional
	public Book create(Book book) {
		if (bookRepository.existsByIsbn(book.getIsbn())) {
			throw new DuplicateIsbnException(book.getIsbn());
		}
		return bookRepository.save(book);
	}

	@Transactional(readOnly = true)
	public List<Book> listAll() {
		return bookRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Book getById(Long id) {
		return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
	}

	@Transactional(readOnly = true)
	public Book getByIsbn(String isbn) {
		return bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException(isbn));
	}

 @Transactional
 public Book update(Long id, Book updates) {
     Book existing = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

     // RF03: Must not allow changing the ISBN of an already existing book
     if (updates.getIsbn() != null && !updates.getIsbn().equals(existing.getIsbn())) {
         throw new IsbnChangeNotAllowedException();
     }

     existing.setTitulo(updates.getTitulo());
     existing.setAutor(updates.getAutor());
     // keep original ISBN unchanged
     existing.setAnoPublicacao(updates.getAnoPublicacao());
     existing.setQuantidadeEstoque(updates.getQuantidadeEstoque());

     return bookRepository.save(existing);
 }

	@Transactional
	public void delete(Long id) {
		if (!bookRepository.existsById(id)) {
			throw new BookNotFoundException(id);
		}
		bookRepository.deleteById(id);
	}
}


