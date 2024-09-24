package com.techstockmaster.api.controllers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CalledTiDto(
        @NotNull(message = "O ID do Usuário não deve estar em branco") Integer idUser,
        @NotNull(message = "O ID do Setor não deve estar em branco") Integer idSector,
        @NotBlank(message = "A descrição da mensagem não deve estar em branco")
        @Pattern(regexp = ".{5,}", message = "A mensagem deve ter no mínimo 5 caracteres") String descricaoMensagem
) {
}
