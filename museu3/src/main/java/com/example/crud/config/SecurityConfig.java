package com.example.crud.config;

import com.example.crud.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Configuração de segurança (Req. 7 – login não obrigatório para o público).
 *
 * Perfis:
 *   Público     → /obras/**, /registro, /login
 *   BIBLIOTECARIO → /biblio/**, notas internas
 *   ADMIN       → /admin/**, tudo acima
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioService usuarioService;

    public SecurityConfig(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authProvider())
            .authorizeHttpRequests(auth -> auth
                // Recursos estáticos e páginas públicas
                .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.svg").permitAll()
                .requestMatchers("/", "/obras", "/obras/**", "/login", "/error",
                                 "/registro", "/acesso-negado").permitAll()

                // Notas internas: ADMIN ou BIBLIOTECARIO
                .requestMatchers("/obras/*/nota/**").hasAnyRole("ADMIN", "BIBLIOTECARIO")

                // Painel do bibliotecário
                .requestMatchers("/biblio/**").hasAnyRole("ADMIN", "BIBLIOTECARIO")

                // Área administrativa
                .requestMatchers("/admin/**").hasRole("ADMIN")

                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(successHandler())
                .failureUrl("/login?erro=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/obras")
                .permitAll()
            )
            .exceptionHandling(ex -> ex.accessDeniedPage("/acesso-negado"));

        return http.build();
    }

    /**
     * Após login: ADMIN → /admin/dashboard | BIBLIOTECARIO → /biblio/painel
     */
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (HttpServletRequest req, HttpServletResponse res, Authentication auth) -> {
            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            res.sendRedirect(isAdmin ? "/admin/dashboard" : "/biblio/painel");
        };
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        var p = new DaoAuthenticationProvider();
        p.setUserDetailsService(usuarioService);
        p.setPasswordEncoder(passwordEncoder());
        return p;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
