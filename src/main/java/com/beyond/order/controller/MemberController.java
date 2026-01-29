package com.beyond.order.controller;

import com.beyond.order.domain.Member;
import com.beyond.order.dto.member.MemberCreateReqDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    @PostMapping("/doLogin")
    public String userLogin(@RequestBody MemberCreateReqDto dto) {
        return "ok";
    }
}
