package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.MemberEntity;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository <MemberEntity, Long> {
    List<MemberEntity>findByNameContainingIgnoreCase(String name);
}
