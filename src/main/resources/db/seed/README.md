# Scripts SQL de Seed - UPSaúde

Esta pasta contém scripts SQL para popular o banco de dados com dados de teste da Prefeitura Municipal de Santa Rita do Sapucaí - MG.

## Estrutura dos Scripts

Os scripts são executados automaticamente na inicialização da aplicação quando `app.seed.enabled=true` estiver configurado no `application.properties`. Eles são executados em ordem numérica (alfabética).

### Scripts Criados

1. **01-tenant.sql** - Cria o Tenant (Prefeitura Municipal de Santa Rita do Sapucaí)
2. **02-estabelecimentos.sql** - Cria 6 estabelecimentos de saúde
3. **03-conselhos-profissionais.sql** - Cria 8 conselhos profissionais
4. **04-estados-cidades.sql** - Cria dados de referência geográfica (MG e Santa Rita do Sapucaí)
5. **05-enderecos.sql** - Cria endereços para tenant e estabelecimentos
6. **06-profissionais-saude.sql** - Cria profissionais de saúde (a criar)
7. **07-pacientes.sql** - Cria pacientes (a criar)
8. **08-equipes-saude.sql** - Cria equipes de saúde (a criar)
9. **09-profissional-estabelecimento.sql** - Cria vínculos profissional-estabelecimento (a criar)
10. **10-vinculo-profissional-equipe.sql** - Cria vínculos profissional-equipe (a criar)

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

1. Estados e Cidades (referências geográficas)
2. Tenant
3. Conselhos Profissionais
4. Estabelecimentos
5. Endereços
6. Profissionais de Saúde
7. Pacientes
8. Equipes de Saúde
9. Vínculos
