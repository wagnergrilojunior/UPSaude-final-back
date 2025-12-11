package com.upsaude.entity.embeddable;

import com.upsaude.enums.TipoControleMedicamentoEnum;
import com.upsaude.util.converter.TipoControleMedicamentoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class RegistroControleMedicamento {

    public RegistroControleMedicamento() {
        this.registroAnvisa = "";
        this.receitaObrigatoria = false;
        this.controlado = false;
        this.usoContinuo = false;
        this.medicamentoEspecial = false;
        this.medicamentoExcepcional = false;
    }

    @Size(max = 50, message = "Registro ANVISA deve ter no máximo 50 caracteres")
    @Column(name = "registro_anvisa", length = 50)
    private String registroAnvisa;

    @Column(name = "data_registro_anvisa")
    private LocalDate dataRegistroAnvisa;

    @Column(name = "data_validade_registro_anvisa")
    private LocalDate dataValidadeRegistroAnvisa;

    @Convert(converter = TipoControleMedicamentoEnumConverter.class)
    @Column(name = "tipo_controle")
    private TipoControleMedicamentoEnum tipoControle;

    @Column(name = "receita_obrigatoria", nullable = false)
    @Builder.Default
    private Boolean receitaObrigatoria = false;

    @Column(name = "controlado", nullable = false)
    @Builder.Default
    private Boolean controlado = false;

    @Column(name = "uso_continuo", nullable = false)
    @Builder.Default
    private Boolean usoContinuo = false;

    @Column(name = "medicamento_especial", nullable = false)
    @Builder.Default
    private Boolean medicamentoEspecial = false;

    @Column(name = "medicamento_excepcional", nullable = false)
    @Builder.Default
    private Boolean medicamentoExcepcional = false;
}
