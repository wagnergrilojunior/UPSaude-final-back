package com.upsaude.regression.profissionaissaude;

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

class ProfissionalSaudeAtualizacaoErroRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID profissionalId;
    private String cpfOriginal;
    private String registroOriginal;

    private String gerarCpfUnico() {
        long timestamp = System.currentTimeMillis();
        String base = String.format("%09d", timestamp % 1000000000L);
        int soma1 = 0, soma2 = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(base.charAt(i));
            soma1 += digito * (10 - i);
            soma2 += digito * (11 - i);
        }
        int dv1 = (soma1 % 11 < 2) ? 0 : (11 - (soma1 % 11));
        soma2 += dv1 * 2;
        int dv2 = (soma2 % 11 < 2) ? 0 : (11 - (soma2 % 11));
        return base + dv1 + dv2;
    }

    private String gerarRegistroProfissionalUnico() {
        return String.format("REG%06d", System.currentTimeMillis() % 1000000L);
    }

    @BeforeEach
    void criarProfissionalParaAtualizar() throws Exception {
        cpfOriginal = gerarCpfUnico();
        registroOriginal = gerarRegistroProfissionalUnico();
        String jsonPayload = String.format("""
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Profissional Para Atualizar"
                  },
                  "documentosBasicos": {
                    "cpf": "%s"
                  },
                  "registroProfissional": {
                    "registroProfissional": "%s"
                  }
                }
                """, cpfOriginal, registroOriginal);

        MvcResult result = mockMvc.perform(post("/v1/profissionais-saude")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        profissionalId = UUID.fromString(idStr);
    }

    @Test
    void profissionalSaudeAtualizadoSemNomeSempreRetorna400() throws Exception {
        String jsonPayload = String.format("""
                {
                  "dadosPessoaisBasicos": {
                    "sexo": "MASCULINO"
                  },
                  "documentosBasicos": {
                    "cpf": "%s"
                  },
                  "registroProfissional": {
                    "registroProfissional": "%s"
                  }
                }
                """, cpfOriginal, registroOriginal);

        mockMvc.perform(put("/v1/profissionais-saude/" + profissionalId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }
}
