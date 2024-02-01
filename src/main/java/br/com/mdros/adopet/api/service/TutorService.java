package br.com.mdros.adopet.api.service;

import br.com.mdros.adopet.api.dto.TutorDto.AtualizarTutorDto;
import br.com.mdros.adopet.api.dto.TutorDto.CadastrarTutorDto;
import br.com.mdros.adopet.api.exception.TutorJaCadastradoNoSistemaException;
import br.com.mdros.adopet.api.exception.ValidacaoException;
import br.com.mdros.adopet.api.model.Tutor;
import br.com.mdros.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;
    public void cadastrar(CadastrarTutorDto tutorDto) {
        boolean tutorJaCadastrado = tutorRepository.existsByTelefoneOrEmail(tutorDto.telefone(), tutorDto.email());

        if (tutorJaCadastrado) {
            throw new TutorJaCadastradoNoSistemaException();
        }

        tutorRepository.save(new Tutor(tutorDto));
    }

    public void atualizarTutor(AtualizarTutorDto tutorDto){
        Tutor tutor = tutorRepository.getReferenceById(tutorDto.id());

        tutor.atualizarInformacoes(tutorDto);

        tutorRepository.save(tutor);
    }
}
