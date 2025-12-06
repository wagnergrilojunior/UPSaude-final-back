package com.upsaude.entity.embeddable;

import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.util.converter.StatusAtivoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Classe embeddable para informações de registro na ANS (Agência Nacional de Saúde Suplementar).
 *
 * @author UPSaúde
 */
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

    @Size(max = 50, message = "Registro ANS deve ter no máximo 50 caracteres")
    @Column(name = "registro_ans", length = 50)
    private String registroAns; // Número de registro na ANS

    @Size(max = 50, message = "Código ANS deve ter no máximo 50 caracteres")
    @Column(name = "codigo_ans", length = 50)
    private String codigoAns; // Código da operadora na ANS

    @Column(name = "data_registro_ans")
    private LocalDate dataRegistroAns; // Data de registro na ANS

    @Column(name = "data_validade_registro_ans")
    private LocalDate dataValidadeRegistroAns; // Data de validade do registro

    @Convert(converter = StatusAtivoEnumConverter.class)
    @Column(name = "status_ans")
    private StatusAtivoEnum statusAns; // Status do registro na ANS

    @Size(max = 100, message = "Razão social ANS deve ter no máximo 100 caracteres")
    @Column(name = "razao_social_ans", length = 100)
    private String razaoSocialAns; // Razão social conforme ANS

    @Size(max = 50, message = "Nome fantasia ANS deve ter no máximo 50 caracteres")
    @Column(name = "nome_fantasia_ans", length = 50)
    private String nomeFantasiaAns; // Nome fantasia conforme ANS

    @Size(max = 50, message = "Código TISS deve ter no máximo 50 caracteres")
    @Column(name = "codigo_tiss", length = 50)
    private String codigoTiss; // Código TISS (Troca de Informação em Saúde Suplementar)

    @Column(name = "habilitado_tiss", nullable = false)
    @Builder.Default
    private Boolean habilitadoTiss = false; // Se está habilitado para TISS

    @Size(max = 255, message = "Observações ANS deve ter no máximo 255 caracteres")
    @Column(name = "observacoes_ans", length = 255)
    private String observacoesAns; // Observações sobre o registro ANS
}

