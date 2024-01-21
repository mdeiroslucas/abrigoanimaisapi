package br.com.mdros.adopet.api.controller;

import br.com.mdros.adopet.api.dto.TutorDto.AtualizarTutorDto;
import br.com.mdros.adopet.api.dto.TutorDto.CadastrarTutorDto;
import br.com.mdros.adopet.api.model.Tutor;
import br.com.mdros.adopet.api.repository.TutorRepository;
import br.com.mdros.adopet.api.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutores")
public class TutorController {

    @Autowired
    private TutorRepository repository;

    @Autowired
    private TutorService tutorService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastrarTutorDto tutorDto) {

        tutorService.cadastrar(tutorDto);

        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<String> atualizar(@RequestBody @Valid AtualizarTutorDto tutorDto) {
        tutorService.atualizarTutor(tutorDto);

        return ResponseEntity.ok().build();
    }

}
