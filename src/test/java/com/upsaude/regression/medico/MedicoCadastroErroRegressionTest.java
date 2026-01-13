package com.upsaude.regression.medico;

import com.upsaude.regression.BaseRegressionTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MedicoCadastroErroRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private String gerarCpfUnico() {
        long timestamp = System.currentTimeMillis();
        String base = String.format("%09d", timestamp % 1000000000L);
        // Gerar dígitos verificadores válidos para CPF
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

    private String gerarCrmUnico() {
        return String.format("%06d", System.currentTimeMillis() % 1000000L);
    }

    @Test
    void medicoSemNomeSempreRetorna400() throws Exception {
        String cpf = gerarCpfUnico();
        String crm = gerarCrmUnico();
        String jsonPayload = String.format("""
                {
                  "documentosBasicos": {
                    "cpf": "%s"
                  },
                  "registroProfissional": {
                    "crm": "%s"
                  }
                }
                """, cpf, crm);

        mockMvc.perform(post("/v1/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }
}
