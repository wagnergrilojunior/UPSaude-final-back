package com.upsaude.regression.usuariosistema;

import com.upsaude.regression.BaseRegressionTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UsuariosSistemaCadastroErroRegressionTest extends BaseRegressionTest {

    @Autowired
    private MockMvc mockMvc;

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

    @Test
    void usuariosSistemaSemEmailSempreRetorna400() throws Exception {
        String username = gerarUsernameUnico();
        String cpf = gerarCpfUnico();
        String jsonPayload = String.format("""
                {
                  "tenantId": "26c54644-56c9-4237-9a01-737b6625099f",
                  "usuarioConsorcio": false,
                  "dadosIdentificacao": {
                    "username": "%s",
                    "cpf": "%s"
                  }
                }
                """, username, cpf);

        mockMvc.perform(post("/v1/usuarios-sistema")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }
}
