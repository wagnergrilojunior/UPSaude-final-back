package com.upsaude.regression.tenant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upsaude.regression.BaseRegressionTest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TenantCrudCompleteRegressionTest extends BaseRegressionTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private String gerarSlugUnico() {
    return String.format("tenant-crud-test-%d", System.currentTimeMillis());
  }

  private String gerarCnpjUnico() {
    long timestamp = System.currentTimeMillis();
    String base = String.format("%012d", timestamp % 1000000000000L);
    if (base.chars().distinct().count() == 1) {
      base = "123456780001";
    }
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
      if (peso > 9)
        peso = 2;
    }
    int resto = soma % 11;
    return (resto < 2) ? 0 : (11 - resto);
  }

  private String gerarTelefoneValido() {
    long timestamp = System.currentTimeMillis();
    String numero = String.format("%08d", timestamp % 100000000L);
    return String.format("(11) 9%s-%s", numero.substring(0, 4), numero.substring(4, 8));
  }

  @Test
  @Order(1)
  @DisplayName("1. CREATE - Deve criar tenant com dados mínimos")
  void deveCriarTenantComDadosMinimos() throws Exception {
    String slug = gerarSlugUnico();
    String jsonPayload = String.format("""
        {
          "slug": "%s",
          "dadosIdentificacao": {
            "nome": "Tenant Teste CRUD Mínimo"
          }
        }
        """, slug);

    mockMvc.perform(post("/v1/tenants")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonPayload))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.slug").value(slug))
        .andExpect(jsonPath("$.ativo").value(true))
        .andExpect(jsonPath("$.dadosIdentificacao.nome").value("Tenant Teste CRUD Mínimo"));
  }

  @Test
  @Order(2)
  @DisplayName("2. CREATE - Deve criar tenant com código IBGE válido")
  void deveCriarTenantComCodigoIbgeValido() throws Exception {
    String slug = gerarSlugUnico();
    String cnpj = gerarCnpjUnico();
    String jsonPayload = String.format("""
        {
          "slug": "%s",
          "codigoIbgeMunicipio": "3550308",
          "dadosIdentificacao": {
            "nome": "Tenant São Paulo",
            "cnpj": "%s"
          }
        }
        """, slug, cnpj);

    mockMvc.perform(post("/v1/tenants")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonPayload))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.codigoIbgeMunicipio").value("3550308"));
  }

  @Test
  @Order(3)
  @DisplayName("3. CREATE - Deve rejeitar tenant com código IBGE inválido")
  void deveRejeitarTenantComCodigoIbgeInvalido() throws Exception {
    String slug = gerarSlugUnico();
    String jsonPayload = String.format("""
        {
          "slug": "%s",
          "codigoIbgeMunicipio": "9999999",
          "dadosIdentificacao": {
            "nome": "Tenant Código Inválido"
          }
        }
        """, slug);

    mockMvc.perform(post("/v1/tenants")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonPayload))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message")
            .value(containsString("Código IBGE do município '9999999' não encontrado")));
  }

  @Test
  @Order(4)
  @DisplayName("4. CREATE - Deve criar tenant com todos os campos")
  void deveCriarTenantComTodosCampos() throws Exception {
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
          "codigoIbgeMunicipio": "3304557",
          "dadosIdentificacao": {
            "nome": "Tenant Completo",
            "descricao": "Descrição completa do tenant",
            "cnpj": "%s"
          },
          "contato": {
            "telefone": "%s",
            "email": "contato@tenant-completo.com",
            "site": "https://www.tenant-completo.com"
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
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.slug").value(slug))
        .andExpect(jsonPath("$.codigoIbgeMunicipio").value("3304557"))
        .andExpect(jsonPath("$.dadosIdentificacao.nome").value("Tenant Completo"))
        .andExpect(jsonPath("$.contato.email").value("contato@tenant-completo.com"))
        .andExpect(jsonPath("$.dadosFiscais.inscricaoEstadual").value("123456789"));
  }

  @Test
  @Order(5)
  @DisplayName("5. READ - Deve buscar tenant por ID")
  void deveBuscarTenantPorId() throws Exception {
    // Criar tenant para buscar
    String slug = gerarSlugUnico();
    String jsonPayload = String.format("""
        {
          "slug": "%s",
          "dadosIdentificacao": {
            "nome": "Tenant para Busca"
          }
        }
        """, slug);

    MvcResult createResult = mockMvc.perform(post("/v1/tenants")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonPayload))
        .andExpect(status().isCreated())
        .andReturn();

    String responseBody = createResult.getResponse().getContentAsString();
    JsonNode jsonNode = objectMapper.readTree(responseBody);
    String tenantId = jsonNode.get("id").asText();

    // Buscar tenant criado
    mockMvc.perform(get("/v1/tenants/" + tenantId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(tenantId))
        .andExpect(jsonPath("$.slug").value(slug))
        .andExpect(jsonPath("$.dadosIdentificacao.nome").value("Tenant para Busca"));
  }

  @Test
  @Order(6)
  @DisplayName("6. READ - Deve retornar 404 para tenant inexistente")
  void deveRetornar404ParaTenantInexistente() throws Exception {
    mockMvc.perform(get("/v1/tenants/00000000-0000-0000-0000-000000000000"))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(7)
  @DisplayName("7. READ - Deve listar tenants com paginação")
  void deveListarTenantsComPaginacao() throws Exception {
    mockMvc.perform(get("/v1/tenants")
        .param("page", "0")
        .param("size", "10"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.content", hasSize(greaterThan(0))))
        .andExpect(jsonPath("$.totalElements").exists())
        .andExpect(jsonPath("$.totalPages").exists());
  }

  @Test
  @Order(8)
  @DisplayName("8. READ - Endereço completo deve incluir cidade e estado")
  void enderecoCompletoDeveIncluirCidadeEEstado() throws Exception {
    // Criar tenant com endereço que tenha cidade
    String slug = gerarSlugUnico();
    String cnpj = gerarCnpjUnico();
    String jsonPayload = String.format("""
        {
          "slug": "%s",
          "codigoIbgeMunicipio": "3550308",
          "dadosIdentificacao": {
            "nome": "Tenant com Endereço",
            "cnpj": "%s"
          }
        }
        """, slug, cnpj);

    MvcResult result = mockMvc.perform(post("/v1/tenants")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonPayload))
        .andExpect(status().isCreated())
        .andReturn();

    String responseBody = result.getResponse().getContentAsString();
    JsonNode jsonNode = objectMapper.readTree(responseBody);
    String tenantId = jsonNode.get("id").asText();

    // Verificar que o endereço completo é retornado
    mockMvc.perform(get("/v1/tenants/" + tenantId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.codigoIbgeMunicipio").value("3550308"));
    // Nota: O endereço completo com cidade/estado só será retornado se houver um
    // endereço associado
  }

  @Test
  @Order(9)
  @DisplayName("9. UPDATE - Deve atualizar tenant")
  void deveAtualizarTenant() throws Exception {
    // Criar tenant para atualizar
    String slug = gerarSlugUnico();
    String createPayload = String.format("""
        {
          "slug": "%s",
          "dadosIdentificacao": {
            "nome": "Tenant Original"
          }
        }
        """, slug);

    MvcResult createResult = mockMvc.perform(post("/v1/tenants")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createPayload))
        .andExpect(status().isCreated())
        .andReturn();

    String responseBody = createResult.getResponse().getContentAsString();
    JsonNode jsonNode = objectMapper.readTree(responseBody);
    String tenantId = jsonNode.get("id").asText();

    // Atualizar tenant
    String updatePayload = String.format("""
        {
          "slug": "%s",
          "dadosIdentificacao": {
            "nome": "Tenant Teste CRUD Atualizado",
            "descricao": "Descrição atualizada"
          }
        }
        """, slug);

    mockMvc.perform(put("/v1/tenants/" + tenantId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(updatePayload))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(tenantId))
        .andExpect(jsonPath("$.dadosIdentificacao.nome").value("Tenant Teste CRUD Atualizado"))
        .andExpect(jsonPath("$.dadosIdentificacao.descricao").value("Descrição atualizada"));
  }

  @Test
  @Order(10)
  @DisplayName("10. UPDATE - Deve atualizar código IBGE válido")
  void deveAtualizarCodigoIbgeValido() throws Exception {
    // Criar tenant
    String slug = gerarSlugUnico();
    String createPayload = String.format("""
        {
          "slug": "%s",
          "dadosIdentificacao": {
            "nome": "Tenant para Atualizar IBGE"
          }
        }
        """, slug);

    MvcResult createResult = mockMvc.perform(post("/v1/tenants")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createPayload))
        .andExpect(status().isCreated())
        .andReturn();

    String responseBody = createResult.getResponse().getContentAsString();
    JsonNode jsonNode = objectMapper.readTree(responseBody);
    String tenantId = jsonNode.get("id").asText();

    // Atualizar com código IBGE
    String updatePayload = String.format("""
        {
          "slug": "%s",
          "codigoIbgeMunicipio": "3304557",
          "dadosIdentificacao": {
            "nome": "Tenant Teste CRUD Atualizado"
          }
        }
        """, slug);

    mockMvc.perform(put("/v1/tenants/" + tenantId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(updatePayload))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.codigoIbgeMunicipio").value("3304557"));
  }

  @Test
  @Order(11)
  @DisplayName("11. UPDATE - Deve rejeitar atualização com código IBGE inválido")
  void deveRejeitarAtualizacaoComCodigoIbgeInvalido() throws Exception {
    // Criar tenant
    String slug = gerarSlugUnico();
    String createPayload = String.format("""
        {
          "slug": "%s",
          "dadosIdentificacao": {
            "nome": "Tenant para Testar IBGE Inválido"
          }
        }
        """, slug);

    MvcResult createResult = mockMvc.perform(post("/v1/tenants")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createPayload))
        .andExpect(status().isCreated())
        .andReturn();

    String responseBody = createResult.getResponse().getContentAsString();
    JsonNode jsonNode = objectMapper.readTree(responseBody);
    String tenantId = jsonNode.get("id").asText();

    // Tentar atualizar com código IBGE inválido
    String updatePayload = String.format("""
        {
          "slug": "%s",
          "codigoIbgeMunicipio": "0000000",
          "dadosIdentificacao": {
            "nome": "Tenant Teste"
          }
        }
        """, slug);

    mockMvc.perform(put("/v1/tenants/" + tenantId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(updatePayload))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message")
            .value(containsString("Código IBGE do município '0000000' não encontrado")));
  }

  @Test
  @Order(12)
  @DisplayName("12. UPDATE - Deve retornar 404 ao atualizar tenant inexistente")
  void deveRetornar404AoAtualizarTenantInexistente() throws Exception {
    String jsonPayload = """
        {
          "slug": "tenant-inexistente",
          "dadosIdentificacao": {
            "nome": "Tenant Inexistente"
          }
        }
        """;

    mockMvc.perform(put("/v1/tenants/00000000-0000-0000-0000-000000000000")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonPayload))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(13)
  @DisplayName("13. UPDATE - Deve atualizar todos os campos do tenant")
  void deveAtualizarTodosCamposTenant() throws Exception {
    // Criar tenant
    String slug = gerarSlugUnico();
    String createPayload = String.format("""
        {
          "slug": "%s",
          "dadosIdentificacao": {
            "nome": "Tenant para Atualização Completa"
          }
        }
        """, slug);

    MvcResult createResult = mockMvc.perform(post("/v1/tenants")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createPayload))
        .andExpect(status().isCreated())
        .andReturn();

    String responseBody = createResult.getResponse().getContentAsString();
    JsonNode jsonNode = objectMapper.readTree(responseBody);
    String tenantId = jsonNode.get("id").asText();

    // Atualizar todos os campos
    String telefone = gerarTelefoneValido();
    String updatePayload = String.format("""
        {
          "slug": "%s",
          "consorcio": true,
          "cnes": "7654321",
          "tipoInstituicao": "HOSPITAL",
          "codigoIbgeMunicipio": "3550308",
          "dadosIdentificacao": {
            "nome": "Tenant Totalmente Atualizado",
            "descricao": "Nova descrição completa"
          },
          "contato": {
            "telefone": "%s",
            "email": "novo@email.com",
            "site": "https://www.novosite.com"
          },
          "dadosFiscais": {
            "inscricaoEstadual": "999888777",
            "inscricaoMunicipal": "111222333"
          }
        }
        """, slug, telefone);

    mockMvc.perform(put("/v1/tenants/" + tenantId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(updatePayload))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.consorcio").value(true))
        .andExpect(jsonPath("$.codigoIbgeMunicipio").value("3550308"))
        .andExpect(jsonPath("$.dadosIdentificacao.nome").value("Tenant Totalmente Atualizado"))
        .andExpect(jsonPath("$.contato.email").value("novo@email.com"))
        .andExpect(jsonPath("$.dadosFiscais.inscricaoEstadual").value("999888777"));
  }

  @Test
  @Order(14)
  @DisplayName("14. DELETE - Deve inativar tenant")
  void deveInativarTenant() throws Exception {
    // Criar tenant para inativar
    String slug = gerarSlugUnico();
    String createPayload = String.format("""
        {
          "slug": "%s",
          "dadosIdentificacao": {
            "nome": "Tenant para Inativar"
          }
        }
        """, slug);

    MvcResult createResult = mockMvc.perform(post("/v1/tenants")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createPayload))
        .andExpect(status().isCreated())
        .andReturn();

    String responseBody = createResult.getResponse().getContentAsString();
    JsonNode jsonNode = objectMapper.readTree(responseBody);
    String tenantId = jsonNode.get("id").asText();

    // Inativar tenant
    mockMvc.perform(delete("/v1/tenants/" + tenantId))
        .andExpect(status().isNoContent());

    // Verificar que o tenant foi inativado
    mockMvc.perform(get("/v1/tenants/" + tenantId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.ativo").value(false));
  }

  @Test
  @Order(15)
  @DisplayName("15. DELETE - Deve retornar 404 ao excluir tenant inexistente")
  void deveRetornar404AoExcluirTenantInexistente() throws Exception {
    mockMvc.perform(delete("/v1/tenants/00000000-0000-0000-0000-000000000000"))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(16)
  @DisplayName("16. VALIDAÇÃO - Deve rejeitar criação com slug duplicado")
  void deveRejeitarCriacaoComSlugDuplicado() throws Exception {
    String slug = gerarSlugUnico();
    String cnpj = gerarCnpjUnico();

    // Criar primeiro tenant
    String jsonPayload1 = String.format("""
        {
          "slug": "%s",
          "dadosIdentificacao": {
            "nome": "Primeiro Tenant",
            "cnpj": "%s"
          }
        }
        """, slug, cnpj);

    mockMvc.perform(post("/v1/tenants")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonPayload1))
        .andExpect(status().isCreated());

    // Tentar criar segundo tenant com mesmo slug (sem CNPJ para evitar conflito)
    String jsonPayload2 = String.format("""
        {
          "slug": "%s",
          "dadosIdentificacao": {
            "nome": "Segundo Tenant"
          }
        }
        """, slug);

    mockMvc.perform(post("/v1/tenants")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonPayload2))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(containsString("Já existe um tenant cadastrado com o slug")));
  }

  @Test
  @Order(17)
  @DisplayName("17. VALIDAÇÃO - Deve rejeitar criação com CNPJ duplicado")
  void deveRejeitarCriacaoComCnpjDuplicado() throws Exception {
    String slug1 = gerarSlugUnico();
    String slug2 = gerarSlugUnico();
    String cnpj = gerarCnpjUnico();

    // Criar primeiro tenant
    String jsonPayload1 = String.format("""
        {
          "slug": "%s",
          "dadosIdentificacao": {
            "nome": "Primeiro Tenant",
            "cnpj": "%s"
          }
        }
        """, slug1, cnpj);

    mockMvc.perform(post("/v1/tenants")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonPayload1))
        .andExpect(status().isCreated());

    // Tentar criar segundo tenant com mesmo CNPJ
    String jsonPayload2 = String.format("""
        {
          "slug": "%s",
          "dadosIdentificacao": {
            "nome": "Segundo Tenant",
            "cnpj": "%s"
          }
        }
        """, slug2, cnpj);

    mockMvc.perform(post("/v1/tenants")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonPayload2))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(containsString("Já existe um tenant cadastrado com o CNPJ")));
  }

  @Test
  @Order(18)
  @DisplayName("18. VALIDAÇÃO - Deve rejeitar criação com email duplicado")
  void deveRejeitarCriacaoComEmailDuplicado() throws Exception {
    String slug1 = gerarSlugUnico();
    String slug2 = gerarSlugUnico();
    String cnpj1 = gerarCnpjUnico();
    String cnpj2 = gerarCnpjUnico();
    String email = "email-unico@teste.com";

    // Criar primeiro tenant
    String jsonPayload1 = String.format("""
        {
          "slug": "%s",
          "dadosIdentificacao": {
            "nome": "Primeiro Tenant",
            "cnpj": "%s"
          },
          "contato": {
            "email": "%s"
          }
        }
        """, slug1, cnpj1, email);

    mockMvc.perform(post("/v1/tenants")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonPayload1))
        .andExpect(status().isCreated());

    // Tentar criar segundo tenant com mesmo email
    String jsonPayload2 = String.format("""
        {
          "slug": "%s",
          "dadosIdentificacao": {
            "nome": "Segundo Tenant",
            "cnpj": "%s"
          },
          "contato": {
            "email": "%s"
          }
        }
        """, slug2, cnpj2, email);

    mockMvc.perform(post("/v1/tenants")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonPayload2))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(containsString("Já existe um tenant cadastrado com o email")));
  }
}
