package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.BookEntity;
import com.example.demo.entity.BorrowingEntity;

import java.util.List;
import java.util.Optional;


@Repository
public interface BorrowingRepository extends JpaRepository<BorrowingEntity, Long> {
    List<BorrowingEntity>findByStatusIgnoreCase(String status);
    List<BorrowingEntity> findByMemberId(Long memberId);
}
