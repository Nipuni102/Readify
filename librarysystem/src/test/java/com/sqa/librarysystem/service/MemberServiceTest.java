package com.sqa.librarysystem.service;

import com.sqa.librarysystem.model.Member;
import com.sqa.librarysystem.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    private Member member;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        member = new Member();
        member.setId(1L);
        member.setName("Test Member");
        member.setEmail("test@example.com");
    }

    @Test
    void registerMember() {
        when(memberRepository.save(member)).thenReturn(member);  // Mock the save behavior

        Member savedMember = memberService.registerMember(member);  // Call the method under test

        // Assertions to verify the result
        assertNotNull(savedMember);
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getEmail(), savedMember.getEmail());

        // Verify that the save method was called once
        verify(memberRepository, times(1)).save(member);
    }



    @Test
    void deleteMember() {
        when(memberRepository.existsById(1L)).thenReturn(true);  // Mock the existsById behavior

        memberService.deleteMember(1L);  // Call the method under test

        // Verify that the delete method was called once
        verify(memberRepository, times(1)).deleteById(1L);
    }



    @Test
    void updateMember() {
        // Mock the findById behavior to return the existing member
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        // Update the member details
        member.setName("Updated Member Name");
        member.setEmail("updated@example.com");

        // Mock the save behavior
        when(memberRepository.save(member)).thenReturn(member);

        Member updatedMember = memberService.updateMember(1L, member);  // Call the method under test

        // Assertions to verify the result
        assertNotNull(updatedMember);
        assertEquals("Updated Member Name", updatedMember.getName());
        assertEquals("updated@example.com", updatedMember.getEmail());

        // Verify that the findById and save methods were called once
        verify(memberRepository, times(1)).findById(1L);
        verify(memberRepository, times(1)).save(member);
    }



    @Test
    void getAllMembers() {
        // Create a list of members
        List<Member> memberList = new ArrayList<>();
        memberList.add(member);

        // Mock the findAll behavior to return the list of members
        when(memberRepository.findAll()).thenReturn(memberList);

        List<Member> foundMembers = memberService.getAllMembers();  // Call the method under test

        // Assertions to verify the result
        assertNotNull(foundMembers);
        assertEquals(1, foundMembers.size());
        assertEquals(member.getId(), foundMembers.get(0).getId());
        assertEquals(member.getName(), foundMembers.get(0).getName());
        assertEquals(member.getEmail(), foundMembers.get(0).getEmail());

        // Verify that the findAll method was called once
        verify(memberRepository, times(1)).findAll();
    }
}
