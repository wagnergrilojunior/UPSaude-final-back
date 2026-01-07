# Glossário CNES - Termos e Definições

## 📖 Termos Técnicos

### A

**Ativação de Leito**
- Processo de disponibilização de um leito para uso.

### C

**CNES - Cadastro Nacional de Estabelecimentos de Saúde**
- Cadastro oficial mantido pelo DATASUS que registra todos os estabelecimentos de saúde do Brasil.
- Código único de 7 dígitos.

**CNS - Cartão Nacional de Saúde**
- Identificação única dos profissionais de saúde no SUS.
- Código de 15 dígitos numéricos.

**Competência**
- Período de referência dos dados, no formato AAAAMM (ano + mês).
- Exemplo: `202501` = Janeiro de 2025.

**CPF - Cadastro de Pessoas Físicas**
- Documento de identificação brasileiro.
- Utilizado para identificar profissionais.

### D

**DATASUS - Departamento de Informática do SUS**
- Órgão do Ministério da Saúde responsável pela gestão de informações do SUS.

### E

**Equipe de Saúde**
- Grupo de profissionais de saúde que atuam juntos.
- Identificada pelo INE.

**Esfera Administrativa**
- Classificação do estabelecimento quanto à administração:
  - **FEDERAL**: Administrado pela União
  - **ESTADUAL**: Administrado pelo Estado
  - **MUNICIPAL**: Administrado pelo Município
  - **PRIVADO**: Administrado por entidade privada

**Estabelecimento de Saúde**
- Unidade física onde são prestados serviços de saúde.
- Cadastrado no CNES com código único.

### I

**INE - Identificador Nacional de Equipe**
- Código único que identifica uma equipe de saúde.
- Formato: 15 caracteres alfanuméricos.

### L

**Leito**
- Unidade de internação em estabelecimento de saúde.
- Pode estar disponível, ocupado, em manutenção ou inativo.

### M

**Multitenancy**
- Arquitetura onde uma única instância da aplicação serve múltiplos clientes (tenants).
- Cada tenant tem seus dados isolados.

### P

**Profissional de Saúde**
- Pessoa física cadastrada no CNES que presta serviços de saúde.
- Identificado por CNS ou CPF.

### S

**Sincronização**
- Processo de atualização de dados locais com dados do CNES.
- Pode ser manual ou automática.

**SOAP - Simple Object Access Protocol**
- Protocolo de comunicação para serviços web.
- Utilizado pelos serviços do CNES.

**Status de Sincronização**
- Estado atual de uma operação de sincronização:
  - **PENDENTE**: Aguardando processamento
  - **PROCESSANDO**: Em execução
  - **SUCESSO**: Concluída com sucesso
  - **ERRO**: Falha na execução

**SUS - Sistema Único de Saúde**
- Sistema público de saúde do Brasil.

### T

**Tenant**
- Cliente ou organização que utiliza o sistema.
- Cada tenant tem seus dados isolados.

**Tipo de Entidade**
- Classificação do tipo de dado sincronizado:
  - **ESTABELECIMENTO**: Estabelecimento de saúde
  - **PROFISSIONAL**: Profissional de saúde
  - **EQUIPE**: Equipe de saúde
  - **VINCULACAO**: Vinculação profissional
  - **EQUIPAMENTO**: Equipamento
  - **LEITO**: Leito

### V

**Vinculação Profissional**
- Relacionamento entre um profissional e um estabelecimento de saúde.
- Indica onde o profissional atua.

### W

**WSDL - Web Services Description Language**
- Linguagem XML para descrever serviços web.
- Define operações, parâmetros e respostas.

**WS-Security**
- Padrão de segurança para serviços web SOAP.
- Utiliza UsernameToken para autenticação.

## 📊 Códigos e Formatos

### Formatos de Códigos

| Código | Formato | Exemplo | Descrição |
|--------|---------|---------|-----------|
| CNES | 7 dígitos | `2530031` | Código do estabelecimento |
| CNS | 15 dígitos | `701009864978597` | Cartão Nacional de Saúde |
| INE | 15 caracteres | `000000000000001` | Identificador Nacional de Equipe |
| CPF | 11 dígitos | `12345678901` | CPF do profissional |
| Competência | AAAAMM | `202501` | Ano e mês |

### Status de Leito

| Código | Nome | Descrição |
|--------|------|-----------|
| 1 | DISPONIVEL | Leito disponível para uso |
| 2 | OCUPADO | Leito em uso |
| 3 | MANUTENCAO | Leito em manutenção |
| 4 | INATIVO | Leito inativo |

### Esfera Administrativa

| Código | Nome | Descrição |
|--------|------|-----------|
| 1 | FEDERAL | Administrado pela União |
| 2 | ESTADUAL | Administrado pelo Estado |
| 3 | MUNICIPAL | Administrado pelo Município |
| 4 | PRIVADO | Administrado por entidade privada |

## 🔗 Relacionamentos

### Hierarquia de Dados

```
DATASUS
  └── CNES
      ├── Estabelecimentos
      │   ├── Profissionais (vinculados)
      │   ├── Equipes
      │   ├── Equipamentos
      │   └── Leitos
      └── Profissionais
          └── Vinculações (estabelecimentos)
```

## 📝 Notas Importantes

1. **CNES é obrigatório**: Todos os estabelecimentos de saúde devem estar cadastrados no CNES.

2. **CNS é obrigatório**: Profissionais do SUS devem possuir CNS.

3. **Competência**: Permite rastrear versões dos dados ao longo do tempo.

4. **Multitenancy**: Garante isolamento de dados entre diferentes clientes.

5. **Sincronização**: Mantém dados locais atualizados com o CNES oficial.

## 🚀 Referências

- [Documentação Oficial DATASUS](./01-documentacao-oficial.md)
- [Referências e Links](./02-referencias-links.md)
- [Normas e Regulamentações](./04-normas-regulamentacoes.md)

