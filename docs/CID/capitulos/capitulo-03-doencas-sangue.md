# Capítulo III - Doenças do sangue e dos órgãos hematopoéticos e alguns transtornos imunitários

## Informações Gerais

- **Número do Capítulo**: 3
- **Intervalo de Códigos**: D50-D89
- **Descrição**: Doenças do sangue e dos órgãos hematopoéticos e alguns transtornos imunitários
- **Descrição Abreviada**: III. Doenças sangue órgãos hemat e transt imunitár

## Como Pesquisar

### Via API REST

```bash
# Obter informações do capítulo
GET /v1/cid/cid10/capitulos/3

# Listar categorias do capítulo (por intervalo)
GET /v1/cid/cid10/categorias/intervalo?catinic=D50&catfim=D89
```

### Via SQL

```sql
-- Buscar todas as categorias do capítulo
SELECT * FROM cid10_categorias 
WHERE cat >= 'D50' AND cat <= 'D89' AND ativo = true 
ORDER BY cat;
```

---

**Última atualização**: Dezembro 2025
