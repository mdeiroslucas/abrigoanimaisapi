package br.com.mdros.adopet.api.controller;

import br.com.mdros.adopet.api.dto.ListagemPetDto;
import br.com.mdros.adopet.api.model.Pet;
import br.com.mdros.adopet.api.repository.PetRepository;
import br.com.mdros.adopet.api.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping
    public ResponseEntity<List<ListagemPetDto>> listarPetDisponiveis() {
        List<ListagemPetDto> pets = petService.listarPetDisponiveis();
        return ResponseEntity.ok(pets);
    }
}
