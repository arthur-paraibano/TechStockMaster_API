package com.techstockmaster.api.controllers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record FeedbackDto(
        @NotBlank(message = "O ID do Usuário não deve estar em branco") Integer idUser,
        @NotBlank(message = "A relevancia não deve estar em branco") @Pattern(regexp = "(?i)ALTO|MEDIO|BAIXO", message = "status deve ser PENDENTE ou RESOLVIDO") String relevancia,
        @NotBlank(message = "A relevancia não deve estar em branco") @Pattern(regexp = ".{5,}", message = "A mensagem deve ter no mínimo 5 caracteres")String descricaoMensagem
        ) {
}
