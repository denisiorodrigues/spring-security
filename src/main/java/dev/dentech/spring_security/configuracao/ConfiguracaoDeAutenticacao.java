package dev.dentech.spring_security.configuracao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.dentech.spring_security.repositorio.UsuarioRepositorio;

@Service
public class ConfiguracaoDeAutenticacao implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;
    
    public ConfiguracaoDeAutenticacao(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return usuarioRepositorio.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
