package com.upsaude.regression.agendamento;

import com.upsaude.regression.BaseRegressionTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AgendamentoCadastroErroRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void agendamentoSemPacienteSempreRetorna400() throws Exception {
        OffsetDateTime dataHora = OffsetDateTime.now().plusDays(1);
        String jsonPayload = String.format("""
                {
                  "dataHora": "%s",
                  "status": "AGENDADO"
                }
                """, dataHora);

        mockMvc.perform(post("/v1/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }
}
