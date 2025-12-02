package com.example.demo.service;

import com.example.demo.Repository.BookRepository;
import com.example.demo.entity.BookEntity;
import com.example.demo.exceptions.DuplicateResourceException;
import com.example.demo.exceptions.InvalidDataException;
import com.example.demo.exceptions.ResourceNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // ✅ Get all books
    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    // ✅ Get single book
    public BookEntity getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    // ✅ Add new book
    public BookEntity addBook(BookEntity book) {
        validateBookData(book);

        // Ensure unique ISBN
        if (bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
            throw new DuplicateResourceException("You must have a unique ISBN number.");
        }

        return bookRepository.save(book);
    }

    // ✅ Update book
    public BookEntity updateBook(Long id, BookEntity updatedBook) {
        validateBookData(updatedBook);

        // Find existing book
        BookEntity existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        // Check for duplicate ISBN in other books
        bookRepository.findByIsbn(updatedBook.getIsbn())
                .filter(book -> !book.getId().equals(id))
                .ifPresent(book -> {
                    throw new DuplicateResourceException("You must have a unique ISBN number.");
                });

        // Update fields
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setPublishedYear(updatedBook.getPublishedYear());
        existingBook.setAvailableCopies(updatedBook.getAvailableCopies());

        return bookRepository.save(existingBook);
    }

    // ✅ Delete book
    public void removeBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    // ✅ Search books by title (case-insensitive)
    public List<BookEntity> searchBookByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    // ✅ Get only available books
    public List<BookEntity> getAvailableBooks() {
        return bookRepository.findByAvailableCopiesGreaterThan(0);
    }

    // 🔒 Common validation logic extracted
    private void validateBookData(BookEntity book) {
        if (book.getTitle() == null || book.getAuthor() == null || book.getIsbn() == null ||
            book.getTitle().trim().isEmpty() || book.getAuthor().trim().isEmpty() ||
            book.getIsbn().trim().isEmpty() || book.getPublishedYear() < 1800 ||
            book.getPublishedYear() > 2025 || book.getAvailableCopies() < 0) {

            throw new InvalidDataException("Invalid book data. Please check the input values.");
        }
    }
}
