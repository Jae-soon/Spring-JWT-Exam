package com.ll.exam.jwt_login.app.member.service;

import com.ll.exam.jwt_login.app.member.entity.Member;
import com.ll.exam.jwt_login.app.member.repository.MemberRepository;
import com.ll.exam.jwt_login.app.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

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

    public String genAccessToken(Member member) {
        return jwtProvider.generateAccessToken(member.getAccessTokenClaims(), 60 * 60 * 24 * 30); // 30일동안 유지되는 token 발급, 정보 포함되어 있음
    }
}