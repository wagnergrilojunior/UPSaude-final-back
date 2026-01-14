package com.upsaude.regression.estabelecimento;

import com.upsaude.regression.BaseRegressionTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EstabelecimentoAtualizacaoRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID estabelecimentoId;

    private String gerarCnesUnico() {
        return String.format("%07d", System.currentTimeMillis() % 10000000L);
    }

    private String gerarCnpjUnico() {
        long timestamp = System.currentTimeMillis();
        String base = String.format("%012d", timestamp % 1000000000000L);
        int[] multiplicadores1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] multiplicadores2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int soma1 = 0, soma2 = 0;
        for (int i = 0; i < 12; i++) {
            int digito = Character.getNumericValue(base.charAt(i));
            soma1 += digito * multiplicadores1[i];
        }
        int dv1 = (soma1 % 11 < 2) ? 0 : (11 - (soma1 % 11));
        for (int i = 0; i < 12; i++) {
            int digito = Character.getNumericValue(base.charAt(i));
            soma2 += digito * multiplicadores2[i];
        }
        soma2 += dv1 * multiplicadores2[12];
        int dv2 = (soma2 % 11 < 2) ? 0 : (11 - (soma2 % 11));
        return base + dv1 + dv2;
    }

    @BeforeEach
    void criarEstabelecimentoParaAtualizar() throws Exception {
        String jsonPayload = """
                {
                  "dadosIdentificacao": {
                    "nome": "Estabelecimento Para Atualizar",
                    "tipo": "CLINICA"
                  },
                  "prestadorServico": false
                }
                """;

        MvcResult result = mockMvc.perform(post("/v1/estabelecimentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        estabelecimentoId = UUID.fromString(idStr);
    }

    @Test
    void atualizacaoMinimaDeEstabelecimentoNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "dadosIdentificacao": {
                    "nome": "Estabelecimento Atualizado Minimo",
                    "tipo": "CLINICA"
                  },
                  "prestadorServico": false
                }
                """;

        mockMvc.perform(put("/v1/estabelecimentos/" + estabelecimentoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }

    @Test
    void atualizacaoCompletaDeEstabelecimentoNaoPodeQuebrar() throws Exception {
        String cnes = gerarCnesUnico();
        String cnpj = gerarCnpjUnico();
        String jsonPayload = String.format("""
                {
                  "dadosIdentificacao": {
                    "nome": "Estabelecimento Atualizado Completo",
                    "tipo": "HOSPITAL",
                    "nomeFantasia": "Hospital Atualizado",
                    "cnes": "%s",
                    "cnpj": "%s"
                  },
                  "observacoes": "Atualização completa",
                  "prestadorServico": false
                }
                """, cnes, cnpj);

        mockMvc.perform(put("/v1/estabelecimentos/" + estabelecimentoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }

    @Test
    void atualizacaoComTodosOsCamposNaoPodeQuebrar() throws Exception {
        String cnes = gerarCnesUnico();
        String cnpj = gerarCnpjUnico();
        String jsonPayload = String.format("""
                {
                  "dadosIdentificacao": {
                    "nome": "Estabelecimento Atualizado Todos Campos",
                    "tipo": "UBS",
                    "nomeFantasia": "UBS Atualizado",
                    "cnes": "%s",
                    "cnpj": "%s",
                    "naturezaJuridica": "ADMINISTRACAO_PUBLICA_DIRETA"
                  },
                  "contato": {
                    "telefone": "11987654321",
                    "email": "atualizado@estabelecimento.com"
                  },
                  "localizacao": {
                    "latitude": -23.5505,
                    "longitude": -46.6333
                  },
                  "observacoes": "Atualização com todos os campos",
                  "prestadorServico": false
                }
                """, cnes, cnpj);

        mockMvc.perform(put("/v1/estabelecimentos/" + estabelecimentoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }
}
