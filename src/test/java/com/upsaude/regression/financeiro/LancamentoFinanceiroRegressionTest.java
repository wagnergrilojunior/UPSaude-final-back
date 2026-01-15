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

class LancamentoFinanceiroRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID lancamentoId;
    private UUID planoContasId;
    private UUID contaContabilId;

    private UUID criarPlanoContas() throws Exception {
        String jsonPayload = """
                {
                  "nome": "Plano para Lancamento",
                  "versao": "1.0"
                }
                """;

        MvcResult result = mockMvc.perform(post("/api/v1/financeiro/planos-contas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        return UUID.fromString(idStr);
    }

    private UUID criarContaContabil(UUID planoId) throws Exception {
        String codigo = String.format("CC%03d", System.currentTimeMillis() % 1000);
        String jsonPayload = String.format("""
                {
                  "planoContas": "%s",
                  "codigo": "%s",
                  "nome": "Conta Lancamento",
                  "natureza": "DESPESA",
                  "aceitaLancamento": true
                }
                """, planoId, codigo);

        MvcResult result = mockMvc.perform(post("/api/v1/financeiro/contas-contabeis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        return UUID.fromString(idStr);
    }

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
    void criarLancamentoParaTestes() throws Exception {
        UUID competenciaId = criarCompetencia();

        String jsonPayload = String.format("""
                {
                  "competencia": "%s",
                  "origemTipo": "MANUAL",
                  "status": "PENDENTE",
                  "dataEvento": "%s"
                }
                """, competenciaId, OffsetDateTime.now());

        MvcResult result = mockMvc.perform(post("/api/v1/financeiro/lancamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        lancamentoId = UUID.fromString(idStr);
    }

    @Test
    void cadastroMinimoDeLancamentoFinanceiroNaoPodeQuebrar() throws Exception {
        UUID competenciaId = criarCompetencia();

        String jsonPayload = String.format("""
                {
                  "competencia": "%s",
                  "origemTipo": "MANUAL",
                  "status": "PENDENTE",
                  "dataEvento": "%s"
                }
                """, competenciaId, OffsetDateTime.now());

        mockMvc.perform(post("/api/v1/financeiro/lancamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void listagemDeLancamentosFinanceirosNaoPodeQuebrar() throws Exception {
        mockMvc.perform(get("/api/v1/financeiro/lancamentos")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void obterLancamentoFinanceiroPorIdNaoPodeQuebrar() throws Exception {
        mockMvc.perform(get("/api/v1/financeiro/lancamentos/" + lancamentoId))
                .andExpect(status().isOk());
    }
}
