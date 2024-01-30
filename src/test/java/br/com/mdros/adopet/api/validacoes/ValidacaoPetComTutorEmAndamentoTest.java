package br.com.mdros.adopet.api.validacoes;

import br.com.mdros.adopet.api.dto.AdocaoDto.SolicitacaoAdocaoDto;
import br.com.mdros.adopet.api.exception.ValidacaoException;
import br.com.mdros.adopet.api.model.StatusAdocao;
import br.com.mdros.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetComTutorEmAndamentoTest {
    @Mock
    private AdocaoRepository adocaoRepository;
    @Mock
    private SolicitacaoAdocaoDto solicitacaoAdocaoDto;
    @InjectMocks
    private ValidacaoPetComTutorEmAndamento validacao;

    @Test
    void deveriaBloquearAdocaoQuandoTutorJaEstiverComOutraAdocaoEmAndamento(){
        BDDMockito.given(
                adocaoRepository.existsByTutorIdAndStatus(solicitacaoAdocaoDto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO)
        ).willReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(solicitacaoAdocaoDto));
    }

    @Test
    void naoDeveriaBloquearAdocaoQuandoTutorJaEstiverComOutraAdocaoEmAndamento(){
        BDDMockito.given(
                adocaoRepository.existsByTutorIdAndStatus(solicitacaoAdocaoDto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO)
        ).willReturn(false);

        Assertions.assertDoesNotThrow(() -> validacao.validar(solicitacaoAdocaoDto));
    }

}