package com.techstockmaster.api.controllers.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/*
 * Classe responsável pro mostrar possíveis erros da entidade UserModal.
 */
public record UserDto(
                @NotBlank(message = "O nome não deve estar em branco") @Pattern(regexp = ".{10,}", message = "O nome deve ter no mínimo 10 caracteres") String fullName,

                @NotBlank(message = "O email não deve estar em branco") @Email(message = "Email deve ser válido") String gmail,

                @NotBlank(message = "O nome não deve estar em branco") String username,

                @NotBlank(message = "O nome não deve estar em branco") String password,

                @NotBlank(message = "O nome não deve estar em branco") @Pattern(regexp = "(?i)SIM|NÃO", message = "fullAccess deve ser SIM ou NÃO") String fullAccess,

                @NotBlank(message = "O nome não deve estar em branco") @Pattern(regexp = "(?i)Comum|Técnico|Admin", message = "userType deve ser Comum, Técnico ou Admin") String userType) {
}
