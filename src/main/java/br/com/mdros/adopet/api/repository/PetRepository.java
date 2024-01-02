package br.com.mdros.adopet.api.repository;

import br.com.mdros.adopet.api.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {

}
