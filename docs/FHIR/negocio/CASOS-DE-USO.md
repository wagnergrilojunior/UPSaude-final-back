# 📊 Casos de Uso - Integração FHIR

## 1. Módulo de Vacinação

### UC-VAC-01: Registrar Aplicação de Vacina

**Ator:** Profissional de Saúde (Enfermeiro, Técnico)

**Pré-condições:**
- Paciente cadastrado no sistema
- Catálogo de vacinas sincronizado com FHIR
- Lote de vacina cadastrado (opcional)

**Fluxo Principal:**
1. Profissional acessa tela de vacinação
2. Busca paciente por nome, CPF ou CNS
3. Sistema exibe carteira de vacinação atual
4. Profissional seleciona vacina (autocomplete com dados FHIR)
5. Seleciona tipo de dose (1ª, 2ª, Reforço)
6. Informa lote ou seleciona do estoque
7. Seleciona local de aplicação
8. Confirma aplicação
9. Sistema registra e atualiza carteira de vacinação

**Pós-condições:**
- Aplicação registrada no histórico do paciente
- Estoque atualizado (se controlado)
- Carteira de vacinação atualizada

---

### UC-VAC-02: Consultar Carteira de Vacinação

**Ator:** Profissional de Saúde, Paciente (autoatendimento)

**Fluxo Principal:**
1. Buscar paciente
2. Sistema consulta todas as aplicações
3. Agrupa por vacina
4. Calcula status do esquema vacinal
5. Exibe vacinas pendentes por idade/protocolo

---

### UC-VAC-03: Registrar Reação Adversa

**Ator:** Profissional de Saúde

**Fluxo Principal:**
1. Selecionar aplicação de vacina
2. Clicar em "Registrar Reação"
3. Buscar reação no catálogo MedDRA
4. Informar data, criticidade, tratamento
5. Salvar registro
6. Sistema alerta para futuras aplicações

---

### UC-VAC-04: Sincronizar Catálogo de Vacinas

**Ator:** Administrador do Sistema

**Fluxo Principal:**
1. Acessar configurações de integração
2. Clicar em "Sincronizar Vacinas"
3. Sistema consulta FHIR `BRImunobiologico`
4. Atualiza tabela local (insert/update)
5. Exibe relatório de sincronização

---

## 2. Módulo de Diagnósticos

### UC-DIAG-01: Registrar Diagnóstico em Atendimento

**Ator:** Médico

**Fluxo Principal:**
1. Médico está em tela de atendimento
2. Busca diagnóstico por código ou termo
3. Sistema busca no CID-10 local (sincronizado)
4. Médico seleciona diagnóstico(s)
5. Define categoria (principal/secundário)
6. Marca se é crônico
7. Salva diagnósticos

---

### UC-DIAG-02: Consultar Histórico de Diagnósticos

**Ator:** Profissional de Saúde

**Fluxo Principal:**
1. Acessar prontuário do paciente
2. Ir para aba "Diagnósticos"
3. Sistema lista todos os diagnósticos
4. Filtrar por período, status, tipo
5. Visualizar linha do tempo

---

## 3. Módulo de Alergias

### UC-ALERGIA-01: Cadastrar Alergia do Paciente

**Ator:** Profissional de Saúde

**Fluxo Principal:**
1. Acessar cadastro do paciente
2. Ir para aba "Alergias"
3. Clicar em "Adicionar Alergia"
4. Buscar alérgeno no catálogo
5. Definir categoria (medicamento, alimento, ambiente)
6. Definir criticidade (baixa, alta)
7. Informar reações observadas
8. Salvar

**Pós-condições:**
- Alergia visível em todo o sistema
- Alertas em prescrições e vacinação

---

### UC-ALERGIA-02: Alerta de Alergia em Prescrição

**Ator:** Médico

**Fluxo Principal:**
1. Médico está prescrevendo medicamento
2. Sistema consulta alergias do paciente
3. Se medicamento relacionado à alergia:
   - Exibe alerta em vermelho
   - Solicita confirmação para prosseguir
4. Médico confirma ou altera prescrição

---

## 4. Módulo de Profissionais

### UC-PROF-01: Cadastrar Profissional com CBO

**Ator:** Administrador

**Fluxo Principal:**
1. Acessar cadastro de profissionais
2. Clicar em "Novo Profissional"
3. Informar dados pessoais
4. Buscar CBO (autocomplete FHIR)
5. Selecionar ocupação
6. Informar conselho (CRM, COREN, etc.)
7. Informar número de registro
8. Salvar

---

### UC-PROF-02: Validar Registro em Conselho

**Ator:** Sistema (automático)

**Fluxo Principal:**
1. Ao cadastrar profissional
2. Sistema identifica conselho e UF
3. Formata identificador conforme NamingSystem FHIR
4. Valida formato do número
5. (Futuro) Consulta API do conselho

---

## 5. Módulo de Procedimentos

### UC-PROC-01: Solicitar Procedimento com Código SUS

**Ator:** Médico

**Fluxo Principal:**
1. Em tela de atendimento
2. Clicar em "Solicitar Procedimento"
3. Buscar por código ou descrição
4. Sistema busca na Tabela SUS local
5. Médico seleciona procedimento
6. Informa quantidade e justificativa
7. Salva solicitação

---

### UC-PROC-02: Faturar Procedimento para Convênio

**Ator:** Faturista

**Fluxo Principal:**
1. Acessar módulo de faturamento
2. Selecionar procedimentos realizados
3. Para cada procedimento:
   - Sistema busca código TUSS correspondente
   - Aplica valores do convênio
4. Gerar guia de faturamento
