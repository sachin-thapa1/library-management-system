package com.example.demo.controller;

import com.example.demo.dto.BorrowRequest;
import com.example.demo.entity.BorrowingEntity;
import com.example.demo.service.BorrowingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.BorrowRequest;

import java.util.List;

@RestController
@RequestMapping("/api/borrowings")
public class BorrowingController {

    private final BorrowingService borrowingService;

    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    // Get all borrowings
    @GetMapping
    public ResponseEntity<List<BorrowingEntity>> getAllBorrowings() {
        return ResponseEntity.ok(borrowingService.getAllBorrowings());
    }

    // Get borrowing by ID
    @GetMapping("/{id}")
    public ResponseEntity<BorrowingEntity> getBorrowingById(@PathVariable Long id) {
        return ResponseEntity.ok(borrowingService.getBorrowingById(id));
    }

    @PostMapping
public ResponseEntity<BorrowingEntity> addBorrowing(@RequestBody BorrowingEntity borrowing) {
    BorrowingEntity created = borrowingService.addBorrowing(borrowing);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
}

    // Borrow a book (NEW - Special endpoint!)
    @PostMapping("/borrow")
    public ResponseEntity<BorrowingEntity> borrowBook(@RequestBody BorrowRequest request) {
        BorrowingEntity borrowing = borrowingService.borrowBook(request.getBookId(), request.getMemberId());
        return ResponseEntity.status(HttpStatus.CREATED).body(borrowing);
    }

    // Return a book (NEW - Special endpoint!)
    @PutMapping("/{id}/return")
    public ResponseEntity<BorrowingEntity> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(borrowingService.returnBorrowing(id));
    }

    // Update borrowing
    @PutMapping("/{id}")
    public ResponseEntity<BorrowingEntity> updateBorrowing(
            @PathVariable Long id,
            @RequestBody BorrowingEntity borrowing) {
        return ResponseEntity.ok(borrowingService.updateBorrowing(id, borrowing));
    }

    // Delete borrowing
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrowing(@PathVariable Long id) {
        borrowingService.removeBorrowing(id);
        return ResponseEntity.noContent().build();
    }

    // Search by status
    @GetMapping("/search")
    public ResponseEntity<List<BorrowingEntity>> searchByStatus(@RequestBody String status) {
        return ResponseEntity.ok(borrowingService.searchByStatus(status));
    }

    // Get borrowings by member
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<BorrowingEntity>> getBorrowingsByMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(borrowingService.getBorrowingsByMember(memberId));
    }

    // Get active borrowings
    @GetMapping("/active")
    public ResponseEntity<List<BorrowingEntity>> getActiveBorrowings() {
        return ResponseEntity.ok(borrowingService.getActiveBorrowings());
    }

    // Get total count
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalBorrowings() {
        return ResponseEntity.ok(borrowingService.getTotalBorrowings());
    }
}
