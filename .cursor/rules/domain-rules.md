version: 1
rules:

  ###########################################################################
  #  1) PACIENTE — REGRAS DE NEGÓCIO E-CADASTRO / e-SUS / CNES
  ###########################################################################
  - id: dominio-paciente
    pattern: "src/main/java/**/Paciente*.java"
    description: >
      Regras oficiais de cadastro de pacientes, seguindo e-SUS APS, SISAB,
      CNES, IBGE e normas do Ministério da Saúde.
    enforce:
      - "CPF deve ter 11 dígitos numéricos — validar com regex."
      - "CNS deve ter exatamente 15 dígitos — validar com regex."
      - "Código IBGE deve ter 7 dígitos sempre."
      - "Data de óbito só pode existir se StatusPaciente = OBITO."
      - "Nome completo é obrigatório."
      - "Nome social é opcional mas limitado a 255 caracteres."
      - "Sempre salvar raça/cor (IBGE) quando disponível."
      - "Paciente deve possuir pelo menos 1 endereço."
      - "Dados clínicos, sociodemográficos, LGPD e integração devem ser sincronizados no cascade."
      - "Responsável legal obrigatório para menores de idade (< 18 anos)."
      - "Telefone deve ter 10 ou 11 dígitos."
      - "CNS desatualizado deve marcar campo cnsValidado = false."
      - "Paciente em situação de rua deve obrigatoriamente ter campo municipioNascimentoIbge preenchido."
      - "Cartão SUS ativo deve ser true por padrão."
      - "Deve verificar duplicidade por: CPF, CNS ou email."

  ###########################################################################
  #  2) ENDEREÇO — CEP, IBGE, LOGRADOURO, PADRÕES NACIONAIS
  ###########################################################################
  - id: dominio-endereco
    pattern: "src/main/java/**/Endereco*.java"
    description: >
      Regras de endereço conforme padrões nacionais: CEP, Bairro, Município,
      IBGE, Zona Domiciliar, Tipo de Logradouro.
    enforce:
      - "CEP deve ter 8 dígitos numéricos."
      - "Código IBGE do município deve ter 7 dígitos."
      - "TipoLogradouroEnum deve ser obrigatório."
      - "Zona de domicílio (urbana/rural) é obrigatória."
      - "Endereços devem ser únicos via constraint (rua + número + bairro + município)."
      - "Sem EAGER."

  ###########################################################################
  #  3) DOENÇAS — CID-10, DIAGNÓSTICO, ACOMPANHAMENTO
  ###########################################################################
  - id: dominio-doencas
    pattern: "src/main/java/**/Doencas*.java"
    description: >
      Regras para registro de doenças, com base na classificação CID-10,
      diagnóstico, datas e acompanhamento.
    enforce:
      - "CID-10 deve estar no formato X00.0 ou X000."
      - "Data de diagnóstico não pode ser futura."
      - "Obrigatório informar se a doença é crônica."
      - "Obrigatório informar profissional responsável pelo diagnóstico."
      - "Relacionamento deve ser sempre bidirecional com paciente."
      - "Doença não pode existir sem paciente associado."

  ###########################################################################
  #  4) ALERGIAS — ALERTAS CRÍTICOS DE PRONTUÁRIO
  ###########################################################################
  - id: dominio-alergias
    pattern: "src/main/java/**/Alergia*.java"
    description: >
      Regras de alergias, essenciais para segurança do paciente.
    enforce:
      - "Descrição da alergia é obrigatória."
      - "Severidade deve ser classificada (LEVE, MODERADA, GRAVE)."
      - "Histórico de reação deve ser opcional mas recomendado."
      - "Alergia deve gerar alerta automático no prontuário."
      - "Relacionamento com paciente deve ser obrigatório."
      - "Remoção deve ser cascade com orphanRemoval."

  ###########################################################################
  #  5) MEDICAÇÕES — MEDICAMENTO CONTÍNUO / POSOLOGIA
  ###########################################################################
  - id: dominio-medicacoes
    pattern: "src/main/java/**/Medicacao*.java"
    description: >
      Regras de medicações contínuas, essenciais para prontuário clínico.
    enforce:
      - "Nome da medicação é obrigatório."
      - "Dose é obrigatória (ex.: 500mg)."
      - "Frequência é obrigatória (ex.: 12/12h)."
      - "Via de administração é obrigatória."
      - "Data de início deve ser <= hoje."
      - "Data de término, quando existir, deve ser >= início."
      - "Relacionamento com paciente obrigatório."

  ###########################################################################
  #  6) TRIAGEM — SINAIS VITAIS E RISCOS
  ###########################################################################
  - id: dominio-triagem
    pattern: "src/main/java/**/Triagem*.java"
    description: >
      Regras de triagem conforme protocolos de enfermagem.
    enforce:
      - "Pressão arterial deve conter sistólica e diastólica."
      - "Classificação de risco deve seguir CORES: VERMELHO, AMARELO, VERDE, AZUL."
      - "Temperatura corporal obrigatória."
      - "Pulso obrigatório."
      - "Paciente em risco deve gerar alerta em tela."
      - "Idoso (> 60 anos) deve gerar atenção especial."

  ###########################################################################
  #  7) PRONTUÁRIO — EVOLUÇÃO, ANAMNESE, PRESCRIÇÃO
  ###########################################################################
  - id: dominio-prontuario
    pattern: "src/main/java/**/Prontuario*.java"
    description: >
      Regras estruturais do prontuário eletrônico clínico.
    enforce:
      - "Registrar data/hora da evolução."
      - "Profissional deve ser identificado (carimbo digital)."
      - "Prescrição deve conter posologia completa."
      - "Anamnese precisa ter motivo da consulta."
      - "Histórico clínico deve exibir alergias e doenças automaticamente."
      - "Nenhum campo pode ser sobrescrito — apenas versões (audit trail)."

  ###########################################################################
  #  8) PROFISSIONAIS — CREMESP/COREN, VÍNCULOS E PERFIS
  ###########################################################################
  - id: dominio-profissionais
    pattern: "src/main/java/**/Medico*.java"
    description: >
      Regras específicas para médicos, enfermeiros e equipes.
    enforce:
      - "CRM ou COREN é obrigatório e validado."
      - "Profissional deve estar vinculado a uma unidade CNES."
      - "Profissional só pode atender pacientes do mesmo tenant."
      - "Especialidades devem ser padronizadas (CID / Tabela SUS)."

  ###########################################################################
  #  9) LGPD — DADOS SENSÍVEIS (PACIENTE)
  ###########################################################################
  - id: dominio-lgpd
    pattern: "**/*.java"
    description: >
      Regras LGPD obrigatórias para manipulação de dados de saúde.
    enforce:
      - "Criptografar nome, CPF, CNS e endereço do devedor quando aplicável."
      - "Logs nunca podem registrar dados pessoais do paciente."
      - "Responses nunca podem expor informações sensíveis."
      - "Consentimento LGPD deve ser checado antes de qualquer operação clínica."
      - "Somente perfis autorizados podem acessar prontuário (RBAC)."

  ###########################################################################
  #  10) SISTEMAS DO GOVERNO — RNDS, e-SUS, CNES
  ###########################################################################
  - id: dominio-integracoes
    pattern: "**/*.java"
    description: >
      Regras obrigatórias para integração com sistemas oficiais do Ministério da Saúde.
    enforce:
      - "CNES da unidade deve existir antes de enviar dados para e-SUS."
      - "Paciente deve ter CNS válido antes de sincronizar com RNDS."
      - "Todo envio ao e-SUS deve estar em lote + retry automático."
      - "Salvar protocolo de envio e retornar em API."
      - "Falhas devem ser classificadas como: VALIDAÇÃO, COMUNICAÇÃO, AUTENTICAÇÃO."
