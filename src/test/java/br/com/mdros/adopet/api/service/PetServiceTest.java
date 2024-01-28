package br.com.mdros.adopet.api.service;

import br.com.mdros.adopet.api.dto.PetDto.CadastroPetDto;
import br.com.mdros.adopet.api.dto.PetDto.PetDto;
import br.com.mdros.adopet.api.model.Abrigo;
import br.com.mdros.adopet.api.model.Pet;
import br.com.mdros.adopet.api.model.TipoPet;
import br.com.mdros.adopet.api.repository.AbrigoRepository;
import br.com.mdros.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetService petService;

    @Mock
    private AbrigoRepository abrigoRepository;

    @Mock
    private Pet pet;

    @Mock
    private List<Pet> petsDisponiveis = new ArrayList<>();

    @Mock
    private CadastroPetDto cadastroPetDto;

    @Mock
    private Abrigo abrigo;

    @Captor
    private ArgumentCaptor<Pet> petCaptor;

    @Test
    void deveBuscarPetDisponiveis() {

        // Arrange
        List<Pet> availablePets = new ArrayList<>();

        Pet pet1 = new Pet();
        pet1.setId(1L);
        pet1.setTipo(TipoPet.CACHORRO);
        pet1.setNome("Rex");
        pet1.setRaca("Labrador");
        pet1.setIdade(2);
        pet1.setAdotado(Boolean.FALSE);

        Pet pet2 = new Pet();
        pet2.setId(2L);
        pet2.setTipo(TipoPet.GATO);
        pet2.setNome("Miau");
        pet2.setRaca("Persa");
        pet2.setIdade(3);
        pet2.setAdotado(Boolean.FALSE);
        availablePets.add(pet1);
        availablePets.add(pet2);

        Mockito.when(petRepository.findAllByAdotadoFalse()).thenReturn(availablePets);

        // Act
        List<PetDto> result = petService.listarPetDisponiveis();

        // Assert
        assertEquals(availablePets.size(), result.size());
        assertEquals(availablePets.get(0).getId(), result.get(0).id());
        assertEquals(availablePets.get(0).getTipo(), result.get(0).tipo());
        assertEquals(availablePets.get(0).getNome(), result.get(0).nome());
        assertEquals(availablePets.get(0).getRaca(), result.get(0).raca());
        assertEquals(availablePets.get(0).getIdade(), result.get(0).idade());
        assertEquals(availablePets.get(1).getId(), result.get(1).id());
        assertEquals(availablePets.get(1).getTipo(), result.get(1).tipo());
        assertEquals(availablePets.get(1).getNome(), result.get(1).nome());
        assertEquals(availablePets.get(1).getRaca(), result.get(1).raca());
        assertEquals(availablePets.get(1).getIdade(), result.get(1).idade());
    }

    @Test
    void deveCadastrarUmPet() {
        //Arrange
        Abrigo abrigo = new Abrigo();
        abrigo.setId(1l);

        this.cadastroPetDto = new CadastroPetDto(TipoPet.CACHORRO, "Teo", "Mestiça", 6, "preto", 18F, abrigo);

        //Act
        petService.cadastrarPet(cadastroPetDto);

        Pet pet = new Pet(cadastroPetDto, abrigo);

        then(petRepository).should(times(1)).save(petCaptor.capture());

        Pet petSalvo = petCaptor.getValue();

        //Assert
        assertEquals(this.cadastroPetDto.nome(), petSalvo.getNome());
        assertEquals(pet.getId(), petSalvo.getId());
    }
}