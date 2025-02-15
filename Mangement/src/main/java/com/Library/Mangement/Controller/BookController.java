package com.Library.Mangement.Controller;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Library.Mangement.Entity.Book;
import com.Library.Mangement.Exception.BookNotFoundException;
import com.Library.Mangement.Service.AiService;
import com.Library.Mangement.Service.BookService;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/books")
public class BookController {

	private final BookService bookService;
	
	private final AiService aiService;

	public BookController(BookService bookService, AiService aiService) {
		this.bookService = bookService;
		this.aiService = aiService;
	}

	@PostMapping
	public ResponseEntity<Book> createBook(@Valid @RequestBody Book bookRequest) {
		 Book book = bookService.createBook(bookRequest);
		 return ResponseEntity.status(201).body(book);
	}

	@GetMapping
	public ResponseEntity<List<Book>> getAllBooks() {
		return ResponseEntity.ok(bookService.getAllBooks());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable Long id) {
		 Book book = bookService.getBookById(id)
		            .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found"));
		        return ResponseEntity.ok(book);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/search")
	public ResponseEntity<List<Book>> searchBooks(@RequestParam(required = false) String title,
			@RequestParam(required = false) String author) {
		List<Book> books = bookService.searchBooks(title, author);
        
        if (books.isEmpty()) {
            throw new BookNotFoundException("No books found matching the search criteria.");
        }

        return ResponseEntity.ok(books);
	}
	
	@GetMapping("/{id}/ai-insights")
	public ResponseEntity<Mono<String>> getAIInsights(@PathVariable Long id) {
	    Optional<Book> book = bookService.getBookById(id);
	    if (book.isPresent()) {
	        Mono<String> insight = aiService.generateAIInsight(book);
	        return ResponseEntity.ok(insight);
	    }
	    return ResponseEntity.notFound().build();
	}
}
