package com.basilisk.rest;


import com.basilisk.JwtToken;
import com.basilisk.dto.RequestTokenDTO;
import com.basilisk.dto.ResponseTokenDTO;
import com.basilisk.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/authenticate")
public class AccountRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtToken jwtToken;

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody RequestTokenDTO dto){
     try {
//        Token ini bukan JWT, ini adalah tokenisasi gaya spring security (sama seperti yang di cookies).
         var token = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
//        Menggunakan spring security melakukan check apakah username dan password user valid.
         authenticationManager.authenticate(token);

     }catch (Exception exception){
        return ResponseEntity.status(403).body("Authentication gagal, username dan password not found.");
     }
//     dapatkan role hanya untuk extra informasi di response token
     String role = accountService.getAccountRole(dto.getUsername());
//     dapatkan jwt token dari method generate token yang sudah kita buat di class JwtToken
     String jwt = jwtToken.generateToken(
             dto.getSubject(),
             dto.getUsername(),
             dto.getSecretKey(),
             role,
             dto.getAudience()
     );
//      dto untuk diberikan ke requester dalam Http Response bodynya
     ResponseTokenDTO responseTokenDTO = new ResponseTokenDTO(
             dto.getUsername(), role, jwt
     );

        return ResponseEntity.status(200).body(responseTokenDTO);
    }
}
