package br.com.mdros.adopet.api.repository;

import br.com.mdros.adopet.api.model.Abrigo;
import br.com.mdros.adopet.api.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findAllByAdotadoFalse();

    List<Pet> findByAbrigo(Abrigo abrigo);

}
