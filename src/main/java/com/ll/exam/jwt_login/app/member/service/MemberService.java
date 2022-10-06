package com.ll.exam.jwt_login.app.member.service;

import com.ll.exam.jwt_login.app.member.entity.Member;
import com.ll.exam.jwt_login.app.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member join(String username, String password, String email) {
        Member member = Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();

        memberRepository.save(member);

        return member;
    }

    public Optional<Member> findByUsername(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);

        return member;
    }
}