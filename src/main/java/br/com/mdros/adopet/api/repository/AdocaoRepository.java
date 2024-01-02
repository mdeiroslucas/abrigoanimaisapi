package br.com.mdros.adopet.api.repository;

import br.com.mdros.adopet.api.model.Adocao;
import br.com.mdros.adopet.api.model.StatusAdocao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdocaoRepository extends JpaRepository<Adocao, Long> {
    boolean existsByPetIdAndStatus(Long id, StatusAdocao status);

    boolean existsByTutorIdAndStatus(Long id, StatusAdocao status);

}
