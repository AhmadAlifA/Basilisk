package com.basilisk.service;

import com.basilisk.ApplicationUserDetails;
import com.basilisk.dao.AccountRepository;
import com.basilisk.dto.RegisterDTO;
import com.basilisk.entity.Account;
import com.basilisk.utility.Dropdown;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerAccount(RegisterDTO dto) {
//        Kita acak-acak passwornya jadi ke encrypt di sini
        String hasilAcakAcak = passwordEncoder.encode(dto.getPassword()); //hasilAcakAcak atau passwordEncoder
        Account account = new Account(
                dto.getUsername(),
                hasilAcakAcak,
                dto.getRole());
        accountRepository.save(account);
    }

    public String getAccountRole(String username) {
        var entity = accountRepository.findById(username).get();
        return entity.getRole();
    }

    public Boolean checkExistingAccount(String username) {
        Long totalUsername = accountRepository.countUsername(username);
        return (totalUsername > 0) ? true : false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account entity = accountRepository.findById(username).get();
        UserDetails userDetailDTO = new ApplicationUserDetails(entity);
        return userDetailDTO;
    }

    public List<Dropdown> getRoleDropdown(){
        return Dropdown.getRoleDropdown();
    }
}
