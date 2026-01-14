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

class EnderecoAtualizacaoRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID enderecoId;

    @BeforeEach
    void criarEnderecoParaAtualizar() throws Exception {
        String jsonPayload = """
                {
                  "logradouro": "Rua Para Atualizar",
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
    void atualizacaoMinimaDeEnderecoNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "logradouro": "Rua Atualizada Mínimo",
                  "numero": "200",
                  "semNumero": false,
                  "bairro": "Novo Bairro",
                  "cep": "54321-876"
                }
                """;

        mockMvc.perform(put("/v1/enderecos/" + enderecoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }

    @Test
    void atualizacaoCompletaDeEnderecoNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "tipoLogradouro": "Avenida",
                  "logradouro": "Avenida Atualizada Completa",
                  "numero": "500",
                  "semNumero": false,
                  "complemento": "Bloco B",
                  "bairro": "Jardim Atualizado",
                  "cep": "98765-432",
                  "pais": "Brasil",
                  "distrito": "Distrito Atualizado",
                  "pontoReferencia": "Próximo à escola",
                  "latitude": -23.5505,
                  "longitude": -46.6333,
                  "tipoEndereco": "Comercial",
                  "zona": "Urbana",
                  "codigoIbgeMunicipio": "3550308",
                  "quadra": "Q3",
                  "lote": "L15",
                  "zonaRuralDescricao": "",
                  "andar": "2",
                  "bloco": "C",
                  "estado": "cb06392f-6154-429c-ae7e-2168b337d1a7",
                  "cidade": "f7895bf0-eee9-4e09-92fb-c928903bb269"
                }
                """;

        mockMvc.perform(put("/v1/enderecos/" + enderecoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }

    @Test
    void atualizacaoComTodosOsCamposNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "tipoLogradouro": "Rua",
                  "logradouro": "Rua Atualizada Todos Campos",
                  "numero": "999",
                  "semNumero": false,
                  "complemento": "Apto 501",
                  "bairro": "Bairro Completo",
                  "cep": "11111-222",
                  "pais": "Brasil",
                  "distrito": "Distrito Completo",
                  "pontoReferencia": "Próximo ao shopping",
                  "latitude": -22.9068,
                  "longitude": -43.1729,
                  "tipoEndereco": "Residencial",
                  "zona": "Periurbana",
                  "codigoIbgeMunicipio": "3106200",
                  "microarea": "002",
                  "ineEquipe": "987654321098765",
                  "quadra": "Q4",
                  "lote": "L20",
                  "zonaRuralDescricao": "Descrição atualizada",
                  "andar": "10",
                  "bloco": "D",
                  "estado": "cb06392f-6154-429c-ae7e-2168b337d1a7",
                  "cidade": "f7895bf0-eee9-4e09-92fb-c928903bb269"
                }
                """;

        mockMvc.perform(put("/v1/enderecos/" + enderecoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }

    @Test
    void atualizacaoParaSemNumeroNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "logradouro": "Estrada Atualizada",
                  "semNumero": true,
                  "bairro": "Zona Rural Atualizada",
                  "cep": "00000-000",
                  "zona": "Rural",
                  "zonaRuralDescricao": "Nova descrição rural"
                }
                """;

        mockMvc.perform(put("/v1/enderecos/" + enderecoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }
}
