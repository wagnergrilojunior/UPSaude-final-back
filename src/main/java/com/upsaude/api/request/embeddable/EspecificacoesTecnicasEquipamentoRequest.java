package com.upsaude.api.request.embeddable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Especificações técnicas do equipamento")
public class EspecificacoesTecnicasEquipamentoRequest {

    @Size(max = 50, message = "Modelo deve ter no máximo 50 caracteres")
    private String modelo;

    @Size(max = 50, message = "Versão deve ter no máximo 50 caracteres")
    private String versao;

    private BigDecimal potencia;

    @Size(max = 50, message = "Unidade de potência deve ter no máximo 50 caracteres")
    private String unidadePotencia;

    private BigDecimal peso;

    private BigDecimal altura;

    private BigDecimal largura;

    private BigDecimal profundidade;

    @Size(max = 50, message = "Tensão elétrica deve ter no máximo 50 caracteres")
    private String tensaoEletrica;

    @Size(max = 50, message = "Frequência deve ter no máximo 50 caracteres")
    private String frequencia;

    @Size(max = 50, message = "Corrente deve ter no máximo 50 caracteres")
    private String corrente;

    @Size(max = 100, message = "Tipo de alimentação deve ter no máximo 100 caracteres")
    private String tipoAlimentacao;
}

