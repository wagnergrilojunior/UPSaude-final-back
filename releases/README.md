# 📦 Releases

Esta pasta contém a documentação de todas as releases do projeto UPSaude Backend.

## 📋 Formato dos Releases

Cada release é documentada em um arquivo Markdown seguindo o padrão:

```
RELEASE-{VERSION}.md
```

Onde `{VERSION}` segue o padrão de versionamento semântico: `MAJOR.MINOR.PATCH.BUILD`

## 📝 Estrutura Padrão

Cada arquivo de release deve conter:

1. **Cabeçalho**
   - Versão da release
   - Data de lançamento
   - Tipo de release (Major, Minor, Patch)

2. **Resumo Executivo**
   - Visão geral das mudanças principais

3. **Seções Principais**
   - ✨ Novas Funcionalidades
   - 🐛 Correções Críticas
   - 🔧 Melhorias Técnicas
   - ⚠️ Breaking Changes (se houver)
   - 🔄 Instruções de Migração

4. **Detalhes Técnicos**
   - Código de exemplo quando relevante
   - Comandos SQL para migrações
   - Configurações necessárias

5. **Estatísticas**
   - Número de arquivos modificados
   - Métricas relevantes

## 🎯 Tipos de Release

### Major Release (X.0.0.0)
- Mudanças significativas na API
- Breaking changes
- Novas funcionalidades principais

### Minor Release (0.X.0.0)
- Novas funcionalidades
- Melhorias sem breaking changes
- Novos endpoints ou recursos

### Patch Release (0.0.X.0)
- Correções de bugs
- Ajustes de performance
- Correções de segurança
- Melhorias menores

### Build Release (0.0.0.X)
- Correções urgentes
- Hotfixes
- Ajustes pontuais

## 📅 Histórico de Releases

| Versão | Data | Tipo | Descrição |
|--------|------|------|-----------|
| [1.0.0.12](RELEASE-1.0.0.12.md) | 13/01/2026 | Patch | Correções críticas de serialização JSON, mapeamento JSONB e testes de regressão |

## 🔗 Links Úteis

- [Changelog Completo](../CHANGELOG.md) (se existir)
- [Documentação Principal](../README.md)
- [Guia de Contribuição](../CONTRIBUTING.md) (se existir)

## 📞 Contato

Para questões sobre releases específicas, consulte o arquivo da release ou entre em contato com a equipe de desenvolvimento.
