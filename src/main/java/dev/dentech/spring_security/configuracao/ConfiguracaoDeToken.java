package dev.dentech.spring_security.configuracao;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

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

    public Optional<JWTUserDetails> validar(String token) {
        
        try {
            Algorithm algoritmo = Algorithm.HMAC256(segredo);

            JWTVerifier verificador = JWT.require(algoritmo)
                .build();

            DecodedJWT jwt = verificador.verify(token);

            var userDetails = JWTUserDetails
            .builder()
            .userId(jwt.getClaim("id").asLong())
            .email(jwt.getSubject())
            .build();

            return Optional.of(userDetails);
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }
}
