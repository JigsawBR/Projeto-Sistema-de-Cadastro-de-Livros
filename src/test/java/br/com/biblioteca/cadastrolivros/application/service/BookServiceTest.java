package br.com.biblioteca.cadastrolivros.application.service;

import br.com.biblioteca.cadastrolivros.domain.exception.BookNotFoundException;
import br.com.biblioteca.cadastrolivros.domain.exception.DuplicateIsbnException;
import br.com.biblioteca.cadastrolivros.domain.model.Book;
import br.com.biblioteca.cadastrolivros.domain.exception.IsbnChangeNotAllowedException;
import br.com.biblioteca.cadastrolivros.domain.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private BookService bookService;

	private Book sample() {
		return new Book(null, "Clean Code", "Robert C. Martin", "9780132350884", 2008, 5);
	}

	@Test
	@DisplayName("Create: deve criar livro quando ISBN não existir")
	void create_success() {
		Book toCreate = sample();
		when(bookRepository.existsByIsbn(toCreate.getIsbn())).thenReturn(false);
		when(bookRepository.save(any())).thenAnswer(inv -> {
			Book b = inv.getArgument(0);
			return new Book(1L, b.getTitulo(), b.getAutor(), b.getIsbn(), b.getAnoPublicacao(), b.getQuantidadeEstoque());
		});

		Book created = bookService.create(toCreate);

		assertNotNull(created.getId());
		assertEquals("9780132350884", created.getIsbn());
		verify(bookRepository).save(any());
	}

	@Test
	@DisplayName("Create: deve lançar exceção quando ISBN já existir")
	void create_duplicateIsbn() {
		Book toCreate = sample();
		when(bookRepository.existsByIsbn(toCreate.getIsbn())).thenReturn(true);

		assertThrows(DuplicateIsbnException.class, () -> bookService.create(toCreate));
		verify(bookRepository, never()).save(any());
	}

	@Test
	@DisplayName("Read: deve retornar livro por ID existente")
	void getById_found() {
		when(bookRepository.findById(1L)).thenReturn(Optional.of(new Book(1L, "A", "B", "X", 2000, 1)));
		Book book = bookService.getById(1L);
		assertEquals(1L, book.getId());
	}

	@Test
	@DisplayName("Read: deve lançar 404 quando ID não existir")
	void getById_notFound() {
		when(bookRepository.findById(1L)).thenReturn(Optional.empty());
		assertThrows(BookNotFoundException.class, () -> bookService.getById(1L));
	}

	@Test
	@DisplayName("Read: deve retornar livro por ISBN existente")
	void getByIsbn_found() {
		when(bookRepository.findByIsbn("X")).thenReturn(Optional.of(new Book(2L, "A", "B", "X", 2000, 1)));
		Book book = bookService.getByIsbn("X");
		assertEquals("X", book.getIsbn());
	}

	@Test
	@DisplayName("Read: deve lançar 404 quando ISBN não existir")
	void getByIsbn_notFound() {
		when(bookRepository.findByIsbn("X")).thenReturn(Optional.empty());
		assertThrows(BookNotFoundException.class, () -> bookService.getByIsbn("X"));
	}

	@Test
 @DisplayName("Update: deve atualizar dados mantendo o mesmo ISBN")
 void update_success_keepSameIsbn() {
     Book existing = new Book(1L, "Old", "Old", "OLD", 1990, 1);
     // mesmo ISBN (não pode alterar)
     Book updates = new Book(null, "New", "New", "OLD", 2020, 10);

     when(bookRepository.findById(1L)).thenReturn(Optional.of(existing));
     when(bookRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

     Book updated = bookService.update(1L, updates);

     assertEquals("New", updated.getTitulo());
     assertEquals("OLD", updated.getIsbn());
 }

	@Test
 @DisplayName("Update: deve lançar conflito quando ISBN já existir em outro livro")
 void update_duplicateIsbn() {
     Book existing = new Book(1L, "Old", "Old", "OLD", 1990, 1);
     Book updates = new Book(null, "New", "New", "DUP", 2020, 10);
     Book other = new Book(2L, "Any", "Any", "DUP", 2000, 2);

     when(bookRepository.findById(1L)).thenReturn(Optional.of(existing));
     // Agora a regra mudou: não pode alterar ISBN em update
     assertThrows(IsbnChangeNotAllowedException.class, () -> bookService.update(1L, updates));
     verify(bookRepository, never()).save(any());
 }

 @Test
 @DisplayName("Update: deve lançar erro ao tentar alterar o ISBN")
 void update_isbnChangeNotAllowed() {
     Book existing = new Book(1L, "Old", "Old", "OLD", 1990, 1);
     Book updates = new Book(null, "New", "New", "NEW", 2020, 10);

     when(bookRepository.findById(1L)).thenReturn(Optional.of(existing));

     assertThrows(IsbnChangeNotAllowedException.class, () -> bookService.update(1L, updates));
     verify(bookRepository, never()).save(any());
 }

	@Test
	@DisplayName("Delete: deve remover quando existir")
	void delete_success() {
		when(bookRepository.existsById(1L)).thenReturn(true);
		doNothing().when(bookRepository).deleteById(1L);

		assertDoesNotThrow(() -> bookService.delete(1L));
		verify(bookRepository).deleteById(1L);
	}

	@Test
	@DisplayName("Delete: deve lançar 404 quando não existir")
	void delete_notFound() {
		when(bookRepository.existsById(1L)).thenReturn(false);
		assertThrows(BookNotFoundException.class, () -> bookService.delete(1L));
		verify(bookRepository, never()).deleteById(anyLong());
	}
}


