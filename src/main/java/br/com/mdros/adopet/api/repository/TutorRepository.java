package br.com.mdros.adopet.api.repository;

import br.com.mdros.adopet.api.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TutorRepository extends JpaRepository<Tutor, Long> {

    boolean existsByTelefoneOrEmail(String telefone, String email);

    @Query(value = "SELECT COUNT(id) FROM adocao WHERE tutor_id = :tutorId AND status = 'APROVADA'", nativeQuery = true)
    int countAdocoesAprovadasByTutorId(@Param("tutorId") Long tutorId);

}
