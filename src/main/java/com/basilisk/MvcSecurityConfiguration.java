package com.basilisk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

//Setting dan konfigurasi untuk Middleware Security check sebelum request ke action apapun.
@Configuration
@EnableWebSecurity
public class MvcSecurityConfiguration {

    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //        pembuatan harus berurutan dari permit lalu authenticated atau
        //        urutan chaim method tidak boleh terbalik urutannya.
//        http.csrf().disable().authorizeHttpRequests()
        http.authorizeHttpRequests()
                .antMatchers("/resources/**","/account/**").permitAll() //yang diperbolehkan tanpa login
                .antMatchers(
                        "/supplier/**",
                        "/category/upsertForm",
                        "/category/delete",
                        "/category/save"
                    ).hasAuthority("Administrator") // yang hanya dapat mengakses link untuk 1 akses
                .antMatchers("/product/**").hasAnyAuthority("Administrator","Salesman") // yang hanya dapat mengakses link untuk lebih dari 1 akses
                .anyRequest().authenticated() //request yang harus log-in agar dapat di akses
                .and().formLogin()
                    .loginPage("/account/loginForm") //kalau belum authenticated, di redirect ke halaman login
                    .loginProcessingUrl("/authenticating") //memberikan routing untuk proses Post log-in
                .and().logout() //untuk mengaktifkan request untuk log-out
                .and().exceptionHandling().accessDeniedPage("/account/accessDenied"); //Access Denied untuk non-authorized
        return http.build();
    }


}
