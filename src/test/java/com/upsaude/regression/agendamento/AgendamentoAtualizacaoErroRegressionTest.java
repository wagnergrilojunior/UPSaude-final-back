package com.upsaude.regression.agendamento;

import com.upsaude.regression.BaseRegressionTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AgendamentoAtualizacaoErroRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID agendamentoId;
    private UUID pacienteId;

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

    private UUID criarPaciente() throws Exception {
        String cpf = gerarCpfUnico();
        String jsonPayload = String.format("""
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Paciente Para Atualizar Agendamento Erro",
                    "sexo": "MASCULINO"
                  },
                  "documentosBasicos": {
                    "cpf": "%s"
                  }
                }
                """, cpf);

        MvcResult result = mockMvc.perform(post("/v1/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        return UUID.fromString(idStr);
    }

    @BeforeEach
    void criarAgendamentoParaAtualizar() throws Exception {
        pacienteId = criarPaciente();
        OffsetDateTime dataHora = OffsetDateTime.now().plusDays(1);
        String jsonPayload = String.format("""
                {
                  "paciente": "%s",
                  "dataHora": "%s",
                  "status": "AGENDADO"
                }
                """, pacienteId, dataHora);

        MvcResult result = mockMvc.perform(post("/v1/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        agendamentoId = UUID.fromString(idStr);
    }

    @Test
    void agendamentoAtualizadoSemPacienteSempreRetorna400() throws Exception {
        OffsetDateTime dataHora = OffsetDateTime.now().plusDays(2);
        String jsonPayload = String.format("""
                {
                  "dataHora": "%s",
                  "status": "AGENDADO"
                }
                """, dataHora);

        mockMvc.perform(put("/v1/agendamentos/" + agendamentoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }
}
