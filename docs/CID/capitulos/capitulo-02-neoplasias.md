# Capítulo II - Neoplasias [tumores]

## Informações Gerais

- **Número do Capítulo**: 2
- **Intervalo de Códigos**: C00-D48
- **Descrição**: Neoplasias [tumores]
- **Descrição Abreviada**: II.  Neoplasias (tumores)

## Como Pesquisar

### Via API REST

```bash
# Obter informações do capítulo
GET /v1/cid/cid10/capitulos/2

# Listar categorias do capítulo (por intervalo)
GET /v1/cid/cid10/categorias/intervalo?catinic=C00&catfim=D48
```

### Via SQL

```sql
-- Buscar todas as categorias do capítulo
SELECT * FROM cid10_categorias 
WHERE cat >= 'C00' AND cat <= 'D48' AND ativo = true 
ORDER BY cat;
```

---

**Última atualização**: Dezembro 2025
