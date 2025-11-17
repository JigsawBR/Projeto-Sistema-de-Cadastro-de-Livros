package br.com.biblioteca.cadastrolivros.infrastructure.persistence.repository;

import br.com.biblioteca.cadastrolivros.infrastructure.persistence.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookJpaRepository extends JpaRepository<BookEntity, Long> {
	Optional<BookEntity> findByIsbn(String isbn);
	boolean existsByIsbn(String isbn);
}


