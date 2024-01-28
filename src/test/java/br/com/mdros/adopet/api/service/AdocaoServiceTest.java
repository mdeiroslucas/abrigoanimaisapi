package br.com.mdros.adopet.api.service;

import br.com.mdros.adopet.api.dto.AdocaoDto.SolicitacaoAdocaoDto;
import br.com.mdros.adopet.api.model.Abrigo;
import br.com.mdros.adopet.api.model.Adocao;
import br.com.mdros.adopet.api.model.Pet;
import br.com.mdros.adopet.api.model.Tutor;
import br.com.mdros.adopet.api.repository.AdocaoRepository;
import br.com.mdros.adopet.api.repository.PetRepository;
import br.com.mdros.adopet.api.repository.TutorRepository;
import br.com.mdros.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {

    @InjectMocks
    private AdocaoService adocaoService;
    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private EmailService emailService;

    @Spy
    private List<ValidacaoSolicitacaoAdocao> validacoes = new ArrayList<>();

    @Mock
    private ValidacaoSolicitacaoAdocao validador1;

    @Mock
    private ValidacaoSolicitacaoAdocao validador2;
    @Mock
    private Pet pet;

    @Mock
    private Tutor tutor;

    @Mock
    private Abrigo abrigo;

    private SolicitacaoAdocaoDto dto;
    @Captor
    private ArgumentCaptor<Adocao> adocaoCaptor;

    @Test
    void deveriaSalvarAdocaoAoSolicitar(){

        this.dto = new SolicitacaoAdocaoDto(10l, 20l, "Motivo");
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);

        validacoes.add(validador1);
        validacoes.add(validador2);

        adocaoService.solicitar(dto);


        BDDMockito.then(adocaoRepository).should(times(1)).save(adocaoCaptor.capture());

        Adocao adocaoSalva = adocaoCaptor.getValue();

        assertEquals(pet, adocaoSalva.getPet());
        assertEquals(tutor, adocaoSalva.getTutor());
        assertEquals(dto.motivo(), adocaoSalva.getMotivo());
    }

    @Test
    void deveriaChamarValidadoresAoSolicitar(){

        this.dto = new SolicitacaoAdocaoDto(10l, 20l, "Motivo");
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);

        validacoes.add(validador1);
        validacoes.add(validador2);

        adocaoService.solicitar(dto);

        for (ValidacaoSolicitacaoAdocao validacao : validacoes) {
            verify(validacao, times(1)).validar(dto);
        }

//        BDDMockito.then(validador1).should().validar(dto);
//        BDDMockito.then(validador2).should().validar(dto);

//        BDDMockito.then(validador1).should().validar(dto);
//        BDDMockito.then(validador2).should().validar(dto);

    }

}