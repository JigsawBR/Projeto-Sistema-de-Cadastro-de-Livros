package br.com.biblioteca.cadastrolivros.interface_.api;

import br.com.biblioteca.cadastrolivros.application.service.BookService;
import br.com.biblioteca.cadastrolivros.domain.model.Book;
import br.com.biblioteca.cadastrolivros.interface_.dto.BookRequest;
import br.com.biblioteca.cadastrolivros.interface_.dto.BookResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@PostMapping
	public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest request, UriComponentsBuilder uriBuilder) {
		Book toCreate = toDomain(request, null);
		Book created = bookService.create(toCreate);
		URI location = uriBuilder.path("/api/livros/{id}").buildAndExpand(created.getId()).toUri();
		return ResponseEntity.created(location).body(toResponse(created));
	}

	@GetMapping
	public ResponseEntity<List<BookResponse>> listAll() {
		List<BookResponse> response = bookService.listAll().stream().map(this::toResponse).toList();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(toResponse(bookService.getById(id)));
	}

	@GetMapping("/isbn/{isbn}")
	public ResponseEntity<BookResponse> getByIsbn(@PathVariable String isbn) {
		return ResponseEntity.ok(toResponse(bookService.getByIsbn(isbn)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<BookResponse> update(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
		Book updates = toDomain(request, id);
		Book updated = bookService.update(id, updates);
		return ResponseEntity.ok(toResponse(updated));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		bookService.delete(id);
		return ResponseEntity.noContent().build();
	}

	private Book toDomain(BookRequest request, Long id) {
		return new Book(
			id,
			request.getTitulo(),
			request.getAutor(),
			request.getIsbn(),
			request.getAnoPublicacao(),
			request.getQuantidadeEstoque()
		);
	}

	private BookResponse toResponse(Book book) {
		BookResponse response = new BookResponse();
		response.setId(book.getId());
		response.setTitulo(book.getTitulo());
		response.setAutor(book.getAutor());
		response.setIsbn(book.getIsbn());
		response.setAnoPublicacao(book.getAnoPublicacao());
		response.setQuantidadeEstoque(book.getQuantidadeEstoque());
		return response;
	}
}


