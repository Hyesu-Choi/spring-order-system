package com.beyond.order.service;

import com.beyond.order.common.auth.JwtTokenProvider;
import com.beyond.order.domain.Member;
import com.beyond.order.dto.member.*;
import com.beyond.order.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Long memberCreate(MemberCreateReqDto dto) {
        if (memberRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 email입니다.");
        }
        Member member = dto.toEntity(passwordEncoder.encode(dto.getPassword()));
        memberRepository.save(member);
        return member.getId();
    }

    public MemberLoginRespDto memberLogin(MemberLoginReqDto dto) {
        Member member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));
        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호 불일치");
        }
        String accessToken = jwtTokenProvider.createToken(member);
        String refreshToken = null;

        return new MemberLoginRespDto(accessToken, refreshToken);
    }

//    @Transactional(readOnly = true)
    public List<MemberListRespDto> memberList(){
        return memberRepository.findAll().stream().map(a -> MemberListRespDto.fromEntity(a))
                .collect(Collectors.toList());
    }

    public MemberMyinfoResqDto memberMyInfo(String email) {
        Optional<Member> opt = memberRepository.findByEmail(email);
        Member member = opt.orElseThrow(() -> new EntityNotFoundException("entity is not found"));
        MemberMyinfoResqDto dto = MemberMyinfoResqDto.fromEntity(member);
        return dto;
    }

    public MemberMyinfoResqDto memberDetail(Long id) {
        Optional<Member> opt = memberRepository.findById(id);
        Member member = opt.orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));
        MemberMyinfoResqDto dto = MemberMyinfoResqDto.fromEntity(member);
        return dto;
    }



}
