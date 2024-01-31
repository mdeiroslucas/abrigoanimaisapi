package br.com.mdros.adopet.api.controller;

import br.com.mdros.adopet.api.dto.AbrigoDto.AbrigoDto;
import br.com.mdros.adopet.api.dto.AbrigoDto.CadastroAbrigoDto;
import br.com.mdros.adopet.api.dto.PetDto.CadastroPetDto;
import br.com.mdros.adopet.api.dto.PetDto.ListagemPetDto;
import br.com.mdros.adopet.api.exception.ValidacaoException;
import br.com.mdros.adopet.api.service.AbrigoService;
import br.com.mdros.adopet.api.service.PetService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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
    public List<ListagemPetDto> listarPets(@PathVariable String idOuNome) {
        return abrigoService.listarPets(idOuNome);
    }

    @PostMapping("/{idOuNome}/pets")
    @Transactional
    public ResponseEntity<String> cadastrarPet(@PathVariable String idOuNome, @RequestBody @Valid CadastroPetDto petDto) {
        try {
            abrigoService.cadastrarPet(idOuNome, petDto);

            return ResponseEntity.ok().build();
        } catch (ValidacaoException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
