package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.Repository.MemberRepository;
import com.example.demo.entity.MemberEntity;
import com.example.demo.exceptions.InvalidDataException;
import com.example.demo.exceptions.ResourceNotFoundException;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // ✅ Get all members
    public List<MemberEntity> getAllMembers() {
        return memberRepository.findAll();
    }

    // ✅ Get member by ID
    public MemberEntity getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
    }

    // ✅ Add new member
    public MemberEntity addMember(MemberEntity member) {
        validateMemberData(member);
        return memberRepository.save(member);
    }

    // ✅ Update member
    public MemberEntity updateMember(Long id, MemberEntity updatedMember) {
        validateMemberData(updatedMember);

        MemberEntity existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));

        existingMember.setName(updatedMember.getName());
        existingMember.setEmail(updatedMember.getEmail());
        existingMember.setPhoneNumber(updatedMember.getPhoneNumber());

        return memberRepository.save(existingMember);
    }

    // ✅ Remove member
    public void removeMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new ResourceNotFoundException("Member not found with id: " + id);
        }
        memberRepository.deleteById(id);
    }

    // ✅ Search by name
    public List<MemberEntity> searchByName(String name) {
        return memberRepository.findByNameContainingIgnoreCase(name);
    }

    // 🔒 Private helper for validation
    private void validateMemberData(MemberEntity member) {
        if (member.getName() == null || member.getName().trim().isEmpty() ||
            member.getEmail() == null || member.getEmail().trim().isEmpty() ||
            member.getPhoneNumber() == null || member.getPhoneNumber().trim().isEmpty()) {
            throw new InvalidDataException("Member name, email, and phone number cannot be empty.");
        }

        if (!member.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new InvalidDataException("Invalid email format. Example: name@example.com");
        }
    }
}
