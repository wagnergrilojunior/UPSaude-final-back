package com.upsaude.regression.estabelecimento;

import com.upsaude.regression.BaseRegressionTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EstabelecimentoCadastroErroRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void estabelecimentoSemNomeSempreRetorna400() throws Exception {
        String jsonPayload = """
                {
                  "dadosIdentificacao": {
                    "tipo": "CLINICA"
                  }
                }
                """;

        mockMvc.perform(post("/v1/estabelecimentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }
}
