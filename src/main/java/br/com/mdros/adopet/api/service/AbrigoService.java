package br.com.mdros.adopet.api.service;

import br.com.mdros.adopet.api.dto.AbrigoDto.AbrigoDto;
import br.com.mdros.adopet.api.dto.AbrigoDto.CadastroAbrigoDto;
import br.com.mdros.adopet.api.dto.PetDto.CadastroPetDto;
import br.com.mdros.adopet.api.dto.PetDto.ListagemPetDto;
import br.com.mdros.adopet.api.exception.ValidacaoException;
import br.com.mdros.adopet.api.model.Abrigo;
import br.com.mdros.adopet.api.model.Pet;
import br.com.mdros.adopet.api.repository.AbrigoRepository;
import br.com.mdros.adopet.api.repository.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository abrigoRepository;

    @Autowired
    private PetRepository petRepository;


    public List<AbrigoDto> listar(){
        return abrigoRepository
                .findAll()
                .stream()
                .map(AbrigoDto::new)
                .toList();
    }
    public void cadastrar(@Valid CadastroAbrigoDto abrigoDto) {
        boolean cadastroJaExiste = abrigoRepository.existsByNomeOrTelefoneOrEmail(abrigoDto.nome(), abrigoDto.telefone(), abrigoDto.email());

        if (cadastroJaExiste) {
            throw new ValidacaoException("Dados já cadastrados para outro abrigo!");
        }

        Abrigo abrigo = new Abrigo(abrigoDto);

        abrigoRepository.save(abrigo);
    }

    public ResponseEntity<List<ListagemPetDto>> listarPets(@Valid String idOuNome){
            Long id = Long.parseLong(idOuNome);
            List<Pet> pets = abrigoRepository.getReferenceById(id).getPets();

            List<ListagemPetDto> petsDto = pets.stream().map(ListagemPetDto::new).collect(Collectors.toList());

            return ResponseEntity.ok(petsDto);
    }

    public void cadastrarPet(String idOuNome, CadastroPetDto petDto) {
        Abrigo abrigo;
        try {
            Long id = Long.parseLong(idOuNome);
            abrigo = abrigoRepository.getReferenceById(id);
        } catch (NumberFormatException e) {
            abrigo = abrigoRepository.findByNome(idOuNome);
        }

        if (abrigo == null) {
            throw new RuntimeException("Abrigo não encontrado");
        }

        Pet pet = new Pet(petDto, abrigo);
        petRepository.save(pet);
        abrigo.adicionarPet(pet);
        abrigoRepository.save(abrigo);
    }
}
