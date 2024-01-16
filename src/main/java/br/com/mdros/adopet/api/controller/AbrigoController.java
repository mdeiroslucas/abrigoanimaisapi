package br.com.mdros.adopet.api.controller;

import br.com.mdros.adopet.api.dto.AbrigoDto;
import br.com.mdros.adopet.api.dto.CadastroAbrigoDto;
import br.com.mdros.adopet.api.dto.ListagemPetDto;
import br.com.mdros.adopet.api.exception.ValidacaoException;
import br.com.mdros.adopet.api.model.Abrigo;
import br.com.mdros.adopet.api.model.Pet;
import br.com.mdros.adopet.api.repository.AbrigoRepository;
import br.com.mdros.adopet.api.service.AbrigoService;
import br.com.mdros.adopet.api.service.PetService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abrigos")
public class AbrigoController {

    @Autowired
    private PetService petService;

    @Autowired
    private AbrigoService abrigoService;

    @GetMapping
    public ResponseEntity<List<AbrigoDto>> listar() {

        return ResponseEntity.ok(abrigoService.listar());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastroAbrigoDto abrigoDto) {
        try {
            abrigoService.cadastrar(abrigoDto);
            return ResponseEntity.ok("Dados cadastrados com sucesso!");
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{idOuNome}/pets")
    public ResponseEntity<List<ListagemPetDto>> listarPets(@PathVariable String idOuNome) {
        try {
            return abrigoService.listarPets(idOuNome);

        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{idOuNome}/pets")
    @Transactional
    public ResponseEntity<String> cadastrarPet(@PathVariable String idOuNome, @RequestBody @Valid Pet pet) {
        try {
            Long id = Long.parseLong(idOuNome);
            Abrigo abrigo = abrigoRepository.getReferenceById(id);
            pet.setAbrigo(abrigo);
            pet.setAdotado(false);
            abrigo.getPets().add(pet);
            abrigoRepository.save(abrigo);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException enfe) {
            return ResponseEntity.notFound().build();
        } catch (NumberFormatException nfe) {
            try {
                Abrigo abrigo = abrigoRepository.findByNome(idOuNome);
                pet.setAbrigo(abrigo);
                pet.setAdotado(false);
                abrigo.getPets().add(pet);
                abrigoRepository.save(abrigo);
                return ResponseEntity.ok().build();
            } catch (EntityNotFoundException enfe) {
                return ResponseEntity.notFound().build();
            }
        }
    }

}
