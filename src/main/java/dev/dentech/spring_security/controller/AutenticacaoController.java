package dev.dentech.spring_security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.dentech.spring_security.configuracao.ConfiguracaoDeToken;
import dev.dentech.spring_security.dto.requisicao.LoginRequisicao;
import dev.dentech.spring_security.dto.requisicao.RegistroUsuarioRequisicao;
import dev.dentech.spring_security.dto.resposta.LoginResposta;
import dev.dentech.spring_security.dto.resposta.RegistroDoUsuarioResposta;
import dev.dentech.spring_security.entidade.Usuario;
import dev.dentech.spring_security.repositorio.UsuarioRepositorio;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager gerenciadorDeAutenticacao;
    private final ConfiguracaoDeToken configuracaoDeToken;

    public AutenticacaoController(UsuarioRepositorio usuarioRepositorio, PasswordEncoder passwordEncoder,
            AuthenticationManager gerenciadorDeAutenticacao, ConfiguracaoDeToken configuracaoDeToken) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
        this.gerenciadorDeAutenticacao = gerenciadorDeAutenticacao;
        this.configuracaoDeToken = configuracaoDeToken;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResposta> login(@Valid @RequestBody LoginRequisicao requisicao) {
        UsernamePasswordAuthenticationToken dadosDeLogin = 
            new UsernamePasswordAuthenticationToken(requisicao.email(), requisicao.senha());

        Authentication autenticacao = gerenciadorDeAutenticacao.authenticate(dadosDeLogin);
        
        Usuario usuarioAutenticado = (Usuario) autenticacao.getPrincipal();
        String token = configuracaoDeToken.gerarToken(usuarioAutenticado);
        return ResponseEntity.ok(new LoginResposta(token));
    }

    @PostMapping("/registrar")
    public ResponseEntity<RegistroDoUsuarioResposta> registrar(@Valid @RequestBody RegistroUsuarioRequisicao requisicao) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(requisicao.email());
        novoUsuario.setSenha(passwordEncoder.encode(requisicao.senha()));  
        novoUsuario.setNome(requisicao.nome());
        
        this.usuarioRepositorio.save(novoUsuario);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new RegistroDoUsuarioResposta(novoUsuario.getNome(), novoUsuario.getEmail()));
    }
}
