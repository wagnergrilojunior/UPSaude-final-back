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

class CentroCustoRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID centroCustoId;

    @BeforeEach
    void criarCentroCustoParaTestes() throws Exception {
        String jsonPayload = """
                {
                  "codigo": "CC001",
                  "nome": "Centro de Custo Teste",
                  "ativo": true
                }
                """;

        MvcResult result = mockMvc.perform(post("/api/v1/financeiro/centros-custo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        centroCustoId = UUID.fromString(idStr);
    }

    @Test
    void cadastroMinimoDeCentroCustoNaoPodeQuebrar() throws Exception {
        String codigo = String.format("CC%03d", System.currentTimeMillis() % 1000);
        String jsonPayload = String.format("""
                {
                  "codigo": "%s",
                  "nome": "Centro Mínimo"
                }
                """, codigo);

        mockMvc.perform(post("/api/v1/financeiro/centros-custo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void cadastroCompletoDeCentroCustoNaoPodeQuebrar() throws Exception {
        String codigo = String.format("CC%03d", System.currentTimeMillis() % 1000);
        String jsonPayload = String.format("""
                {
                  "codigo": "%s",
                  "nome": "Centro Completo",
                  "ativo": true,
                  "ordem": 1
                }
                """, codigo);

        mockMvc.perform(post("/api/v1/financeiro/centros-custo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void listagemDeCentrosCustoNaoPodeQuebrar() throws Exception {
        mockMvc.perform(get("/api/v1/financeiro/centros-custo")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void obterCentroCustoPorIdNaoPodeQuebrar() throws Exception {
        mockMvc.perform(get("/api/v1/financeiro/centros-custo/" + centroCustoId))
                .andExpect(status().isOk());
    }

    @Test
    void atualizacaoDeCentroCustoNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "codigo": "CC001",
                  "nome": "Centro Atualizado",
                  "ativo": false,
                  "ordem": 2
                }
                """;

        mockMvc.perform(put("/api/v1/financeiro/centros-custo/" + centroCustoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }

    @Test
    void exclusaoDeCentroCustoNaoPodeQuebrar() throws Exception {
        mockMvc.perform(delete("/api/v1/financeiro/centros-custo/" + centroCustoId))
                .andExpect(status().isNoContent());
    }
}
