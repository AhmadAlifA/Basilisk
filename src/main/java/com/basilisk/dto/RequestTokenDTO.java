package com.basilisk.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RequestTokenDTO {

    private String username;
    private String password;
    private String subject;
    private String audience;
    private String secretKey;

}
