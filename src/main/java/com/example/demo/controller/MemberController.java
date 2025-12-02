package com.example.demo.controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.MemberService;
import org.springframework.http.ResponseEntity;
import com.example.demo.entity.MemberEntity;

import java.util.List;
import java.net.URI;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<MemberEntity>>getAllMembers(){
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberEntity> getMemberById(@PathVariable Long id) {
        MemberEntity member = memberService.getMemberById(id);
        if(member == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(member);
    }

    @PostMapping
    public ResponseEntity<MemberEntity> addMember(@RequestBody MemberEntity member) {
        MemberEntity created = memberService.addMember(member);
        URI location = URI.create("/api/members/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberEntity>updateMember(@PathVariable Long id, @RequestBody MemberEntity updatedMember) {
        MemberEntity updated = memberService.updateMember(id, updatedMember);
        if(updated == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>removeMember(@PathVariable Long id) {
        memberService.removeMember(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<MemberEntity>> searchMember(@RequestParam String name){
        return ResponseEntity.ok(memberService.searchByName(name));
    }

}
