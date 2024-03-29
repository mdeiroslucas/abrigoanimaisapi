package br.com.mdros.adopet.api.service;

import br.com.mdros.adopet.api.dto.PetDto.CadastroPetDto;
import br.com.mdros.adopet.api.dto.PetDto.PetDto;
import br.com.mdros.adopet.api.model.Abrigo;
import br.com.mdros.adopet.api.model.Pet;
import br.com.mdros.adopet.api.repository.AbrigoRepository;
import br.com.mdros.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private AbrigoRepository abrigoRepository;

    public List<PetDto> listarPetDisponiveis() {
        return petRepository
                    .findAllByAdotadoFalse()
                    .stream()
                    .map(PetDto::new)
                    .toList();
    }

    public void cadastrarPet(CadastroPetDto cadastroPetDto){
        Abrigo abrigo = abrigoRepository.getReferenceById(cadastroPetDto.abrigo().getId());
        Pet pet = new Pet(cadastroPetDto, abrigo);

       petRepository.save(pet);
    }
}
