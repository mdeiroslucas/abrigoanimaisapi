package br.com.mdros.adopet.api.dto.TutorDto;

import jakarta.validation.constraints.NotNull;

public record AtualizarTutorDto(
        @NotNull
        long id,
        String nome,
        String telefone,
        String email

) {
}
