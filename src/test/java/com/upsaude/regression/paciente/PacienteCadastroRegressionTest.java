package com.upsaude.regression.paciente;

import com.upsaude.regression.BaseRegressionTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PacienteCadastroRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void cadastroMinimoDePacienteNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Paciente Regressao Minimo",
                    "sexo": "MASCULINO"
                  }
                }
                """;

        mockMvc.perform(post("/v1/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void cadastroCompletoDePacienteNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Paciente Regressao Completo",
                    "nomeSocial": "Paciente Completo",
                    "dataNascimento": "1990-01-15",
                    "sexo": "FEMININO"
                  },
                  "documentosBasicos": {
                    "rg": "1234567",
                    "orgaoEmissorRg": "SSP",
                    "ufEmissorRg": "SP"
                  },
                  "contato": {
                    "email": "paciente.completo@example.com"
                  },
                  "dadosDemograficos": {
                    "escolaridade": "MEDIO_COMPLETO",
                    "nacionalidade": "BRASILEIRA",
                    "naturalidade": "São Paulo",
                    "racaCor": "BRANCA",
                    "situacaoRua": false
                  },
                  "statusPaciente": "ATIVO",
                  "tipoAtendimentoPreferencial": "NENHUM",
                  "observacoes": "Paciente de teste de regressão completo"
                }
                """;

        mockMvc.perform(post("/v1/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void cadastroComTodosOsCamposNaoPodeQuebrar() throws Exception {
        String jsonPayload = """
                {
                  "dadosPessoaisBasicos": {
                    "nomeCompleto": "Paciente Regressao Todos Campos",
                    "nomeSocial": "Paciente Todos",
                    "dataNascimento": "1985-05-20",
                    "sexo": "MASCULINO"
                  },
                  "documentosBasicos": {
                    "cpf": "11144477735",
                    "rg": "12345678",
                    "cns": "701009864978597",
                    "orgaoEmissorRg": "SSP",
                    "ufEmissorRg": "SP"
                  },
                  "contato": {
                    "telefone": "1133334444",
                    "celular": "11987654321",
                    "email": "paciente.todos.campos@example.com"
                  },
                  "dadosDemograficos": {
                    "escolaridade": "SUPERIOR_COMPLETO",
                    "nacionalidade": "BRASILEIRA",
                    "naturalidade": "São Paulo",
                    "paisNascimento": "Brasil",
                    "municipioNascimentoIbge": "3550308",
                    "racaCor": "PARDA",
                    "ocupacaoProfissao": "Engenheiro de Software",
                    "situacaoRua": false,
                    "identidadeGenero": "CIS",
                    "orientacaoSexual": "HETEROSSEXUAL"
                  },
                  "statusPaciente": "ATIVO",
                  "tipoAtendimentoPreferencial": "IDOSO",
                  "informacoesConvenio": {
                    "numeroCarteirinha": "CARTAO123456",
                    "dataValidadeCarteirinha": "2028-12-31"
                  },
                  "observacoes": "Paciente de teste de regressão com todos os campos possíveis",
                  "enderecoPrincipal": {
                    "tipoLogradouro": "RUA",
                    "logradouro": "Avenida Paulista",
                    "numero": "1000",
                    "complemento": "Apto 101",
                    "bairro": "Bela Vista",
                    "cep": "01310100",
                    "pais": "Brasil",
                    "distrito": "Bela Vista",
                    "pontoReferencia": "Próximo ao metrô",
                    "latitude": -23.5505,
                    "longitude": -46.6333,
                    "tipoEndereco": "RESIDENCIAL",
                    "zona": "URBANA",
                    "codigoIbgeMunicipio": "3550308",
                    "microarea": "001",
                    "ineEquipe": "123456789012345",
                    "quadra": "001",
                    "lote": "002",
                    "zonaRuralDescricao": "",
                    "andar": "10",
                    "bloco": "A",
                    "semNumero": false
                  },
                  "enderecos": [
                    {
                      "dadosEndereco": {
                        "tipoLogradouro": "AVENIDA",
                        "logradouro": "Rua das Flores",
                        "numero": "500",
                        "complemento": "Casa",
                        "bairro": "Centro",
                        "cep": "01000000",
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
                      "valor": "PASSAPORTE123456",
                      "origem": "UPSAUDE",
                      "validado": true,
                      "dataValidacao": "2024-01-15",
                      "principal": false,
                      "observacoes": "Passaporte válido"
                    }
                  ],
                  "contatos": [
                    {
                      "tipo": "EMAIL",
                      "nome": "Contato Secundário",
                      "email": "contato.secundario@example.com"
                    },
                    {
                      "tipo": "WHATSAPP",
                      "nome": "WhatsApp Principal",
                      "celular": "11999887766"
                    }
                  ],
                  "dadosSociodemograficos": {
                    "racaCor": "PARDA",
                    "nacionalidade": "BRASILEIRA",
                    "paisNascimento": "Brasil",
                    "naturalidade": "São Paulo",
                    "municipioNascimentoIbge": "3550308",
                    "escolaridade": "SUPERIOR_COMPLETO",
                    "ocupacaoProfissao": "Engenheiro de Software",
                    "situacaoRua": false,
                    "tempoSituacaoRua": null,
                    "condicaoMoradia": "PROPRIO_QUITADO",
                    "situacaoFamiliar": "COM_FAMILIA"
                  },
                  "dadosClinicosBasicos": {
                    "gestante": false,
                    "fumante": false,
                    "alcoolista": false,
                    "usuarioDrogas": false,
                    "historicoViolencia": false,
                    "acompanhamentoPsicossocial": false
                  },
                  "dadosPessoaisComplementares": {
                    "nomeMae": "Maria da Silva",
                    "nomePai": "João da Silva",
                    "identidadeGenero": "CIS",
                    "orientacaoSexual": "HETEROSSEXUAL"
                  },
                  "responsavelLegal": {
                    "nome": "Responsável Legal Teste",
                    "cpf": "98765432100",
                    "telefone": "11988776655"
                  },
                  "integracaoGov": {
                    "cartaoSusAtivo": true,
                    "dataAtualizacaoCns": "2024-01-15",
                    "origemCadastro": "UPSAUDE",
                    "cnsValidado": true,
                    "tipoCns": "DEFINITIVO"
                  },
                  "obito": {
                    "dataObito": "2023-10-20",
                    "causaObitoCid10": "A00.0",
                    "origem": "MANUAL",
                    "observacoes": "Observações do óbito"
                  },
                  "lgpdConsentimento": {
                    "autorizacaoUsoDados": true,
                    "autorizacaoContatoWhatsApp": true,
                    "autorizacaoContatoEmail": true,
                    "dataConsentimento": "2024-01-15T10:00:00"
                  },
                  "vinculosTerritoriais": [
                    {
                      "municipioIbge": "3550308",
                      "cnesEstabelecimento": "1234567",
                      "ineEquipe": "1234567890",
                      "microarea": "001",
                      "dataInicio": "2024-01-01",
                      "dataFim": null,
                      "origem": "ESUS",
                      "observacoes": "Vínculo territorial de teste"
                    }
                  ]
                }
                """;

        mockMvc.perform(post("/v1/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }
}
