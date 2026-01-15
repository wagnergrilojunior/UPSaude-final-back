package com.upsaude.regression.financeiro;

import com.upsaude.regression.BaseRegressionTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MovimentacaoContaRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID movimentacaoId;
    private UUID contaFinanceiraId;

    private UUID criarContaFinanceira() throws Exception {
        String sufixo = String.valueOf(System.currentTimeMillis() % 1000000L);
        String jsonPayload = String.format("""
                {
                  "nome": "Conta Teste %s",
                  "tipo": "CORRENTE",
                  "banco": "001",
                  "agencia": "1234",
                  "numero": "%s"
                }
                """, sufixo, sufixo);

        MvcResult result = mockMvc.perform(post("/api/v1/financeiro/contas-financeiras")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        return UUID.fromString(idStr);
    }

    @BeforeEach
    void criarMovimentacaoParaTestes() throws Exception {
        contaFinanceiraId = criarContaFinanceira();

        String jsonPayload = String.format("""
                {
                  "contaFinanceira": "%s",
                  "tipo": "CREDITO",
                  "valor": 1000.00,
                  "dataMovimento": "%s",
                  "status": "CONFIRMADO",
                  "descricao": "Movimentação teste"
                }
                """, contaFinanceiraId, OffsetDateTime.now());

        MvcResult result = mockMvc.perform(post("/api/v1/financeiro/movimentacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        movimentacaoId = UUID.fromString(idStr);
    }

    @Test
    void cadastroMinimoDeMovimentacaoContaNaoPodeQuebrar() throws Exception {
        UUID contaId = criarContaFinanceira();

        String jsonPayload = String.format("""
                {
                  "contaFinanceira": "%s",
                  "tipo": "DEBITO",
                  "valor": 500.00,
                  "dataMovimento": "%s",
                  "status": "CONFIRMADO"
                }
                """, contaId, OffsetDateTime.now());

        mockMvc.perform(post("/api/v1/financeiro/movimentacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void listagemDeMovimentacoesContaNaoPodeQuebrar() throws Exception {
        mockMvc.perform(get("/api/v1/financeiro/movimentacoes")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void obterMovimentacaoContaPorIdNaoPodeQuebrar() throws Exception {
        mockMvc.perform(get("/api/v1/financeiro/movimentacoes/" + movimentacaoId))
                .andExpect(status().isOk());
    }
}
