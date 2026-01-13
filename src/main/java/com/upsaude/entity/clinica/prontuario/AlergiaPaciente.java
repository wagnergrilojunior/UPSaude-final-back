package com.upsaude.entity.clinica.prontuario;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.alergia.Alergeno;
import com.upsaude.entity.alergia.CategoriaAgenteAlergia;
import com.upsaude.entity.alergia.CriticidadeAlergia;
import com.upsaude.entity.alergia.ReacaoAdversaCatalogo;
import com.upsaude.entity.referencia.cid.Cid10Subcategorias;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@Table(name = "alergias_paciente", schema = "public", indexes = {
        @Index(name = "idx_alergia_paciente_prontuario", columnList = "prontuario_id"),
        @Index(name = "idx_alergia_paciente_tipo", columnList = "tipo_alergia")
})
@Data
@EqualsAndHashCode(callSuper = true)
public class AlergiaPaciente extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prontuario_id", nullable = false)
    private Prontuario prontuario;

    @Column(name = "tipo_alergia", nullable = false)
    private String tipoAlergia;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_diagnostico")
    private LocalDate dataDiagnostico;

    @Column(name = "gravidade")
    private String gravidade;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid10_subcategorias_id")
    private Cid10Subcategorias diagnosticoRelacionado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alergeno_id")
    private Alergeno alergeno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reacao_adversa_catalogo_id")
    private ReacaoAdversaCatalogo reacaoAdversaCatalogo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criticidade_id")
    private CriticidadeAlergia criticidade;

    @Column(name = "clinical_status", length = 20)
    private String clinicalStatus = "active";

    @Column(name = "verification_status", length = 20)
    private String verificationStatus = "unconfirmed";

    @Column(name = "grau_certeza", length = 50)
    private String grauCerteza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_agente_id")
    private CategoriaAgenteAlergia categoriaAgente;
}
