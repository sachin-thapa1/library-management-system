package com.example.demo.entity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table (name = "borrowing")
public class BorrowingEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bookId;
    private Long memberId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private String status;

    public BorrowingEntity(Long id, Long bookId, Long memberId, LocalDate borrowDate, LocalDate returnDate, String status){
        this.id = id;
        this.bookId = bookId;
        this.memberId = memberId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public BorrowingEntity(){
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public Long getBookId(){
        return bookId;
    }
    public void setBookId(Long bookId){
        this.bookId = bookId;
    }

    public Long getMemberId(){
        return memberId;
    }
    public void setMemberId(Long memberId){
        this.memberId = memberId;
    }

    public LocalDate getBorrowDate(){
        return borrowDate;
    }
    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate(){
        return returnDate;
    }
    public void  setReturnDate(LocalDate returnDate){
        this.returnDate = returnDate;
    }

    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }

}
