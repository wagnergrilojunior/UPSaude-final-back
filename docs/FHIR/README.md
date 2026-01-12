# 📚 Integração FHIR - Terminologia do Brasil

> Documentação completa para integração com o sistema FHIR do Ministério da Saúde do Brasil

## 🌐 Sobre o Sistema FHIR

O **FHIR (Fast Healthcare Interoperability Resources)** é um padrão internacional para troca de dados em saúde. O Ministério da Saúde do Brasil disponibiliza o **Guia de Implementação de Terminologias do Brasil** baseado no padrão **HL7 FHIR R4**.

- **URL Base:** https://terminologia.saude.gov.br/fhir
- **Versão FHIR:** R4 (4.0.1)
- **Versão do Guia BR:** 1.0.0 - STU1
- **Licença:** CC0-BY

---

## 📁 Estrutura da Documentação

```
docs/FHIR/
├── README.md                          # Este arquivo (visão geral)
├── 01-VISAO-GERAL.md                  # Arquitetura e conceitos
├── 02-ENDPOINTS-FHIR.md               # URLs e endpoints disponíveis
│
├── integracao/                        # Documentação técnica de integração
│   ├── VACINACAO.md                   # Módulo de vacinação (detalhado)
│   ├── DIAGNOSTICOS.md                # CID-10, CIAP2
│   ├── PROCEDIMENTOS.md               # Tabela SUS, CBHPM/TUSS
│   ├── MEDICAMENTOS.md                # Catálogo de medicamentos
│   ├── PROFISSIONAIS.md               # CBO, Conselhos
│   ├── ALERGIAS.md                    # Alérgenos e reações
│   └── DADOS-REFERENCIA.md            # Geográficos, documentos, etc.
│
├── negocio/                           # Documentação de negócio
│   ├── CASOS-DE-USO.md                # Casos de uso por módulo
│   ├── ROADMAP.md                     # Roadmap de implementação
│   └── BENEFICIOS.md                  # Benefícios da integração
│
└── modelagem/                         # Modelos de dados
    ├── ENTIDADES-VACINACAO.md         # Entidades do módulo de vacinação
    └── ENTIDADES-REFERENCIA.md        # Entidades de referência FHIR
```

---

## 🚀 Início Rápido

### 1. Entenda os Conceitos
Leia o arquivo [01-VISAO-GERAL.md](./01-VISAO-GERAL.md) para entender a arquitetura FHIR e os tipos de recursos.

### 2. Explore os Endpoints
Consulte [02-ENDPOINTS-FHIR.md](./02-ENDPOINTS-FHIR.md) para ver todos os endpoints disponíveis.

### 3. Escolha o Módulo
Navegue pela pasta `integracao/` para ver a documentação técnica de cada módulo.

### 4. Veja o Roadmap
Consulte [negocio/ROADMAP.md](./negocio/ROADMAP.md) para entender a ordem de implementação sugerida.

---

## 📋 Módulos Disponíveis

| Módulo | Prioridade | Status | Documentação |
|--------|------------|--------|--------------|
| Vacinação | 🔴 Alta | 📝 Documentado | [integracao/VACINACAO.md](./integracao/VACINACAO.md) |
| Diagnósticos (CID-10) | 🟠 Média-Alta | 📝 Documentado | [integracao/DIAGNOSTICOS.md](./integracao/DIAGNOSTICOS.md) |
| Procedimentos (SUS/TUSS) | 🟠 Média-Alta | 📝 Documentado | [integracao/PROCEDIMENTOS.md](./integracao/PROCEDIMENTOS.md) |
| Medicamentos | 🟡 Média | 📝 Documentado | [integracao/MEDICAMENTOS.md](./integracao/MEDICAMENTOS.md) |
| Profissionais | 🟡 Média | 📝 Documentado | [integracao/PROFISSIONAIS.md](./integracao/PROFISSIONAIS.md) |
| Alergias | 🟢 Baixa | 📝 Documentado | [integracao/ALERGIAS.md](./integracao/ALERGIAS.md) |
| Dados de Referência | 🟢 Baixa | 📝 Documentado | [integracao/DADOS-REFERENCIA.md](./integracao/DADOS-REFERENCIA.md) |

---

## 🔗 Links Úteis

- [Site FHIR BR](https://terminologia.saude.gov.br/fhir/index.html)
- [Lista de Artefatos](https://terminologia.saude.gov.br/fhir/artifacts.html)
- [HL7 FHIR R4 Documentation](https://hl7.org/fhir/R4/)
- [HL7 Brasil](https://hl7.org.br/)

---

## 📅 Histórico de Atualizações

| Data | Versão | Descrição |
|------|--------|-----------|
| 2026-01-09 | 1.0.0 | Documentação inicial criada |

---

> **Nota:** Esta documentação foi criada como referência para as futuras integrações do sistema UPSaude com o FHIR do Ministério da Saúde.
