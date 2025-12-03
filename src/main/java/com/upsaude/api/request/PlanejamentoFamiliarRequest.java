package com.upsaude.api.request;

import com.upsaude.enums.TipoMetodoContraceptivoEnum;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanejamentoFamiliarRequest {
    private UUID paciente;
    private UUID profissionalResponsavel;
    private UUID equipeSaude;
    private TipoMetodoContraceptivoEnum metodoAtual;
    private LocalDate dataInicioMetodo;
    private String nomeComercialMetodo;
    private TipoMetodoContraceptivoEnum metodoAnterior;
    private String motivoTrocaMetodo;
    private Integer numeroGestacoes;
    private Integer numeroPartos;
    private Integer numeroAbortos;
    private Integer numeroFilhosVivos;
    private LocalDate dataUltimoParto;
    private Boolean ultimaGestacaoPlanejada;
    private Boolean desejaEngravidar;
    private String prazoDesejoGestacao;
    private Boolean desejaMetodoDefinitivo;
    private Boolean temContraindicacoes;
    private String contraindicacoes;
    private String doencasPreexistentes;
    private String medicamentosUso;
    private String alergias;
    private Boolean cicloMenstrualRegular;
    private Integer duracaoCiclo;
    private LocalDate dataUltimaMenstruacao;
    private Boolean dismenorreia;
    private Boolean sangramentoIrregular;
    private LocalDate dataInicioAcompanhamento;
    private OffsetDateTime dataProximaConsulta;
    private LocalDate dataProximaDispensacao;
    private LocalDate dataInsercaoDiu;
    private LocalDate dataValidadeDiu;
    private LocalDate dataCirurgia;
    private String localCirurgia;
    private Boolean documentacaoCompleta;
    private Boolean prazoMinimoCumprido;
    private Boolean orientacaoMetodosRealizada;
    private LocalDate dataOrientacao;
    private Boolean consentimentoInformado;
    private LocalDate dataConsentimento;
    private String observacoes;
}
