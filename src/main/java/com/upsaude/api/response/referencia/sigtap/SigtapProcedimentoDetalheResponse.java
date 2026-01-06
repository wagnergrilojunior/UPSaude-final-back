package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "Resposta com detalhes adicionais de um procedimento SIGTAP")
public class SigtapProcedimentoDetalheResponse {
    @Schema(description = "Identificador único do detalhe", example = "660e8400-e29b-41d4-a716-446655440001")
    private UUID id;

    @Schema(description = "ID do procedimento relacionado", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID procedimentoId;

    @Schema(description = "Competência inicial de vigência (formato AAAAMM)", example = "202501")
    private String competenciaInicial;

    @Schema(description = "Competência final de vigência (formato AAAAMM). Null se ainda está vigente")
    private String competenciaFinal;

    @Schema(description = "Descrição completa do procedimento")
    private String descricaoCompleta;

    @Schema(description = "JSON serializado com CIDs relacionados ao procedimento (legado)")
    private String cids;

    @Schema(description = "JSON serializado com CBOs (ocupações) relacionadas ao procedimento (legado)")
    private String cbos;

    @Schema(description = "JSON serializado com categorias CBO (legado)")
    private String categoriasCbo;

    @Schema(description = "JSON serializado com tipos de leito permitidos (legado)")
    private String tiposLeito;

    @Schema(description = "JSON serializado com serviços e classificações (legado)")
    private String servicosClassificacoes;

    @Schema(description = "JSON serializado com habilitações necessárias (legado)")
    private String habilitacoes;

    @Schema(description = "JSON serializado com grupos de habilitação (legado)")
    private String gruposHabilitacao;

    @Schema(description = "JSON serializado com incrementos aplicáveis (legado)")
    private String incrementos;

    @Schema(description = "JSON serializado com componentes da rede (legado)")
    private String componentesRede;

    @Schema(description = "JSON serializado com origens SIGTAP (legado)")
    private String origensSigtap;

    @Schema(description = "JSON serializado com origens SIA/SIH (legado)")
    private String origensSiaSih;

    @Schema(description = "JSON serializado com regras condicionadas (legado)")
    private String regrasCondicionadas;

    @Schema(description = "JSON serializado com RENASES relacionados (legado)")
    private String renases;

    @Schema(description = "JSON serializado com códigos TUSS relacionados (legado)")
    private String tuss;

    @Schema(description = "Lista de CIDs relacionados ao procedimento")
    private List<SigtapProcedimentoDetalheCidResponse> listaCids;

    @Schema(description = "Lista de CBOs (ocupações) relacionadas ao procedimento")
    private List<SigtapProcedimentoDetalheCboResponse> listaCbos;

    @Schema(description = "Lista de tipos de leito permitidos")
    private List<SigtapProcedimentoDetalheLeitoResponse> listaLeitos;

    @Schema(description = "Lista de serviços e classificações")
    private List<SigtapProcedimentoDetalheServicoResponse> listaServicosClassificacoes;

    @Schema(description = "Lista de habilitações necessárias")
    private List<SigtapProcedimentoDetalheHabilitacaoResponse> listaHabilitacoes;

    @Schema(description = "Lista de componentes de rede")
    private List<SigtapProcedimentoDetalheRedeResponse> listaRedes;

    @Schema(description = "Lista de procedimentos origem")
    private List<SigtapProcedimentoDetalheOrigemResponse> listaOrigens;

    @Schema(description = "Lista de regras condicionadas")
    private List<SigtapProcedimentoDetalheRegraCondicionadaResponse> listaRegrasCondicionadas;

    @Schema(description = "Lista de RENASES relacionados")
    private List<SigtapProcedimentoDetalheRenasesResponse> listaRenases;

    @Schema(description = "Lista de códigos TUSS relacionados")
    private List<SigtapProcedimentoDetalheTussResponse> listaTuss;

    @Schema(description = "Lista de modalidades de atendimento")
    private List<SigtapProcedimentoDetalheModalidadeResponse> listaModalidades;

    @Schema(description = "Lista de instrumentos de registro")
    private List<SigtapProcedimentoDetalheRegistroResponse> listaRegistros;
}
