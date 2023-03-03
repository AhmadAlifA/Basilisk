package com.basilisk.dto;

import com.basilisk.validator.UniqueRegionName;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@UniqueRegionName(message = "Region City sudah ada di dalam Table")
public class UpsertRegionDTO {

        private Long id;
        @NotBlank(message = "Region City tidak boleh kosong")
        @Size(max = 50,message = "Region City tidak boleh lebih dari 50 characters.")
        private String city;
        @Size(max = 500,message = "Remark tidak boleh lebih dari 500 characters.")
        private String remark;
}
