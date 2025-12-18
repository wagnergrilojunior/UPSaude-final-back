package com.upsaude.api.request.saude_publica.planejamento;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.ClassificacaoRiscoGestacionalEnum;
import com.upsaude.util.converter.ClassificacaoRiscoGestacionalEnumDeserializer;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de pré-natal")
public class PreNatalRequest {
    private UUID paciente;
    private UUID profissionalResponsavel;
    private UUID equipeSaude;
    private LocalDate dataUltimaMenstruacao;
    private LocalDate dataProvavelParto;
    private Integer idadeGestacionalCadastro;
    private Integer gestacoesAnteriores;
    private Integer partos;
    private Integer abortos;
    private Integer filhosVivos;
    private Integer partosVaginais;
    private Integer cesareas;
    private Integer partosPrematuros;
    private Integer natimortos;
    @JsonDeserialize(using = ClassificacaoRiscoGestacionalEnumDeserializer.class)
    private ClassificacaoRiscoGestacionalEnum classificacaoRisco;
    private String motivosAltoRisco;
    private LocalDate dataInicioAcompanhamento;
    private LocalDate dataEncerramento;
    private OffsetDateTime dataParto;
    private Integer idadeGestacionalParto;
    private String tipoParto;
    private String localParto;
    private BigDecimal pesoNascimento;
    private BigDecimal comprimentoNascimento;
    private Integer apgar1Minuto;
    private Integer apgar5Minutos;
    private BigDecimal perimetroCefalico;
    private String tipoSanguineo;
    private String fatorRh;
    private BigDecimal pesoPreGestacional;
    private BigDecimal altura;
    private BigDecimal imcPreGestacional;
    private LocalDate dataPrimeiraUltrassonografia;
    private String antecedentesFamiliares;
    private String antecedentesPessoais;
    private String observacoes;
    private String observacoesInternas;
}
