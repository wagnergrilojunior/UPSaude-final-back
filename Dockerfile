FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

# Configurações de memória para Render Starter Plan
# -Xms: heap inicial de 256MB
# -Xmx: heap máximo de 1GB (ajustar conforme recursos disponíveis)
# -XX:+UseG1GC: usar G1 garbage collector (melhor para aplicações server-side)
# -XX:MaxGCPauseMillis: tentar manter pausas do GC abaixo de 200ms
ENTRYPOINT ["java", "-Xms256m", "-Xmx1g", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=200", "-jar", "app.jar"]
