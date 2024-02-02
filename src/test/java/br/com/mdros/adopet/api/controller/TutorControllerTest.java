package br.com.mdros.adopet.api.controller;

import br.com.mdros.adopet.api.dto.TutorDto.AtualizarTutorDto;
import br.com.mdros.adopet.api.dto.TutorDto.CadastrarTutorDto;
import br.com.mdros.adopet.api.service.TutorService;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TutorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TutorService tutorService;

    @Autowired
    private JacksonTester<CadastrarTutorDto> jsonDto;

    @Autowired
    private JacksonTester<AtualizarTutorDto> jsonAtualizar;

    @Test
    void deveriaDevolverCodigo200AoCadastrarTutor() throws Exception {
        CadastrarTutorDto dto = new CadastrarTutorDto("Lucas", "75999998888", "email@example.com.br");

        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.post("/tutores")
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());

    }

    @Test
    void deveriaDevolverCodigo400AoCadastrarTutor() throws Exception {
        String json = "{}";

        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.post("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200AoAtualizarTutor() throws Exception {
        AtualizarTutorDto dto = new AtualizarTutorDto(1l, "nome", "12345678", "email@gmail.com");

        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.put("/tutores")
                        .content(jsonAtualizar.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo400AoAtualizarTutor() throws Exception {
        String json = "";

        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.put("/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

}