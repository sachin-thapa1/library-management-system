package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.Repository.BookRepository;
import com.example.demo.Repository.MemberRepository;
import com.example.demo.entity.BookEntity;
import com.example.demo.entity.BorrowingEntity;
import com.example.demo.entity.MemberEntity;
import com.example.demo.exceptions.InvalidDataException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.Repository.BorrowingRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowingService {

    private final BorrowingRepository borrowingRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public BorrowingService(BorrowingRepository borrowingRepository,
                            BookRepository bookRepository,
                            MemberRepository memberRepository) {
        this.borrowingRepository = borrowingRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    public List<BorrowingEntity> getAllBorrowings() {
        return borrowingRepository.findAll();
    }

    public BorrowingEntity getBorrowingById(Long id) {
        return borrowingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Borrowing record not found with id: " + id));
    }

    public BorrowingEntity addBorrowing(BorrowingEntity borrowing) {
    if (borrowing.getBookId() == null || borrowing.getMemberId() == null) {
        throw new InvalidDataException("Book ID and Member ID cannot be null");
    }
    if (borrowing.getBorrowDate() == null) {
        throw new InvalidDataException("Borrow date cannot be null");
    }

    // Verify book and member exist
    bookRepository.findById(borrowing.getBookId())
        .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    memberRepository.findById(borrowing.getMemberId())
        .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

    if (borrowing.getStatus() == null) {
        borrowing.setStatus("BORROWED");
    }

    return borrowingRepository.save(borrowing);
}
    // Borrow a book
    public BorrowingEntity borrowBook(Long bookId, Long memberId) {
        // ✅ Added validation
        if (bookId == null || memberId == null) {
            throw new InvalidDataException("Book ID and Member ID cannot be null");
        }

        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));

        if (book.getAvailableCopies() <= 0) {
            throw new InvalidDataException("No copies available for book: " + book.getTitle());
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        BorrowingEntity borrowing = new BorrowingEntity();
        borrowing.setBookId(bookId);
        borrowing.setMemberId(memberId);
        borrowing.setBorrowDate(LocalDate.now());
        borrowing.setStatus("BORROWED");

        return borrowingRepository.save(borrowing);
    }

    // Return a book
    public BorrowingEntity returnBorrowing(Long borrowingId) {
        // ✅ Added validation
        if (borrowingId == null) {
            throw new InvalidDataException("Borrowing ID cannot be null");
        }

        BorrowingEntity borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrowing record not found with id: " + borrowingId));

        if ("RETURNED".equalsIgnoreCase(borrowing.getStatus())) {
            throw new InvalidDataException("Book already returned.");
        }

        BookEntity book = bookRepository.findById(borrowing.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + borrowing.getBookId()));

        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        borrowing.setReturnDate(LocalDate.now());
        borrowing.setStatus("RETURNED");
        return borrowingRepository.save(borrowing);
    }

    // Update borrowing
    public BorrowingEntity updateBorrowing(Long id, BorrowingEntity updatedBorrowing) {
        BorrowingEntity borrowing = borrowingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Borrowing record not found with id: " + id));

        if (updatedBorrowing.getBorrowDate() != null) {
            // ✅ Added date validation
            if (updatedBorrowing.getBorrowDate().isAfter(LocalDate.now())) {
                throw new InvalidDataException("Borrow date cannot be in the future");
            }
            borrowing.setBorrowDate(updatedBorrowing.getBorrowDate());
        }

        if (updatedBorrowing.getReturnDate() != null) {
            // ✅ Added date validation
            if (updatedBorrowing.getReturnDate().isBefore(borrowing.getBorrowDate())) {
                throw new InvalidDataException("Return date cannot be before borrow date");
            }
            borrowing.setReturnDate(updatedBorrowing.getReturnDate());
        }

        if (updatedBorrowing.getStatus() != null &&
                ("BORROWED".equalsIgnoreCase(updatedBorrowing.getStatus()) ||
                        "RETURNED".equalsIgnoreCase(updatedBorrowing.getStatus()))) {
            borrowing.setStatus(updatedBorrowing.getStatus());
        }

        return borrowingRepository.save(borrowing);
    }

    // Delete borrowing record
    public void removeBorrowing(Long id) {
        if (!borrowingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Borrowing record not found with id: " + id);
        }
        borrowingRepository.deleteById(id);
    }

    // Search by status
    public List<BorrowingEntity> searchByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new InvalidDataException("Status cannot be empty");
        }
        return borrowingRepository.findByStatusIgnoreCase(status);
    }

    // ✅ Added: Get borrowings by member
    public List<BorrowingEntity> getBorrowingsByMember(Long memberId) {
        if (memberId == null) {
            throw new InvalidDataException("Member ID cannot be null");
        }
        if (!memberRepository.existsById(memberId)) {
            throw new ResourceNotFoundException("Member not found with id: " + memberId);
        }
        return borrowingRepository.findByMemberId(memberId);
    }

    // ✅ Added: Get active borrowings
    public List<BorrowingEntity> getActiveBorrowings() {
        return borrowingRepository.findByStatusIgnoreCase("BORROWED");
    }

    // ✅ Added: Count total borrowings
    public long getTotalBorrowings() {
        return borrowingRepository.count();
    }
}
