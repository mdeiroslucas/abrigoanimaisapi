package br.com.mdros.adopet.api.validacoes;

import br.com.mdros.adopet.api.dto.SolicitacaoAdocaoDto;

public interface ValidacaoSolicitacaoAdocao {
    public void validar(SolicitacaoAdocaoDto dto);
}
