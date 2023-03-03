package com.basilisk.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ResponseTokenDTO {

    private String username;
    private String role;
    private String token;
}
