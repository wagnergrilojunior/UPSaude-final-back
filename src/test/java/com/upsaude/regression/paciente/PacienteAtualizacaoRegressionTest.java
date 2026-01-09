package com.upsaude.regression.paciente;

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

class PacienteAtualizacaoRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID pacienteId;

    @BeforeEach
    void criarPacienteParaAtualizar() throws Exception {
        String jsonPayload = """
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Paciente Para Atualizar",
                    "sexo": "MASCULINO"
                  }
                }
                """;

        MvcResult result = mockMvc.perform(post("/v1/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        pacienteId = UUID.fromString(idStr);
    }

    @Test
    void atualizacaoMinimaDePacienteNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Paciente Atualizado Minimo",
                    "sexo": "FEMININO"
                  }
                }
                """;

        mockMvc.perform(put("/v1/pacientes/" + pacienteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }

    @Test
    void atualizacaoCompletaDePacienteNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Paciente Atualizado Completo",
                    "nomeSocial": "Paciente Completo Atualizado",
                    "dataNascimento": "1988-06-20",
                    "sexo": "FEMININO"
                  },
                  "documentosBasicos": {
                    "rg": "98765432",
                    "orgaoEmissorRg": "SSP",
                    "ufEmissorRg": "RJ"
                  },
                  "contato": {
                    "email": "paciente.atualizado.completo@example.com"
                  },
                  "dadosDemograficos": {
                    "escolaridade": "SUPERIOR_INCOMPLETO",
                    "nacionalidade": "BRASILEIRA",
                    "naturalidade": "Rio de Janeiro",
                    "racaCor": "PARDA",
                    "situacaoRua": false
                  },
                  "statusPaciente": "ATIVO",
                  "tipoAtendimentoPreferencial": "GESTANTE",
                  "observacoes": "Paciente atualizado com todos os campos"
                }
                """;

        mockMvc.perform(put("/v1/pacientes/" + pacienteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }

    @Test
    void atualizacaoComTodosOsCamposNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Paciente Atualizado Todos Campos",
                    "nomeSocial": "Paciente Todos Atualizado",
                    "dataNascimento": "1987-08-25",
                    "sexo": "MASCULINO"
                  },
                  "documentosBasicos": {
                    "rg": "11223344",
                    "orgaoEmissorRg": "SSP",
                    "ufEmissorRg": "MG"
                  },
                  "contato": {
                    "email": "paciente.todos.campos.atualizado@example.com"
                  },
                  "dadosDemograficos": {
                    "escolaridade": "POS_GRADUACAO",
                    "nacionalidade": "BRASILEIRA",
                    "naturalidade": "Belo Horizonte",
                    "paisNascimento": "Brasil",
                    "municipioNascimentoIbge": "3106200",
                    "racaCor": "PRETA",
                    "ocupacaoProfissao": "Médico",
                    "situacaoRua": false,
                    "identidadeGenero": "TRANS",
                    "orientacaoSexual": "BISSEXUAL"
                  },
                  "statusPaciente": "ATIVO",
                  "tipoAtendimentoPreferencial": "DEFICIENTE_FISICO",
                  "observacoes": "Paciente atualizado com todos os campos possíveis",
                  "enderecoPrincipal": {
                    "tipoLogradouro": "AVENIDA",
                    "logradouro": "Avenida Atlântica",
                    "numero": "2000",
                    "complemento": "Apto 502",
                    "bairro": "Copacabana",
                    "cep": "22021000",
                    "pais": "Brasil",
                    "distrito": "Copacabana",
                    "pontoReferencia": "Próximo à praia",
                    "latitude": -22.9711,
                    "longitude": -43.1822,
                    "tipoEndereco": "RESIDENCIAL",
                    "zona": "URBANA",
                    "codigoIbgeMunicipio": "3304557",
                    "microarea": "002",
                    "ineEquipe": "987654321012345",
                    "quadra": "003",
                    "lote": "004",
                    "zonaRuralDescricao": "",
                    "andar": "5",
                    "bloco": "B",
                    "semNumero": false
                  },
                  "enderecos": [
                    {
                      "dadosEndereco": {
                        "tipoLogradouro": "RUA",
                        "logradouro": "Rua das Acácias",
                        "numero": "300",
                        "complemento": "Casa",
                        "bairro": "Jardim Botânico",
                        "cep": "22460000",
                        "tipoEndereco": "COMERCIAL",
                        "zona": "URBANA",
                        "semNumero": false
                      },
                      "principal": false
                    }
                  ],
                  "identificadores": [
                    {
                      "tipo": "OUTRO",
                      "valor": "PASSAPORTE987654",
                      "origem": "CADSUS",
                      "validado": false,
                      "principal": false,
                      "observacoes": "Passaporte atualizado"
                    }
                  ],
                  "contatos": [
                    {
                      "tipo": "EMAIL",
                      "nome": "Email Secundário Atualizado",
                      "email": "email.secundario.atualizado@example.com"
                    }
                  ],
                  "dadosSociodemograficos": {
                    "racaCor": "PRETA",
                    "nacionalidade": "BRASILEIRA",
                    "paisNascimento": "Brasil",
                    "naturalidade": "Belo Horizonte",
                    "municipioNascimentoIbge": "3106200",
                    "escolaridade": "POS_GRADUACAO",
                    "ocupacaoProfissao": "Médico",
                    "situacaoRua": false,
                    "tempoSituacaoRua": null,
                    "condicaoMoradia": "ALUGADO",
                    "situacaoFamiliar": "SOZINHO"
                  },
                  "dadosClinicosBasicos": {
                    "gestante": false,
                    "fumante": true,
                    "alcoolista": false,
                    "usuarioDrogas": false,
                    "historicoViolencia": false,
                    "acompanhamentoPsicossocial": true
                  },
                  "dadosPessoaisComplementares": {
                    "nomeMae": "Maria Santos",
                    "nomePai": "João Santos",
                    "identidadeGenero": "TRANS",
                    "orientacaoSexual": "BISSEXUAL"
                  },
                  "responsavelLegal": {
                    "nome": "Responsável Legal Atualizado"
                  },
                  "integracaoGov": {
                    "cartaoSusAtivo": false,
                    "dataAtualizacaoCns": "2024-06-15",
                    "origemCadastro": "ESUS",
                    "cnsValidado": false,
                    "tipoCns": "PROVISORIO"
                  },
                  "lgpdConsentimento": {
                    "autorizacaoUsoDados": false,
                    "autorizacaoContatoWhatsApp": false,
                    "autorizacaoContatoEmail": true,
                    "dataConsentimento": "2024-06-15T14:30:00"
                  },
                  "vinculosTerritoriais": [
                    {
                      "municipioIbge": "3304557",
                      "cnesEstabelecimento": "7654321",
                      "ineEquipe": "9876543210",
                      "microarea": "002",
                      "dataInicio": "2024-06-01",
                      "dataFim": null,
                      "origem": "RNDS",
                      "observacoes": "Vínculo territorial atualizado"
                    }
                  ]
                }
                """;

        mockMvc.perform(put("/v1/pacientes/" + pacienteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }
}
