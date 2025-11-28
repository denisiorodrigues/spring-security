package dev.dentech.spring_security.configuracao;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import dev.dentech.spring_security.entidade.Usuario;

@Component
public class ConfiguracaoDeToken {

    private String segredo = "meu-segredo-muito-secreto-para-assinar-tokens-jwt-*^*^&^*&%$#@!";

    public String gerarToken(Usuario usuario) {
        Algorithm algoritmo = Algorithm.HMAC256(segredo);
        
        return JWT.create()
        .withClaim("userId", usuario.getId())
            .withSubject(usuario.getEmail())
            .withExpiresAt(Instant.now().plusSeconds(87987))
            .withIssuedAt(Instant.now())
            .sign(algoritmo);
    }
}
