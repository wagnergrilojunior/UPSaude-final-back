package com.upsaude.regression.financeiro;

import com.upsaude.regression.BaseRegressionTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PlanoContasRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID planoContasId;

    @BeforeEach
    void criarPlanoContasParaTestes() throws Exception {
        String jsonPayload = """
                {
                  "nome": "Plano de Contas Teste",
                  "versao": "1.0",
                  "padrao": false,
                  "ativo": true
                }
                """;

        MvcResult result = mockMvc.perform(post("/api/v1/financeiro/planos-contas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        planoContasId = UUID.fromString(idStr);
    }

    @Test
    void cadastroMinimoDePlanoContasNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "nome": "Plano Mínimo",
                  "versao": "1.0"
                }
                """;

        mockMvc.perform(post("/api/v1/financeiro/planos-contas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void cadastroCompletoDePlanoContasNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "nome": "Plano Completo",
                  "versao": "2.0",
                  "padrao": true,
                  "ativo": true
                }
                """;

        mockMvc.perform(post("/api/v1/financeiro/planos-contas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void listagemDePlanosContasNaoPodeQuebrar() throws Exception {
        mockMvc.perform(get("/api/v1/financeiro/planos-contas")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void obterPlanoContasPorIdNaoPodeQuebrar() throws Exception {
        mockMvc.perform(get("/api/v1/financeiro/planos-contas/" + planoContasId))
                .andExpect(status().isOk());
    }

    @Test
    void atualizacaoDePlanoContasNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "nome": "Plano Atualizado",
                  "versao": "1.1",
                  "padrao": false,
                  "ativo": true
                }
                """;

        mockMvc.perform(put("/api/v1/financeiro/planos-contas/" + planoContasId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }

    @Test
    void exclusaoDePlanoContasNaoPodeQuebrar() throws Exception {
        mockMvc.perform(delete("/api/v1/financeiro/planos-contas/" + planoContasId))
                .andExpect(status().isNoContent());
    }
}
