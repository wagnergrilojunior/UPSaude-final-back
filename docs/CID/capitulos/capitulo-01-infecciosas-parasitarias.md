# Capítulo I - Algumas doenças infecciosas e parasitárias

## Informações Gerais

- **Número do Capítulo**: 1
- **Intervalo de Códigos**: A00-B99
- **Descrição**: Algumas doenças infecciosas e parasitárias
- **Descrição Abreviada**: I.   Algumas doenças infecciosas e parasitárias

## Como Pesquisar

### Via API REST

```bash
# Obter informações do capítulo
GET /v1/cid/cid10/capitulos/1

# Listar categorias do capítulo (por intervalo)
GET /v1/cid/cid10/categorias/intervalo?catinic=A00&catfim=B99

# Pesquisar categorias do capítulo
GET /v1/cid/cid10/categorias?q=A00&page=0&size=20
```

### Via SQL

```sql
-- Buscar todas as categorias do capítulo
SELECT * FROM cid10_categorias 
WHERE cat >= 'A00' AND cat <= 'B99' AND ativo = true 
ORDER BY cat;

-- Buscar subcategorias de uma categoria específica
SELECT * FROM cid10_subcategorias 
WHERE categoria_cat = 'A00' AND ativo = true 
ORDER BY subcat;
```

## Principais Categorias

| Código | Descrição |
|--------|-----------|
| A00 | Cólera |
| A01 | Febres tifóide e paratifóide |
| A02 | Outras infecções por Salmonella |
| A03 | Shiguelose |
| A04 | Outras infecções intestinais bacterianas |
| A05 | Outras intoxicações alimentares bacterianas |
| A06 | Amebíase |
| A07 | Outras doenças intestinais por protozoários |
| A08 | Infecções intestinais virais |
| A09 | Diarréia e gastroenterite de origem infecciosa |

## Exemplos de Subcategorias

- **A000** - Cólera devida a Vibrio cholerae 01, biótipo cholerae
- **A001** - Cólera devida a Vibrio cholerae 01, biótipo El Tor
- **A009** - Cólera não especificada

---

**Última atualização**: Dezembro 2025
