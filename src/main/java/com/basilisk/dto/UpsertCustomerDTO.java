package com.basilisk.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UpsertCustomerDTO {

    private Long id;

    @NotBlank(message = "Company Name tidak boleh kosong")
    @Size(max = 50,message = "Company Name tidak boleh lebih dari 50 characters.")
    private String companyName;

    @NotBlank(message = "Contact Person tidak boleh kosong")
    @Size(max = 50,message = "Contact Person tidak boleh lebih dari 50 characters.")
    private String contactPerson;

    @Size(max = 500,message = "Address tidak boleh lebih dari 500 characters.")
    private String address;

    @Size(max = 50,message = "City tidak boleh lebih dari 50 characters.")
    private String city;

    @Size(max = 50,message = "Phone tidak boleh lebih dari 50 characters.")
    private String phone;

    @Size(max = 50,message = "Email tidak boleh lebih dari 50 characters.")
    private String emial;

    private LocalDateTime deleteDate;
}
