package dev.dentech.spring_security.configuracao;

import lombok.Builder;

@Builder
public record JWTUserDetails(Long userId, String email) {

}
