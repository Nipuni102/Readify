package com.sqa.librarysystem.service;



import com.sqa.librarysystem.model.Member;
import com.sqa.librarysystem.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member registerMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    @Override
    public Member updateMember(Long id, Member updatedMember) {
        return memberRepository.findById(id).map(member -> {
            member.setName(updatedMember.getName());
            member.setEmail(updatedMember.getEmail());
            member.setPassword(updatedMember.getPassword());
            return memberRepository.save(member);
        }).orElseThrow(() -> new RuntimeException("Member not found"));
    }
}
