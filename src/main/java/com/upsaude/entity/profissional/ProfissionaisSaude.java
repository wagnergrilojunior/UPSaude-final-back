package com.upsaude.entity.profissional;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.embeddable.ContatoProfissional;
import com.upsaude.entity.embeddable.DadosDeficienciaProfissional;
import com.upsaude.entity.embeddable.DadosDemograficosProfissional;
import com.upsaude.entity.embeddable.DadosPessoaisBasicosProfissional;
import com.upsaude.entity.embeddable.DocumentosBasicosProfissional;
import com.upsaude.entity.embeddable.RegistroProfissional;
import com.upsaude.entity.paciente.Endereco;
import com.upsaude.entity.referencia.sigtap.SigtapOcupacao;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "profissionais_saude", schema = "public", uniqueConstraints = {
                @UniqueConstraint(name = "uk_profissional_cpf", columnNames = { "cpf" })
}, indexes = {
                @Index(name = "idx_profissional_cpf", columnList = "cpf"),
                @Index(name = "idx_profissional_registro", columnList = "registro_profissional"),
                @Index(name = "idx_profissional_status_registro", columnList = "status_registro"),
                @Index(name = "idx_profissional_cbo", columnList = "codigo_cbo"),
                @Index(name = "idx_profissional_cns", columnList = "cns")
})
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ProfissionaisSaude extends BaseEntity {

        @Embedded
        private DadosPessoaisBasicosProfissional dadosPessoaisBasicos;

        @Embedded
        private DocumentosBasicosProfissional documentosBasicos;

        @Embedded
        private DadosDemograficosProfissional dadosDemograficos;

        @Embedded
        private DadosDeficienciaProfissional dadosDeficiencia;

        @Embedded
        private RegistroProfissional registroProfissional;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "sigtap_ocupacao_id")
        private SigtapOcupacao sigtapOcupacao;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "conselho_profissional_id")
        private com.upsaude.entity.referencia.profissional.ConselhoProfissional conselhoProfissional;

        @Embedded
        private ContatoProfissional contato;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "endereco_profissional_id")
        private Endereco enderecoProfissional;

        @Column(name = "data_ultima_sincronizacao_cnes")
        private java.time.OffsetDateTime dataUltimaSincronizacaoCnes;

        @Column(name = "observacoes", columnDefinition = "TEXT")
        private String observacoes;
}
