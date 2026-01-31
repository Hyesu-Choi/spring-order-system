package com.beyond.order.member.controller;

import com.beyond.order.member.dto.MemberCreateReqDto;
import com.beyond.order.member.dto.MemberListRespDto;
import com.beyond.order.member.dto.MemberLoginReqDto;
import com.beyond.order.member.dto.MemberLoginRespDto;
import com.beyond.order.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> memberCreate(@RequestBody MemberCreateReqDto dto) {
        Long memberId = memberService.memberCreate(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberId);
    }

    @PostMapping("/doLogin")
    public ResponseEntity<MemberLoginRespDto> memberLogin(
            @RequestBody MemberLoginReqDto dto
    ) {
        MemberLoginRespDto response = memberService.memberLogin(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MemberListRespDto>> memberList(){
        return ResponseEntity.status(HttpStatus.OK).body(memberService.memberList());
    }

    @GetMapping("/myinfo")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> memberMyInfo(@AuthenticationPrincipal String principal ){
        return ResponseEntity.status(HttpStatus.OK).body(memberService.memberMyInfo(principal));
    }

    @GetMapping("/detail/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> memberDetail(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(memberService.memberDetail(id));
    }







}
