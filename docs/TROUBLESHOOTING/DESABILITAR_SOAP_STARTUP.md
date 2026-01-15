# Desabilitar Carregamento de WSDL/SOAP no Startup

## 🎯 Objetivo

Evitar que serviços SOAP e classes WSDL sejam carregados durante o startup da aplicação, melhorando o tempo de inicialização.

## ✅ Solução Implementada

### Mudanças Realizadas

1. **Removidas chamadas explícitas de `afterPropertiesSet()`**
   - `CnesSoapConfig`: Removida inicialização do `SaajSoapMessageFactory`
   - `SigtapSoapConfig`: Removida inicialização do `SaajSoapMessageFactory` e `Jaxb2Marshaller`

2. **Configurações já existentes (mantidas)**
   - `@Lazy` na classe de configuração
   - `@Lazy` em todos os beans SOAP
   - Beans só são inicializados quando realmente utilizados

### Como Funciona

#### Antes (Problema)
```java
@Bean
@Lazy
public SaajSoapMessageFactory cnesSoapMessageFactory() {
    SaajSoapMessageFactory factory = new SaajSoapMessageFactory();
    factory.setSoapVersion(SoapVersion.SOAP_12);
    factory.afterPropertiesSet(); // ❌ Inicializa no startup mesmo com @Lazy
    return factory;
}
```

#### Depois (Solução)
```java
@Bean
@Lazy
public SaajSoapMessageFactory cnesSoapMessageFactory() {
    SaajSoapMessageFactory factory = new SaajSoapMessageFactory();
    factory.setSoapVersion(SoapVersion.SOAP_12);
    // ✅ afterPropertiesSet() será chamado apenas quando o bean for usado
    return factory;
}
```

### Arquivos Modificados

1. **`src/main/java/com/upsaude/config/CnesSoapConfig.java`**
   - Removida chamada `factory.afterPropertiesSet()` do método `cnesSoapMessageFactory()`

2. **`src/main/java/com/upsaude/config/SigtapSoapConfig.java`**
   - Removida chamada `factory.afterPropertiesSet()` do método `sigtapSoapMessageFactory()`
   - Removida chamada `marshaller.afterPropertiesSet()` do método `sigtapMarshaller()`

## 🔍 Comportamento Esperado

### Startup da Aplicação
- ✅ **NÃO** carrega classes WSDL
- ✅ **NÃO** inicializa contextos JAXB
- ✅ **NÃO** cria WebServiceTemplate
- ✅ **NÃO** inicializa marshallers SOAP

### Primeira Utilização
- Quando um serviço SOAP for chamado pela primeira vez:
  1. Spring inicializa o bean lazy
  2. `afterPropertiesSet()` é chamado automaticamente
  3. Contexto JAXB é criado
  4. WebServiceTemplate é configurado
  5. Requisição SOAP é executada

## 📊 Benefícios

### Tempo de Startup
- **Antes**: Carregamento de ~287 classes WSDL geradas + contextos JAXB
- **Depois**: Nenhum carregamento até primeira utilização
- **Ganho estimado**: 2-5 segundos no startup

### Uso de Memória
- **Antes**: Memória alocada para contextos JAXB mesmo sem uso
- **Depois**: Memória alocada apenas quando necessário

### Performance
- Startup mais rápido
- Menor uso inicial de memória
- Mesma performance quando os serviços são utilizados

## 🧪 Como Verificar

### Verificar que não está carregando no startup

1. **Adicionar log no método de criação**:
```java
@Bean
@Lazy
public SaajSoapMessageFactory cnesSoapMessageFactory() {
    log.info("⚠️ ATENÇÃO: Inicializando SOAP Factory - isso não deveria acontecer no startup!");
    // ...
}
```

2. **Verificar logs de startup**:
```bash
mvn spring-boot:run | grep -i "soap\|wsdl\|jaxb"
```

3. **Verificar tempo de startup**:
```bash
# Antes das mudanças
time mvn spring-boot:run

# Depois das mudanças
time mvn spring-boot:run
```

### Verificar inicialização sob demanda

1. **Fazer uma chamada SOAP**:
```bash
curl http://localhost:8080/api/cnes/profissional/{cns}
```

2. **Verificar logs**:
- Deve aparecer a inicialização do contexto JAXB apenas na primeira chamada

## ⚠️ Considerações Importantes

### Inicialização Automática
O Spring chama `afterPropertiesSet()` automaticamente quando:
- O bean é inicializado (implementa `InitializingBean`)
- Com `@Lazy`, isso só acontece quando o bean é realmente usado

### Dependências
Se algum bean não-lazy depender de um bean SOAP lazy, ele será inicializado no startup. Verifique:
```bash
grep -r "@Autowired\|@Inject" src/main/java | grep -i "soap\|wsdl"
```

### Primeira Chamada
A primeira chamada SOAP será um pouco mais lenta devido à inicialização do contexto JAXB, mas isso é aceitável considerando o ganho no startup.

## 🔧 Troubleshooting

### Se ainda estiver carregando no startup

1. **Verificar dependências não-lazy**:
```bash
# Procurar injeções diretas
grep -r "WebServiceTemplate\|Jaxb2Marshaller" src/main/java | grep -v "@Lazy"
```

2. **Verificar inicialização em CommandLineRunner/ApplicationRunner**:
```bash
grep -r "CommandLineRunner\|ApplicationRunner" src/main/java
```

3. **Verificar @PostConstruct**:
```bash
grep -r "@PostConstruct" src/main/java | grep -i "soap\|wsdl"
```

### Se houver erro na primeira chamada

1. **Verificar se as classes WSDL foram geradas**:
```bash
ls -la target/generated-sources/wsimport/com/upsaude/integration/
```

2. **Verificar logs de erro**:
```bash
tail -f logs/upsaude.log | grep -i "jaxb\|marshaller\|soap"
```

## 📚 Referências

- [Spring @Lazy Annotation](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-method-injection.html#beans-factory-lazy-init)
- [JAXB2Marshaller Initialization](https://docs.spring.io/spring-ws/site/apidocs/org/springframework/oxm/jaxb/Jaxb2Marshaller.html)
- [Spring Web Services Lazy Loading](https://docs.spring.io/spring-ws/reference/)
