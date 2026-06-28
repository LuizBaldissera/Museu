package com.example.crud;

import com.example.crud.model.*;
import com.example.crud.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Ponto de entrada.
 *
 * Usuários criados na 1ª execução:
 *   admin   / admin123   → ADMIN
 *   biblio  / biblio123  → BIBLIOTECARIO
 */
@SpringBootApplication
public class CrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudApplication.class, args);
    }

    @Bean
    CommandLineRunner seed(UsuarioRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.count() > 0) return;

            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            admin.setNomeCompleto("Administrador");
            admin.setAtivo(true);
            repo.save(admin);

            Usuario biblio = new Usuario();
            biblio.setUsername("biblio");
            biblio.setPassword(encoder.encode("biblio123"));
            biblio.setRole(Role.BIBLIOTECARIO);
            biblio.setNomeCompleto("Bibliotecário");
            biblio.setAtivo(true);
            repo.save(biblio);

            System.out.println("==============================================");
            System.out.println("  ADMIN       → admin   / admin123");
            System.out.println("  BIBLIOTECARIO → biblio / biblio123");
            System.out.println("==============================================");
        };
    }
}
