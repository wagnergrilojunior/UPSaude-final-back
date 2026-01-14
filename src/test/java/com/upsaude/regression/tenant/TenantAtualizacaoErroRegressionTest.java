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

class TenantAtualizacaoErroRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID tenantId;
    private String slugOriginal;

    private String gerarSlugUnico() {
        return String.format("tenant-teste-%d", System.currentTimeMillis());
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
    void tenantAtualizadoSemSlugSempreRetorna400() throws Exception {
        String jsonPayload = """
                {
                  "dadosIdentificacao": {
                    "nome": "Tenant Sem Slug"
                  }
                }
                """;

        mockMvc.perform(put("/v1/tenants/" + tenantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void tenantAtualizadoSemNomeSempreRetorna400() throws Exception {
        String slug = gerarSlugUnico();
        String jsonPayload = String.format("""
                {
                  "slug": "%s",
                  "dadosIdentificacao": {}
                }
                """, slug);

        mockMvc.perform(put("/v1/tenants/" + tenantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void tenantAtualizadoComSlugInvalidoSempreRetorna400() throws Exception {
        String jsonPayload = """
                {
                  "slug": "SLUG_INVALIDO_COM_MAIUSCULAS_E_ESPACOS",
                  "dadosIdentificacao": {
                    "nome": "Tenant Com Slug Inválido"
                  }
                }
                """;

        mockMvc.perform(put("/v1/tenants/" + tenantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }
}
