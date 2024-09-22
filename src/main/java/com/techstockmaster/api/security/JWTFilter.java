package com.techstockmaster.api.security;

import com.techstockmaster.api.config.SecurityConfig;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JWTFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Obtem o token da request com AUTHORIZATION
//        String token = request.getHeader(JWTCreator.HEADER_AUTHORIZATION);
//
//        // Esta implementação só está validando a integridade do token
//        try {
//            if (token != null && !token.isEmpty()) {
//                JWTObject tokenObject = JWTCreator.create(token, SecurityConfig.PREFIX, SecurityConfig.KEY);
//
//                List<SimpleGrantedAuthority> authorities = authorities(tokenObject.getRoles());
//
//                UsernamePasswordAuthenticationToken userToken =
//                        new UsernamePasswordAuthenticationToken(
//                                tokenObject.getSubject(),
//                                null,
//                                authorities);
//
//                SecurityContextHolder.getContext().setAuthentication(userToken);
//            } else {
//                SecurityContextHolder.clearContext();
//            }
//            filterChain.doFilter(request, response);
//        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
//            e.printStackTrace();
//            response.setStatus(HttpStatus.FORBIDDEN.value());
//            return;
//        }
    }

//    private List<SimpleGrantedAuthority> authorities(List<String> roles) {
//        return roles.stream().map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//    }
}