package com.basilisk.dto;

import com.basilisk.validator.PasswordComparer;
import com.basilisk.validator.UniqueUsername;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@PasswordComparer(message = "Passowrd dan COnfirmation harus match")
public class RegisterDTO {

    @NotBlank(message="Username harus diinput")
    @UniqueUsername(message = "Username sudah dipakai")
    @Size(max=20, message="Username can't be more than 20 characters.")
    private String username;

    @NotBlank(message="Password harus diinput")
    @Size(max=10, message="Password tidak boleh lebih dari 10 chars.")
    private String password;

    @NotBlank(message="Password harus dikonfirmasi")
    @Size(max=10, message="Password tidak boleh lebih dari 10 chars.")
    private String passwordConfirmation;

    @NotBlank(message="Role harus dipilih")
    private String role;


}
