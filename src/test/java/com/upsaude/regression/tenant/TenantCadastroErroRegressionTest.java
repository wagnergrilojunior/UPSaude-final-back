package com.upsaude.regression.tenant;

import com.upsaude.regression.BaseRegressionTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TenantCadastroErroRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private String gerarSlugUnico() {
        return String.format("tenant-teste-%d", System.currentTimeMillis());
    }

    @Test
    void tenantSemSlugPodeSerCriadoComSucesso() throws Exception {
        String jsonPayload = """
                {
                  "dadosIdentificacao": {
                    "nome": "Tenant Sem Slug"
                  }
                }
                """;

        mockMvc.perform(post("/v1/tenants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }

    @Test
    void tenantSemNomeSempreRetorna400() throws Exception {
        String slug = gerarSlugUnico();
        String jsonPayload = String.format("""
                {
                  "slug": "%s",
                  "dadosIdentificacao": {}
                }
                """, slug);

        mockMvc.perform(post("/v1/tenants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void tenantComSlugInvalidoPodeSerCriadoComSucesso() throws Exception {
        // Slug agora é opcional e não tem validação de formato rígida
        String slug = gerarSlugUnico();
        String jsonPayload = String.format("""
                {
                  "slug": "%s",
                  "dadosIdentificacao": {
                    "nome": "Tenant Com Slug"
                  }
                }
                """, slug);

        mockMvc.perform(post("/v1/tenants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated());
    }
}
