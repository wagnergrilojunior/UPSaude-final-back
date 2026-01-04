package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformacoesConvenioPaciente {

    @Column(name = "numero_carteirinha", length = 50)
    private String numeroCarteirinha;

    @Column(name = "data_validade_carteirinha")
    private LocalDate dataValidadeCarteirinha;
}

