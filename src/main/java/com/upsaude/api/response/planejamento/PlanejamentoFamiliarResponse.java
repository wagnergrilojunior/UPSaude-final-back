package com.upsaude.api.response.planejamento;
import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;
import com.upsaude.api.response.paciente.PacienteResponse;

import com.upsaude.enums.TipoMetodoContraceptivoEnum;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanejamentoFamiliarResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteResponse paciente;
    private ProfissionaisSaudeResponse profissionalResponsavel;
    private EquipeSaudeResponse equipeSaude;
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
