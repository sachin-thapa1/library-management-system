package com.example.demo.entity;
import com.fasterxml.jackson.annotation.JsonFormat;


import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table (name = "members")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate  membershipDate;

    public MemberEntity(Long id, String name, String email, String phoneNumber, LocalDate  membershipDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.membershipDate = membershipDate;
    }
    public MemberEntity(){
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
    this.name = name;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getMembershipDate(){
        return membershipDate;
    }
    public void setMembershipDate(LocalDate membershipDate){
        this.membershipDate = membershipDate;
    }
}
