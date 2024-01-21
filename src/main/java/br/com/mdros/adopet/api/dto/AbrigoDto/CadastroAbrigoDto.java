package br.com.mdros.adopet.api.dto.AbrigoDto;

import jakarta.validation.constraints.NotBlank;

public record CadastroAbrigoDto(
        @NotBlank
        String nome,
        @NotBlank
        String email,
        @NotBlank
        String telefone
) {
}
