# Documentação Oficial - Integração CNES

## 📚 Fontes Oficiais

### DATASUS - Ministério da Saúde

A integração CNES utiliza os serviços oficiais do **DATASUS (Departamento de Informática do SUS)** do Ministério da Saúde.

## 🔗 Links Oficiais

### 1. Portal DATASUS

**URL**: https://datasus.saude.gov.br/

**Descrição**: Portal principal do DATASUS com informações sobre sistemas, dados e serviços.

### 2. SOA-CNES

**URL**: https://servicos.saude.gov.br/cnes

**Descrição**: Serviços Web Services (SOAP) do CNES disponibilizados pelo DATASUS.

### 3. Documentação Técnica

**URL**: https://servicos.saude.gov.br/cnes/docs/

**Descrição**: Documentação técnica dos serviços SOAP do CNES.

## 📋 Serviços Disponíveis

### 1. CnesService (v1r0)

**WSDL**: https://servicos.saude.gov.br/cnes/CnesService/v1r0?wsdl

**Operações**:
- `consultarEstabelecimentoSaude`
- `consultarEstabelecimentoSaudePorMunicipio`
- `consultarDadosComplementaresEstabelecimentoSaude`

### 2. EstabelecimentoSaudeService

**v1r0 WSDL**: https://servicos.saude.gov.br/cnes/EstabelecimentoSaudeService/v1r0?wsdl

**v2r0 WSDL**: https://servicos.saude.gov.br/cnes/EstabelecimentoSaudeService/v2r0?wsdl

### 3. ProfissionalSaudeService (v1r0)

**WSDL**: https://servicos.saude.gov.br/cnes/ProfissionalSaudeService/v1r0?wsdl

**Operações**:
- `consultarProfissionalSaude`
- `consultarProfissionaisSaude`

### 4. EquipeService (v1r0)

**WSDL**: https://servicos.saude.gov.br/cnes/EquipeService/v1r0?wsdl

**Operações**:
- `pesquisarEquipe`
- `detalharEquipe`

### 5. EquipamentoService (v1r0)

**WSDL**: https://servicos.saude.gov.br/cnes/EquipamentoService/v1r0?wsdl

**Operações**:
- `consultarEquipamentos`

### 6. LeitoService (v1r0)

**WSDL**: https://servicos.saude.gov.br/cnes/LeitoService/v1r0?wsdl

**Operações**:
- `consultarLeitosCNES`

### 7. VinculacaoProfissionalService (v1r0)

**WSDL**: https://servicos.saude.gov.br/cnes/VinculacaoProfissionalService/v1r0?wsdl

## 🔐 Credenciais de Acesso

### Credenciais Públicas

O DATASUS disponibiliza credenciais públicas para acesso aos serviços:

```
Username: CNES.PUBLICO
Password: cnes#2015public
```

**⚠️ Nota**: Estas são credenciais públicas e não requerem cadastro prévio.

## 📖 Normas e Regulamentações

### Portarias e Normas

1. **Portaria GM/MS nº 1.101/2002**: Estabelece o CNES
2. **Portaria GM/MS nº 2.048/2002**: Regulamenta o CNES
3. **Portaria GM/MS nº 1.646/2015**: Atualiza o CNES

### Links para Normas

- [Legislação CNES](https://bvsms.saude.gov.br/bvs/saudelegis/gm/2002/prt1101_12_06_2002.html)
- [Normas Técnicas](https://datasus.saude.gov.br/normas-tecnicas/)

## 📊 Estrutura de Dados Oficial

### CNES - Cadastro Nacional de Estabelecimentos de Saúde

O CNES é o cadastro oficial de todos os estabelecimentos de saúde do Brasil, mantido pelo DATASUS.

**Características**:
- Código único de 7 dígitos
- Atualização contínua
- Dados públicos e oficiais
- Integração com outros sistemas do SUS

### CNS - Cartão Nacional de Saúde

Identificação única dos profissionais de saúde no SUS.

**Características**:
- Código de 15 dígitos
- Vinculado ao CPF
- Obrigatório para profissionais do SUS

### INE - Identificador Nacional de Equipe

Identificação única de equipes de saúde.

**Características**:
- Código de 15 caracteres alfanuméricos
- Vinculado ao estabelecimento
- Identifica equipes de atenção básica

## 🔄 Atualizações e Versões

### Versionamento de Serviços

Os serviços SOAP do CNES seguem versionamento:

- **v1r0**: Versão 1, Release 0
- **v2r0**: Versão 2, Release 0

### Frequência de Atualização

- **Estabelecimentos**: Atualização contínua
- **Profissionais**: Atualização contínua
- **Equipes**: Atualização mensal
- **Leitos**: Atualização diária

## 📞 Suporte Oficial

### Canais de Suporte DATASUS

1. **Central de Atendimento**: 136
2. **Email**: cnes@saude.gov.br
3. **Portal**: https://datasus.saude.gov.br/atendimento/

### Documentação de Suporte

- [FAQ CNES](https://datasus.saude.gov.br/faq/)
- [Fóruns Técnicos](https://datasus.saude.gov.br/forums/)
- [Tutoriais](https://datasus.saude.gov.br/tutoriais/)

## 🔍 Referências Técnicas

### Especificações

- **WSDL 1.1**: Especificação de serviços web
- **SOAP 1.2**: Protocolo de comunicação
- **WS-Security**: Segurança de serviços web
- **XML Schema**: Estrutura de dados

### Padrões Utilizados

- **ISO 8601**: Formato de datas
- **UTF-8**: Codificação de caracteres
- **HTTP/HTTPS**: Protocolo de transporte

## 📝 Glossário Oficial

Consulte o [Glossário CNES](./03-glossario-cnes.md) para definições oficiais de termos utilizados.

## 🚀 Próximos Passos

- Veja [Referências e Links](./02-referencias-links.md) para mais recursos
- Consulte [Normas e Regulamentações](./04-normas-regulamentacoes.md) para detalhes legais
- Leia o [Glossário CNES](./03-glossario-cnes.md) para definições

