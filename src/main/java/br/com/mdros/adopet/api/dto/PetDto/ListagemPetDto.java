package br.com.mdros.adopet.api.dto.PetDto;

import br.com.mdros.adopet.api.model.Pet;
import br.com.mdros.adopet.api.model.TipoPet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ListagemPetDto(
        @NotNull
        TipoPet tipo,
        @NotBlank
        String nome,
        @NotBlank
        String raca,
        @NotNull
        Integer idade
) {
    public ListagemPetDto(Pet pet){
        this(pet.getTipo(), pet.getNome(), pet.getRaca(), pet.getIdade());
    }
}
