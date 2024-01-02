package br.com.mdros.adopet.api.service;

import br.com.mdros.adopet.api.dto.CadastroAbrigoDto;
import br.com.mdros.adopet.api.exception.ValidacaoException;
import br.com.mdros.adopet.api.model.Abrigo;
import br.com.mdros.adopet.api.repository.AbrigoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository abrigoRepository;
    public void cadastrar(@Valid CadastroAbrigoDto abrigoDto) {
        boolean cadastroJaExiste = abrigoRepository.existsByNomeOrTelefoneOrEmail(abrigoDto.nome(), abrigoDto.telefone(), abrigoDto.email());

        if (cadastroJaExiste) {
            throw new ValidacaoException("Dados já cadastrados para outro abrigo!");
        }

        Abrigo abrigo = new Abrigo(abrigoDto.nome(), abrigoDto.telefone(), abrigoDto.email());

        abrigoRepository.save(abrigo);
    }
}
