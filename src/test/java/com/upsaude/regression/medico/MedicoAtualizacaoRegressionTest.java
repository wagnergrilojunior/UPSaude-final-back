package com.upsaude.regression.medico;

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

class MedicoAtualizacaoRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID medicoId;
    private String cpfOriginal;
    private String crmOriginal;

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

    @BeforeEach
    void criarMedicoParaAtualizar() throws Exception {
        cpfOriginal = gerarCpfUnico();
        crmOriginal = gerarCrmUnico();
        String jsonPayload = String.format("""
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Medico Para Atualizar"
                  },
                  "documentosBasicos": {
                    "cpf": "%s"
                  },
                  "registroProfissional": {
                    "crm": "%s"
                  }
                }
                """, cpfOriginal, crmOriginal);

        MvcResult result = mockMvc.perform(post("/v1/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        medicoId = UUID.fromString(idStr);
    }

    @Test
    void atualizacaoMinimaDeMedicoNaoPodeQuebrar() throws Exception {
        String jsonPayload = String.format("""
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Medico Atualizado Minimo"
                  },
                  "documentosBasicos": {
                    "cpf": "%s"
                  },
                  "registroProfissional": {
                    "crm": "%s"
                  }
                }
                """, cpfOriginal, crmOriginal);

        mockMvc.perform(put("/v1/medicos/" + medicoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }

    @Test
    void atualizacaoCompletaDeMedicoNaoPodeQuebrar() throws Exception {
        String jsonPayload = String.format("""
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Medico Atualizado Completo",
                    "nomeSocial": "Medico Completo Atualizado",
                    "dataNascimento": "1985-07-25",
                    "sexo": "FEMININO"
                  },
                  "documentosBasicos": {
                    "cpf": "%s",
                    "rg": "99887766",
                    "orgaoEmissorRg": "SSP",
                    "ufEmissorRg": "RJ"
                  },
                  "registroProfissional": {
                    "crm": "%s",
                    "crmUf": "RJ",
                    "statusCrm": "ATIVO",
                    "dataEmissaoCrm": "2015-03-20",
                    "dataValidadeCrm": "2025-03-20"
                  },
                  "contato": {
                    "email": "medico.atualizado.completo@example.com"
                  },
                  "dadosDemograficos": {
                    "escolaridade": "SUPERIOR_INCOMPLETO",
                    "nacionalidade": "BRASILEIRA",
                    "naturalidade": "Rio de Janeiro",
                    "estadoCivil": "DIVORCIADO"
                  },
                  "observacoes": "Medico atualizado com todos os campos"
                }
                """, cpfOriginal, crmOriginal);

        mockMvc.perform(put("/v1/medicos/" + medicoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }

    @Test
    void atualizacaoComTodosOsCamposNaoPodeQuebrar() throws Exception {
        String jsonPayload = String.format("""
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Medico Atualizado Todos Campos",
                    "nomeSocial": "Medico Todos Atualizado",
                    "dataNascimento": "1982-11-30",
                    "sexo": "MASCULINO"
                  },
                  "documentosBasicos": {
                    "cpf": "%s",
                    "rg": "55667788",
                    "orgaoEmissorRg": "SSP",
                    "ufEmissorRg": "MG"
                  },
                  "registroProfissional": {
                    "crm": "%s",
                    "crmUf": "MG",
                    "statusCrm": "ATIVO",
                    "dataEmissaoCrm": "2008-09-15",
                    "dataValidadeCrm": "2028-09-15",
                    "crmComplementar": "CRM ATUALIZADO",
                    "observacoesCrm": "Observações do CRM atualizado"
                  },
                  "contato": {
                    "telefone": "1133334444",
                    "celular": "11987654321",
                    "email": "medico.todos.campos.atualizado@example.com",
                    "site": "https://www.medicoatualizado.com.br"
                  },
                  "dadosDemograficos": {
                    "escolaridade": "POS_GRADUACAO",
                    "nacionalidade": "BRASILEIRA",
                    "naturalidade": "Belo Horizonte",
                    "estadoCivil": "VIUVO"
                  },
                  "enderecoMedico": {
                    "tipoLogradouro": "AVENIDA",
                    "logradouro": "Avenida dos Médicos",
                    "numero": "1000",
                    "complemento": "Sala 205",
                    "bairro": "Jardim Paulista",
                    "cep": "01310100",
                    "pais": "Brasil",
                    "distrito": "Jardim Paulista",
                    "pontoReferencia": "Próximo à clínica",
                    "latitude": -23.5505,
                    "longitude": -46.6333,
                    "tipoEndereco": "COMERCIAL",
                    "zona": "URBANA",
                    "codigoIbgeMunicipio": "3550308",
                    "microarea": "002",
                    "ineEquipe": "987654321012345",
                    "quadra": "003",
                    "lote": "004",
                    "zonaRuralDescricao": "",
                    "andar": "2",
                    "bloco": "B",
                    "semNumero": false
                  },
                  "observacoes": "Medico atualizado com todos os campos possíveis"
                }
                """, cpfOriginal, crmOriginal);

        mockMvc.perform(put("/v1/medicos/" + medicoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }
}
