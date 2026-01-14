package com.upsaude.regression.endereco;

import com.upsaude.regression.BaseRegressionTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EnderecoAtualizacaoErroRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID enderecoId;

    @BeforeEach
    void criarEnderecoParaAtualizar() throws Exception {
        String jsonPayload = """
                {
                  "logradouro": "Rua Para Erro",
                  "numero": "100",
                  "semNumero": false,
                  "bairro": "Centro",
                  "cep": "12345-678"
                }
                """;

        MvcResult result = mockMvc.perform(post("/v1/enderecos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        enderecoId = UUID.fromString(idStr);
    }

    @Test
    void enderecoAtualizadoComLogradouroMuitoLongoSempreRetorna400() throws Exception {
        String logradouroLongo = "A".repeat(201); // Excede o limite de 200 caracteres
        String jsonPayload = String.format("""
                {
                  "logradouro": "%s",
                  "numero": "200",
                  "semNumero": false,
                  "bairro": "Novo Bairro",
                  "cep": "54321-876"
                }
                """, logradouroLongo);

        mockMvc.perform(put("/v1/enderecos/" + enderecoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void enderecoAtualizadoComCepInvalidoSempreRetorna400() throws Exception {
        String jsonPayload = """
                {
                  "logradouro": "Rua Atualizada",
                  "numero": "200",
                  "semNumero": false,
                  "bairro": "Novo Bairro",
                  "cep": "12345-67"
                }
                """;

        mockMvc.perform(put("/v1/enderecos/" + enderecoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void enderecoAtualizadoComLogradouroInvalidoSempreRetorna400() throws Exception {
        String jsonPayload = """
                {
                  "logradouro": "Rua Atualizada @#$%",
                  "numero": "200",
                  "semNumero": false,
                  "bairro": "Novo Bairro",
                  "cep": "54321-876"
                }
                """;

        mockMvc.perform(put("/v1/enderecos/" + enderecoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void enderecoAtualizadoComBairroInvalidoSempreRetorna400() throws Exception {
        String jsonPayload = """
                {
                  "logradouro": "Rua Atualizada",
                  "numero": "200",
                  "semNumero": false,
                  "bairro": "Novo Bairro @#$%",
                  "cep": "54321-876"
                }
                """;

        mockMvc.perform(put("/v1/enderecos/" + enderecoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void enderecoAtualizadoComCodigoIbgeInvalidoSempreRetorna400() throws Exception {
        String jsonPayload = """
                {
                  "logradouro": "Rua Atualizada",
                  "numero": "200",
                  "semNumero": false,
                  "bairro": "Novo Bairro",
                  "cep": "54321-876",
                  "codigoIbgeMunicipio": "12345"
                }
                """;

        mockMvc.perform(put("/v1/enderecos/" + enderecoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void enderecoAtualizadoComIdInexistenteSempreRetorna404() throws Exception {
        UUID idInexistente = UUID.randomUUID();
        String jsonPayload = """
                {
                  "logradouro": "Rua Atualizada",
                  "numero": "200",
                  "semNumero": false,
                  "bairro": "Novo Bairro",
                  "cep": "54321-876"
                }
                """;

        mockMvc.perform(put("/v1/enderecos/" + idInexistente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isNotFound());
    }
}
