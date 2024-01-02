package br.com.mdros.adopet.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReprovacaoAdocaoDto(
        @NotNull
        Long idAdocao,
        @NotBlank
        String justificativa
        ) {
}