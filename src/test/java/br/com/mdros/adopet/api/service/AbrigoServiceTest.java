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
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
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

    @Test
    void deveriaTrazerListaDeAbrigos(){

        //Act
        abrigoService.listar();

        //Assert
        then(abrigoRepository).should().findAll();
    }

    @Test
    void deveriaCadastrarAbrigo(){
        //Arrange
        cadastroAbrigoDto = new CadastroAbrigoDto("nome1", "7599", "email1@gmail.com");

        abrigoService.cadastrar(cadastroAbrigoDto);

        //Act
        then(abrigoRepository).should(times(1)).save(abrigoCaptor.capture());

        Abrigo abrigoSalvo = abrigoCaptor.getValue();

        //Assert
        assertEquals(abrigoSalvo.getNome(), abrigoCaptor.getValue().getNome());
        assertEquals(abrigoSalvo.getTelefone(), abrigoCaptor.getValue().getTelefone());
        assertEquals(abrigoSalvo.getTelefone(), abrigoCaptor.getValue().getTelefone());
    }

    @Test
    void deveriaChamarListaDePetsDoAbrigoAtravesDoNome() {
        //Arrange
        String nome = "Miau";
        given(abrigoRepository.findByNome(nome)).willReturn(Optional.of(abrigo));

        //Act
        abrigoService.listarPetsDoAbrigo(nome);

        //Assert
        then(petRepository).should().findByAbrigo(abrigo);
    }
}