FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

# =========================================
# JVM tuning para Spring Boot em produção
# =========================================
# -Xms1g   : heap inicial
# -Xmx4g   : heap máximo (seguro para host 16GB)
# -XX:+UseG1GC : GC recomendado para apps server
# -XX:MaxGCPauseMillis=200 : alvo de pausa de GC
# -XX:+UseContainerSupport : respeita limites do container
# -Djava.security.egd=file:/dev/./urandom : startup mais rápido
ENTRYPOINT [
  "java",
  "-Xms1g",
  "-Xmx4g",
  "-XX:+UseG1GC",
  "-XX:MaxGCPauseMillis=200",
  "-XX:+UseContainerSupport",
  "-Djava.security.egd=file:/dev/./urandom",
  "-jar",
  "app.jar"
]