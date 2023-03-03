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
@UniqueSalesmanNumber(message = "Employee number sudah ada di dalam Table")
public class InsertSalesmanDTO {

    @NotBlank(message="Employee number is required.")
    @Size(max=20, message="Employee number can't be more than 20 characters.")
    private String employeeNumber;

    @NotBlank(message="First name is required.")
    @Size(max=50, message="First name can't be more than 50 characters.")
    private String firstName;

    @Size(max=50, message="Last name can't be more than 50 characters.")
    private String lastName;

    @NotBlank(message="Level is required.")
    private String level;

    @NotNull(message="Birth date is required.")
//    @FutureParadox(message="This salesman can't be possibly born in the future.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotNull(message="Hired date is required.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hiredDate;

    @Size(max=500, message="Address can't be more than 500 characters.")
    private String address;

    @Size(max=50, message="City can't be more than 50 characters.")
    private String city;

    @Size(max=20, message="Phone number can't be more than 20 characters.")
    private String phone;

    private String superiorEmployeeNumber;
}
