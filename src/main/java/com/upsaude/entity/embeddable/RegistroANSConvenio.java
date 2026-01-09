package com.upsaude.entity.embeddable;

import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.util.converter.StatusAtivoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class RegistroANSConvenio {

    public RegistroANSConvenio() {
        this.registroAns = "";
        this.codigoAns = "";
        this.razaoSocialAns = "";
        this.nomeFantasiaAns = "";
        this.codigoTiss = "";
        this.habilitadoTiss = false;
        this.observacoesAns = "";
    }

    @Column(name = "registro_ans", length = 50)
    private String registroAns;

    @Column(name = "codigo_ans", length = 50)
    private String codigoAns;

    @Column(name = "data_registro_ans")
    private LocalDate dataRegistroAns;

    @Column(name = "data_validade_registro_ans")
    private LocalDate dataValidadeRegistroAns;

    @Convert(converter = StatusAtivoEnumConverter.class)
    @Column(name = "status_ans")
    private StatusAtivoEnum statusAns;

    @Column(name = "razao_social_ans", length = 100)
    private String razaoSocialAns;

    @Column(name = "nome_fantasia_ans", length = 50)
    private String nomeFantasiaAns;

    @Column(name = "codigo_tiss", length = 50)
    private String codigoTiss;

    @Column(name = "habilitado_tiss", nullable = false)
    @Builder.Default
    private Boolean habilitadoTiss = false;

    @Column(name = "observacoes_ans", length = 255)
    private String observacoesAns;
}
