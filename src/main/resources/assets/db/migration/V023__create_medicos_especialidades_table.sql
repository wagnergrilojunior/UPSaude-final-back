-- =====================================================
-- MIGRATION: Tabela de Relacionamento Médicos e Especialidades (CBO)
-- =====================================================
-- Objetivo: Criar tabela de relacionamento ManyToMany entre médicos e especialidades (CBO)
-- Autor: UPSaúde
-- =====================================================

-- =====================================================
-- MIGRATION: Tabela de Relacionamento Médicos e Especialidades (CBO)
-- =====================================================
-- Objetivo: Criar tabela de relacionamento ManyToMany entre médicos e especialidades (CBO)
-- Nota: A tabela já existe no banco com a coluna especialidade_id
-- Esta migration garante que a estrutura está correta
-- =====================================================

-- A tabela já existe, apenas garantimos que os índices e constraints estão corretos
CREATE UNIQUE INDEX IF NOT EXISTS uk_medico_especialidade
    ON public.medicos_especialidades (medico_id, especialidade_id);

CREATE INDEX IF NOT EXISTS idx_medico_especialidade_medico
    ON public.medicos_especialidades (medico_id);

CREATE INDEX IF NOT EXISTS idx_medico_especialidade_ocupacao
    ON public.medicos_especialidades (especialidade_id);

