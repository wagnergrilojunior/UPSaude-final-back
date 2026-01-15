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

class ContaFinanceiraRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID contaFinanceiraId;

    @BeforeEach
    void criarContaFinanceiraParaTestes() throws Exception {
        String jsonPayload = """
                {
                  "tipo": "CAIXA",
                  "nome": "Caixa Principal",
                  "moeda": "BRL",
                  "ativo": true
                }
                """;

        MvcResult result = mockMvc.perform(post("/api/v1/financeiro/contas-financeiras")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        contaFinanceiraId = UUID.fromString(idStr);
    }

    @Test
    void cadastroMinimoDeContaFinanceiraNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "tipo": "CAIXA",
                  "nome": "Caixa Mínimo"
                }
                """;

        mockMvc.perform(post("/api/v1/financeiro/contas-financeiras")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void cadastroCompletoDeContaFinanceiraNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "tipo": "BANCO",
                  "nome": "Banco Completo",
                  "moeda": "BRL",
                  "bancoCodigo": "001",
                  "agencia": "1234",
                  "numeroConta": "567890",
                  "pixChave": "teste@teste.com",
                  "ativo": true
                }
                """;

        mockMvc.perform(post("/api/v1/financeiro/contas-financeiras")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void listagemDeContasFinanceirasNaoPodeQuebrar() throws Exception {
        mockMvc.perform(get("/api/v1/financeiro/contas-financeiras")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void obterContaFinanceiraPorIdNaoPodeQuebrar() throws Exception {
        mockMvc.perform(get("/api/v1/financeiro/contas-financeiras/" + contaFinanceiraId))
                .andExpect(status().isOk());
    }

    @Test
    void atualizacaoDeContaFinanceiraNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "tipo": "BANCO",
                  "nome": "Conta Atualizada",
                  "moeda": "BRL",
                  "ativo": false
                }
                """;

        mockMvc.perform(put("/api/v1/financeiro/contas-financeiras/" + contaFinanceiraId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }

    @Test
    void exclusaoDeContaFinanceiraNaoPodeQuebrar() throws Exception {
        mockMvc.perform(delete("/api/v1/financeiro/contas-financeiras/" + contaFinanceiraId))
                .andExpect(status().isNoContent());
    }
}
