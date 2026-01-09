package com.upsaude.regression.estabelecimento;

import com.upsaude.regression.BaseRegressionTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EstabelecimentoCadastroRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

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

    @Test
    void cadastroMinimoDeEstabelecimentoNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "dadosIdentificacao": {
                    "nome": "Estabelecimento Teste Minimo",
                    "tipo": "CLINICA"
                  }
                }
                """;

        mockMvc.perform(post("/v1/estabelecimentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void cadastroCompletoDeEstabelecimentoNaoPodeQuebrar() throws Exception {
        String cnes = gerarCnesUnico();
        String cnpj = gerarCnpjUnico();
        String jsonPayload = String.format("""
                {
                  "dadosIdentificacao": {
                    "nome": "Estabelecimento Teste Completo",
                    "tipo": "HOSPITAL",
                    "nomeFantasia": "Hospital Teste",
                    "cnes": "%s",
                    "cnpj": "%s"
                  },
                  "observacoes": "Estabelecimento de teste completo"
                }
                """, cnes, cnpj);

        mockMvc.perform(post("/v1/estabelecimentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void cadastroComTodosOsCamposNaoPodeQuebrar() throws Exception {
        String cnes = gerarCnesUnico();
        String cnpj = gerarCnpjUnico();
        String jsonPayload = String.format("""
                {
                  "dadosIdentificacao": {
                    "nome": "Estabelecimento Teste Todos Campos",
                    "tipo": "UBS",
                    "nomeFantasia": "UBS Teste Completo",
                    "cnes": "%s",
                    "cnpj": "%s",
                    "naturezaJuridica": "ADMINISTRACAO_PUBLICA_DIRETA"
                  },
                  "contato": {
                    "telefone": "11987654321",
                    "email": "teste@estabelecimento.com"
                  },
                  "localizacao": {
                    "latitude": -23.5505,
                    "longitude": -46.6333
                  },
                  "observacoes": "Estabelecimento de teste com todos os campos"
                }
                """, cnes, cnpj);

        mockMvc.perform(post("/v1/estabelecimentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }
}
