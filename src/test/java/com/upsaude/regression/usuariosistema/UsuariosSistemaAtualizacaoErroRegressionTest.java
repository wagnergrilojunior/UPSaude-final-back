package com.upsaude.regression.usuariosistema;

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

class UsuariosSistemaAtualizacaoErroRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID usuariosSistemaId;
    private String emailOriginal;
    private String usernameOriginal;
    private String cpfOriginal;

    private String gerarCpfUnico() {
        long timestamp = System.currentTimeMillis();
        String base = String.format("%09d", timestamp % 1000000000L);
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

    private String gerarEmailUnico() {
        return String.format("usuario.teste.%d@example.com", System.currentTimeMillis());
    }

    private String gerarUsernameUnico() {
        return String.format("usuario_teste_%d", System.currentTimeMillis());
    }

    @BeforeEach
    void criarUsuariosSistemaParaAtualizar() throws Exception {
        emailOriginal = gerarEmailUnico();
        usernameOriginal = gerarUsernameUnico();
        cpfOriginal = gerarCpfUnico();
        String jsonPayload = String.format("""
                {
                  "email": "%s",
                  "tenantId": "26c54644-56c9-4237-9a01-737b6625099f",
                  "usuarioConsorcio": false,
                  "dadosIdentificacao": {
                    "username": "%s",
                    "cpf": "%s"
                  }
                }
                """, emailOriginal, usernameOriginal, cpfOriginal);

        MvcResult result = mockMvc.perform(post("/v1/usuarios-sistema")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String idStr = responseBody.split("\"id\":\"")[1].split("\"")[0];
        usuariosSistemaId = UUID.fromString(idStr);
    }

    @Test
    void usuariosSistemaAtualizadoSemUsernameSempreRetorna400() throws Exception {
        String jsonPayload = String.format("""
                {
                  "email": "%s",
                  "tenantId": "26c54644-56c9-4237-9a01-737b6625099f",
                  "usuarioConsorcio": false,
                  "dadosIdentificacao": {
                    "cpf": "%s"
                  }
                }
                """, emailOriginal, cpfOriginal);

        mockMvc.perform(put("/v1/usuarios-sistema/" + usuariosSistemaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }
}
