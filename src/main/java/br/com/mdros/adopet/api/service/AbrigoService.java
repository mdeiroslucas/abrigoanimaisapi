package br.com.mdros.adopet.api.service;

import br.com.mdros.adopet.api.dto.AbrigoDto;
import br.com.mdros.adopet.api.dto.CadastroAbrigoDto;
import br.com.mdros.adopet.api.dto.CadastroPetDto;
import br.com.mdros.adopet.api.dto.ListagemPetDto;
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
//        try {
            Long id = Long.parseLong(idOuNome);
            List<Pet> pets = abrigoRepository.getReferenceById(id).getPets();
            List<ListagemPetDto> petsDto = pets.stream().map(ListagemPetDto::new).collect(Collectors.toList());

            return ResponseEntity.ok(petsDto);
//        } catch (EntityNotFoundException enfe) {
//            return ResponseEntity.notFound().build();
//        } catch (NumberFormatException e) {
//            try {
//                List<Pet> pets = repository.findByNome(idOuNome).getPets();
//                return ResponseEntity.ok(pets);
//            } catch (EntityNotFoundException enfe) {
//                return ResponseEntity.notFound().build();
//            }
//        }
    }

    public void cadastrarPet(String idOuNome, @Valid CadastroPetDto petDto) {
        try {
            Long id = Long.parseLong(idOuNome);
            Abrigo abrigo = abrigoRepository.getReferenceById(id);
            Pet pet = new Pet(petDto);
            abrigo.getPets().add(pet);
            abrigoRepository.save(abrigo);
//            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException enfe) {
            return ResponseEntity.notFound().build();
        }
    }
}
