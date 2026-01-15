package com.upsaude.regression.endereco;

import com.upsaude.regression.BaseRegressionTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EnderecoCadastroErroRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void enderecoComLogradouroMuitoLongoSempreRetorna400() throws Exception {
        String logradouroLongo = "A".repeat(201); // Excede o limite de 200 caracteres
        String jsonPayload = String.format("""
                {
                  "logradouro": "%s",
                  "numero": "123",
                  "semNumero": false,
                  "bairro": "Centro",
                  "cep": "12345-678"
                }
                """, logradouroLongo);

        mockMvc.perform(post("/v1/enderecos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void enderecoComCepInvalidoSempreRetorna400() throws Exception {
        String jsonPayload = """
                {
                  "logradouro": "Rua Teste",
                  "numero": "123",
                  "semNumero": false,
                  "bairro": "Centro",
                  "cep": "12345-67"
                }
                """;

        mockMvc.perform(post("/v1/enderecos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void enderecoComLogradouroInvalidoSempreRetorna400() throws Exception {
        String jsonPayload = """
                {
                  "logradouro": "Rua Teste @#$%",
                  "numero": "123",
                  "semNumero": false,
                  "bairro": "Centro",
                  "cep": "12345-678"
                }
                """;

        mockMvc.perform(post("/v1/enderecos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void enderecoComBairroInvalidoSempreRetorna400() throws Exception {
        String jsonPayload = """
                {
                  "logradouro": "Rua Teste",
                  "numero": "123",
                  "semNumero": false,
                  "bairro": "Centro @#$%",
                  "cep": "12345-678"
                }
                """;

        mockMvc.perform(post("/v1/enderecos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void enderecoComCodigoIbgeInvalidoSempreRetorna400() throws Exception {
        String jsonPayload = """
                {
                  "logradouro": "Rua Teste",
                  "numero": "123",
                  "semNumero": false,
                  "bairro": "Centro",
                  "cep": "12345-678",
                  "codigoIbgeMunicipio": "12345"
                }
                """;

        mockMvc.perform(post("/v1/enderecos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }
}
