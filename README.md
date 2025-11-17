# Cadastro de Livros (Spring Boot)

About (EN)

This is a simple Spring Boot 3 (Java 17) application that provides a REST API to manage a library of books. It implements full CRUD operations (create, read, update, delete), persists data with Spring Data JPA on an in‑memory H2 database, and follows a clean 4‑layer architecture (interface, application, domain, infrastructure). The project includes unit tests with JUnit and Mockito and can be run locally with Maven.

—

Sobre (PT‑BR)

Aplicação Spring Boot 3 (Java 17) com CRUD de livros, arquitetura em 4 camadas, JPA/H2 e testes unitários.

## Stack
- Java 17+
- Spring Boot 3.2+
- Spring Data JPA
- H2 Database (memória)
- JUnit 5 e Mockito
- Maven

## Como executar

```bash
mvn spring-boot:run
```

Aplicação em `http://localhost:8080`. H2 Console em `/h2-console` (JDBC URL `jdbc:h2:mem:cadastro_livros_db`).

## Como testar

```bash
mvn test
```

## Endpoints

- POST `/api/livros` → cria livro
- GET `/api/livros` → lista livros
- GET `/api/livros/{id}` → busca por ID
- GET `/api/livros/isbn/{isbn}` → busca por ISBN
- PUT `/api/livros/{id}` → atualiza livro
- DELETE `/api/livros/{id}` → remove livro

### Exemplo de payload (POST/PUT)
```json
{
  "titulo": "Clean Code",
  "autor": "Robert C. Martin",
  "isbn": "9780132350884",
  "anoPublicacao": 2008,
  "quantidadeEstoque": 5
}
```

## Arquitetura (4 camadas)
- `interface` (API REST, DTOs, handlers)
- `application` (serviço, transações)
- `domain` (modelo, repositório, regras/erros)
- `infrastructure` (JPA/H2, mapeamentos, adapters)


