package com.upsaude.regression.financeiro;

import com.upsaude.regression.BaseRegressionTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CompetenciaFinanceiraCadastroRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private String gerarCodigoUnico() {
        return String.format("%06d", System.currentTimeMillis() % 1000000L);
    }

    @Test
    void cadastroMinimoDeCompetenciaFinanceiraNaoPodeQuebrar() throws Exception {
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

        mockMvc.perform(post("/api/v1/financeiro/competencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void cadastroCompletoDeCompetenciaFinanceiraNaoPodeQuebrar() throws Exception {
        String codigo = gerarCodigoUnico();
        LocalDate hoje = LocalDate.now();
        String jsonPayload = String.format("""
                {
                  "codigo": "%s",
                  "tipo": "MENSAL",
                  "dataInicio": "%s",
                  "dataFim": "%s",
                  "descricao": "Competência financeira de teste completo"
                }
                """, codigo, hoje, hoje.plusMonths(1).minusDays(1));

        mockMvc.perform(post("/api/v1/financeiro/competencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void cadastroCompetenciaCustomNaoPodeQuebrar() throws Exception {
        String codigo = gerarCodigoUnico();
        LocalDate hoje = LocalDate.now();
        String jsonPayload = String.format("""
                {
                  "codigo": "%s",
                  "tipo": "CUSTOM",
                  "dataInicio": "%s",
                  "dataFim": "%s",
                  "descricao": "Competência customizada de teste"
                }
                """, codigo, hoje, hoje.plusDays(15));

        mockMvc.perform(post("/api/v1/financeiro/competencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }
}
