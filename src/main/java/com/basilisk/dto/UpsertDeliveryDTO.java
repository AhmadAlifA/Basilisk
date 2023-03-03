package com.basilisk.dto;

import com.basilisk.validator.UniqueCategoryName;
import com.basilisk.validator.UniqueDeliveryName;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@UniqueDeliveryName(message = "Nama sudah ada di dalam Table")
public class UpsertDeliveryDTO {

    private Long id;

    @NotBlank(message = "Nama Company tidak boleh kosong")
    @Size(max = 50,message = "Nama Company tidak boleh lebih dari 50 characters.")
    private String companyName;

    @Size(max = 50,message = "phone tidak boleh lebih dari 50 characters.")
    private String phone;


//    @Size(max = 50,message = "Cost tidak boleh lebih dari 50 characters.")
    private Double cost;
}
