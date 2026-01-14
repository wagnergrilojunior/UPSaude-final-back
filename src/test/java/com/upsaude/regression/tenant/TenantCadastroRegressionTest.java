package com.upsaude.regression.tenant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.upsaude.regression.BaseRegressionTest;

class TenantCadastroRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private String gerarSlugUnico() {
        return String.format("tenant-teste-%d", System.currentTimeMillis());
    }

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

    private String gerarCnpjUnico() {
        long timestamp = System.currentTimeMillis();
        String base = String.format("%012d", timestamp % 1000000000000L);
        // Garantir que não seja todos dígitos iguais
        if (base.chars().distinct().count() == 1) {
            base = "123456780001";
        }
        // Gerar dígitos verificadores válidos para CNPJ (algoritmo oficial)
        int dv1 = calcularDvCnpj(base, 12);
        int dv2 = calcularDvCnpj(base + dv1, 13);
        return base + dv1 + dv2;
    }

    private int calcularDvCnpj(String cnpj, int len) {
        int soma = 0;
        int peso = 2;
        for (int i = len - 1; i >= 0; i--) {
            int digito = Character.getNumericValue(cnpj.charAt(i));
            soma += digito * peso;
            peso++;
            if (peso > 9) peso = 2;
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : (11 - resto);
    }

    private String gerarTelefoneValido() {
        // Gera telefone celular válido: (11) 9XXXX-XXXX
        long timestamp = System.currentTimeMillis();
        String numero = String.format("%08d", timestamp % 100000000L);
        return String.format("(11) 9%s-%s", numero.substring(0, 4), numero.substring(4, 8));
    }

    @Test
    void cadastroMinimoDeTenantNaoPodeQuebrar() throws Exception {
        String slug = gerarSlugUnico();
        String jsonPayload = String.format("""
                {
                  "slug": "%s",
                  "dadosIdentificacao": {
                    "nome": "Tenant Teste Mínimo"
                  }
                }
                """, slug);

        mockMvc.perform(post("/v1/tenants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void cadastroCompletoDeTenantNaoPodeQuebrar() throws Exception {
        String slug = gerarSlugUnico();
        String cnpj = gerarCnpjUnico();
        String telefone = gerarTelefoneValido();
        String jsonPayload = String.format("""
                {
                  "slug": "%s",
                  "ativo": true,
                  "consorcio": false,
                  "cnes": "1234567",
                  "tipoInstituicao": "PREFEITURA",
                  "dadosIdentificacao": {
                    "nome": "Tenant Teste Completo",
                    "descricao": "Descrição do tenant de teste completo",
                    "cnpj": "%s"
                  },
                  "contato": {
                    "telefone": "%s",
                    "email": "contato@tenant.com",
                    "site": "https://www.tenant.com"
                  },
                  "dadosFiscais": {
                    "inscricaoEstadual": "123456789",
                    "inscricaoMunicipal": "987654321"
                  }
                }
                """, slug, cnpj, telefone);

        mockMvc.perform(post("/v1/tenants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void cadastroComTodosOsCamposNaoPodeQuebrar() throws Exception {
        String slug = gerarSlugUnico();
        String cpf = gerarCpfUnico();
        String cnpj = gerarCnpjUnico();
        String telefone = gerarTelefoneValido();
        String telefoneResponsavel = gerarTelefoneValido();
        String jsonPayload = String.format("""
                {
                  "slug": "%s",
                  "metadados": "{\\"chave\\": \\"valor\\"}",
                  "ativo": true,
                  "consorcio": true,
                  "cnes": "7654321",
                  "tipoInstituicao": "CONSORCIO",
                  "dadosIdentificacao": {
                    "nome": "Tenant Teste Todos Campos",
                    "descricao": "Descrição completa do tenant de teste",
                    "urlLogo": "https://www.tenant.com/logo.png",
                    "cnpj": "%s"
                  },
                  "contato": {
                    "telefone": "%s",
                    "email": "contato@tenant-todos-campos.com",
                    "site": "https://www.tenant-todos-campos.com"
                  },
                  "dadosFiscais": {
                    "inscricaoEstadual": "987654321",
                    "inscricaoMunicipal": "123456789"
                  },
                  "responsavel": {
                    "responsavelNome": "João Silva",
                    "responsavelCpf": "%s",
                    "responsavelTelefone": "%s"
                  },
                  "informacoesAdicionais": {
                    "observacoes": "Observações adicionais do tenant"
                  }
                }
                """, slug, cnpj, telefone, cpf, telefoneResponsavel);

        mockMvc.perform(post("/v1/tenants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }
}
