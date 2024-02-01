package br.com.mdros.adopet.api.controller;

import br.com.mdros.adopet.api.service.PetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.print.attribute.standard.Media;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PetService petService;

    @Test
     void deveriaRetornarCodigo200AoListarPets() throws Exception {
        //Act
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.get("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //Assert

        assertEquals(200, response.getStatus());
    }

}