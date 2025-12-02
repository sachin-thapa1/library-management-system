package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.apache.catalina.connector.Response;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import com.example.demo.entity.BookEntity;
import java.net.URI;

import com.example.demo.entity.BookEntity;
import com.example.demo.service.BookService;
import com.example.demo.service.FileStorageService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private FileStorageService fileStorageService;

    public BookController(BookService bookService, FileStorageService fileStorageService) {
        this.bookService = bookService;
        this.fileStorageService = fileStorageService;
    }

    // Get all books
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<BookEntity>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // Get a book by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<BookEntity> getBookById(@PathVariable Long id) {
        BookEntity book = bookService.getBookById(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    // Add a new book
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookEntity> addBook(@RequestBody BookEntity book) {
        BookEntity created = bookService.addBook(book);
        URI location = URI.create("/api/books/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    // Update existing book
    @PutMapping("/{id}")
     @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookEntity> updateBook(@PathVariable Long id, @RequestBody BookEntity updatedBook) {
        BookEntity updated = bookService.updateBook(id, updatedBook);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // Delete book
    @DeleteMapping("/{id}")
     @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeBook(@PathVariable Long id) {
         bookService.removeBook(id);
        return ResponseEntity.noContent().build();
    }

    // Search book by title
    @GetMapping("/search")
    public ResponseEntity<List<BookEntity>> searchBook(@RequestParam String title) {
        List<BookEntity> result = bookService.searchBookByTitle(title);
        return ResponseEntity.ok(result);
    }

    //cover upload
    @PostMapping("/{id}/cover")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>>uploadBookCover(@PathVariable Long id, @RequestParam("file")MultipartFile file){
        //get book
        BookEntity  book = bookService.getBookById(id);
        //delete old cover if exist
        if(book.getCoverImage()!= null){
            fileStorageService.deleteFile(book.getCoverImage());
        }
        //store new file
        String filename = fileStorageService.storeFile(file);
        //update book entity
        book.setCoverImage(filename);
        bookService.updateBook(id, book);
        //return response
        Map<String,String> response = new HashMap<>();
        response.put("message", "Cover uploaded successfully");
        response.put("coverURL", "/api/books/" + id + "/cover");
        response.put("filename", filename);
        return ResponseEntity.ok(response);
    }

    //dwnload
    @GetMapping("/{id}/cover")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Resource>downloadBookCover(@PathVariable Long id){
        //get the book
        BookEntity book = bookService.getBookById(id);
        //check if cover exists
        if(book.getCoverImage() == null){
            return ResponseEntity.notFound().build();
        }
        //load file as resource
        Resource resource = fileStorageService.loadFileAsResource(book.getCoverImage());
        //determine content type
        String contentType = "application/octet-stream";
        String filename = book.getCoverImage();
        if(filename.endsWith(".jpeg") || filename.endsWith(".jpeg")){
            contentType = "image/jpeg";
        }else if(filename.endsWith(".png") || filename.endsWith(".png")){
            contentType = "image/png";
        }else if(filename.endsWith(".gif") || filename.endsWith(".gif")){
            contentType = "image/gif";
        }
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename = \"" +filename + "\"")
            .body(resource);
    }

    @DeleteMapping("/{id}/cover")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<Map<String, String>> deleteBookCover(@PathVariable Long id) {
    // Get the book
    BookEntity book = bookService.getBookById(id);

    // Check if cover exists
    if (book.getCoverImage() == null) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Book has no cover to delete");
        return ResponseEntity.ok(response);
    }

    // Delete the file
    fileStorageService.deleteFile(book.getCoverImage());

    // Update book entity
    book.setCoverImage(null);
    bookService.updateBook(id, book);

    // Return response
    Map<String, String> response = new HashMap<>();
    response.put("message", "Cover deleted successfully");
    return ResponseEntity.ok(response);
}
}
