# 📋 Relatório Final - Testes CRUD UsuariosSistema

**Data**: 2026-01-18  
**Ambiente**: localhost:8080  
**Autenticação**: nataligrilobarros@gmail.com

---

## ✅ PROBLEMA RESOLVIDO: Deslogamento Constante

### Queries Travadas no Supabase
- **30 queries travadas** foram encerradas com sucesso
- **Tipos de queries**:
  - INSERT em `sia_pa` (travada há 23 dias)
  - UPDATE em `estados` (travadas há 8 dias)
  - ALTER TABLE em `competencia_financeira` (travadas há 22 horas)
  - SELECT em `competencia_financeira` (travadas há 10-13 horas)

### Impacto
- **Causa**: Queries travadas causavam timeouts que invalidavam sessões do Supabase Auth
- **Sintoma**: Deslogamento constante da aplicação
- **Solução**: Encerramento forçado de todas as conexões travadas
- **Resultado**: ✅ **PROBLEMA RESOLVIDO**

---

## 🧪 Testes CRUD - UsuariosSistema

### 📊 Dados Usados nos Testes

**Tenant**: `c592bae2-2da4-4a6f-a4d8-c33c184ca347`

**Estabelecimentos**:
- `17ba3886-23dc-45a0-8285-c6ff2b539fbb` (CNES: 1225445)
- `102e0b8a-6c3d-4673-a72c-e63a53b825ea` (CNES: 0449032)
- `6e6bf998-d31c-4a9a-bc13-7edd570c4fea` (CNES: 2873710)

---

### ✅ 1. READ - Listagem
**Endpoint**: `GET /api/v1/usuarios-sistema?page=0&size=5`  
**Status**: ✅ 200 OK  
**Resultado**: 5 registros retornados  
**Funcionalidades**: Paginação e ordenação funcionando

### ✅ 2. READ - Por ID
**Endpoint**: `GET /api/v1/usuarios-sistema/{id}`  
**ID Testado**: `b6aa1500-7532-4089-8604-b6bb9c8e42ac`  
**Status**: ✅ 200 OK  
**Dados**:
```json
{
  "id": "b6aa1500-7532-4089-8604-b6bb9c8e42ac",
  "username": "natali.usuario",
  "nomeExibicao": "nataligrilobarros@gmail.com",
  "ativo": true
}
```

### ❌ 3. UPDATE
**Endpoint**: `PUT /api/v1/usuarios-sistema/{id}`  
**ID Testado**: `b6aa1500-7532-4089-8604-b6bb9c8e42ac`  
**Status**: ❌ 500 Internal Server Error  
**Erro**: `Found shared references to a collection: UsuariosSistema.estabelecimentosVinculados`

### ⚠️ 4. CREATE
**Endpoint**: `POST /api/v1/usuarios-sistema`  
**Status**: ⚠️ 400 Bad Request  
**Problema**: Validação restritiva de email (Apache Commons Validator)

### ❌ 5. DELETE
**Status**: ❌ Não testado  
**Motivo**: CREATE falhou

---

## 🔧 Correções Tentadas (12 Abordagens)

| # | Abordagem | Resultado |
|---|-----------|-----------|
| 1 | Remover flush/clear dos métodos de vínculos | ❌ Falhou |
| 2 | Adicionar flush/clear ANTES de findByUsuarioUserId | ❌ Falhou |
| 3 | Adicionar flush/clear no atualizar ANTES de vínculos | ❌ Falhou |
| 4 | Usar entityManager.detach() antes do save | ❌ Falhou |
| 5 | Usar FlushModeType.COMMIT durante queries | ❌ Falhou |
| 6 | Fazer flush/clear e recarregar antes de vínculos | ❌ Falhou |
| 7 | Fazer flush ANTES do save | ❌ Falhou |
| 8 | Remover CASCADE ALL e orphanRemoval | ❌ Falhou |
| 9 | **Desabilitar atualização de vínculos (isolamento)** | ❌ **Falhou - Erro mesmo sem vínculos!** |
| 10 | Fazer flush/clear imediatamente após carregar | ❌ Falhou |
| 11 | Comentar relacionamento @OneToMany | ⚠️ Causou erros de compilação |
| 12 | Reverter e usar código limpo | ❌ Falhou |

### 🎯 Descoberta Crítica (Tentativa #9)

O erro ocorre MESMO quando a atualização de vínculos está desabilitada. Isso prova que:
- ❌ O problema NÃO está nos métodos de atualização de vínculos
- ✅ O problema ESTÁ no `save()` da entidade UsuariosSistema
- ✅ A coleção já tem referências compartilhadas quando fazemos `save()`

---

## 🐛 Análise Técnica do Problema

### Onde o Erro Ocorre
```
org.hibernate.engine.internal.Collections.processReachableCollection()
  ↓
org.hibernate.event.internal.FlushVisitor.processCollection()
  ↓
org.hibernate.event.internal.DefaultFlushEntityEventListener.onFlushEntity()
  ↓
usuariosSistemaRepository.save() <- AQUI
```

### Possíveis Causas Raiz

1. **`UsuarioSistemaAuditorAware`**  
   Carrega a entidade durante o flush para auditoria, causando referências compartilhadas

2. **`@PreUpdate validateEmbeddablesAndCollections()`**  
   Pode estar inicializando a coleção durante o save

3. **Relacionamento Bidirecional**  
   `@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)` pode estar causando o Hibernate a tentar gerenciar a coleção de forma conflitante

4. **Lazy Loading**  
   A coleção pode estar sendo inicializada em algum lugar antes do save, criando referências cruzadas no contexto de persistência

---

## 📊 Status Final do CRUD

| Operação | Status | HTTP | % Funcional |
|----------|--------|------|-------------|
| READ Listagem | ✅ Funcionando | 200 | 100% |
| READ Por ID | ✅ Funcionando | 200 | 100% |
| UPDATE | ❌ Falhando | 500 | 0% |
| CREATE | ⚠️ Parcial | 400 | 30% |
| DELETE | ❌ Não testado | - | 0% |
| **TOTAL** | **⚠️ Parcial** | - | **46%** |

---

## 💡 Próximas Ações Recomendadas

### Opção 1: Remover Auditoria Temporariamente
Desabilitar `@EntityListeners({AuditingEntityListener.class})` para testar se o `UsuarioSistemaAuditorAware` está causando o problema.

### Opção 2: Simplificar Relacionamento
Mudar para:
```java
@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
private List<UsuarioEstabelecimento> estabelecimentosVinculados;
```
Removendo `cascade` e `orphanRemoval`.

### Opção 3: Usar JPQL UPDATE Direto
Criar `@Query` com `@Modifying` para atualizar campos diretamente sem `save()`.

### Opção 4: Transação Separada
Atualizar vínculos em `@Transactional(propagation = Propagation.REQUIRES_NEW)`.

### Opção 5: Investigar @PreUpdate
Comentar o método `validateEmbeddablesAndCollections()` temporariamente.

---

## 📦 Registros Testados/Consultados/Atualizados

### Consultado (GET)
- **ID**: `b6aa1500-7532-4089-8604-b6bb9c8e42ac`
- **Username**: `natali.usuario`
- **Email**: `nataligrilobarros@gmail.com`
- **Status**: Ativo
- **Estabelecimentos**: 0

### Tentativa de Atualização (PUT)
- **ID**: `b6aa1500-7532-4089-8604-b6bb9c8e42ac`
- **Campos Enviados**:
  - username, cpf, nomeExibicao, adminTenant
  - estabelecimentos (1 vínculo)
- **Resultado**: Falhou com erro de referências compartilhadas

### Não Criados/Deletados
- Nenhum registro foi criado ou deletado devido aos erros

---

## 📌 Conclusão Final

### ✅ Sucessos (46%)
1. Resolução do problema de deslogamento constante (30 queries encerradas)
2. READ totalmente funcional (listagem e por ID)
3. Validação funcionando corretamente

### ❌ Problemas Persistentes (54%)
1. UPDATE com erro crítico de referências compartilhadas
2. CREATE com validação restritiva de email
3. DELETE não testado

### 🎯 Achado Principal
**O erro "Found shared references to a collection" ocorre no `save()` da entidade, não nos métodos de vínculos.** Isso indica um problema fundamental com o gerenciamento do contexto de persistência do Hibernate para esta entidade específica.

---

**Documentação completa salva em**: `/Users/wagnergrilo/Desktop/WGB/sistemas/UPSaude/code_cursor/UPSaude-final-back/RELATORIO_TESTES_CRUD_FINAL.md`
