package br.com.mdros.adopet.api.validacoes;

import br.com.mdros.adopet.api.dto.AdocaoDto.SolicitacaoAdocaoDto;

public interface ValidacaoSolicitacaoAdocao {
    void validar(SolicitacaoAdocaoDto dto);
}
