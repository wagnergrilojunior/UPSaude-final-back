package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

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

    @Schema(description = "JSON serializado com CIDs relacionados ao procedimento")
    private String cids;
    
    @Schema(description = "JSON serializado com CBOs (ocupações) relacionadas ao procedimento")
    private String cbos;
    
    @Schema(description = "JSON serializado com categorias CBO")
    private String categoriasCbo;
    
    @Schema(description = "JSON serializado com tipos de leito permitidos")
    private String tiposLeito;
    
    @Schema(description = "JSON serializado com serviços e classificações")
    private String servicosClassificacoes;
    
    @Schema(description = "JSON serializado com habilitações necessárias")
    private String habilitacoes;
    
    @Schema(description = "JSON serializado com grupos de habilitação")
    private String gruposHabilitacao;
    
    @Schema(description = "JSON serializado com incrementos aplicáveis")
    private String incrementos;
    
    @Schema(description = "JSON serializado com componentes da rede")
    private String componentesRede;
    
    @Schema(description = "JSON serializado com origens SIGTAP")
    private String origensSigtap;
    
    @Schema(description = "JSON serializado com origens SIA/SIH")
    private String origensSiaSih;
    
    @Schema(description = "JSON serializado com regras condicionadas")
    private String regrasCondicionadas;
    
    @Schema(description = "JSON serializado com RENASES relacionados")
    private String renases;
    
    @Schema(description = "JSON serializado com códigos TUSS relacionados")
    private String tuss;
}
