package com.basilisk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductGridDTO {
    private Long id;
    private String productName;
    private String categoryName;
    private String supplierName;
    private Double price;
}
