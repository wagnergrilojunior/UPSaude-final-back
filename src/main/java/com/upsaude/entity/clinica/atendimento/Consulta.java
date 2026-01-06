    package com.upsaude.entity.clinica.atendimento;

    import com.upsaude.entity.BaseEntity;
    import com.upsaude.entity.embeddable.AnamneseConsulta;
    import com.upsaude.entity.embeddable.AtestadoConsulta;
    import com.upsaude.entity.embeddable.DiagnosticoConsulta;
    import com.upsaude.entity.embeddable.EncaminhamentoConsulta;
    import com.upsaude.entity.embeddable.ExamesSolicitadosConsulta;
    import com.upsaude.entity.embeddable.InformacoesConsulta;
    import com.upsaude.entity.embeddable.PrescricaoConsulta;
    import com.upsaude.entity.profissional.Medicos;

    import jakarta.persistence.Column;
    import jakarta.persistence.Embedded;
    import jakarta.persistence.Entity;
    import jakarta.persistence.FetchType;
    import jakarta.persistence.Index;
    import jakarta.persistence.JoinColumn;
    import jakarta.persistence.ManyToOne;
    import jakarta.persistence.OneToOne;
    import jakarta.persistence.PrePersist;
    import jakarta.persistence.PreUpdate;
    import jakarta.persistence.Table;
    import lombok.Data;
    import lombok.EqualsAndHashCode;

    @Entity
    @Table(name = "consultas", schema = "public",
        indexes = {
            @Index(name = "idx_consulta_atendimento", columnList = "atendimento_id"),
            @Index(name = "idx_consulta_medico", columnList = "medico_id")
        })
    @Data
    @EqualsAndHashCode(callSuper = true)
    public class Consulta extends BaseEntity {

        public Consulta() {
            this.informacoes = new InformacoesConsulta();
            this.anamnese = new AnamneseConsulta();
            this.diagnostico = new DiagnosticoConsulta();
            this.prescricao = new PrescricaoConsulta();
            this.examesSolicitados = new ExamesSolicitadosConsulta();
            this.encaminhamento = new EncaminhamentoConsulta();
            this.atestado = new AtestadoConsulta();
        }

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "atendimento_id", nullable = false, unique = true)
        private Atendimento atendimento;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "medico_id", nullable = false)
        private Medicos medico;

        @Embedded
        private InformacoesConsulta informacoes;

        @Embedded
        private AnamneseConsulta anamnese;

        @Embedded
        private DiagnosticoConsulta diagnostico;

        @Embedded
        private PrescricaoConsulta prescricao;

        @Embedded
        private ExamesSolicitadosConsulta examesSolicitados;

        @Embedded
        private EncaminhamentoConsulta encaminhamento;

        @Embedded
        private AtestadoConsulta atestado;

        @Column(name = "observacoes", columnDefinition = "TEXT")
        private String observacoes;

        @Column(name = "observacoes_internas", columnDefinition = "TEXT")
        private String observacoesInternas;

        @PrePersist
        @PreUpdate
        public void validateEmbeddables() {
            if (informacoes == null) {
                informacoes = new InformacoesConsulta();
            }
            if (anamnese == null) {
                anamnese = new AnamneseConsulta();
            }
            if (diagnostico == null) {
                diagnostico = new DiagnosticoConsulta();
            }
            if (prescricao == null) {
                prescricao = new PrescricaoConsulta();
            }
            if (examesSolicitados == null) {
                examesSolicitados = new ExamesSolicitadosConsulta();
            }
            if (encaminhamento == null) {
                encaminhamento = new EncaminhamentoConsulta();
            }
            if (atestado == null) {
                atestado = new AtestadoConsulta();
            }
        }
    }

