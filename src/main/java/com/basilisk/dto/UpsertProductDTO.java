package com.basilisk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpsertProductDTO {
    private Long id;

    @NotBlank(message = "Nama tidak boleh kosong.")
    @Size( max = 200, message = "Tidak boleh lebih dari 200 Characters.")
    private String productName;

    private Long supplierId;

    @NotNull(message = "harus ada Category.")
    private Long categoryId;

    @Size( max = 1000, message = "Tidak boleh lebih dari 1000 Characters.")
    private String description;

    @NotNull(message = "harus ada Harga")
    @Min(value = 0, message = "Harga tidak boleh minus")
    @Digits(integer = 12, fraction = 2, message = "Desimal dengan 2 fractions.")
    private Double price;

    @NotNull(message = "harus ada Stock.")
    @Min(value = 0, message = "Stock Min 0.")
    @Max(value = 999, message = "Stock Max 999.")
    private Integer stock;

    @NotNull(message = "harus ada On Order.")
    @Min(value = 0, message = "OnOrder Min 0.")
    @Max(value = 999, message = "OnOrder Max 999.")
    private Integer onOrder;
    private Boolean discontinue;

}
