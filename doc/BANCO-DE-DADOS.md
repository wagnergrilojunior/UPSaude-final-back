# Documentação de Banco de Dados - Integração CNES

## Diagnóstico Atual
O banco de dados atual possui estrutura sólida para estabelecimentos e profissionais, mas carece de campos específicos para integração com o SOA-CNES (S7) e controle de sincronização.

### Tabelas Existentes Analisadas
- **estabelecimentos**: Possui `cnes`, `cnpj`, `nome_fantasia`. Faltam campos de controle de sincronização.
- **enderecos**: Possui `codigo_ibge_municipio`. Estrutura adequada.
- **profissionais_saude**: Possui `cpf`, `codigo_ocupacional` (CBO). **Falta campo `cns`**.
- **medicos**: Possui dados de CRM. **Falta campo `cns`**.

## Modelo de Extensão (Aditivo)

Todas as alterações propostas são **ADITIVAS** (novas colunas ou tabelas) e **OPCIONAIS** (nullable), garantindo zero impacto em funcionalidades existentes.

### 1. Alterações em Tabelas Existentes

#### `estabelecimentos`
Adição de campos para controle de status e data da última sincronização com o CNES.
```sql
ALTER TABLE estabelecimentos 
ADD COLUMN IF NOT EXISTS data_ultima_sincronizacao_cnes TIMESTAMP WITH TIME ZONE NULL,
ADD COLUMN IF NOT EXISTS status_cnes VARCHAR(50) NULL, -- 'ATIVO', 'DESATIVADO', 'DIVERGENTE'
ADD COLUMN IF NOT EXISTS cnes_dados_json JSONB NULL; -- Cache completo do retorno CNES para auditoria
```

#### `profissionais_saude`
Adição do Cartão Nacional de Saúde (CNS).
```sql
ALTER TABLE profissionais_saude
ADD COLUMN IF NOT EXISTS cns VARCHAR(15) NULL;
```

#### `medicos`
Adição do Cartão Nacional de Saúde (CNS).
```sql
ALTER TABLE medicos
ADD COLUMN IF NOT EXISTS cns VARCHAR(15) NULL;
```

### 2. Novas Tabelas (Sincronização e Dados Complementares)

#### `cnes_sync_log`
Registra o histórico de sincronizações para auditoria.
```sql
CREATE TABLE IF NOT EXISTS cnes_sync_log (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    tenant_id UUID REFERENCES tenants(id),
    data_sincronizacao TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    status VARCHAR(20) NOT NULL, -- 'SUCESSO', 'ERRO', 'PARCIAL'
    mensagem TEXT,
    dados_recebidos JSONB, -- O XML/JSON recebido do CNES
    criado_em TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);
```

#### `estabelecimento_leitos` (Espelho do CNES)
Armazena a quantidade de leitos por tipo, sincronizado do CNES.
```sql
CREATE TABLE IF NOT EXISTS estabelecimento_leitos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    tenant_id UUID REFERENCES tenants(id),
    tipo_leito_codigo VARCHAR(10) NOT NULL,
    tipo_leito_descricao VARCHAR(255),
    quantidade_existente INTEGER,
    quantidade_contratada INTEGER,
    quantidade_sus INTEGER,
    data_atualizacao TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    ativo BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    atualizado_em TIMESTAMP WITH TIME ZONE
);
```

#### `estabelecimento_equipamentos` (Espelho do CNES)
Armazena equipamentos disponíveis.
```sql
CREATE TABLE IF NOT EXISTS estabelecimento_equipamentos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    estabelecimento_id UUID REFERENCES estabelecimentos(id),
    tenant_id UUID REFERENCES tenants(id),
    tipo_equipamento_codigo VARCHAR(10) NOT NULL,
    tipo_equipamento_descricao VARCHAR(255),
    quantidade_existente INTEGER,
    quantidade_em_uso INTEGER,
    quantidade_sus INTEGER,
    data_atualizacao TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    ativo BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    atualizado_em TIMESTAMP WITH TIME ZONE
);
```

### Estratégia de Migração
As migrações serão criadas utilizando o Flyway (ou ferramenta padrão do projeto) e aplicadas de forma segura. Nenhuma coluna será renomeada ou removida.
