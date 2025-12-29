package com.upsaude.entity.embeddable;

import com.upsaude.entity.profissional.ProfissionaisSaude;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsaveisEstabelecimento {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavel_tecnico_id")
    private ProfissionaisSaude responsavelTecnico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavel_administrativo_id")
    private ProfissionaisSaude responsavelAdministrativo;

    @Column(name = "responsavel_legal_nome", length = 255)
    private String responsavelLegalNome;

    @Column(name = "responsavel_legal_cpf", length = 11)
    private String responsavelLegalCpf;
}

