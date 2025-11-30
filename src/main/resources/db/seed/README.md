# Scripts SQL de Seed - UPSaúde

Esta pasta contém scripts SQL para popular o banco de dados com dados de teste da Prefeitura Municipal de Santa Rita do Sapucaí - MG.

## Estrutura dos Scripts

Os scripts são executados automaticamente na inicialização da aplicação quando `app.seed.enabled=true` estiver configurado no `application.properties`. Eles são executados em ordem numérica (alfabética).

### Scripts Criados

#### Entidades com Tenant (Dados Específicos do Município)
1. **01-tenant.sql** - Cria o Tenant (Prefeitura Municipal de Santa Rita do Sapucaí)
2. **02-estabelecimentos.sql** - Cria 6 estabelecimentos de saúde
3. **05-enderecos.sql** - Cria endereços para tenant e estabelecimentos

#### Entidades de Escopo Global (Sem Tenant - Dados de Referência)
4. **03-conselhos-profissionais.sql** - Cria 8 conselhos profissionais (CRM, COREN, CRF, CRP, CRO, CREFITO, CRN, CRFA)
5. **04-estados-cidades.sql** - Cria dados de referência geográfica (MG e Santa Rita do Sapucaí)
6. **06-especialidades-medicas.sql** - Cria especialidades médicas conforme CFM (10 especialidades principais)
7. **07-cid-doencas.sql** - Cria códigos CID-10 mais comuns (10 códigos principais)
8. **08-alergias.sql** - Cria catálogo de alergias comuns (8 alergias principais)
9. **09-deficiencias.sql** - Cria deficiências conforme SUS/e-SUS/CIF (12 tipos de deficiências)
10. **10-doencas.sql** - Cria catálogo de doenças comuns (5 doenças principais) - Escopo Global
11. **11-papeis.sql** - Cria papéis/perfis do sistema (10 papéis: Admin, Médico, Enfermeiro, etc)
12. **12-fabricantes-vacina.sql** - Cria fabricantes de vacinas reais (7 fabricantes: Butantan, Fiocruz, Pfizer, etc)
13. **13-fabricantes-medicamento.sql** - Cria fabricantes de medicamentos reais (8 fabricantes: EMS, Aché, Eurofarma, etc)
14. **14-fabricantes-equipamento.sql** - Cria fabricantes de equipamentos médicos reais (6 fabricantes: GE, Philips, Mindray, etc)
15. **15-vacinas.sql** - Cria vacinas do PNI/Calendário Nacional (10 vacinas principais)
16. **16-medicacoes-continuas.sql** - Cria medicações de uso contínuo comuns (10 medicações principais)

#### Entidades com Tenant (Dados Específicos do Município) - Continuação
17. **17-profissionais-saude.sql** - Cria profissionais de saúde (7 profissionais)
18. **18-pacientes.sql** - Cria pacientes (5 pacientes de diferentes faixas etárias)
19. **19-equipes-saude.sql** - Cria equipes de saúde (6 equipes: ESF, UPA, NASF)
20. **20-profissional-estabelecimento.sql** - Cria vínculos profissional-estabelecimento (7 vínculos)
21. **21-vinculo-profissional-equipe.sql** - Cria vínculos profissional-equipe (7 vínculos)

## Dados Reais

Os scripts utilizam dados reais da cidade de Santa Rita do Sapucaí - MG:
- **Código IBGE:** 3543204
- **Coordenadas:** -22.2511, -45.7056
- **DDD:** 35
- **CEP:** 37540-000

### Estabelecimentos Criados

1. **UBS Centro** - CNES: 7101234
2. **UPA 24 Horas** - CNES: 7101235
3. **Posto de Saúde São Cristóvão** - CNES: 7101236
4. **Hospital Municipal** - CNES: 7101237
5. **UBS Novo Horizonte** - CNES: 7101238
6. **UBS Jardim Primavera** - CNES: 7101239

### Conselhos Profissionais

- CRM - Conselho Regional de Medicina
- COREN - Conselho Regional de Enfermagem
- CRF - Conselho Regional de Farmácia
- CRP - Conselho Regional de Psicologia
- CRO - Conselho Regional de Odontologia
- CREFITO - Conselho Regional de Fisioterapia e Terapia Ocupacional
- CRN - Conselho Regional de Nutrição
- CRFA - Conselho Regional de Fonoaudiologia

## Habilitar/Desabilitar

Para habilitar o seed:
```properties
app.seed.enabled=true
```

Para desabilitar:
```properties
app.seed.enabled=false
```

## Observações Importantes

- ✅ Todos os scripts utilizam `NOT EXISTS` ou `ON CONFLICT` para evitar duplicação
- ✅ Os scripts respeitam todos os relacionamentos entre entidades
- ✅ Todos os dados são criados para o tenant "prefeitura-santa-rita-do-sapucai"
- ✅ Os dados são criados com informações realistas baseadas na cidade real

## Ordem de Dependências

### Entidades Globais (Sem Tenant) - Podem ser executadas em paralelo após Estados/Cidades
1. **Estados e Cidades** (referências geográficas - base para endereços)
2. **Conselhos Profissionais** (referência para profissionais)
3. **Especialidades Médicas** (referência para médicos)
4. **CID-10** (referência para diagnósticos)
5. **Alergias** (catálogo global)
6. **Deficiências** (catálogo global conforme SUS/e-SUS)
7. **Papeis** (perfis do sistema)
8. **Fabricantes** (Vacinas, Medicamentos, Equipamentos - referência para produtos)
9. **Vacinas** (depende de FabricantesVacina - catálogo PNI)
10. **Medicações Contínuas** (catálogo global)

### Entidades com Tenant (Dados Específicos do Município)
11. **Tenant** (Prefeitura Municipal)
12. **Estabelecimentos** (depende de Tenant)
13. **Endereços** (depende de Tenant, Estabelecimentos, Estados e Cidades)
14. **Profissionais de Saúde** (depende de Tenant, Estabelecimentos, Conselhos Profissionais, Especialidades)
15. **Pacientes** (depende de Tenant, Estabelecimentos, Endereços)
16. **Equipes de Saúde** (depende de Tenant, Estabelecimentos)
17. **Vínculos** (depende de Profissionais, Estabelecimentos, Equipes)

## Entidades de Escopo Global

As entidades de escopo global (sem relacionamento com Tenant) são compartilhadas entre todos os municípios/tenants e representam dados de referência padronizados para integração com sistemas governamentais:

- **Conselhos Profissionais**: Conselhos nacionais (CRM, COREN, etc)
- **Especialidades Médicas**: Catálogo CFM de especialidades
- **CID-10**: Classificação Internacional de Doenças (OMS)
- **Alergias**: Catálogo de alergias comuns
- **Deficiências**: Classificação conforme SUS/e-SUS/CIF
- **Vacinas**: Catálogo do PNI (Programa Nacional de Imunizações)
- **Fabricantes**: Fabricantes reais de vacinas, medicamentos e equipamentos
- **Papeis**: Perfis de acesso do sistema

Esses dados são essenciais para integração com:
- Sistema de Informação do PNI (SIPNI)
- e-SUS Atenção Básica
- CNES (Cadastro Nacional de Estabelecimentos de Saúde)
- Sistemas de notificação (SINAN)
- Integração com outros sistemas do SUS
