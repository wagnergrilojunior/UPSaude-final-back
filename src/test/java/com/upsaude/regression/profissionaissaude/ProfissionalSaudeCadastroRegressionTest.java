package com.upsaude.regression.profissionaissaude;

import com.upsaude.regression.BaseRegressionTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfissionalSaudeCadastroRegressionTest extends BaseRegressionTest {

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

    private String gerarRegistroProfissionalUnico() {
        return String.format("REG%06d", System.currentTimeMillis() % 1000000L);
    }

    private String gerarRgUnico() {
        return String.format("%08d", System.currentTimeMillis() % 100000000L);
    }

    @Test
    void cadastroMinimoDeProfissionalSaudeNaoPodeQuebrar() throws Exception {
        String cpf = gerarCpfUnico();
        String registro = gerarRegistroProfissionalUnico();
        String jsonPayload = String.format("""
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Profissional Saude Regressao Minimo"
                  },
                  "documentosBasicos": {
                    "cpf": "%s"
                  },
                  "registroProfissional": {
                    "registroProfissional": "%s"
                  }
                }
                """, cpf, registro);

        mockMvc.perform(post("/v1/profissionais-saude")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void cadastroCompletoDeProfissionalSaudeNaoPodeQuebrar() throws Exception {
        String cpf = gerarCpfUnico();
        String rg = gerarRgUnico();
        String registro = gerarRegistroProfissionalUnico();
        String jsonPayload = String.format("""
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Profissional Saude Regressao Completo",
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
                  "observacoes": "Profissional de saúde de teste de regressão completo"
                }
                """, cpf, rg, registro);

        mockMvc.perform(post("/v1/profissionais-saude")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void cadastroComTodosOsCamposNaoPodeQuebrar() throws Exception {
        String cpf = gerarCpfUnico();
        String rg = gerarRgUnico();
        String registro = gerarRegistroProfissionalUnico();
        String jsonPayload = String.format("""
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Profissional Saude Regressao Todos Campos",
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
                  "observacoes": "Profissional de saúde de teste de regressão com todos os campos possíveis"
                }
                """, cpf, rg, registro);

        mockMvc.perform(post("/v1/profissionais-saude")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }
}
