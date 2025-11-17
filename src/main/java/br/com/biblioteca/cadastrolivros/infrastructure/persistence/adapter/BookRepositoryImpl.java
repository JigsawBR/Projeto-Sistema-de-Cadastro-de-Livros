package br.com.biblioteca.cadastrolivros.infrastructure.persistence.adapter;

import br.com.biblioteca.cadastrolivros.domain.model.Book;
import br.com.biblioteca.cadastrolivros.domain.repository.BookRepository;
import br.com.biblioteca.cadastrolivros.infrastructure.mapper.BookMapper;
import br.com.biblioteca.cadastrolivros.infrastructure.persistence.entity.BookEntity;
import br.com.biblioteca.cadastrolivros.infrastructure.persistence.repository.BookJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {

	private final BookJpaRepository jpaRepository;

	public BookRepositoryImpl(BookJpaRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	@Override
	public Book save(Book book) {
		BookEntity saved = jpaRepository.save(BookMapper.toEntity(book));
		return BookMapper.toDomain(saved);
	}

	@Override
	public Optional<Book> findById(Long id) {
		return jpaRepository.findById(id).map(BookMapper::toDomain);
	}

	@Override
	public Optional<Book> findByIsbn(String isbn) {
		return jpaRepository.findByIsbn(isbn).map(BookMapper::toDomain);
	}

	@Override
	public List<Book> findAll() {
		return jpaRepository.findAll().stream().map(BookMapper::toDomain).toList();
	}

	@Override
	public boolean existsByIsbn(String isbn) {
		return jpaRepository.existsByIsbn(isbn);
	}

	@Override
	public void deleteById(Long id) {
		jpaRepository.deleteById(id);
	}

	@Override
	public boolean existsById(Long id) {
		return jpaRepository.existsById(id);
	}
}


