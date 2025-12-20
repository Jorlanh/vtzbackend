package com.votzz.backend.config;

import com.votzz.backend.config.security.TenantSecurityFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final TenantSecurityFilter tenantSecurityFilter;

    public SecurityConfig(TenantSecurityFilter tenantSecurityFilter) {
        this.tenantSecurityFilter = tenantSecurityFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // Desabilita CSRF para API
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Habilita CORS
            .authorizeHttpRequests(auth -> auth
                // Libera endpoints públicos, login e WebSocket
                .requestMatchers("/api/auth/**", "/ws-votzz/**", "/h2-console/**", "/api/reports/**").permitAll()
                // Qualquer outra requisição precisa de autenticação (ou do nosso filtro simulado)
                .anyRequest().authenticated()
            )
            // Adiciona nosso filtro de Tenant antes do filtro de autenticação padrão
            .addFilterBefore(tenantSecurityFilter, UsernamePasswordAuthenticationFilter.class);
            
        // Configuração necessária para o H2 Console funcionar no navegador
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:3000")); // Seu Frontend
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}