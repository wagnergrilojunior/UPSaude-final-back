package com.upsaude.regression.financeiro;

import com.upsaude.regression.BaseRegressionTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CompetenciaFinanceiraCadastroErroRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void cadastroSemCodigoDeveRetornarErro() throws Exception {
        String jsonPayload = """
                {
                  "tipo": "MENSAL",
                  "dataInicio": "2024-01-01",
                  "dataFim": "2024-01-31"
                }
                """;

        mockMvc.perform(post("/api/v1/financeiro/competencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void cadastroSemTipoDeveRetornarErro() throws Exception {
        String jsonPayload = """
                {
                  "codigo": "202401",
                  "dataInicio": "2024-01-01",
                  "dataFim": "2024-01-31"
                }
                """;

        mockMvc.perform(post("/api/v1/financeiro/competencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void cadastroSemDataInicioDeveRetornarErro() throws Exception {
        String jsonPayload = """
                {
                  "codigo": "202401",
                  "tipo": "MENSAL",
                  "dataFim": "2024-01-31"
                }
                """;

        mockMvc.perform(post("/api/v1/financeiro/competencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void cadastroSemDataFimDeveRetornarErro() throws Exception {
        String jsonPayload = """
                {
                  "codigo": "202401",
                  "tipo": "MENSAL",
                  "dataInicio": "2024-01-01"
                }
                """;

        mockMvc.perform(post("/api/v1/financeiro/competencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }
}
