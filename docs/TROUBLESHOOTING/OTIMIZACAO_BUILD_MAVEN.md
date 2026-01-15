# 🐌 Análise de Lentidão do Build Maven

## 📊 Situação Atual

### Estatísticas do Projeto
- **Arquivos Java no código fonte**: 2.464 arquivos
- **Arquivos Java gerados**: 287 arquivos (WSDL, annotations)
- **Total de arquivos compilados**: 2.538 arquivos
- **Cores disponíveis**: 8 cores
- **Java Version**: 17
- **Spring Boot**: 3.3.4

### Logs Observados
```
[INFO] Compiling 2538 source files with javac [debug release 17] to target/classes
```

## 🔍 Principais Causas da Lentidão

### 1. **Falta de Paralelização do Maven** ⚠️ CRÍTICO
O Maven está compilando **sequencialmente** em vez de usar múltiplos threads.

**Impacto**: Com 8 cores disponíveis, você está usando apenas **12.5% da capacidade** do processador.

### 2. **Falta de Configuração de Memória do Maven** ⚠️ IMPORTANTE
Sem configuração explícita de memória, o Maven pode estar usando valores padrão baixos, causando:
- Múltiplas coletas de lixo (GC)
- Swap de memória (muito lento)
- Recompilações desnecessárias

### 3. **Annotation Processors Sequenciais** ⚠️ MODERADO
Lombok e MapStruct estão processando annotations de forma sequencial, sem otimizações.

### 4. **Grande Volume de Arquivos** ℹ️ INFORMATIVO
2.538 arquivos é um volume significativo, mas otimizável com as configurações corretas.

### 5. **Falta de Compilação Incremental** ℹ️ MODERADO
Sem configuração adequada, o Maven pode estar recompilando arquivos desnecessariamente.

## ✅ Soluções Recomendadas

### Solução 1: Configurar Paralelização do Maven (MAIOR IMPACTO)

Crie o arquivo `.mvn/jvm.config` na raiz do projeto:

```bash
mkdir -p .mvn
```

**Conteúdo do arquivo `.mvn/jvm.config`:**
```
-Xmx4g
-Xms1g
-XX:+TieredCompilation
-XX:TieredStopAtLevel=1
```

**Para paralelização, use uma das opções:**

**Opção A: Script Helper (Recomendado)**
```bash
./mvn-build.sh
```

**Opção B: Parâmetro na linha de comando**
```bash
mvn clean install -T 1C
```

**Explicação:**
- `-T 1C`: Usa 1 thread por core (8 threads no seu caso)
- `-Xmx4g`: Memória máxima de 4GB para o Maven
- `-Xms1g`: Memória inicial de 1GB
- `-XX:+TieredCompilation`: Otimiza compilação JIT
- `-XX:TieredStopAtLevel=1`: Acelera startup do Maven

**Nota**: O arquivo `maven.config` não suporta a opção `-T` em algumas versões do Maven. Use o script helper ou passe o parâmetro diretamente.

**Ganho esperado**: **4-6x mais rápido** 🚀

### Solução 2: Otimizar Plugin de Compilação

Adicione configurações ao `maven-compiler-plugin` no `pom.xml`:

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-compiler-plugin</artifactId>
  <version>3.11.0</version>
  <configuration>
    <source>${java.version}</source>
    <target>${java.version}</target>
    <!-- Otimizações de compilação -->
    <compilerArgs>
      <arg>-Amapstruct.defaultComponentModel=spring</arg>
      <arg>-parameters</arg>
      <!-- Otimiza processamento de annotations -->
      <arg>-proc:full</arg>
    </compilerArgs>
    <!-- Usa compilação incremental -->
    <useIncrementalCompilation>true</useIncrementalCompilation>
    <!-- Memória para o compilador -->
    <meminitial>512m</meminitial>
    <maxmem>2048m</maxmem>
    <annotationProcessorPaths>
      <path>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.34</version>
      </path>
      <path>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct-processor</artifactId>
        <version>1.5.5.Final</version>
      </path>
    </annotationProcessorPaths>
  </configuration>
</plugin>
```

**Ganho esperado**: **20-30% mais rápido** ⚡

### Solução 3: Usar Maven Daemon (mvnd) - OPCIONAL MAS RECOMENDADO

O Maven Daemon mantém uma JVM quente em background, acelerando builds subsequentes.

**Instalação (macOS com Homebrew):**
```bash
brew install mvnd
```

**Uso:**
```bash
mvnd clean install
```

**Ganho esperado**: **2-3x mais rápido** em builds subsequentes 🔥

### Solução 4: Configurar Cache de Dependências

Adicione ao `pom.xml` (se ainda não existir):

```xml
<properties>
  <java.version>17</java.version>
  <!-- Cache de dependências -->
  <maven.compiler.fork>true</maven.compiler.fork>
  <maven.compiler.maxmem>2048m</maven.compiler.maxmem>
</properties>
```

### Solução 5: Otimizar Processamento de WSDL

O plugin `jaxws-maven-plugin` está configurado com `verbose=true`, o que adiciona overhead.

**Recomendação**: Remover `verbose=true` em produção ou usar apenas em desenvolvimento:

```xml
<configuration>
  <!-- ... outras configurações ... -->
  <verbose>false</verbose> <!-- ou remover esta linha -->
</configuration>
```

## 📋 Implementação Passo a Passo

### Passo 1: Criar Configurações do Maven

Execute os seguintes comandos:

```bash
cd /Users/wagnergrilo/Desktop/WGB/sistemas/UPSaude/code_cursor/UPSaude-final-back

# Criar diretório .mvn
mkdir -p .mvn

# Criar arquivo de configuração JVM
cat > .mvn/jvm.config << 'EOF'
-Xmx4g
-Xms1g
-XX:+TieredCompilation
-XX:TieredStopAtLevel=1
EOF
```

O script `mvn-build.sh` já foi criado na raiz do projeto para facilitar o uso da paralelização.

### Passo 2: Atualizar pom.xml

Adicionar as otimizações ao `maven-compiler-plugin`.

### Passo 3: Testar Build

```bash
mvn clean install -DskipTests
```

**Tempo esperado**: 
- **Antes**: 5-10 minutos (estimado)
- **Depois**: 1-2 minutos (com paralelização)

## 🎯 Comparação de Performance Esperada

| Configuração | Tempo Estimado | Ganho |
|-------------|----------------|-------|
| **Atual (sem otimizações)** | 5-10 min | Baseline |
| **Com paralelização (-T 1C)** | 1-2 min | **4-6x** |
| **+ Otimizações de memória** | 1-1.5 min | **5-7x** |
| **+ Maven Daemon (mvnd)** | 30-60s | **8-10x** |

## 🔧 Comandos Úteis

### Build com Paralelização Manual
```bash
mvn clean install -T 1C -DskipTests
```

### Build com Mais Memória
```bash
export MAVEN_OPTS="-Xmx4g -Xms1g"
mvn clean install -DskipTests
```

### Build Apenas Compilação (sem testes e package)
```bash
mvn clean compile -T 1C
```

### Verificar Uso de CPU Durante Build
```bash
# Em outro terminal
top -pid $(pgrep -f maven)
```

## ⚠️ Considerações Importantes

1. **Memória**: Ajuste `-Xmx4g` conforme a RAM disponível. Se tiver 16GB+, pode usar `-Xmx6g` ou `-Xmx8g`.

2. **Threads**: `-T 1C` usa 1 thread por core. Se quiser ser mais agressivo, use `-T 2C` (2 threads por core), mas pode causar contenção.

3. **Primeiro Build**: O primeiro build após `clean` sempre será mais lento devido ao download de dependências e geração de código.

4. **Builds Incrementais**: Builds subsequentes (sem `clean`) devem ser muito mais rápidos.

## 📈 Monitoramento

Para verificar se as otimizações estão funcionando:

```bash
# Build com timing detalhado
mvn clean install -DskipTests -X | grep -E "(BUILD|Compiling|Time)"
```

Ou use o plugin de timing do Maven:

```bash
mvn clean install -DskipTests -Dmaven.compiler.showWarnings=true
```

## 🐛 Troubleshooting

### Se o build falhar com "OutOfMemoryError":
- Aumente `-Xmx` no `.mvn/jvm.config`
- Reduza threads: `-T 0.5C` (metade dos cores)

### Se houver erros de compilação com paralelização:
- Use `-T 1` (apenas 1 thread) para debug
- Verifique se há dependências circulares entre módulos

### Se o build ainda estiver lento:
- Verifique I/O do disco (SSD vs HDD)
- Verifique antivírus escaneando arquivos durante build
- Considere usar `mvnd` (Maven Daemon)

## 📚 Referências

- [Maven Parallel Builds](https://cwiki.apache.org/confluence/display/MAVEN/Parallel+builds+in+Maven+3)
- [Maven Performance Tuning](https://maven.apache.org/guides/mini/guide-performance.html)
- [Maven Daemon (mvnd)](https://github.com/apache/maven-mvnd)
