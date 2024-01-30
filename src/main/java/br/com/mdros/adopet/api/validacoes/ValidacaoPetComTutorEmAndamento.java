package br.com.mdros.adopet.api.validacoes;

import br.com.mdros.adopet.api.dto.AdocaoDto.SolicitacaoAdocaoDto;
import br.com.mdros.adopet.api.exception.ValidacaoException;
import br.com.mdros.adopet.api.model.StatusAdocao;
import br.com.mdros.adopet.api.model.Tutor;
import br.com.mdros.adopet.api.repository.AdocaoRepository;
import br.com.mdros.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidacaoPetComTutorEmAndamento implements ValidacaoSolicitacaoAdocao {

    @Autowired
    private AdocaoRepository repository;

    @Autowired
    private TutorRepository tutorRepository;

    public void validar(SolicitacaoAdocaoDto dto) {
        boolean tutorComAdocaoEmAndamento = repository.existsByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO);

            if (tutorComAdocaoEmAndamento) {
                throw new ValidacaoException("Tutor já possui outra adoção aguardando avaliação!");
            }
    }
}
