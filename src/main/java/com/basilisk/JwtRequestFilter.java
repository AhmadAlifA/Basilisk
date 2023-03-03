package com.basilisk;

import com.basilisk.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        ngambil data dari Http Header dengan key "Authorization"
        String authorizationHeader = request.getHeader("Authorization");

        String token = null, username = null;
//        harus di check null, kalau tidak kena exception
        if (authorizationHeader != null){
            token = authorizationHeader.replace("Bearer ", "");
            username = jwtToken.getUsername(token);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (username != null && authentication == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

//            Apakah token valid atau tidak?
            Boolean isValid = jwtToken.validateToken(token, userDetails);
            if (isValid){
//                Langkah untuk menandakan kalau kita authenticated di dalam Spring Security, tanpa
//                menggukan password.
                var authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
