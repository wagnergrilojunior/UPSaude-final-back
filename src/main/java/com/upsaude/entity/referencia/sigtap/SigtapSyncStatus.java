package com.upsaude.entity.referencia.sigtap;

import com.upsaude.entity.BaseEntityWithoutTenant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidade para rastrear o estado da sincroniza??o SIGTAP.
 * Permite retomar a sincroniza??o de onde parou em caso de falha.
 */
@Entity
@Table(name = "sigtap_sync_status", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapSyncStatus extends BaseEntityWithoutTenant {

    @Column(name = "competencia", length = 6, nullable = false)
    private String competencia;

    @Column(name = "etapa_atual", length = 50, nullable = false)
    private String etapaAtual; // grupos, subgrupos, formas_organizacao, procedimentos, detalhes, compatibilidades_possiveis, compatibilidades

    @Column(name = "status", length = 20, nullable = false)
    private String status; // em_andamento, concluida, erro, pausada

    @Column(name = "progresso_atual", length = 500)
    private String progressoAtual; // JSON com informa??es do checkpoint (ex: ?ltimo grupo processado, ?ltima p?gina, etc)

    @Column(name = "total_registros_processados")
    private Long totalRegistrosProcessados;

    @Column(name = "ultimo_erro", columnDefinition = "text")
    private String ultimoErro;

    @Column(name = "tentativas_erro")
    private Integer tentativasErro;

    @Column(name = "ultima_tentativa_em")
    private java.time.OffsetDateTime ultimaTentativaEm;
}
