package com.upsaude.regression.financeiro;

import com.upsaude.regression.BaseRegressionTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CompetenciaFinanceiraListagemRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private String gerarCodigoUnico() {
        return String.format("%06d", System.currentTimeMillis() % 1000000L);
    }

    @BeforeEach
    void criarCompetenciasParaListar() throws Exception {
        for (int i = 0; i < 3; i++) {
            String codigo = gerarCodigoUnico();
            LocalDate hoje = LocalDate.now();
            String jsonPayload = String.format("""
                    {
                      "codigo": "%s",
                      "tipo": "MENSAL",
                      "dataInicio": "%s",
                      "dataFim": "%s"
                    }
                    """, codigo, hoje.minusMonths(i), hoje.minusMonths(i).plusMonths(1).minusDays(1));

            mockMvc.perform(post("/api/v1/financeiro/competencias")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonPayload))
                    .andExpect(status().isCreated());
        }
    }

    @Test
    void listagemDeCompetenciasFinanceirasNaoPodeQuebrar() throws Exception {
        mockMvc.perform(get("/api/v1/financeiro/competencias")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void listagemComPaginacaoNaoPodeQuebrar() throws Exception {
        mockMvc.perform(get("/api/v1/financeiro/competencias")
                        .param("page", "0")
                        .param("size", "5")
                        .param("sort", "dataInicio,desc"))
                .andExpect(status().isOk());
    }
}
