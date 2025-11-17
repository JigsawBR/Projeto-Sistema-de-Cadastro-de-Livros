package br.com.biblioteca.cadastrolivros.interface_.exception;

import br.com.biblioteca.cadastrolivros.domain.exception.BookNotFoundException;
import br.com.biblioteca.cadastrolivros.domain.exception.DuplicateIsbnException;
import br.com.biblioteca.cadastrolivros.domain.exception.IsbnChangeNotAllowedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
																  HttpHeaders headers,
																  HttpStatusCode status,
																  WebRequest request) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", Instant.now().toString());
		body.put("status", HttpStatus.BAD_REQUEST.value());
		body.put("error", "Validation failed");
		body.put("fields", ex.getBindingResult().getFieldErrors().stream()
			.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (a, b) -> a)));
		return ResponseEntity.badRequest().body(body);
	}

	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleNotFound(BookNotFoundException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", Instant.now().toString());
		body.put("status", HttpStatus.NOT_FOUND.value());
		body.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
	}

    @ExceptionHandler(DuplicateIsbnException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(DuplicateIsbnException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(IsbnChangeNotAllowedException.class)
    public ResponseEntity<Map<String, Object>> handleIsbnChangeNotAllowed(IsbnChangeNotAllowedException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}


