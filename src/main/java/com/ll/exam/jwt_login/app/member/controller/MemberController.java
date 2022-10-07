package com.ll.exam.jwt_login.app.member.controller;

import com.ll.exam.jwt_login.app.base.dto.RsData;
import com.ll.exam.jwt_login.app.member.dto.request.LoginDto;
import com.ll.exam.jwt_login.app.member.entity.Member;
import com.ll.exam.jwt_login.app.member.service.MemberService;
import com.ll.exam.jwt_login.app.security.entity.MemberContext;
import com.ll.exam.jwt_login.app.security.jwt.JwtProvider;
import com.ll.exam.jwt_login.app.util.Util;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/test")
    public String test(@AuthenticationPrincipal MemberContext memberContext) {
        return "안녕" + memberContext;
    }

    @PostMapping("/login")
    public ResponseEntity<RsData> login(@RequestBody LoginDto loginDto) {
        if (loginDto.isNotValid()) { // DB에 저장되어 있지 않는 아이디가 입력되었을 경우
            return Util.spring.responseEntityOf(RsData.of("F-1", "로그인 정보가 올바르지 않습니다.")); // RsData와 null이 입력된다. 따로 header값을 넣어주지 않음
        }

        Member member = memberService.findByUsername(loginDto.getUsername()).orElse(null); // username을 갖고 loginDto를 받아온다.

        if (member == null) { // loginDto가 없을 경우
            return Util.spring.responseEntityOf(RsData.of("F-2", "일치하는 회원이 없습니다.")); // RsData와 null이 입력된다. 따로 header값을 넣어주지 않음
        }

        if (passwordEncoder.matches(loginDto.getPassword(), member.getPassword()) == false) { // 패스워드가 입력되지 않거나 패스워드가 다를 경우
            return Util.spring.responseEntityOf(RsData.of("F-3", "비밀번호가 일치하지 않습니다.")); // RsData와 null이 입력된다. 따로 header값을 넣어주지 않음
        }

        String accessToken = memberService.genAccessToken(member); // JWTToken 발급

        return Util.spring.responseEntityOf(
                RsData.of("S-1", "로그인 성공, Access Token을 발급합니다.",
                        Util.mapOf("accessToken", accessToken)
                ),
                Util.spring.httpHeadersOf("Authentication", "JWT_Access_Token") // header 넣어줌
        );
    }

    @GetMapping("/me")
    public ResponseEntity<RsData> me(@AuthenticationPrincipal MemberContext memberContext) {
        if (memberContext == null) { // 임시코드, 나중에는 시프링 시큐리티를 이용해서 로그인을 안했다면, 아예 여기로 못 들어오도록
            return Util.spring.responseEntityOf(RsData.failOf(null));
        }

        return Util.spring.responseEntityOf(RsData.successOf(memberContext));
    }
}