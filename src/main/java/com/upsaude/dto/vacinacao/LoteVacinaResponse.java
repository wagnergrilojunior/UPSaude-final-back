package com.upsaude.dto.vacinacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.upsaude.entity.vacinacao.LoteVacina;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoteVacinaResponse {

    private UUID id;
    private UUID imunobiologicoId;
    private String imunobiologicoNome;
    private UUID fabricanteId;
    private String fabricanteNome;
    private String numeroLote;
    private LocalDate dataFabricacao;
    private LocalDate dataValidade;
    private Integer quantidadeRecebida;
    private Integer quantidadeDisponivel;
    private BigDecimal precoUnitario;
    private String observacoes;
    private UUID estabelecimentoId;
    private String estabelecimentoNome;
    private Boolean ativo;
    private Boolean vencido;
    private Boolean disponivel;
    private OffsetDateTime criadoEm;
    private OffsetDateTime atualizadoEm;

    public static LoteVacinaResponse fromEntity(LoteVacina entity) {
        if (entity == null)
            return null;

        return LoteVacinaResponse.builder()
                .id(entity.getId())
                .imunobiologicoId(entity.getImunobiologico() != null ? entity.getImunobiologico().getId() : null)
                .imunobiologicoNome(entity.getImunobiologico() != null ? entity.getImunobiologico().getNome() : null)
                .fabricanteId(entity.getFabricante() != null ? entity.getFabricante().getId() : null)
                .fabricanteNome(entity.getFabricante() != null ? entity.getFabricante().getNome() : null)
                .numeroLote(entity.getNumeroLote())
                .dataFabricacao(entity.getDataFabricacao())
                .dataValidade(entity.getDataValidade())
                .quantidadeRecebida(entity.getQuantidadeRecebida())
                .quantidadeDisponivel(entity.getQuantidadeDisponivel())
                .precoUnitario(entity.getPrecoUnitario())
                .observacoes(entity.getObservacoes())
                .estabelecimentoId(entity.getEstabelecimento() != null ? entity.getEstabelecimento().getId() : null)
                .estabelecimentoNome(entity.getEstabelecimento() != null
                        && entity.getEstabelecimento().getDadosIdentificacao() != null
                                ? entity.getEstabelecimento().getDadosIdentificacao().getNomeFantasia()
                                : null)
                .ativo(entity.getAtivo())
                .vencido(entity.isVencido())
                .disponivel(entity.isDisponivel())
                .criadoEm(entity.getCriadoEm())
                .atualizadoEm(entity.getAtualizadoEm())
                .build();
    }
}
