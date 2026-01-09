-- Migração para refatorar equipe_cirurgica
-- Remove campos diretos profissional_id, medico_id, funcao
-- Cria tabelas intermediárias equipe_cirurgica_profissional e equipe_cirurgica_medico

-- 1. Criar tabela equipe_cirurgica_profissional
CREATE TABLE IF NOT EXISTS equipe_cirurgica_profissional (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITH TIME ZONE,
    ativo BOOLEAN NOT NULL DEFAULT true,
    tenant_id UUID NOT NULL,
    estabelecimento_id UUID,
    equipe_cirurgica_id UUID NOT NULL,
    profissional_id UUID NOT NULL,
    funcao VARCHAR(100),
    observacoes TEXT,
    CONSTRAINT fk_equipe_cirurgica_profissional_equipe FOREIGN KEY (equipe_cirurgica_id) REFERENCES equipe_cirurgica(id) ON DELETE CASCADE,
    CONSTRAINT fk_equipe_cirurgica_profissional_profissional FOREIGN KEY (profissional_id) REFERENCES profissionais_saude(id),
    CONSTRAINT fk_equipe_cirurgica_profissional_tenant FOREIGN KEY (tenant_id) REFERENCES tenants(id),
    CONSTRAINT fk_equipe_cirurgica_profissional_estabelecimento FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimentos(id),
    CONSTRAINT uk_equipe_cirurgica_profissional UNIQUE (equipe_cirurgica_id, profissional_id)
);

-- 2. Criar tabela equipe_cirurgica_medico
CREATE TABLE IF NOT EXISTS equipe_cirurgica_medico (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    criado_em TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP WITH TIME ZONE,
    ativo BOOLEAN NOT NULL DEFAULT true,
    tenant_id UUID NOT NULL,
    estabelecimento_id UUID,
    equipe_cirurgica_id UUID NOT NULL,
    medico_id UUID NOT NULL,
    funcao VARCHAR(100),
    observacoes TEXT,
    CONSTRAINT fk_equipe_cirurgica_medico_equipe FOREIGN KEY (equipe_cirurgica_id) REFERENCES equipe_cirurgica(id) ON DELETE CASCADE,
    CONSTRAINT fk_equipe_cirurgica_medico_medico FOREIGN KEY (medico_id) REFERENCES medicos(id),
    CONSTRAINT fk_equipe_cirurgica_medico_tenant FOREIGN KEY (tenant_id) REFERENCES tenants(id),
    CONSTRAINT fk_equipe_cirurgica_medico_estabelecimento FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimentos(id),
    CONSTRAINT uk_equipe_cirurgica_medico UNIQUE (equipe_cirurgica_id, medico_id)
);

-- 3. Criar índices para melhorar performance
CREATE INDEX IF NOT EXISTS idx_equipe_cirurgica_profissional_equipe ON equipe_cirurgica_profissional(equipe_cirurgica_id);
CREATE INDEX IF NOT EXISTS idx_equipe_cirurgica_profissional_profissional ON equipe_cirurgica_profissional(profissional_id);
CREATE INDEX IF NOT EXISTS idx_equipe_cirurgica_medico_equipe ON equipe_cirurgica_medico(equipe_cirurgica_id);
CREATE INDEX IF NOT EXISTS idx_equipe_cirurgica_medico_medico ON equipe_cirurgica_medico(medico_id);

-- 4. Migrar dados existentes (se houver) das colunas antigas para as novas tabelas
-- Migrar profissionais
INSERT INTO equipe_cirurgica_profissional (id, criado_em, atualizado_em, ativo, tenant_id, estabelecimento_id, equipe_cirurgica_id, profissional_id, funcao, observacoes)
SELECT 
    gen_random_uuid(),
    ec.criado_em,
    ec.atualizado_em,
    ec.ativo,
    ec.tenant_id,
    ec.estabelecimento_id,
    ec.id,
    ec.profissional_id,
    ec.funcao,
    ec.observacoes
FROM equipe_cirurgica ec
WHERE ec.profissional_id IS NOT NULL
ON CONFLICT (equipe_cirurgica_id, profissional_id) DO NOTHING;

-- Migrar médicos
INSERT INTO equipe_cirurgica_medico (id, criado_em, atualizado_em, ativo, tenant_id, estabelecimento_id, equipe_cirurgica_id, medico_id, funcao, observacoes)
SELECT 
    gen_random_uuid(),
    ec.criado_em,
    ec.atualizado_em,
    ec.ativo,
    ec.tenant_id,
    ec.estabelecimento_id,
    ec.id,
    ec.medico_id,
    ec.funcao,
    ec.observacoes
FROM equipe_cirurgica ec
WHERE ec.medico_id IS NOT NULL
ON CONFLICT (equipe_cirurgica_id, medico_id) DO NOTHING;

-- 5. Remover constraints NOT NULL dos campos antigos (torná-los opcionais)
ALTER TABLE equipe_cirurgica ALTER COLUMN profissional_id DROP NOT NULL;
ALTER TABLE equipe_cirurgica ALTER COLUMN funcao DROP NOT NULL;

-- 6. Remover foreign keys antigas (opcional, mas recomendado para limpeza)
-- As foreign keys serão removidas automaticamente quando as colunas forem removidas no futuro
-- Por enquanto, apenas tornamos os campos opcionais para manter compatibilidade

