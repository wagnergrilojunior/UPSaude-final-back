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

class TituloPagarRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID tituloId;
    private UUID planoContasId;
    private UUID contaContabilId;
    private UUID centroCustoId;
    private UUID fornecedorId;

    private UUID criarPlanoContas() throws Exception {
        String nome = "Plano para Titulo Pagar " + System.currentTimeMillis();
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

    private UUID criarContaContabil(UUID planoId) throws Exception {
        String codigo = String.format("CC%03d", System.currentTimeMillis() % 1000);
        String jsonPayload = String.format("""
                {
                  "planoContas": "%s",
                  "codigo": "%s",
                  "nome": "Conta Despesa",
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

    private UUID criarCentroCusto() throws Exception {
        String codigo = String.format("CCUSTO%06d", System.currentTimeMillis() % 1000000L);
        String jsonPayload = String.format("""
                {
                  "codigo": "%s",
                  "nome": "Centro de Custo Teste"
                }
                """, codigo);

        MvcResult result = mockMvc.perform(post("/api/v1/financeiro/centros-custo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        return UUID.fromString(idStr);
    }

    private UUID criarFornecedor() throws Exception {
        String jsonPayload = """
                {
                  "tipo": "FORNECEDOR",
                  "nome": "Fornecedor Teste"
                }
                """;

        MvcResult result = mockMvc.perform(post("/api/v1/financeiro/partes-financeiras")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        return UUID.fromString(idStr);
    }

    @BeforeEach
    void criarTituloParaTestes() throws Exception {
        planoContasId = criarPlanoContas();
        contaContabilId = criarContaContabil(planoContasId);
        centroCustoId = criarCentroCusto();
        fornecedorId = criarFornecedor();

        String numero = String.format("TP%06d", System.currentTimeMillis() % 1000000L);
        LocalDate hoje = LocalDate.now();
        String jsonPayload = String.format("""
                {
                  "fornecedor": "%s",
                  "contaContabilDespesa": "%s",
                  "centroCusto": "%s",
                  "numeroDocumento": "%s",
                  "valorOriginal": 1000.00,
                  "valorAberto": 1000.00,
                  "dataEmissao": "%s",
                  "dataVencimento": "%s",
                  "status": "ABERTO"
                }
                """, fornecedorId, contaContabilId, centroCustoId, numero, hoje, hoje.plusDays(30));

        MvcResult result = mockMvc.perform(post("/api/v1/financeiro/titulos-pagar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        tituloId = UUID.fromString(idStr);
    }

    @Test
    void cadastroMinimoDeTituloPagarNaoPodeQuebrar() throws Exception {
        UUID planoId = criarPlanoContas();
        UUID contaId = criarContaContabil(planoId);
        UUID centroCusto = criarCentroCusto();
        UUID fornecedor = criarFornecedor();
        String numero = String.format("TP%06d", System.currentTimeMillis() % 1000000L);
        LocalDate hoje = LocalDate.now();

        String jsonPayload = String.format("""
                {
                  "fornecedor": "%s",
                  "contaContabilDespesa": "%s",
                  "centroCusto": "%s",
                  "numeroDocumento": "%s",
                  "valorOriginal": 500.00,
                  "valorAberto": 500.00,
                  "dataEmissao": "%s",
                  "dataVencimento": "%s",
                  "status": "ABERTO"
                }
                """, fornecedor, contaId, centroCusto, numero, hoje, hoje.plusDays(30));

        mockMvc.perform(post("/api/v1/financeiro/titulos-pagar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void listagemDeTitulosPagarNaoPodeQuebrar() throws Exception {
        mockMvc.perform(get("/api/v1/financeiro/titulos-pagar")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void obterTituloPagarPorIdNaoPodeQuebrar() throws Exception {
        mockMvc.perform(get("/api/v1/financeiro/titulos-pagar/" + tituloId))
                .andExpect(status().isOk());
    }
}
