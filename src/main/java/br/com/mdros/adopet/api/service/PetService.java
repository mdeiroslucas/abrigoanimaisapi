package br.com.mdros.adopet.api.service;

import br.com.mdros.adopet.api.dto.CadastroPetDto;
import br.com.mdros.adopet.api.dto.ListagemPetDto;
import br.com.mdros.adopet.api.model.Abrigo;
import br.com.mdros.adopet.api.model.Pet;
import br.com.mdros.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public List<ListagemPetDto> listarPetDisponiveis() {
        return petRepository
                    .findAllByAdotadoFalse()
                    .stream()
                    .map(ListagemPetDto::new)
                    .toList();
    }

//    public void cadastrarPet(Abrigo abrigo, CadastroPetDto petDto) {
//        petRepository.save(new Pet(petDto));
//    }
}
