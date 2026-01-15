#!/bin/bash
# Script helper para build otimizado do Maven
# Usa paralelização automática baseada nos cores disponíveis

# Detecta número de cores
CORES=$(sysctl -n hw.ncpu 2>/dev/null || echo 8)

# Executa build com paralelização
# -T 1C = 1 thread por core (usa todos os cores disponíveis)
mvn clean install -T 1C "$@"
