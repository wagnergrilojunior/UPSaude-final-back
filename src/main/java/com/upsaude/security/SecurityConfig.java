package com.upsaude.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Desabilita CSRF já que estamos usando JWT
            .csrf(AbstractHttpConfigurer::disable)
            
            // Configura CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // Configura autorização de endpoints
            // Nota: O context-path é /api, então os caminhos devem ser relativos ao context-path
            // Spring Security automaticamente considera o context-path
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos - login e verificação de acesso
                // Paths relativos ao context-path (/api)
                .requestMatchers("/v1/auth/login").permitAll()
                .requestMatchers("/v1/auth/verificar-acesso").permitAll()
                
                // TEMPORÁRIO: Endpoints de alergias públicos para testes
                // TODO: Remover após testes
                .requestMatchers("/v1/alergias/**").permitAll()
                
                // Endpoints de teste do Redis - públicos para diagnóstico
                .requestMatchers("/v1/test/redis/**").permitAll()
                
                // Endpoints do Actuator - públicos para monitoramento via Spring Boot Admin
                // IMPORTANTE: Em produção, considere proteger estes endpoints com autenticação básica
                .requestMatchers("/actuator/**").permitAll() // Permite todos os endpoints do Actuator para Spring Boot Admin
                
                // Swagger/OpenAPI - público para desenvolvimento
                .requestMatchers("/api-docs/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/swagger-ui.html").permitAll()
                
                // Todos os outros endpoints requerem autenticação
                .anyRequest().authenticated()
            )
            
            // Desabilita sessões (stateless com JWT)
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // Configura tratamento de exceções de autenticação
            // Garante que retorna 401 em vez de 404 quando não autenticado
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(customAuthenticationEntryPoint)
            )
            
            // Adiciona o filtro JWT antes do filtro de autenticação padrão
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Permite todas as origens (em produção, configure apenas os domínios permitidos)
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
        // Quando allowCredentials é true, não pode usar "*" em allowedOrigins
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Registra CORS para todos os paths (incluindo o context-path /api)
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

