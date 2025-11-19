package dev.dentech.spring_security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public AutenticacaoController(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResposta> login(@Valid @RequestBody LoginRequisicao requisicao) {
        return null;        
    }

    @PostMapping("/registrar")
    public ResponseEntity<RegistroDoUsuarioResposta> registrar(@Valid @RequestBody RegistroUsuarioRequisicao requisicao) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(requisicao.email());
        novoUsuario.setSenha(requisicao.senha());  
        novoUsuario.setNome(requisicao.nome());
        
        this.usuarioRepositorio.save(novoUsuario);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new RegistroDoUsuarioResposta(novoUsuario.getNome(), novoUsuario.getEmail()));
    }
}
