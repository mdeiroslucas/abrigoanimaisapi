package br.com.mdros.adopet.api.validacoes;

import br.com.mdros.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.mdros.adopet.api.exception.ValidacaoException;
import br.com.mdros.adopet.api.model.Adocao;
import br.com.mdros.adopet.api.model.StatusAdocao;
import br.com.mdros.adopet.api.model.Tutor;
import br.com.mdros.adopet.api.repository.AdocaoRepository;
import br.com.mdros.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ValidacaoTutorComLimiteDeAdocoes implements ValidacaoSolicitacaoAdocao {

    @Autowired
    private AdocaoRepository repository;

    @Autowired
    private TutorRepository tutorRepository;

    public static final int LIMITE_MAXIMO_ADOCAO = 5;

    public void validar(SolicitacaoAdocaoDto dto) {

        Tutor tutor = tutorRepository.getReferenceById(dto.idPet());
        List<Adocao> adocoes = repository.findAll();

        for (Adocao a : adocoes) {
            int contador = 0;
            if (a.getTutor() == tutor && a.getStatus() == StatusAdocao.APROVADO) {
                contador = contador + 1;
            }
            if (contador == LIMITE_MAXIMO_ADOCAO) {
                throw new ValidacaoException("Tutor chegou ao limite máximo de 5 adoções!");
            }
        }
    }
}
