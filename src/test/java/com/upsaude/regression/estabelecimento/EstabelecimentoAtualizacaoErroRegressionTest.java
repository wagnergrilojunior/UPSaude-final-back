package com.upsaude.regression.estabelecimento;

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

class EstabelecimentoAtualizacaoErroRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID estabelecimentoId;

    @BeforeEach
    void criarEstabelecimentoParaAtualizar() throws Exception {
        String jsonPayload = """
                {
                  "dadosIdentificacao": {
                    "nome": "Estabelecimento Para Erro",
                    "tipo": "CLINICA"
                  },
                  "prestadorServico": false
                }
                """;

        MvcResult result = mockMvc.perform(post("/v1/estabelecimentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        estabelecimentoId = UUID.fromString(idStr);
    }

    @Test
    void estabelecimentoAtualizadoSemNomeSempreRetorna400() throws Exception {
        String jsonPayload = """
                {
                  "dadosIdentificacao": {
                    "tipo": "CLINICA"
                  }
                }
                """;

        mockMvc.perform(put("/v1/estabelecimentos/" + estabelecimentoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }
}
