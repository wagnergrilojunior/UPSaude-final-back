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

class ContaContabilRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID contaContabilId;
    private UUID planoContasId;

    private UUID criarPlanoContas() throws Exception {
        String nome = "Plano para Conta Contábil " + System.currentTimeMillis();
        String versao = "1.0." + (System.currentTimeMillis() % 100000);
        String jsonPayload = String.format("""
                {
                  "nome": "%s",
                  "versao": "%s"
                }
                """, nome, versao);

        MvcResult result = mockMvc.perform(post("/api/v1/financeiro/planos-contas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        return UUID.fromString(idStr);
    }

    @BeforeEach
    void criarContaContabilParaTestes() throws Exception {
        planoContasId = criarPlanoContas();

        String codigo = String.format("CC%06d", System.currentTimeMillis() % 1000000L);
        String jsonPayload = String.format("""
                {
                  "planoContas": "%s",
                  "codigo": "%s",
                  "nome": "Conta Contábil Teste",
                  "natureza": "RECEITA",
                  "aceitaLancamento": true
                }
                """, planoContasId, codigo);

        MvcResult result = mockMvc.perform(post("/api/v1/financeiro/contas-contabeis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        contaContabilId = UUID.fromString(idStr);
    }

    @Test
    void cadastroMinimoDeContaContabilNaoPodeQuebrar() throws Exception {
        UUID planoId = criarPlanoContas();
        String codigo = String.format("CC%06d", System.currentTimeMillis() % 1000000L);
        String jsonPayload = String.format("""
                {
                  "planoContas": "%s",
                  "codigo": "%s",
                  "nome": "Conta Mínima",
                  "natureza": "DESPESA"
                }
                """, planoId, codigo);

        mockMvc.perform(post("/api/v1/financeiro/contas-contabeis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void cadastroCompletoDeContaContabilNaoPodeQuebrar() throws Exception {
        UUID planoId = criarPlanoContas();
        String codigo = String.format("CC%06d", System.currentTimeMillis() % 1000000L);
        String jsonPayload = String.format("""
                {
                  "planoContas": "%s",
                  "codigo": "%s",
                  "nome": "Conta Completa",
                  "natureza": "ATIVO",
                  "aceitaLancamento": true,
                  "nivel": 1,
                  "ordem": 1
                }
                """, planoId, codigo);

        mockMvc.perform(post("/api/v1/financeiro/contas-contabeis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void listagemDeContasContabeisNaoPodeQuebrar() throws Exception {
        mockMvc.perform(get("/api/v1/financeiro/contas-contabeis")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void obterContaContabilPorIdNaoPodeQuebrar() throws Exception {
        mockMvc.perform(get("/api/v1/financeiro/contas-contabeis/" + contaContabilId))
                .andExpect(status().isOk());
    }

    @Test
    void atualizacaoDeContaContabilNaoPodeQuebrar() throws Exception {
        String codigoAtualizado = String.format("CC%06d", System.currentTimeMillis() % 1000000L);
        String jsonPayload = String.format("""
                {
                  "planoContas": "%s",
                  "codigo": "%s",
                  "nome": "Conta Atualizada",
                  "natureza": "PASSIVO",
                  "aceitaLancamento": false
                }
                """, planoContasId, codigoAtualizado);

        mockMvc.perform(put("/api/v1/financeiro/contas-contabeis/" + contaContabilId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }

    @Test
    void exclusaoDeContaContabilNaoPodeQuebrar() throws Exception {
        mockMvc.perform(delete("/api/v1/financeiro/contas-contabeis/" + contaContabilId))
                .andExpect(status().isNoContent());
    }
}
