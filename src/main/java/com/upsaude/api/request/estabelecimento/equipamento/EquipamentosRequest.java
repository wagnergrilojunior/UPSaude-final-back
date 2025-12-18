package com.upsaude.api.request.estabelecimento.equipamento;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoEquipamentoEnum;
import com.upsaude.util.converter.StatusAtivoEnumDeserializer;
import com.upsaude.util.converter.TipoEquipamentoEnumDeserializer;
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
@Schema(description = "Dados de equipamentos")
public class EquipamentosRequest {
    private String nome;
    private String nomeComercial;
    private String codigoInterno;
    private String codigoCnes;
    private String registroAnvisa;
    @JsonDeserialize(using = TipoEquipamentoEnumDeserializer.class)
    private TipoEquipamentoEnum tipo;
    private String categoria;
    private String subcategoria;
    private String classeRisco;
    private UUID fabricante;
    private String modelo;
    private String versao;
    private BigDecimal potencia;
    private String unidadePotencia;
    private BigDecimal peso;
    private BigDecimal altura;
    private BigDecimal largura;
    private BigDecimal profundidade;
    private String tensaoEletrica;
    private String frequencia;
    private String corrente;
    private String tipoAlimentacao;
    private String certificacaoIso;
    private String certificacaoCe;
    private String certificacaoFda;
    private LocalDate dataCertificacao;
    private LocalDate dataValidadeCertificacao;
    private Integer periodoCalibracaoMeses;
    private Integer periodoManutencaoMeses;
    private String tipoManutencao;
    private BigDecimal valorAquisicao;
    private LocalDate dataAquisicao;
    private String fornecedor;
    private String numeroNotaFiscal;
    private String numeroContrato;
    private Integer tempoGarantiaMeses;
    private LocalDate dataInicioGarantia;
    private LocalDate dataFimGarantia;
    private String condicoesGarantia;
    @JsonDeserialize(using = StatusAtivoEnumDeserializer.class)
    private StatusAtivoEnum status;
    private String manualTecnico;
    private String manualUsuario;
    private String fichaTecnica;
    private String descricao;
    private String caracteristicas;
    private String aplicacoes;
    private String observacoes;
}
