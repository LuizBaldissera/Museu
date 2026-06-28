package com.example.crud.service;

import com.example.crud.model.Role;
import com.example.crud.model.Usuario;
import com.example.crud.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
        if (!u.isAtivo()) throw new UsernameNotFoundException("Usuário inativo.");
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + u.getRole().name())));
    }

    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    public Optional<Usuario> buscarOpcionalPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    /**
     * Registra um novo usuário com perfil BIBLIOTECARIO.
     * Não há dependência circular: criamos o BCrypt aqui diretamente.
     */
    @Transactional
    public void registrar(String username, String senha, String nomeCompleto) {
        if (username == null || username.isBlank())
            throw new IllegalArgumentException("O nome de usuário é obrigatório.");
        if (nomeCompleto == null || nomeCompleto.isBlank())
            throw new IllegalArgumentException("O nome completo é obrigatório.");
        if (senha == null || senha.length() < 6)
            throw new IllegalArgumentException("A senha precisa ter pelo menos 6 caracteres.");

        String user = username.trim().toLowerCase();
        if (usuarioRepository.findByUsername(user).isPresent())
            throw new IllegalArgumentException("O usuário '" + user + "' já está em uso. Escolha outro.");

        Usuario u = new Usuario();
        u.setUsername(user);
        u.setPassword(new BCryptPasswordEncoder().encode(senha));
        u.setRole(Role.BIBLIOTECARIO);
        u.setNomeCompleto(nomeCompleto.trim());
        u.setAtivo(true);
        usuarioRepository.save(u);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
}
