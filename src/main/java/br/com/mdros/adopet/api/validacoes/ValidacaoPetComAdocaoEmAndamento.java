package br.com.mdros.adopet.api.validacoes;

import br.com.mdros.adopet.api.dto.AdocaoDto.SolicitacaoAdocaoDto;
import br.com.mdros.adopet.api.exception.ValidacaoException;
import br.com.mdros.adopet.api.model.StatusAdocao;
import br.com.mdros.adopet.api.repository.AdocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidacaoPetComAdocaoEmAndamento implements ValidacaoSolicitacaoAdocao {

    @Autowired
    private AdocaoRepository adocaoRepository;

    public void validar(SolicitacaoAdocaoDto dto) {
        boolean petTemAdocaoEmAndamento = adocaoRepository.existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO);

        if (petTemAdocaoEmAndamento) {
            throw new ValidacaoException("Pet já está aguardando avaliação para ser adotado!");
        }
    }
}
