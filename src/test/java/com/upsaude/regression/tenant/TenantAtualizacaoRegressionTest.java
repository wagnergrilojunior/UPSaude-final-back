package com.upsaude.regression.tenant;

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

class TenantAtualizacaoRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID tenantId;
    private String slugOriginal;

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

    @BeforeEach
    void criarTenantParaAtualizar() throws Exception {
        slugOriginal = gerarSlugUnico();
        String jsonPayload = String.format("""
                {
                  "slug": "%s",
                  "dadosIdentificacao": {
                    "nome": "Tenant Original"
                  }
                }
                """, slugOriginal);

        MvcResult result = mockMvc.perform(post("/v1/tenants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        tenantId = UUID.fromString(idStr);
    }

    @Test
    void atualizacaoMinimaDeTenantNaoPodeQuebrar() throws Exception {
        String slug = gerarSlugUnico();
        String jsonPayload = String.format("""
                {
                  "slug": "%s",
                  "dadosIdentificacao": {
                    "nome": "Tenant Atualizado Mínimo"
                  }
                }
                """, slug);

        mockMvc.perform(put("/v1/tenants/" + tenantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }

    @Test
    void atualizacaoCompletaDeTenantNaoPodeQuebrar() throws Exception {
        String slug = gerarSlugUnico();
        String cnpj = gerarCnpjUnico();
        String telefone = gerarTelefoneValido();
        String jsonPayload = String.format("""
                {
                  "slug": "%s",
                  "ativo": true,
                  "consorcio": false,
                  "cnes": "1111111",
                  "tipoInstituicao": "PREFEITURA",
                  "dadosIdentificacao": {
                    "nome": "Tenant Atualizado Completo",
                    "descricao": "Nova descrição do tenant atualizado",
                    "cnpj": "%s"
                  },
                  "contato": {
                    "telefone": "%s",
                    "email": "novo-contato@tenant.com",
                    "site": "https://www.novo-tenant.com"
                  },
                  "dadosFiscais": {
                    "inscricaoEstadual": "111111111",
                    "inscricaoMunicipal": "222222222"
                  }
                }
                """, slug, cnpj, telefone);

        mockMvc.perform(put("/v1/tenants/" + tenantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }

    @Test
    void atualizacaoComTodosOsCamposNaoPodeQuebrar() throws Exception {
        String slug = gerarSlugUnico();
        String cpf = gerarCpfUnico();
        String cnpj = gerarCnpjUnico();
        String telefone = gerarTelefoneValido();
        String telefoneResponsavel = gerarTelefoneValido();
        String jsonPayload = String.format("""
                {
                  "slug": "%s",
                  "metadados": "{\\"novaChave\\": \\"novoValor\\"}",
                  "ativo": false,
                  "consorcio": true,
                  "cnes": "9999999",
                  "tipoInstituicao": "CONSORCIO",
                  "dadosIdentificacao": {
                    "nome": "Tenant Atualizado Todos Campos",
                    "descricao": "Descrição completa atualizada",
                    "urlLogo": "https://www.tenant.com/novo-logo.png",
                    "cnpj": "%s"
                  },
                  "contato": {
                    "telefone": "%s",
                    "email": "novo-contato@tenant-todos-campos.com",
                    "site": "https://www.novo-tenant-todos-campos.com"
                  },
                  "dadosFiscais": {
                    "inscricaoEstadual": "999999999",
                    "inscricaoMunicipal": "888888888"
                  },
                  "responsavel": {
                    "responsavelNome": "Maria Santos",
                    "responsavelCpf": "%s",
                    "responsavelTelefone": "%s"
                  },
                  "informacoesAdicionais": {
                    "observacoes": "Novas observações adicionais do tenant"
                  }
                }
                """, slug, cnpj, telefone, cpf, telefoneResponsavel);

        mockMvc.perform(put("/v1/tenants/" + tenantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }
}
