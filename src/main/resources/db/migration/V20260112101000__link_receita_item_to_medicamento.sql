-- Migration para vincular ReceitaItem ao novo catálogo de medicamentos
-- Adição de campos para integração OBM/RNDS

ALTER TABLE receita_itens 
ADD COLUMN medicamento_id UUID REFERENCES medicamentos(id),
ADD COLUMN via_administracao_id UUID REFERENCES vias_administracao(id),
ADD COLUMN unidade_medida_id UUID REFERENCES unidades_medida(id);

-- Tornar sigtap_procedimento_id opcional (pode ser o novo catálogo ou o antigo)
ALTER TABLE receita_itens ALTER COLUMN sigtap_procedimento_id DROP NOT NULL;

-- Índices para os novos campos
CREATE INDEX idx_receita_itens_medicamento ON receita_itens(medicamento_id);
CREATE INDEX idx_receita_itens_via ON receita_itens(via_administracao_id);
CREATE INDEX idx_receita_itens_unidade ON receita_itens(unidade_medida_id);
