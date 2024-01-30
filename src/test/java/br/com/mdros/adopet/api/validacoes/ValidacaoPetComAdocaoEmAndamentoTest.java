package br.com.mdros.adopet.api.validacoes;

import br.com.mdros.adopet.api.dto.AdocaoDto.SolicitacaoAdocaoDto;
import br.com.mdros.adopet.api.dto.PetDto.PetDto;
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
class ValidacaoPetComAdocaoEmAndamentoTest {
    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private SolicitacaoAdocaoDto solicitacaoAdocaoDto;

    @InjectMocks
    private ValidacaoPetComAdocaoEmAndamento validacao;

    @Test
    void deveRetornarFalsoParaAdocaoEmAndamento (){

        BDDMockito.given(adocaoRepository.existsByPetIdAndStatus(solicitacaoAdocaoDto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(false);

        Assertions.assertDoesNotThrow(() -> validacao.validar(solicitacaoAdocaoDto));
    }

    @Test
    void deveRetornarTrueParaAdocaoEmAndamento (){

        BDDMockito.given(adocaoRepository.existsByPetIdAndStatus(solicitacaoAdocaoDto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(solicitacaoAdocaoDto));
    }


}