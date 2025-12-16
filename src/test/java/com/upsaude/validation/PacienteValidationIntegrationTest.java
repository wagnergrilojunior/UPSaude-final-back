package com.upsaude.validation;

import com.upsaude.controller.PacienteController;
import com.upsaude.exception.ApiExceptionHandler;
import com.upsaude.service.PacienteService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PacienteValidationIntegrationTest {

    @Test
    void deveRetornar400ComMensagemPadronizadaCpfInvalido() throws Exception {
        PacienteService pacienteService = mock(PacienteService.class);
        PacienteController controller = new PacienteController(pacienteService);

        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(controller)
            .setControllerAdvice(new ApiExceptionHandler())
            .setValidator(validator)
            .setMessageConverters(new MappingJackson2HttpMessageConverter())
            .build();

        String body = """
            {
              \"nomeCompleto\": \"Paciente Teste\",
              \"sexo\": 1,
              \"cpf\": \"123\"
            }
            """;

        mockMvc.perform(post("/v1/pacientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Erro de validação"))
            .andExpect(jsonPath("$.errors[?(@.campo=='cpf')].mensagem", Matchers.hasItem("CPF inválido")));
    }
}
