---
name: Melhorar tratamento de erros 500 com logs e stack trace no response
overview: Melhorar o tratamento de erros 500 para incluir logs detalhados e retornar stack trace completo no response HTTP, facilitando o debug e ajustes rápidos. Aplicar para todos os controllers via ApiExceptionHandler.
todos:
  - id: improve-exception-handler
    content: Melhorar ApiExceptionHandler para retornar stack trace completo em erros 500 e usar SLF4J para logs
    status: completed
  - id: improve-usuarios-service-logs
    content: Melhorar logs de erro em UsuariosSistemaServiceImpl para incluir stack trace completo
    status: completed
  - id: improve-medicos-service-logs
    content: Melhorar logs de erro em MedicosServiceImpl para incluir stack trace completo
    status: completed
  - id: improve-paciente-service-logs
    content: Melhorar logs de erro em PacienteServiceImpl para incluir stack trace completo
    status: completed
  - id: improve-alergias-service-logs
    content: Melhorar logs de erro em AlergiasServiceImpl e AlergiasPacienteServiceImpl para incluir stack trace completo
    status: completed
  - id: improve-controllers-logs
    content: Melhorar logs de erro nos controllers (UsuariosSistema, Medicos, Paciente, Alergias) para incluir stack trace completo
    status: completed
---

# Plano: Melhorar Tratamento de Erros 500 com Logs e Stack Trace no Response

## Objetivo

Quando ocorrer erro 500, o sistema deve:

1. Registrar logs detalhados do erro
2. Retornar stack trace completo no response HTTP para facilitar debug

## Escopo

- Aplicar para todos os controllers via `ApiExceptionHandler`
- Focar inicialmente nos módulos: usuarios-sistema, medicos, pacientes, alergias
- Manter compatibilidade com endpoints do Actuator

## Implementação

### 1. Melhorar ApiExceptionHandler.java

**Arquivo**: `src/main/java/com/upsaude/exception/ApiExceptionHandler.java`

**Mudanças**:

- Substituir `System.err.println` por `log.error` do SLF4J (adicionar `@Slf4j`)
- Melhorar método `handleGenericException()` para:
  - Fazer log detalhado com stack trace completo
  - Retornar stack trace no response JSON
  - Incluir informações úteis: mensagem, classe da exceção, stack trace, path, método HTTP
- Melhorar método `handleInternalServerErrorException()` para:
  - Fazer log detalhado quando houver causa (Throwable)
  - Retornar stack trace quando disponível
- Criar método auxiliar `buildErrorResponseWithStackTrace()` para construir response com stack trace

**Estrutura do response JSON para erro 500**:

```json
{
  "timestamp": "2025-12-08T10:30:00",
  "status": 500,
  "erro": "Erro Interno do Servidor",
  "mensagem": "Mensagem da exceção",
  "path": "/api/v1/pacientes",
  "exception": "com.upsaude.exception.InternalServerErrorException",
  "stackTrace": [
    "com.upsaude.service.impl.PacienteServiceImpl.criar(PacienteServiceImpl.java:95)",
    "com.upsaude.controller.PacienteController.criar(PacienteController.java:53)",
    ...
  ]
}
```

### 2. Verificar e melhorar logs nos Services

**Arquivos a verificar**:

- `src/main/java/com/upsaude/service/impl/UsuariosSistemaServiceImpl.java`
- `src/main/java/com/upsaude/service/impl/MedicosServiceImpl.java`
- `src/main/java/com/upsaude/service/impl/PacienteServiceImpl.java`
- `src/main/java/com/upsaude/service/impl/AlergiasServiceImpl.java`
- `src/main/java/com/upsaude/service/impl/AlergiasPacienteServiceImpl.java`

**Mudanças**:

- Garantir que todos os `catch (Exception ex)` façam `log.error` com stack trace completo
- Usar `log.error("mensagem", ex)` ao invés de apenas `log.error("mensagem: {}", ex.getMessage())`
- Adicionar contexto útil nos logs (request payload, IDs, etc.)

### 3. Verificar e melhorar logs nos Controllers

**Arquivos a verificar**:

- `src/main/java/com/upsaude/controller/UsuariosSistemaController.java`
- `src/main/java/com/upsaude/controller/MedicosController.java`
- `src/main/java/com/upsaude/controller/PacienteController.java`
- `src/main/java/com/upsaude/controller/AlergiasController.java`
- `src/main/java/com/upsaude/controller/AlergiasPacienteController.java`

**Mudanças**:

- Garantir que `catch (Exception ex)` façam `log.error` com stack trace completo
- Adicionar informações de contexto (request payload, path, método HTTP)

## Detalhes Técnicos

### Logs

- Usar `log.error("mensagem descritiva", ex)` para incluir stack trace completo
- Incluir contexto: path, método HTTP, payload (quando seguro), IDs relevantes
- Manter logs estruturados para facilitar busca e análise

### Response HTTP

- Retornar stack trace completo como array de strings no JSON
- Incluir classe da exceção (`exception` field)
- Manter mensagem de erro legível (`mensagem` field)
- Incluir timestamp, status, path para rastreabilidade

### Segurança

- Stack trace completo expõe código interno
- Considerar adicionar flag de configuração para desabilitar stack trace em produção futuramente
- Por enquanto, retornar stack trace completo conforme solicitado

## Arquivos a Modificar

1. `src/main/java/com/upsaude/exception/ApiExceptionHandler.java` - Principal mudança
2. `src/main/java/com/upsaude/service/impl/UsuariosSistemaServiceImpl.java` - Melhorar logs
3. `src/main/java/com/upsaude/service/impl/MedicosServiceImpl.java` - Melhorar logs
4. `src/main/java/com/upsaude/service/impl/PacienteServiceImpl.java` - Melhorar logs
5. `src/main/java/com/upsaude/service/impl/AlergiasServiceImpl.java` - Melhorar logs
6. `src/main/java/com/upsaude/service/impl/AlergiasPacienteServiceImpl.java` - Melhorar logs
7. `src/main/java/com/upsaude/controller/UsuariosSistemaController.java` - Melhorar logs
8. `src/main/java/com/upsaude/controller/MedicosController.java` - Melhorar logs
9. `src/main/java/com/upsaude/controller/PacienteController.java` - Melhorar logs
10. `src/main/java/com/upsaude/controller/AlergiasController.java` - Melhorar logs
11. `src/main/java/com/upsaude/controller/AlergiasPacienteController.java` - Melhorar logs

## Resultado Esperado

Após implementação:

- Erros 500 serão logados com stack trace completo
- Response HTTP incluirá stack trace completo para facilitar debug
- Logs terão contexto suficiente para identificar problemas rapidamente
- Desenvolvedores poderão ajustar problemas mais rapidamente com informações detalhadas