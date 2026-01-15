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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrcamentoCompetenciaRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID orcamentoId;
    private UUID competenciaId;

    private UUID criarCompetencia() throws Exception {
        String codigo = String.format("%06d", System.currentTimeMillis() % 1000000L);
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
        return UUID.fromString(idStr);
    }

    @BeforeEach
    void criarOrcamentoParaTestes() throws Exception {
        competenciaId = criarCompetencia();

        String jsonPayload = String.format("""
                {
                  "competencia": "%s",
                  "saldoAnterior": 1000.00,
                  "creditos": 5000.00,
                  "reservasAtivas": 2000.00,
                  "consumos": 1500.00,
                  "saldoFinal": 2500.00
                }
                """, competenciaId);

        MvcResult result = mockMvc.perform(post("/api/v1/financeiro/orcamentos-competencia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        orcamentoId = UUID.fromString(idStr);
    }

    @Test
    void cadastroMinimoDeOrcamentoCompetenciaNaoPodeQuebrar() throws Exception {
        UUID compId = criarCompetencia();
        String jsonPayload = String.format("""
                {
                  "competencia": "%s"
                }
                """, compId);

        mockMvc.perform(post("/api/v1/financeiro/orcamentos-competencia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void listagemDeOrcamentosCompetenciaNaoPodeQuebrar() throws Exception {
        mockMvc.perform(get("/api/v1/financeiro/orcamentos-competencia")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void obterOrcamentoCompetenciaPorIdNaoPodeQuebrar() throws Exception {
        mockMvc.perform(get("/api/v1/financeiro/orcamentos-competencia/" + orcamentoId))
                .andExpect(status().isOk());
    }

    @Test
    void atualizacaoDeOrcamentoCompetenciaNaoPodeQuebrar() throws Exception {
        String jsonPayload = String.format("""
                {
                  "competencia": "%s",
                  "creditos": 6000.00,
                  "saldoFinal": 3500.00
                }
                """, competenciaId);

        mockMvc.perform(put("/api/v1/financeiro/orcamentos-competencia/" + orcamentoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }

    @Test
    void exclusaoDeOrcamentoCompetenciaNaoPodeQuebrar() throws Exception {
        UUID compId = criarCompetencia();
        String jsonPayload = String.format("""
                {
                  "competencia": "%s"
                }
                """, compId);

        MvcResult result = mockMvc.perform(post("/api/v1/financeiro/orcamentos-competencia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        UUID id = UUID.fromString(idStr);

        mockMvc.perform(delete("/api/v1/financeiro/orcamentos-competencia/" + id))
                .andExpect(status().isNoContent());
    }
}
