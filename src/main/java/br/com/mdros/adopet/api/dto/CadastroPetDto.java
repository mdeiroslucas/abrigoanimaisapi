package br.com.mdros.adopet.api.dto;

import br.com.mdros.adopet.api.model.Abrigo;
import br.com.mdros.adopet.api.model.Pet;
import br.com.mdros.adopet.api.model.TipoPet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroPetDto(
        @NotNull
        TipoPet tipo,
        @NotBlank
        String nome,
        @NotBlank
        String raca,
        @NotNull
        Integer idade,

        @NotBlank
        String cor,
        @NotNull
        float peso

//        @NotNull
//        AbrigoDto abritoDto
){
        public CadastroPetDto(Pet pet) {
                this(pet.getTipo(), pet.getNome(), pet.getRaca(), pet.getIdade(), pet.getCor(), pet.getPeso());
        }
}
