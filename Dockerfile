# Dockerfile para deploy no Render
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copia o arquivo de configuração do Maven
COPY pom.xml .

# Baixa as dependências (cache layer)
RUN mvn dependency:go-offline -B

# Copia o código fonte
COPY src ./src

# Compila a aplicação
RUN mvn clean package -DskipTests

# Imagem final
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copia o JAR da aplicação
COPY --from=build /app/target/upsaude-back-1.0.0.jar app.jar

# Expõe a porta (Render usa a variável PORT)
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]

