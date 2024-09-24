package com.techstockmaster.api.controllers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record FeedbackDto(
        @NotNull(message = "O ID do Usuário não deve estar em branco") Integer idUser,
        @NotBlank(message = "A relevância não deve estar em branco") String relevancia,
        @NotBlank(message = "A descrição da mensagem não deve estar em branco")
        @Pattern(regexp = ".{5,}", message = "A mensagem deve ter no mínimo 5 caracteres") String descricaoMensagem
) {
}
