package com.sqa.librarysystem.service;

import com.sqa.librarysystem.model.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    List<Member> getAllMembers();
    Member registerMember(Member member);
    Optional<Member> getMemberById(Long id);
    void deleteMember(Long id);
    Member updateMember(Long id, Member updatedMember);
}
