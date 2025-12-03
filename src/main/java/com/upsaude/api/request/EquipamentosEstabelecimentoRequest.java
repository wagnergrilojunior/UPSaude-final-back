package com.upsaude.api.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipamentosEstabelecimentoRequest {
    private UUID estabelecimento;
    private UUID equipamento;
    private String codigoInterno;
    private String numeroSerie;
    private String numeroPatrimonio;
    private String numeroTombo;
    private Integer quantidadeDisponivel;
    private Integer quantidadeEmManutencao;
    private Integer quantidadeInativa;
    private String setorDepartamento;
    private String salaAmbiente;
    private String andar;
    private String localizacaoFisica;
    private LocalDate dataUltimaManutencao;
    private LocalDate dataProximaManutencao;
    private LocalDate dataUltimaCalibracao;
    private LocalDate dataProximaCalibracao;
    private String empresaManutencao;
    private String contatoManutencao;
    private LocalDate dataAquisicao;
    private LocalDate dataInstalacao;
    private LocalDate dataInicioGarantia;
    private LocalDate dataFimGarantia;
    private BigDecimal valorAquisicao;
    private String numeroNotaFiscal;
    private String responsavelTecnico;
    private String registroResponsavel;
    private Integer horasUsoTotal;
    private Integer horasUsoUltimoMes;
    private String observacoes;
    private String historicoManutencao;
    private String problemasConhecidos;
}
