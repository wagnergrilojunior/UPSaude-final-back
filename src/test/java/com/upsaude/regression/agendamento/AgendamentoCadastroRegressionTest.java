package com.upsaude.regression.agendamento;

import com.upsaude.regression.BaseRegressionTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AgendamentoCadastroRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

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
                    "nomeCompleto": "Paciente Para Agendamento",
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

    @Test
    void cadastroMinimoDeAgendamentoNaoPodeQuebrar() throws Exception {
        UUID pacienteId = criarPaciente();
        OffsetDateTime dataHora = OffsetDateTime.now().plusDays(1);
        String jsonPayload = String.format("""
                {
                  "paciente": "%s",
                  "dataHora": "%s",
                  "status": "AGENDADO"
                }
                """, pacienteId, dataHora);

        mockMvc.perform(post("/v1/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void cadastroCompletoDeAgendamentoNaoPodeQuebrar() throws Exception {
        UUID pacienteId = criarPaciente();
        OffsetDateTime dataHora = OffsetDateTime.now().plusDays(1);
        OffsetDateTime dataHoraFim = dataHora.plusHours(1);
        String jsonPayload = String.format("""
                {
                  "paciente": "%s",
                  "dataHora": "%s",
                  "dataHoraFim": "%s",
                  "duracaoPrevistaMinutos": 60,
                  "status": "AGENDADO",
                  "prioridade": "ALTA",
                  "ehEncaixe": false,
                  "ehRetorno": false,
                  "motivoConsulta": "Consulta de rotina",
                  "observacoesAgendamento": "Observações do agendamento"
                }
                """, pacienteId, dataHora, dataHoraFim);

        mockMvc.perform(post("/v1/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void cadastroComTodosOsCamposNaoPodeQuebrar() throws Exception {
        UUID pacienteId = criarPaciente();
        OffsetDateTime dataHora = OffsetDateTime.now().plusDays(1);
        OffsetDateTime dataHoraFim = dataHora.plusHours(1);
        String jsonPayload = String.format("""
                {
                  "paciente": "%s",
                  "dataHora": "%s",
                  "dataHoraFim": "%s",
                  "duracaoPrevistaMinutos": 60,
                  "status": "AGENDADO",
                  "prioridade": "ALTA",
                  "ehEncaixe": false,
                  "ehRetorno": false,
                  "motivoConsulta": "Consulta completa de teste",
                  "observacoesAgendamento": "Observações do agendamento completo",
                  "observacoesInternas": "Observações internas",
                  "temConflitoHorario": false,
                  "sobreposicaoPermitida": false,
                  "justificativaConflito": "",
                  "notificacaoEnviada24h": false,
                  "notificacaoEnviada1h": false,
                  "confirmacaoEnviada": false
                }
                """, pacienteId, dataHora, dataHoraFim);

        mockMvc.perform(post("/v1/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }
}
