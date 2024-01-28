package br.com.mdros.adopet.api.validacoes;

import br.com.mdros.adopet.api.dto.AdocaoDto.SolicitacaoAdocaoDto;
import br.com.mdros.adopet.api.exception.ValidacaoException;
import br.com.mdros.adopet.api.model.Pet;
import br.com.mdros.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetDisponivelTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private SolicitacaoAdocaoDto solicitacaoAdocaoDto;

    @InjectMocks
    private ValidacaoPetDisponivel validacaoPetDisponivel;

    @Mock
    private Pet pet;

    @Test
    void deveriaPermitirAdocaoPet(){
        BDDMockito.given(petRepository.getReferenceById(solicitacaoAdocaoDto.idPet())).willReturn(pet);
        BDDMockito.given(pet.getAdotado()).willReturn(false);

        Assertions.assertDoesNotThrow(() -> validacaoPetDisponivel.validar(solicitacaoAdocaoDto));
    }

    @Test
    void naoDeveriaPermitirAdocaoPet(){
        BDDMockito.given(petRepository.getReferenceById(solicitacaoAdocaoDto.idPet())).willReturn(pet);
        BDDMockito.given(pet.getAdotado()).willReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> validacaoPetDisponivel.validar(solicitacaoAdocaoDto));
    }

}