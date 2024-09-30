package com.sqa.librarysystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqa.librarysystem.model.Member;
import com.sqa.librarysystem.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MemberControllerTest {

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberController memberController;

    private MockMvc mockMvc;
    private Member member;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();

        member = new Member();
        member.setId(1L);
        member.setName("Test Member");
        member.setEmail("test@example.com");
    }

    @Test
    void getAllMembers() throws Exception {
        List<Member> memberList = new ArrayList<>();
        memberList.add(member);

        when(memberService.getAllMembers()).thenReturn(memberList);

        mockMvc.perform(get("/api/member"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Member"))
                .andExpect(jsonPath("$[0].email").value("test@example.com"));

        verify(memberService, times(1)).getAllMembers();
    }

    @Test
    void registerMember() throws Exception {
        when(memberService.registerMember(any(Member.class))).thenReturn(member);

        mockMvc.perform(post("/api/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(member)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Member"))
                .andExpect(jsonPath("$.email").value("test@example.com"));

        verify(memberService, times(1)).registerMember(any(Member.class));
    }

    @Test
    void deleteMember() throws Exception {
        doNothing().when(memberService).deleteMember(anyLong());

        mockMvc.perform(delete("/api/member/{id}", 1L))
                .andExpect(status().isOk());

        verify(memberService, times(1)).deleteMember(1L);
    }

    @Test
    void updateMember() throws Exception {
        when(memberService.updateMember(anyLong(), any(Member.class))).thenReturn(member);

        mockMvc.perform(put("/api/member/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(member)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Member"))
                .andExpect(jsonPath("$.email").value("test@example.com"));

        verify(memberService, times(1)).updateMember(anyLong(), any(Member.class));
    }


}
