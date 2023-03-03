package com.basilisk.dto;

import com.basilisk.validator.UniqueSalesmanNumber;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UpdateSalesmanDTO {

    @NotBlank(message="Employee number tidak boleh kosong.")
    @Size(max = 50,message = "Nama kategori tidak boleh lebih dari 50 characters.")
    private String employeeNumber;

    @NotBlank(message="First name tidak boleh kosong.")
    @Size(max = 50,message = "Nama depan tidak boleh lebih dari 50 characters.")
    private String firstName;

    @Size(max = 50,message = "Nama belakang tidak boleh lebih dari 50 characters.")
    private String lastName;

    @NotBlank(message="Level tidak boleh kosong.")
    private String level;

    @NotNull(message="Birth date tidak boleh kosong.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotNull(message="Hired date tidak boleh kosong.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hiredDate;

    @Size(max = 50,message = "Alamat tidak boleh lebih dari 50 characters.")
    private String address;

    @Size(max = 50,message = "Nama kota tidak boleh lebih dari 50 characters.")
    private String city;

    @Size(max = 50,message = "Nomor Telfon tidak boleh lebih dari 50 characters.")
    private String phone;

    private String superiorEmployeeNumber;
}
