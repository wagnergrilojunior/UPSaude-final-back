# Conversão de Arquivos .DBC usando R

## Por que usar R

O R é atualmente a forma mais estável e utilizada para leitura de arquivos .dbc no macOS e Linux.

Vantagens:
- Compatível com macOS moderno (Apple Silicon)
- Amplamente usado em saúde pública
- Permite automação e execução em lote

## Instalação do R no macOS

1. Acesse: https://cran.r-project.org/bin/macosx/
2. Baixe e instale o pacote R-4.x.pkg
3. Verifique a instalação:

R --version

## Instalação do pacote read.dbc

O pacote não está mais disponível no CRAN e deve ser instalado via GitHub:

install.packages("remotes")
library(remotes)
remotes::install_github("cran/read.dbc")
library(read.dbc)

## Script de conversão (converter_dbc.R)

args <- commandArgs(trailingOnly = TRUE)

if (length(args) == 0) {
  stop("Informe o arquivo .dbc como argumento")
}

arquivo_dbc <- args[1]

library(read.dbc)

dados <- read.dbc(arquivo_dbc)

arquivo_csv <- sub("\\.dbc$", ".csv", arquivo_dbc)

write.csv(dados, arquivo_csv, row.names = FALSE)

cat("Arquivo convertido com sucesso:", arquivo_csv, "\n")

## Conversão em massa

for f in *.dbc; do
  Rscript converter_dbc.R "$f"
done


--- *** Na pasta: 

/Users/wagnergrilo/Desktop/WGB/sistemas/UPSaude/SIA_SUS

execute o comando:

for f in /Users/wagnergrilo/Desktop/WGB/sistemas/UPSaude/SIA_SUS/2025/MG/*.dbc; do
  echo "Convertendo $f"
Rscript /Users/wagnergrilo/Desktop/WGB/sistemas/UPSaude/SIA_SUS/converter_dbc.R "$f"