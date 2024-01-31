package br.com.mdros.adopet.api.service;

import br.com.mdros.adopet.api.dto.AbrigoDto.AbrigoDto;
import br.com.mdros.adopet.api.dto.AbrigoDto.CadastroAbrigoDto;
import br.com.mdros.adopet.api.dto.PetDto.ListagemPetDto;
import br.com.mdros.adopet.api.model.Abrigo;
import br.com.mdros.adopet.api.model.Adocao;
import br.com.mdros.adopet.api.model.Pet;
import br.com.mdros.adopet.api.model.TipoPet;
import br.com.mdros.adopet.api.repository.AbrigoRepository;
import br.com.mdros.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @InjectMocks
    private AbrigoService abrigoService;

    @Mock
    private AbrigoRepository abrigoRepository;

    @Mock
    private CadastroAbrigoDto cadastroAbrigoDto;
    @Mock
    private PetRepository petRepository;
    @Captor
    private ArgumentCaptor<Abrigo> abrigoCaptor;

    @Mock
    private Abrigo abrigo;

    @Mock
    private AbrigoDto abrigoDto;

    @Mock
    private List<Pet> petsAbrigo = new ArrayList<>();

    @Test
    void deveriaTrazerListaDeAbrigos(){

        //Act
        abrigoService.listar();

        //Assert
        BDDMockito.then(abrigoRepository).should().findAll();
    }

    @Test
    void deveriaCadastrarAbrigo(){
        //Arrange
        cadastroAbrigoDto = new CadastroAbrigoDto("nome1", "7599", "email1@gmail.com");

        abrigoService.cadastrar(cadastroAbrigoDto);

        //Act
        BDDMockito.then(abrigoRepository).should(times(1)).save(abrigoCaptor.capture());

        Abrigo abrigoSalvo = abrigoCaptor.getValue();

        //Assert
        assertEquals(abrigoSalvo.getNome(), abrigoCaptor.getValue().getNome());
        assertEquals(abrigoSalvo.getTelefone(), abrigoCaptor.getValue().getTelefone());
        assertEquals(abrigoSalvo.getTelefone(), abrigoCaptor.getValue().getTelefone());
    }

    @Test
    void deveriaListarPetsPeloNome(){
        //Arrange
        float id = 1l;
        BDDMockito.given(abrigoRepository.findById(id)).willReturn(abrigo);

        //Act
        abrigoService.listarPets(id);

        //Assert
        BDDMockito.then(petRepository).should().findByAbrigo(abrigo);
    }

    @Test
    void deveriaListaPetsDeAbrigos(){
        //Arrange
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

        petsAbrigo.add(pet1);
        petsAbrigo.add(pet2);

        //Act
        BDDMockito.when(abrigoRepository.getReferenceById(1l).getPets()).thenReturn(petsAbrigo);

        ResponseEntity<List<ListagemPetDto>> resultPets = abrigoService.listarPets("1");

        //Assert
        assertEquals(HttpStatus.OK, resultPets.getStatusCode());
        assertEquals(pet1.getId(), resultPets.getBody().get(0).id());
        assertEquals(pet2.getId(), resultPets.getBody().get(1).id());
    }

}