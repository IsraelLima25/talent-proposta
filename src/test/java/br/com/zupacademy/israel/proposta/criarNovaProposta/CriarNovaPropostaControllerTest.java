package br.com.zupacademy.israel.proposta.criarNovaProposta;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CriarNovaPropostaControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Mock
    PropostaRepository propostaRepository;
    @Autowired
    ObjectMapper jsonMapper;
    NovaPropostaRequest propostaRequest;
    Proposta proposta;

    @BeforeEach
    void init() {
        propostaRequest = new NovaPropostaRequest("11652008055",
                "colaborador@gmail.com", "ColaboradorA", "Alameda A", new BigDecimal(3.000));
        proposta = new Proposta("15000115", "usuario2@gmail.com",
                "Usuario", "Alameda c", new BigDecimal(3.500));
    }

    @Test
    @DisplayName("um documento não pode possuir mais de uma proposta associada")
    void propostaNaoDeveSerRepetidaParaMesmoDocumentoTest() throws Exception {
        Mockito.when(propostaRepository.findAll()).thenReturn(List.of(propostaRequest.toModel(new BCryptPasswordEncoder())));
        Mockito.when(propostaRepository.save(proposta)).thenReturn(proposta);
        mockMvc.perform(post("/propostas")
                .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_proposta:write")))
                .content(json(propostaRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    String json(Object request) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(request);
    }
}
