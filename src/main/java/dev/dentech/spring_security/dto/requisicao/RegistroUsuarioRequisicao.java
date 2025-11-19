package dev.dentech.spring_security.dto.requisicao;

import jakarta.validation.constraints.NotEmpty;

public record RegistroUsuarioRequisicao(@NotEmpty(message = "O campo nome não pode estar vazio") String nome,
                                        @NotEmpty(message = "O campo email não pode estar vazio") String email,
                                        @NotEmpty(message = "O campo senha não pode estar vazio") String senha) {

}
