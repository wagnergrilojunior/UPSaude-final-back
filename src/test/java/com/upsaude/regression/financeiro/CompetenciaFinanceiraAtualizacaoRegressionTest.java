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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CompetenciaFinanceiraAtualizacaoRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID competenciaId;

    private String gerarCodigoUnico() {
        return String.format("%06d", System.currentTimeMillis() % 1000000L);
    }

    @BeforeEach
    void criarCompetenciaParaAtualizar() throws Exception {
        String codigo = gerarCodigoUnico();
        LocalDate hoje = LocalDate.now();
        String jsonPayload = String.format("""
                {
                  "codigo": "%s",
                  "tipo": "MENSAL",
                  "dataInicio": "%s",
                  "dataFim": "%s"
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
    void atualizacaoMinimaDeCompetenciaFinanceiraNaoPodeQuebrar() throws Exception {
        String codigo = gerarCodigoUnico();
        LocalDate hoje = LocalDate.now();
        String jsonPayload = String.format("""
                {
                  "codigo": "%s",
                  "tipo": "MENSAL",
                  "dataInicio": "%s",
                  "dataFim": "%s"
                }
                """, codigo, hoje, hoje.plusMonths(1).minusDays(1));

        mockMvc.perform(put("/api/v1/financeiro/competencias/" + competenciaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }

    @Test
    void atualizacaoCompletaDeCompetenciaFinanceiraNaoPodeQuebrar() throws Exception {
        String codigo = gerarCodigoUnico();
        LocalDate hoje = LocalDate.now();
        String jsonPayload = String.format("""
                {
                  "codigo": "%s",
                  "tipo": "CUSTOM",
                  "dataInicio": "%s",
                  "dataFim": "%s",
                  "descricao": "Competência atualizada com descrição"
                }
                """, codigo, hoje, hoje.plusDays(20));

        mockMvc.perform(put("/api/v1/financeiro/competencias/" + competenciaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }
}
