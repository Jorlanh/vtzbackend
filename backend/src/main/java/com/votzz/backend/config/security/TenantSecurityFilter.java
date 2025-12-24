package com.votzz.backend.config.security;

import com.votzz.backend.service.TokenService;
import com.votzz.backend.domain.User;
import com.votzz.backend.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.Collections;

@Component
public class TenantSecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public TenantSecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response, 
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        
        var token = recoverToken(request);

        if (token != null) {
            try {
                // CORREÇÃO: O método retorna Claims diretamente, não precisa de instanceof
                Claims claims = tokenService.validateToken(token);
                String email = claims.getSubject();

                if (email != null) {
                    User user = userRepository.findByEmail(email).orElse(null);
                    
                    if (user != null) {
                        if (user.getTenant() != null) {
                            TenantContext.setCurrentTenant(user.getTenant().getId().toString());
                        }

                        var authentication = new UsernamePasswordAuthenticationToken(
                                user, 
                                null, 
                                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
                        );
                        
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (Exception e) {
                // Token inválido ou expirado - não autentica, mas deixa a requisição seguir (vai dar 403 se a rota for protegida)
                logger.error("Erro ao validar token: " + e.getMessage());
            }
        }
        
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}