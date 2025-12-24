package com.upsaude.enums;

/**
 * Status possíveis de um job de importação.
 */
public enum ImportJobStatusEnum {
    ENFILEIRADO("ENFILEIRADO", "Aguardando processamento"),
    PROCESSANDO("PROCESSANDO", "Em processamento"),
    CONCLUIDO("CONCLUIDO", "Processamento concluído com sucesso"),
    ERRO("ERRO", "Processamento falhou"),
    CANCELADO("CANCELADO", "Job cancelado pelo usuário"),
    PAUSADO("PAUSADO", "Job pausado");

    private final String codigo;
    private final String descricao;

    ImportJobStatusEnum(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static ImportJobStatusEnum fromCodigo(String codigo) {
        if (codigo == null) return null;
        for (ImportJobStatusEnum status : values()) {
            if (status.codigo.equals(codigo)) {
                return status;
            }
        }
        return null;
    }
}

