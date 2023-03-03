package com.basilisk.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ErrorDTO {
    private String  jenisException;
    private String  message;
    private LocalDateTime waktuError;
}
