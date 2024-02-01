package br.com.mdros.adopet.api.controller;

import br.com.mdros.adopet.api.dto.AbrigoDto.AbrigoDto;
import br.com.mdros.adopet.api.dto.AdocaoDto.SolicitacaoAdocaoDto;
import br.com.mdros.adopet.api.model.Abrigo;
import br.com.mdros.adopet.api.service.AbrigoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AbrigoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<AbrigoDto> jsonDto;

    @MockBean
    private AbrigoService abrigoService;

    @Test
    void deveriaDevolverCodigo200AolistarAbrigos() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.get("/abrigos")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo400AoRetornarErroNocadastro() throws Exception {
        //Arrange
        String json = "{}";

        //Act
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.post("/abrigos")
                        .contentType(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assert
        assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200AoCadastrarAbrigo() throws Exception {
        AbrigoDto dto = new AbrigoDto("nome", "77777777", "motivo");

        //Act
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.post("/abrigos")
                        .contentType(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
    }
}