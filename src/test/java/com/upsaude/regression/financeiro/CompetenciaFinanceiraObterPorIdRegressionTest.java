package com.upsaude.regression.financeiro;

import com.upsaude.regression.BaseRegressionTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CompetenciaFinanceiraObterPorIdRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID competenciaId;

    private String gerarCodigoUnico() {
        return String.format("%06d", System.currentTimeMillis() % 1000000L);
    }

    @BeforeEach
    void criarCompetenciaParaObter() throws Exception {
        String codigo = gerarCodigoUnico();
        LocalDate hoje = LocalDate.now();
        String jsonPayload = String.format("""
                {
                  "codigo": "%s",
                  "tipo": "MENSAL",
                  "dataInicio": "%s",
                  "dataFim": "%s",
                  "descricao": "Competência para teste de obter por ID"
                }
                """, codigo, hoje, hoje.plusMonths(1).minusDays(1));

        MvcResult result = mockMvc.perform(post("/api/v1/financeiro/competencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        competenciaId = UUID.fromString(idStr);
    }

    @Test
    void obterCompetenciaFinanceiraPorIdNaoPodeQuebrar() throws Exception {
        mockMvc.perform(get("/api/v1/financeiro/competencias/" + competenciaId))
                .andExpect(status().isOk());
    }

    @Test
    void obterCompetenciaFinanceiraInexistenteDeveRetornarNotFound() throws Exception {
        UUID idInexistente = UUID.randomUUID();
        mockMvc.perform(get("/api/v1/financeiro/competencias/" + idInexistente))
                .andExpect(status().isNotFound());
    }
}
