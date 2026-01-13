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

class ProfissionalSaudeAtualizacaoRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID profissionalId;
    private String cpfOriginal;
    private String registroOriginal;

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

    private String gerarRegistroProfissionalUnico() {
        return String.format("REG%06d", System.currentTimeMillis() % 1000000L);
    }

    private String gerarRgUnico() {
        return String.format("%08d", System.currentTimeMillis() % 100000000L);
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
    void atualizacaoMinimaDeProfissionalSaudeNaoPodeQuebrar() throws Exception {
        String jsonPayload = String.format("""
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Profissional Atualizado Minimo"
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
                .andExpect(status().isOk());
    }

    @Test
    void atualizacaoCompletaDeProfissionalSaudeNaoPodeQuebrar() throws Exception {
        String rg = gerarRgUnico();
        String jsonPayload = String.format("""
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Profissional Atualizado Completo",
                    "dataNascimento": "1980-05-15",
                    "sexo": "MASCULINO"
                  },
                  "documentosBasicos": {
                    "cpf": "%s",
                    "rg": "%s",
                    "orgaoEmissorRg": "SSP",
                    "ufEmissaoRg": "SP"
                  },
                  "registroProfissional": {
                    "registroProfissional": "%s",
                    "ufRegistro": "SP",
                    "statusRegistro": "ATIVO"
                  },
                  "contato": {
                    "email": "profissional.completo@example.com"
                  },
                  "dadosDemograficos": {
                    "escolaridade": "SUPERIOR_COMPLETO",
                    "nacionalidade": "BRASILEIRA",
                    "naturalidade": "São Paulo",
                    "estadoCivil": "CASADO"
                  },
                  "observacoes": "Profissional de saúde de teste de regressão completo atualizado"
                }
                """, cpfOriginal, rg, registroOriginal);

        mockMvc.perform(put("/v1/profissionais-saude/" + profissionalId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }

    @Test
    void atualizacaoComTodosOsCamposNaoPodeQuebrar() throws Exception {
        String rg = gerarRgUnico();
        String jsonPayload = String.format("""
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Profissional Atualizado Todos Campos",
                    "dataNascimento": "1975-08-20",
                    "sexo": "FEMININO"
                  },
                  "documentosBasicos": {
                    "cpf": "%s",
                    "rg": "%s",
                    "orgaoEmissorRg": "SSP",
                    "ufEmissaoRg": "MG",
                    "cns": "701009864978597"
                  },
                  "registroProfissional": {
                    "registroProfissional": "%s",
                    "ufRegistro": "MG",
                    "statusRegistro": "ATIVO"
                  },
                  "contato": {
                    "telefone": "1133334444",
                    "celular": "11987654321",
                    "email": "profissional.todos.campos@example.com",
                    "telefoneInstitucional": "1133335555",
                    "emailInstitucional": "profissional.institucional@example.com"
                  },
                  "dadosDemograficos": {
                    "escolaridade": "POS_GRADUACAO",
                    "nacionalidade": "BRASILEIRA",
                    "naturalidade": "Belo Horizonte",
                    "estadoCivil": "SOLTEIRO",
                    "identidadeGenero": "HOMEM",
                    "racaCor": "PARDA"
                  },
                  "dadosDeficiencia": {
                    "temDeficiencia": false
                  },
                  "enderecoProfissional": {
                    "tipoLogradouro": "RUA",
                    "logradouro": "Rua dos Profissionais",
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
                  "observacoes": "Profissional de saúde de teste de regressão com todos os campos possíveis atualizado"
                }
                """, cpfOriginal, rg, registroOriginal);

        mockMvc.perform(put("/v1/profissionais-saude/" + profissionalId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }
}
