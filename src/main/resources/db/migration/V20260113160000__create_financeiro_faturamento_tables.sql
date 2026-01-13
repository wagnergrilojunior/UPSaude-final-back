-- =========================================================
-- MÓDULOS: FINANCEIRO + FATURAMENTO (MVP completo)
-- Regra: somente criação/enriquecimento (sem DROP/RENAME)
-- =========================================================

-- =========================================================
-- 1) TABELAS BASE (sem tenant): competência financeira
-- =========================================================

CREATE TABLE competencia_financeira (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    codigo VARCHAR(20) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    descricao VARCHAR(500),

    CONSTRAINT uk_competencia_financeira_codigo UNIQUE (codigo)
);

CREATE INDEX idx_competencia_financeira_data ON competencia_financeira (data_inicio, data_fim);

-- =========================================================
-- 2) ENRIQUECER TABELAS EXISTENTES (assistencial)
-- =========================================================

ALTER TABLE agendamentos
    ADD COLUMN competencia_financeira_id UUID REFERENCES competencia_financeira(id);

ALTER TABLE agendamentos
    ADD COLUMN valor_estimado_total NUMERIC(14,2);

ALTER TABLE agendamentos
    ADD COLUMN status_financeiro VARCHAR(30);

CREATE INDEX idx_agendamento_competencia ON agendamentos (competencia_financeira_id);

ALTER TABLE atendimentos
    ADD COLUMN competencia_financeira_id UUID REFERENCES competencia_financeira(id);

CREATE INDEX idx_atendimento_competencia ON atendimentos (competencia_financeira_id);

-- =========================================================
-- 3) TABELA DE PROCEDIMENTOS DO ATENDIMENTO (nova)
-- =========================================================

CREATE TABLE atendimento_procedimento (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    atendimento_id UUID NOT NULL REFERENCES atendimentos(id),
    sigtap_procedimento_id UUID REFERENCES sigtap_procedimento(id),

    quantidade INTEGER NOT NULL DEFAULT 1,
    valor_unitario NUMERIC(14,2),
    valor_total NUMERIC(14,2),

    financiamento_id UUID,
    rubrica_id UUID,
    modalidade_financeira VARCHAR(50),

    cbo_codigo VARCHAR(6),
    cid_principal_codigo VARCHAR(10),
    carater_atendimento VARCHAR(2),
    cnes VARCHAR(7),

    observacoes TEXT
);

CREATE INDEX idx_atendimento_procedimento_atendimento ON atendimento_procedimento (atendimento_id);
CREATE INDEX idx_atendimento_procedimento_procedimento ON atendimento_procedimento (sigtap_procedimento_id);

-- =========================================================
-- 4) PLANO DE CONTAS / CONTAS CONTÁBEIS / CENTRO DE CUSTO
-- =========================================================

CREATE TABLE plano_contas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    nome VARCHAR(255) NOT NULL,
    versao VARCHAR(50) NOT NULL,
    padrao BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT uk_plano_contas_tenant_nome_versao UNIQUE (tenant_id, nome, versao)
);

CREATE INDEX idx_plano_contas_tenant ON plano_contas (tenant_id);
CREATE INDEX idx_plano_contas_padrao ON plano_contas (tenant_id, padrao);

CREATE TABLE conta_contabil (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    plano_contas_id UUID NOT NULL REFERENCES plano_contas(id),
    conta_pai_id UUID REFERENCES conta_contabil(id),

    codigo VARCHAR(50) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    natureza VARCHAR(20) NOT NULL,
    aceita_lancamento BOOLEAN NOT NULL DEFAULT FALSE,
    nivel INTEGER,
    ordem INTEGER,

    CONSTRAINT uk_conta_contabil_plano_codigo UNIQUE (plano_contas_id, codigo)
);

CREATE INDEX idx_conta_contabil_plano ON conta_contabil (plano_contas_id);
CREATE INDEX idx_conta_contabil_pai ON conta_contabil (plano_contas_id, conta_pai_id);

CREATE TABLE centro_custo (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    pai_id UUID REFERENCES centro_custo(id),
    codigo VARCHAR(50) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    ordem INTEGER,

    CONSTRAINT uk_centro_custo_tenant_codigo UNIQUE (tenant_id, codigo)
);

CREATE INDEX idx_centro_custo_pai ON centro_custo (tenant_id, pai_id);

-- =========================================================
-- 5) CONTAS FINANCEIRAS (caixa/banco) + RECORRÊNCIA
-- =========================================================

CREATE TABLE conta_financeira (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    tipo VARCHAR(20) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    moeda VARCHAR(3) NOT NULL DEFAULT 'BRL',
    banco_codigo VARCHAR(10),
    agencia VARCHAR(20),
    numero_conta VARCHAR(50),
    pix_chave VARCHAR(255),

    CONSTRAINT uk_conta_financeira_tenant_tipo_nome UNIQUE (tenant_id, tipo, nome)
);

CREATE INDEX idx_conta_financeira_tipo ON conta_financeira (tenant_id, tipo);

CREATE TABLE recorrencia_financeira (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    tipo VARCHAR(20) NOT NULL,
    periodicidade VARCHAR(20) NOT NULL,
    dia_mes INTEGER,
    dia_semana INTEGER,
    proxima_geracao_em TIMESTAMPTZ
);

CREATE INDEX idx_recorrencia_financeira_tipo ON recorrencia_financeira (tenant_id, tipo);

-- =========================================================
-- 6) PARTE FINANCEIRA (pagador/fornecedor)
-- =========================================================

CREATE TABLE parte_financeira (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    tipo VARCHAR(30) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    documento VARCHAR(20),
    email VARCHAR(255),
    telefone VARCHAR(50),
    referencia_tipo VARCHAR(50),
    referencia_id UUID,

    CONSTRAINT uk_parte_financeira_tenant_tipo_doc UNIQUE (tenant_id, tipo, documento)
);

CREATE INDEX idx_parte_financeira_tipo ON parte_financeira (tenant_id, tipo);

-- =========================================================
-- 7) GUIA AMBULATORIAL (GAA) + FATURAMENTO (Documento/Itens/Glosa)
--    Observação: FK guia.documento_faturamento_id é adicionada depois (ciclo).
-- =========================================================

CREATE TABLE guia_atendimento_ambulatorial (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    competencia_id UUID NOT NULL REFERENCES competencia_financeira(id),
    agendamento_id UUID REFERENCES agendamentos(id),
    atendimento_id UUID REFERENCES atendimentos(id),
    paciente_id UUID NOT NULL REFERENCES pacientes(id),
    documento_faturamento_id UUID,

    numero VARCHAR(100) NOT NULL,
    status VARCHAR(30) NOT NULL,
    emitida_em TIMESTAMPTZ,
    cancelada_em TIMESTAMPTZ,
    cancelada_por UUID,
    observacoes TEXT,

    CONSTRAINT uk_guia_ambulatorial_tenant_comp_num UNIQUE (tenant_id, competencia_id, numero)
);

CREATE INDEX idx_guia_ambulatorial_status ON guia_atendimento_ambulatorial (tenant_id, competencia_id, status);

CREATE TABLE documento_faturamento (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    competencia_id UUID NOT NULL REFERENCES competencia_financeira(id),
    convenio_id UUID REFERENCES convenios(id),
    agendamento_id UUID REFERENCES agendamentos(id),
    atendimento_id UUID REFERENCES atendimentos(id),
    guia_ambulatorial_id UUID REFERENCES guia_atendimento_ambulatorial(id),

    tipo VARCHAR(50) NOT NULL,
    numero VARCHAR(100) NOT NULL,
    serie VARCHAR(20),
    status VARCHAR(30) NOT NULL,
    pagador_tipo VARCHAR(30),
    emitido_em TIMESTAMPTZ,
    cancelado_em TIMESTAMPTZ,
    cancelado_por UUID,
    payload_layout JSONB,

    CONSTRAINT uk_documento_faturamento_tenant_tipo_comp_num UNIQUE (tenant_id, tipo, competencia_id, numero, serie)
);

CREATE INDEX idx_documento_faturamento_status ON documento_faturamento (tenant_id, competencia_id, status);

CREATE TABLE documento_faturamento_item (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    documento_id UUID NOT NULL REFERENCES documento_faturamento(id),
    sigtap_procedimento_id UUID REFERENCES sigtap_procedimento(id),

    quantidade INTEGER NOT NULL DEFAULT 1,
    valor_unitario NUMERIC(14,2),
    valor_total NUMERIC(14,2),

    origem_tipo VARCHAR(50),
    origem_id UUID,
    payload_layout_item JSONB
);

CREATE INDEX idx_documento_faturamento_item_documento ON documento_faturamento_item (documento_id);

CREATE TABLE glosa (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    documento_id UUID NOT NULL REFERENCES documento_faturamento(id),
    item_id UUID REFERENCES documento_faturamento_item(id),

    tipo VARCHAR(20) NOT NULL,
    valor_glosado NUMERIC(14,2) NOT NULL,
    motivo_codigo VARCHAR(50),
    motivo_descricao TEXT,
    status VARCHAR(30) NOT NULL
);

CREATE INDEX idx_glosa_documento ON glosa (documento_id);
CREATE INDEX idx_glosa_status ON glosa (status);

ALTER TABLE guia_atendimento_ambulatorial
    ADD CONSTRAINT fk_guia_documento_faturamento
    FOREIGN KEY (documento_faturamento_id) REFERENCES documento_faturamento(id);

-- =========================================================
-- 8) COMPETÊNCIA POR TENANT (fechamento / integridade)
-- =========================================================

CREATE TABLE competencia_financeira_tenant (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    competencia_id UUID NOT NULL REFERENCES competencia_financeira(id),
    status VARCHAR(20) NOT NULL,
    fechada_em TIMESTAMPTZ,
    fechada_por UUID,
    motivo_fechamento TEXT,
    snapshot_hash VARCHAR(64),

    documento_bpa_fechamento_id UUID REFERENCES documento_faturamento(id),
    hash_movimentacoes VARCHAR(64),
    hash_bpa VARCHAR(64),
    validacao_integridade BOOLEAN,

    CONSTRAINT uk_competencia_tenant_tenant_competencia UNIQUE (tenant_id, competencia_id)
);

CREATE INDEX idx_competencia_tenant_status ON competencia_financeira_tenant (tenant_id, status);

-- =========================================================
-- 9) ORÇAMENTO / CRÉDITOS
-- =========================================================

CREATE TABLE orcamento_competencia (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    competencia_id UUID NOT NULL REFERENCES competencia_financeira(id),
    saldo_anterior NUMERIC(14,2),
    creditos NUMERIC(14,2),
    reservas_ativas NUMERIC(14,2),
    consumos NUMERIC(14,2),
    estornos NUMERIC(14,2),
    despesas_admin NUMERIC(14,2),
    saldo_final NUMERIC(14,2),

    CONSTRAINT uk_orcamento_competencia_tenant_competencia UNIQUE (tenant_id, competencia_id)
);

CREATE INDEX idx_orcamento_competencia_competencia ON orcamento_competencia (tenant_id, competencia_id);

CREATE TABLE credito_orcamentario (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    competencia_id UUID NOT NULL REFERENCES competencia_financeira(id),
    valor NUMERIC(14,2) NOT NULL,
    fonte VARCHAR(50) NOT NULL,
    documento_referencia VARCHAR(255),
    data_credito DATE NOT NULL,
    idempotency_key VARCHAR(255) NOT NULL,

    CONSTRAINT uk_credito_orcamentario_tenant_idempotency UNIQUE (tenant_id, idempotency_key)
);

CREATE INDEX idx_credito_orcamentario_competencia ON credito_orcamentario (tenant_id, competencia_id);

-- =========================================================
-- 10) RENEGOCIAÇÃO (estrutura base)
-- =========================================================

CREATE TABLE renegociacao_receber (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    data DATE NOT NULL,
    motivo VARCHAR(255),
    observacao TEXT
);

CREATE INDEX idx_renegociacao_receber_data ON renegociacao_receber (tenant_id, data);

-- =========================================================
-- 11) CONTAS A RECEBER / A PAGAR
-- =========================================================

CREATE TABLE titulo_receber (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    documento_faturamento_id UUID REFERENCES documento_faturamento(id),
    pagador_id UUID NOT NULL REFERENCES parte_financeira(id),
    conta_contabil_receita_id UUID NOT NULL REFERENCES conta_contabil(id),
    centro_custo_id UUID REFERENCES centro_custo(id),

    numero VARCHAR(100) NOT NULL,
    parcela INTEGER,
    total_parcelas INTEGER,
    valor_original NUMERIC(14,2) NOT NULL,
    desconto NUMERIC(14,2),
    juros NUMERIC(14,2),
    multa NUMERIC(14,2),
    valor_aberto NUMERIC(14,2) NOT NULL,
    data_emissao DATE NOT NULL,
    data_vencimento DATE NOT NULL,
    status VARCHAR(30) NOT NULL,
    idempotency_key VARCHAR(255) NOT NULL,

    CONSTRAINT uk_titulo_receber_tenant_idempotency UNIQUE (tenant_id, idempotency_key)
);

CREATE INDEX idx_titulo_receber_status_vencimento ON titulo_receber (tenant_id, status, data_vencimento);

CREATE TABLE titulo_pagar (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    fornecedor_id UUID NOT NULL REFERENCES parte_financeira(id),
    conta_contabil_despesa_id UUID NOT NULL REFERENCES conta_contabil(id),
    centro_custo_id UUID NOT NULL REFERENCES centro_custo(id),
    recorrencia_financeira_id UUID REFERENCES recorrencia_financeira(id),

    numero_documento VARCHAR(100) NOT NULL,
    valor_original NUMERIC(14,2) NOT NULL,
    valor_aberto NUMERIC(14,2) NOT NULL,
    data_emissao DATE NOT NULL,
    data_vencimento DATE NOT NULL,
    status VARCHAR(30) NOT NULL,
    idempotency_key VARCHAR(255) NOT NULL,

    CONSTRAINT uk_titulo_pagar_tenant_idempotency UNIQUE (tenant_id, idempotency_key)
);

CREATE INDEX idx_titulo_pagar_status_vencimento ON titulo_pagar (tenant_id, status, data_vencimento);

-- =========================================================
-- 12) CONCILIAÇÃO BANCÁRIA (base) + LANÇAMENTOS
-- =========================================================

CREATE TABLE conciliacao_bancaria (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    conta_financeira_id UUID NOT NULL REFERENCES conta_financeira(id),
    periodo_inicio DATE NOT NULL,
    periodo_fim DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    fechada_em TIMESTAMPTZ
);

CREATE INDEX idx_conciliacao_bancaria_conta ON conciliacao_bancaria (conta_financeira_id);

CREATE TABLE lancamento_financeiro (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    competencia_id UUID NOT NULL REFERENCES competencia_financeira(id),
    documento_faturamento_id UUID REFERENCES documento_faturamento(id),
    titulo_receber_id UUID REFERENCES titulo_receber(id),
    titulo_pagar_id UUID REFERENCES titulo_pagar(id),
    conciliacao_id UUID REFERENCES conciliacao_bancaria(id),

    origem_tipo VARCHAR(50) NOT NULL,
    origem_id UUID,
    status VARCHAR(30) NOT NULL,
    data_evento TIMESTAMPTZ NOT NULL,
    descricao VARCHAR(500),
    idempotency_key VARCHAR(255) NOT NULL,
    grupo_lancamento UUID,
    travado BOOLEAN NOT NULL DEFAULT FALSE,
    travado_em TIMESTAMPTZ,

    motivo_estorno VARCHAR(30),
    referencia_estorno_tipo VARCHAR(50),
    referencia_estorno_id UUID,

    prestador_id UUID,
    prestador_tipo VARCHAR(30),

    CONSTRAINT uk_lancamento_financeiro_tenant_idempotency UNIQUE (tenant_id, idempotency_key)
);

CREATE INDEX idx_lancamento_financeiro_competencia ON lancamento_financeiro (tenant_id, competencia_id, data_evento);
CREATE INDEX idx_lancamento_financeiro_origem ON lancamento_financeiro (tenant_id, origem_tipo, origem_id);
CREATE INDEX idx_lancamento_financeiro_prestador ON lancamento_financeiro (tenant_id, prestador_tipo, prestador_id);
CREATE INDEX idx_lancamento_financeiro_estorno ON lancamento_financeiro (tenant_id, status, motivo_estorno);

CREATE TABLE lancamento_financeiro_item (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    lancamento_id UUID NOT NULL REFERENCES lancamento_financeiro(id),
    conta_contabil_id UUID NOT NULL REFERENCES conta_contabil(id),
    centro_custo_id UUID REFERENCES centro_custo(id),

    tipo_partida VARCHAR(20) NOT NULL,
    valor NUMERIC(14,2) NOT NULL,
    historico TEXT
);

CREATE INDEX idx_lancamento_item_lancamento ON lancamento_financeiro_item (lancamento_id);
CREATE INDEX idx_lancamento_item_conta ON lancamento_financeiro_item (conta_contabil_id);

-- =========================================================
-- 13) TRANSFERÊNCIA / MOVIMENTAÇÃO DE CONTA (ciclo com baixa/pagamento)
-- =========================================================

CREATE TABLE transferencia_entre_contas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    conta_origem_id UUID NOT NULL REFERENCES conta_financeira(id),
    conta_destino_id UUID NOT NULL REFERENCES conta_financeira(id),
    valor NUMERIC(14,2) NOT NULL,
    data TIMESTAMPTZ NOT NULL,
    status VARCHAR(30) NOT NULL,
    idempotency_key VARCHAR(255) NOT NULL,

    CONSTRAINT uk_transferencia_tenant_idempotency UNIQUE (tenant_id, idempotency_key)
);

CREATE INDEX idx_transferencia_contas ON transferencia_entre_contas (conta_origem_id, conta_destino_id);

CREATE TABLE movimentacao_conta (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    conta_financeira_id UUID NOT NULL REFERENCES conta_financeira(id),
    baixa_receber_id UUID,
    pagamento_pagar_id UUID,
    transferencia_id UUID REFERENCES transferencia_entre_contas(id),
    lancamento_financeiro_id UUID REFERENCES lancamento_financeiro(id),

    tipo VARCHAR(20) NOT NULL,
    valor NUMERIC(14,2) NOT NULL,
    data_movimento TIMESTAMPTZ NOT NULL,
    status VARCHAR(30) NOT NULL,
    idempotency_key VARCHAR(255) NOT NULL,

    CONSTRAINT uk_movimentacao_conta_conta_idempotency UNIQUE (conta_financeira_id, idempotency_key)
);

CREATE INDEX idx_movimentacao_conta_data ON movimentacao_conta (conta_financeira_id, data_movimento);

CREATE TABLE baixa_receber (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    titulo_receber_id UUID NOT NULL REFERENCES titulo_receber(id),
    conta_financeira_id UUID NOT NULL REFERENCES conta_financeira(id),
    movimentacao_conta_id UUID REFERENCES movimentacao_conta(id),
    lancamento_financeiro_id UUID REFERENCES lancamento_financeiro(id),

    valor_pago NUMERIC(14,2) NOT NULL,
    data_pagamento DATE NOT NULL,
    meio_pagamento VARCHAR(30),
    status VARCHAR(30) NOT NULL,
    observacao TEXT,
    idempotency_key VARCHAR(255) NOT NULL,

    CONSTRAINT uk_baixa_receber_idempotency UNIQUE (tenant_id, idempotency_key)
);

CREATE INDEX idx_baixa_receber_titulo ON baixa_receber (titulo_receber_id);

CREATE TABLE pagamento_pagar (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    titulo_pagar_id UUID NOT NULL REFERENCES titulo_pagar(id),
    conta_financeira_id UUID NOT NULL REFERENCES conta_financeira(id),
    movimentacao_conta_id UUID REFERENCES movimentacao_conta(id),
    lancamento_financeiro_id UUID REFERENCES lancamento_financeiro(id),

    valor_pago NUMERIC(14,2) NOT NULL,
    data_pagamento DATE NOT NULL,
    meio_pagamento VARCHAR(30),
    status VARCHAR(30) NOT NULL,
    idempotency_key VARCHAR(255) NOT NULL,

    CONSTRAINT uk_pagamento_pagar_idempotency UNIQUE (tenant_id, idempotency_key)
);

CREATE INDEX idx_pagamento_pagar_titulo ON pagamento_pagar (titulo_pagar_id);

ALTER TABLE movimentacao_conta
    ADD CONSTRAINT fk_movimentacao_baixa_receber
    FOREIGN KEY (baixa_receber_id) REFERENCES baixa_receber(id);

ALTER TABLE movimentacao_conta
    ADD CONSTRAINT fk_movimentacao_pagamento_pagar
    FOREIGN KEY (pagamento_pagar_id) REFERENCES pagamento_pagar(id);

-- =========================================================
-- 14) EXTRATO IMPORTADO + ITENS DE CONCILIAÇÃO
-- =========================================================

CREATE TABLE extrato_bancario_importado (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    conta_financeira_id UUID NOT NULL REFERENCES conta_financeira(id),
    hash_linha VARCHAR(64) NOT NULL,
    descricao VARCHAR(500),
    valor NUMERIC(14,2) NOT NULL,
    data DATE NOT NULL,
    documento VARCHAR(100),
    saldo_apos NUMERIC(14,2),
    status_conciliacao VARCHAR(30) NOT NULL DEFAULT 'NAO_CONCILIADO',

    CONSTRAINT uk_extrato_bancario_hash UNIQUE (hash_linha)
);

CREATE INDEX idx_extrato_bancario_conta_data ON extrato_bancario_importado (conta_financeira_id, data);

CREATE TABLE conciliacao_item (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    conciliacao_id UUID NOT NULL REFERENCES conciliacao_bancaria(id),
    extrato_importado_id UUID REFERENCES extrato_bancario_importado(id),
    movimentacao_conta_id UUID REFERENCES movimentacao_conta(id),

    tipo_match VARCHAR(20) NOT NULL,
    diferenca NUMERIC(14,2)
);

CREATE INDEX idx_conciliacao_item_conciliacao ON conciliacao_item (conciliacao_id);

-- =========================================================
-- 15) ESTORNOS / RESERVAS (assistencial)
-- =========================================================

CREATE TABLE estorno_financeiro (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    competencia_id UUID NOT NULL REFERENCES competencia_financeira(id),
    agendamento_id UUID REFERENCES agendamentos(id),
    atendimento_id UUID REFERENCES atendimentos(id),
    guia_ambulatorial_id UUID REFERENCES guia_atendimento_ambulatorial(id),
    paciente_id UUID NOT NULL REFERENCES pacientes(id),
    atendimento_procedimento_id UUID REFERENCES atendimento_procedimento(id),
    lancamento_financeiro_origem_id UUID REFERENCES lancamento_financeiro(id),
    lancamento_financeiro_estorno_id UUID REFERENCES lancamento_financeiro(id),

    motivo VARCHAR(30) NOT NULL,
    valor_estornado NUMERIC(14,2) NOT NULL,
    procedimento_codigo VARCHAR(20),
    procedimento_nome VARCHAR(255),
    data_estorno TIMESTAMPTZ NOT NULL,
    observacoes TEXT
);

CREATE INDEX idx_estorno_financeiro_competencia_motivo ON estorno_financeiro (tenant_id, competencia_id, motivo);
CREATE INDEX idx_estorno_financeiro_data ON estorno_financeiro (tenant_id, data_estorno);

CREATE TABLE reserva_orcamentaria_assistencial (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    competencia_id UUID NOT NULL REFERENCES competencia_financeira(id),
    agendamento_id UUID REFERENCES agendamentos(id),
    documento_faturamento_id UUID REFERENCES documento_faturamento(id),
    guia_ambulatorial_id UUID REFERENCES guia_atendimento_ambulatorial(id),

    prestador_id UUID,
    prestador_tipo VARCHAR(30),

    valor_reservado_total NUMERIC(14,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    grupo_reserva UUID,
    idempotency_key VARCHAR(255) NOT NULL
);

CREATE INDEX idx_reserva_orcamentaria_competencia ON reserva_orcamentaria_assistencial (tenant_id, competencia_id, status);
CREATE INDEX idx_reserva_orcamentaria_agendamento ON reserva_orcamentaria_assistencial (agendamento_id);
CREATE INDEX idx_reserva_orcamentaria_prestador ON reserva_orcamentaria_assistencial (tenant_id, prestador_tipo, prestador_id);

-- =========================================================
-- 16) AUDITORIA / LOGS
-- =========================================================

CREATE TABLE log_financeiro (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMPTZ,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    entidade_tipo VARCHAR(50) NOT NULL,
    entidade_id UUID NOT NULL,
    acao VARCHAR(50) NOT NULL,
    usuario_id UUID,
    correlation_id VARCHAR(100),
    payload_antes JSONB,
    payload_depois JSONB,
    ip VARCHAR(50),
    user_agent VARCHAR(500)
);

CREATE INDEX idx_log_financeiro_entidade ON log_financeiro (tenant_id, entidade_tipo, entidade_id, criado_em);
CREATE INDEX idx_log_financeiro_data ON log_financeiro (tenant_id, criado_em);

