package com.beyond.order.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class Member {
    @Id
    private Long id;
    private String email;
    private String password;
    private String name;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private LocalDateTime createdTime;

}
