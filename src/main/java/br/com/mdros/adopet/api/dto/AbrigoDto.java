package br.com.mdros.adopet.api.dto;

import br.com.mdros.adopet.api.model.Abrigo;
import jakarta.validation.constraints.NotBlank;

public record AbrigoDto(
        @NotBlank
        String nome,
        @NotBlank
        String telefone,
        @NotBlank
        String email
) {
        public AbrigoDto(Abrigo abrigo){
                this(abrigo.getNome(), abrigo.getTelefone(), abrigo.getEmail());
        }
}
