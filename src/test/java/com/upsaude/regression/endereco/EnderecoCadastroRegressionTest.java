package com.upsaude.regression.endereco;

import com.upsaude.regression.BaseRegressionTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EnderecoCadastroRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void cadastroMinimoDeEnderecoNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "logradouro": "Rua Teste Mínimo",
                  "numero": "123",
                  "bairro": "Centro",
                  "cep": "12345-678",
                  "semNumero": false
                }
                """;

        mockMvc.perform(post("/v1/enderecos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void cadastroCompletoDeEnderecoNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "tipoLogradouro": "Rua",
                  "logradouro": "Avenida Francisco Moreira Bilac Pinto",
                  "numero": "477",
                  "semNumero": false,
                  "complemento": "Apto 101",
                  "bairro": "Monte Verde",
                  "cep": "37537-230",
                  "pais": "Brasil",
                  "distrito": "Centro",
                  "pontoReferencia": "Próximo ao mercado",
                  "latitude": -22.9068,
                  "longitude": -43.1729,
                  "tipoEndereco": "Residencial",
                  "zona": "Urbana",
                  "codigoIbgeMunicipio": "3106200",
                  "quadra": "Q1",
                  "lote": "L5",
                  "zonaRuralDescricao": "",
                  "andar": "1",
                  "bloco": "A",
                  "estado": "cb06392f-6154-429c-ae7e-2168b337d1a7",
                  "cidade": "f7895bf0-eee9-4e09-92fb-c928903bb269"
                }
                """;

        mockMvc.perform(post("/v1/enderecos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void cadastroComTodosOsCamposNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "tipoLogradouro": "Avenida",
                  "logradouro": "Avenida Paulista",
                  "numero": "1000",
                  "semNumero": false,
                  "complemento": "Sala 10",
                  "bairro": "Bela Vista",
                  "cep": "01310-100",
                  "pais": "Brasil",
                  "distrito": "Bela Vista",
                  "pontoReferencia": "Próximo ao metrô Trianon",
                  "latitude": -23.5614,
                  "longitude": -46.6565,
                  "tipoEndereco": "Comercial",
                  "zona": "Urbana",
                  "codigoIbgeMunicipio": "3550308",
                  "microarea": "001",
                  "ineEquipe": "123456789012345",
                  "quadra": "Q2",
                  "lote": "L10",
                  "zonaRuralDescricao": "",
                  "andar": "5",
                  "bloco": "B",
                  "estado": "cb06392f-6154-429c-ae7e-2168b337d1a7",
                  "cidade": "f7895bf0-eee9-4e09-92fb-c928903bb269"
                }
                """;

        mockMvc.perform(post("/v1/enderecos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void cadastroDeEnderecoSemNumeroNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "logradouro": "Estrada Rural",
                  "semNumero": true,
                  "bairro": "Zona Rural",
                  "cep": "12345-000",
                  "zona": "Rural",
                  "zonaRuralDescricao": "Estrada de terra, próximo ao córrego"
                }
                """;

        mockMvc.perform(post("/v1/enderecos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }
}
