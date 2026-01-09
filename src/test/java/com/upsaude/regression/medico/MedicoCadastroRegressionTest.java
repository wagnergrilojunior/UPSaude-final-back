package com.upsaude.regression.medico;

import com.upsaude.regression.BaseRegressionTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MedicoCadastroRegressionTest extends BaseRegressionTest {

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
    void cadastroMinimoDeMedicoNaoPodeQuebrar() throws Exception {
        String cpf = gerarCpfUnico();
        String crm = gerarCrmUnico();
        String jsonPayload = String.format("""
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Medico Regressao Minimo"
                  },
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
                .andExpect(status().isCreated());
    }

    @Test
    void cadastroCompletoDeMedicoNaoPodeQuebrar() throws Exception {
        String cpf = gerarCpfUnico();
        String crm = gerarCrmUnico();
        String jsonPayload = String.format("""
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Medico Regressao Completo",
                    "nomeSocial": "Medico Completo",
                    "dataNascimento": "1980-05-15",
                    "sexo": "MASCULINO"
                  },
                  "documentosBasicos": {
                    "cpf": "%s",
                    "rg": "98765432",
                    "orgaoEmissorRg": "SSP",
                    "ufEmissorRg": "SP"
                  },
                  "registroProfissional": {
                    "crm": "%s",
                    "crmUf": "SP",
                    "statusCrm": "ATIVO",
                    "dataEmissaoCrm": "2010-01-15",
                    "dataValidadeCrm": "2030-01-15"
                  },
                  "contato": {
                    "email": "medico.completo@example.com"
                  },
                  "dadosDemograficos": {
                    "escolaridade": "SUPERIOR_COMPLETO",
                    "nacionalidade": "BRASILEIRA",
                    "naturalidade": "São Paulo",
                    "estadoCivil": "CASADO"
                  },
                  "observacoes": "Medico de teste de regressão completo"
                }
                """, cpf, crm);

        mockMvc.perform(post("/v1/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void cadastroComTodosOsCamposNaoPodeQuebrar() throws Exception {
        String cpf = gerarCpfUnico();
        String crm = gerarCrmUnico();
        String jsonPayload = String.format("""
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Medico Regressao Todos Campos",
                    "nomeSocial": "Medico Todos",
                    "dataNascimento": "1975-08-20",
                    "sexo": "FEMININO"
                  },
                  "documentosBasicos": {
                    "cpf": "%s",
                    "rg": "11223344",
                    "orgaoEmissorRg": "SSP",
                    "ufEmissorRg": "MG"
                  },
                  "registroProfissional": {
                    "crm": "%s",
                    "crmUf": "MG",
                    "statusCrm": "ATIVO",
                    "dataEmissaoCrm": "2005-06-10",
                    "dataValidadeCrm": "2025-06-10",
                    "crmComplementar": "CRM COMPLEMENTAR",
                    "observacoesCrm": "Observações do CRM"
                  },
                  "contato": {
                    "telefone": "1133334444",
                    "celular": "11987654321",
                    "email": "medico.todos.campos@example.com",
                    "site": "https://www.medico.com.br"
                  },
                  "dadosDemograficos": {
                    "escolaridade": "POS_GRADUACAO",
                    "nacionalidade": "BRASILEIRA",
                    "naturalidade": "Belo Horizonte",
                    "estadoCivil": "SOLTEIRO"
                  },
                  "enderecoMedico": {
                    "tipoLogradouro": "RUA",
                    "logradouro": "Rua dos Médicos",
                    "numero": "500",
                    "complemento": "Sala 101",
                    "bairro": "Centro",
                    "cep": "01310100",
                    "pais": "Brasil",
                    "distrito": "Centro",
                    "pontoReferencia": "Próximo ao hospital",
                    "latitude": -23.5505,
                    "longitude": -46.6333,
                    "tipoEndereco": "COMERCIAL",
                    "zona": "URBANA",
                    "codigoIbgeMunicipio": "3550308",
                    "microarea": "001",
                    "ineEquipe": "123456789012345",
                    "quadra": "001",
                    "lote": "002",
                    "zonaRuralDescricao": "",
                    "andar": "5",
                    "bloco": "A",
                    "semNumero": false
                  },
                  "observacoes": "Medico de teste de regressão com todos os campos possíveis"
                }
                """, cpf, crm);

        mockMvc.perform(post("/v1/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }
}
