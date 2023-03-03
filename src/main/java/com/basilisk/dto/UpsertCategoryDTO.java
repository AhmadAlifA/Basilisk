package com.basilisk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import com.basilisk.validator.UniqueCategoryName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Schema(description = "DTO yang digunakan untuk standard menambahkan atau merubah data Category.")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@UniqueCategoryName(message = "Nama sudah ada di dalam Table")
public class UpsertCategoryDTO {
    private Long id;

    @Schema(description = "String tidak boleh kosong")
    @NotBlank(message = "Nama kategori tidak boleh kosong")
    @Size(max = 50,message = "Nama kategori tidak boleh lebih dari 50 characters.")
    private String name;
    @Size(max = 500,message = "Description tidak boleh lebih dari 500 characters.")
    private String description;
}
