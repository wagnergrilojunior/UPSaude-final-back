# Configurações de Otimização do Maven

Este diretório contém configurações que otimizam o build do Maven.

## Arquivos

- **`jvm.config`**: Configurações de memória e JVM para o Maven

## Efeitos

- ✅ Mais memória alocada para o Maven (4GB)
- ✅ Compilação incremental habilitada
- ✅ Processamento otimizado de annotations (Lombok + MapStruct)

## Paralelização

Para usar paralelização, você tem duas opções:

### Opção 1: Script Helper (Recomendado)
```bash
./mvn-build.sh
```

### Opção 2: Parâmetro Direto
```bash
mvn clean install -T 1C
```

O parâmetro `-T 1C` usa 1 thread por core (8 threads no seu caso).

## Ganho Esperado

**4-6x mais rápido** em builds completos com paralelização.

## Como Funciona

O arquivo `jvm.config` é lido automaticamente pelo Maven quando executado na raiz do projeto.
Para paralelização, use o script `mvn-build.sh` ou passe `-T 1C` diretamente.

## Desabilitar Temporariamente

Se precisar desabilitar as otimizações temporariamente:

```bash
# Renomear o arquivo
mv .mvn/jvm.config .mvn/jvm.config.bak

# Ou usar Maven sem estas configurações
MAVEN_OPTS="" mvn clean install
```
