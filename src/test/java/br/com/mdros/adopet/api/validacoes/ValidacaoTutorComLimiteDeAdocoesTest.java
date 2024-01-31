package br.com.mdros.adopet.api.validacoes;

import br.com.mdros.adopet.api.dto.AdocaoDto.SolicitacaoAdocaoDto;
import br.com.mdros.adopet.api.exception.ValidacaoException;
import br.com.mdros.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComLimiteDeAdocoesTest {

    @Mock
    private SolicitacaoAdocaoDto solicitacaoAdocaoDto;

    @Mock
    private TutorRepository tutorRepository;

    @InjectMocks
    private ValidacaoTutorComLimiteDeAdocoes validacao;

    @Test
    void naoDeveriaPermitirAdocaoDeTutorQueTenhaUltrapassadoNumeroMaximoDeAdocao(){
        BDDMockito.given(tutorRepository.countAdocoesAprovadasByTutorId(solicitacaoAdocaoDto.idTutor())).willReturn(5);

        Assertions.assertThrows(ValidacaoException.class ,()-> validacao.validar(solicitacaoAdocaoDto));
    }

    @Test
    void deveriaPermitirAdocaoDeTutorQueTenhaUltrapassadoNumeroMaximoDeAdocao(){
        BDDMockito.given(tutorRepository.countAdocoesAprovadasByTutorId(solicitacaoAdocaoDto.idTutor())).willReturn(4);

        Assertions.assertDoesNotThrow(()-> validacao.validar(solicitacaoAdocaoDto));
    }

}