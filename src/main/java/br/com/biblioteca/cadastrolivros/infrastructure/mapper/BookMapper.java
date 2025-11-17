package br.com.biblioteca.cadastrolivros.infrastructure.mapper;

import br.com.biblioteca.cadastrolivros.domain.model.Book;
import br.com.biblioteca.cadastrolivros.infrastructure.persistence.entity.BookEntity;

public final class BookMapper {
	private BookMapper() {
	}

	public static BookEntity toEntity(Book book) {
		if (book == null) return null;
		BookEntity entity = new BookEntity();
		entity.setId(book.getId());
		entity.setTitulo(book.getTitulo());
		entity.setAutor(book.getAutor());
		entity.setIsbn(book.getIsbn());
		entity.setAnoPublicacao(book.getAnoPublicacao());
		entity.setQuantidadeEstoque(book.getQuantidadeEstoque());
		return entity;
	}

	public static Book toDomain(BookEntity entity) {
		if (entity == null) return null;
		return new Book(
			entity.getId(),
			entity.getTitulo(),
			entity.getAutor(),
			entity.getIsbn(),
			entity.getAnoPublicacao(),
			entity.getQuantidadeEstoque()
		);
	}
}


