package com.upsaude.regression.consulta;

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

class ConsultaAtualizacaoRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID consultaId;
    private UUID atendimentoId;
    private UUID medicoId;

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

    private String gerarCrmUnico() {
        return String.format("%06d", System.currentTimeMillis() % 1000000L);
    }

    private UUID criarPaciente() throws Exception {
        String cpf = gerarCpfUnico();
        String jsonPayload = String.format("""
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Paciente Para Atualizar Consulta",
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

    private UUID criarMedico() throws Exception {
        String cpf = gerarCpfUnico();
        String crm = gerarCrmUnico();
        String jsonPayload = String.format("""
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Medico Para Atualizar Consulta",
                    "sexo": "MASCULINO"
                  },
                  "documentosBasicos": {
                    "cpf": "%s"
                  },
                  "registroProfissional": {
                    "crm": "%s"
                  }
                }
                """, cpf, crm);

        MvcResult result = mockMvc.perform(post("/v1/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        return UUID.fromString(idStr);
    }

    private UUID criarProfissionalSaude() throws Exception {
        String cpf = gerarCpfUnico();
        String registro = String.format("REG%06d", System.currentTimeMillis() % 1000000L);
        String jsonPayload = String.format("""
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Profissional Para Atualizar Consulta",
                    "sexo": "MASCULINO"
                  },
                  "documentosBasicos": {
                    "cpf": "%s"
                  },
                  "registroProfissional": {
                    "registroProfissional": "%s"
                  }
                }
                """, cpf, registro);

        MvcResult result = mockMvc.perform(post("/v1/profissionais-saude")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        return UUID.fromString(idStr);
    }

    private UUID criarAtendimento(UUID pacienteId, UUID profissionalId) throws Exception {
        String jsonPayload = String.format("""
                {
                  "pacienteId": "%s",
                  "profissionalId": "%s"
                }
                """, pacienteId, profissionalId);

        MvcResult result = mockMvc.perform(post("/v1/atendimentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        return UUID.fromString(idStr);
    }

    @BeforeEach
    void criarConsultaParaAtualizar() throws Exception {
        UUID pacienteId = criarPaciente();
        UUID profissionalId = criarProfissionalSaude();
        atendimentoId = criarAtendimento(pacienteId, profissionalId);
        medicoId = criarMedico();
        String jsonPayload = String.format("""
                {
                  "atendimentoId": "%s",
                  "medicoId": "%s"
                }
                """, atendimentoId, medicoId);

        MvcResult result = mockMvc.perform(post("/v1/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        consultaId = UUID.fromString(idStr);
    }

    @Test
    void atualizacaoMinimaDeConsultaNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "anamnese": {
                    "queixaPrincipal": "Atualização mínima de anamnese"
                  }
                }
                """;

        mockMvc.perform(put("/v1/consultas/" + consultaId + "/anamnese")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }

    @Test
    void atualizacaoCompletaDeConsultaNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "anamnese": {
                    "queixaPrincipal": "Consulta completa",
                    "historiaDoencaAtual": "História da doença atual",
                    "exameFisico": "Exame físico completo"
                  }
                }
                """;

        mockMvc.perform(put("/v1/consultas/" + consultaId + "/anamnese")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }

    @Test
    void atualizacaoComTodosOsCamposNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "anamnese": {
                    "queixaPrincipal": "Queixa principal completa",
                    "historiaDoencaAtual": "História da doença atual completa",
                    "exameFisico": "Exame físico completo",
                    "antecedentesPessoais": "Antecedentes pessoais",
                    "antecedentesFamiliares": "Antecedentes familiares",
                    "medicamentosUso": "Medicamentos em uso",
                    "alergias": "Alergias conhecidas"
                  }
                }
                """;

        mockMvc.perform(put("/v1/consultas/" + consultaId + "/anamnese")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }
}
