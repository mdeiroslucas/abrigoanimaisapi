package br.com.mdros.adopet.api.service;

import br.com.mdros.adopet.api.dto.TutorDto.AtualizarTutorDto;
import br.com.mdros.adopet.api.dto.TutorDto.CadastrarTutorDto;
import br.com.mdros.adopet.api.model.Tutor;
import br.com.mdros.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @InjectMocks
    private TutorService tutorService;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private Tutor tutor;

    @Mock
    private CadastrarTutorDto cadastrarTutorDto;

    @Mock
    private AtualizarTutorDto atualizarTutorDto;

    @Test
    void cadastrar() {
        BDDMockito.when(tutorRepository.existsByTelefoneOrEmail(cadastrarTutorDto.telefone(), cadastrarTutorDto.email())).thenReturn(false);

        assertDoesNotThrow(() -> tutorService.cadastrar(cadastrarTutorDto));

        BDDMockito.then(tutorRepository).should().save(new Tutor(cadastrarTutorDto));
    }

    @Test
    void atualizarTutor() {
        BDDMockito.given(tutorRepository.getReferenceById(atualizarTutorDto.id())).willReturn(tutor);

        tutorService.atualizarTutor(atualizarTutorDto);

        BDDMockito.then(tutorRepository).should().save(tutor);
    }
}