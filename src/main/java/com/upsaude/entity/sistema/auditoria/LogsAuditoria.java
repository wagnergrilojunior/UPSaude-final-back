package com.upsaude.entity.sistema.auditoria;
import com.upsaude.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "logs_auditoria", schema = "public")
@Data
public class LogsAuditoria extends BaseEntity {

    @Column(name = "acao", length = 100)
    private String acao;

    @Column(name = "entidade", length = 100)
    private String entidade;

    @Column(name = "entidade_id")
    private java.util.UUID entidadeId;

    @Column(name = "detalhes", columnDefinition = "jsonb")
    private String detalhes;

    @Column(name = "realizado_por")
    private java.util.UUID realizadoPor;
}
