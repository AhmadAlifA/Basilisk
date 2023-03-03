package com.basilisk.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class OrderGridDTO {

    private String invoiceNumber;
    private String customer;
    private String salesman;
    private LocalDate orderDate;
    private String delivery;
}
