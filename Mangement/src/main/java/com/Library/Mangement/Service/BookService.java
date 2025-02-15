package com.Library.Mangement.Service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.Library.Mangement.Entity.Book;
import com.Library.Mangement.Repo.BookRepository;

@Service
public class BookService {

	private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> searchBooks(String title, String author) {
        if (title != null && author != null) {
            return bookRepository.findByTitle(title);
        } else if (title != null) {
            return bookRepository.findByTitle(title);
        } else if (author != null) {
            return bookRepository.findByAuthor(author);
        } else {
            return bookRepository.findAll();
        }
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
