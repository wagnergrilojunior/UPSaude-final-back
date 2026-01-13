package com.upsaude.dto.vacinacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.upsaude.entity.vacinacao.AplicacaoVacina;
import com.upsaude.entity.vacinacao.StatusAplicacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AplicacaoVacinaResponse {

        private UUID id;
        private String fhirIdentifier;
        private StatusAplicacao fhirStatus;

        private UUID pacienteId;
        private String pacienteNome;

        private UUID imunobiologicoId;
        private String imunobiologicoNome;

        private UUID loteId;
        private String numeroLote;
        private UUID fabricanteId;
        private String fabricanteNome;
        private LocalDate dataValidade;

        private UUID tipoDoseId;
        private String tipoDoseNome;
        private Integer numeroDose;
        private BigDecimal doseQuantidade;
        private String doseUnidade;

        private UUID localAplicacaoId;
        private String localAplicacaoNome;
        private UUID viaAdministracaoId;
        private String viaAdministracaoNome;
        private UUID estrategiaId;
        private String estrategiaNome;

        private OffsetDateTime dataAplicacao;
        private OffsetDateTime dataRegistro;

        private UUID profissionalId;
        private String profissionalNome;
        private String profissionalFuncao;

        private UUID estabelecimentoId;
        private String estabelecimentoNome;

        private Boolean fontePrimaria;
        private String origemRegistro;
        private Boolean doseSubpotente;
        private String motivoSubpotencia;
        private String elegibilidadePrograma;
        private String fonteFinanciamento;
        private String observacoes;

        private Boolean ativo;
        private OffsetDateTime criadoEm;
        private OffsetDateTime atualizadoEm;

        private List<ReacaoResponse> reacoes;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ReacaoResponse {
                private UUID id;
                private String tipoReacao;
                private String gravidade;
                private OffsetDateTime dataOcorrencia;
                private String descricao;
                private String tratamento;
                private String evolucao;
        }

        public static AplicacaoVacinaResponse fromEntity(AplicacaoVacina entity) {
                if (entity == null)
                        return null;

                return AplicacaoVacinaResponse.builder()
                                .id(entity.getId())
                                .fhirIdentifier(entity.getFhirIdentifier())
                                .fhirStatus(entity.getFhirStatus())
                                .pacienteId(entity.getPaciente() != null ? entity.getPaciente().getId() : null)
                                .pacienteNome(entity.getPaciente() != null ? entity.getPaciente().getNomeCompleto()
                                                : null)
                                .imunobiologicoId(
                                                entity.getImunobiologico() != null ? entity.getImunobiologico().getId()
                                                                : null)
                                .imunobiologicoNome(entity.getImunobiologico() != null
                                                ? entity.getImunobiologico().getNome()
                                                : null)
                                .loteId(entity.getLote() != null ? entity.getLote().getId() : null)
                                .numeroLote(entity.getNumeroLote())
                                .fabricanteId(entity.getFabricante() != null ? entity.getFabricante().getId() : null)
                                .fabricanteNome(entity.getFabricante() != null ? entity.getFabricante().getNome()
                                                : null)
                                .dataValidade(entity.getDataValidade())
                                .tipoDoseId(entity.getTipoDose() != null ? entity.getTipoDose().getId() : null)
                                .tipoDoseNome(entity.getTipoDose() != null ? entity.getTipoDose().getNome() : null)
                                .numeroDose(entity.getNumeroDose())
                                .doseQuantidade(entity.getDoseQuantidade())
                                .doseUnidade(entity.getDoseUnidade())
                                .localAplicacaoId(
                                                entity.getLocalAplicacao() != null ? entity.getLocalAplicacao().getId()
                                                                : null)
                                .localAplicacaoNome(entity.getLocalAplicacao() != null
                                                ? entity.getLocalAplicacao().getNome()
                                                : null)
                                .viaAdministracaoId(entity.getViaAdministracao() != null
                                                ? entity.getViaAdministracao().getId()
                                                : null)
                                .viaAdministracaoNome(
                                                entity.getViaAdministracao() != null
                                                                ? entity.getViaAdministracao().getNome()
                                                                : null)
                                .estrategiaId(entity.getEstrategia() != null ? entity.getEstrategia().getId() : null)
                                .estrategiaNome(entity.getEstrategia() != null ? entity.getEstrategia().getNome()
                                                : null)
                                .dataAplicacao(entity.getDataAplicacao())
                                .dataRegistro(entity.getDataRegistro())
                                .profissionalId(entity.getProfissional() != null ? entity.getProfissional().getId()
                                                : null)
                                .profissionalNome(entity.getProfissional() != null
                                                && entity.getProfissional().getDadosPessoaisBasicos() != null
                                                                ? entity.getProfissional().getDadosPessoaisBasicos()
                                                                                .getNomeCompleto()
                                                                : null)
                                .profissionalFuncao(entity.getProfissionalFuncao())
                                .estabelecimentoId(entity.getEstabelecimento() != null
                                                ? entity.getEstabelecimento().getId()
                                                : null)
                                .estabelecimentoNome(entity.getEstabelecimento() != null
                                                && entity.getEstabelecimento().getDadosIdentificacao() != null
                                                                ? entity.getEstabelecimento().getDadosIdentificacao()
                                                                                .getNomeFantasia()
                                                                : null)
                                .fontePrimaria(entity.getFontePrimaria())
                                .origemRegistro(entity.getOrigemRegistro())
                                .doseSubpotente(entity.getDoseSubpotente())
                                .motivoSubpotencia(entity.getMotivoSubpotencia())
                                .elegibilidadePrograma(entity.getElegibilidadePrograma())
                                .fonteFinanciamento(entity.getFonteFinanciamento())
                                .observacoes(entity.getObservacoes())
                                .ativo(entity.getAtivo())
                                .criadoEm(entity.getCriadoEm())
                                .atualizadoEm(entity.getAtualizadoEm())
                                .reacoes(entity.getReacoes() != null ? entity.getReacoes().stream()
                                                .map(r -> ReacaoResponse.builder()
                                                                .id(r.getId())
                                                                .tipoReacao(r.getNomeReacao())
                                                                .gravidade(r.getCriticidade())
                                                                .dataOcorrencia(r.getDataOcorrencia())
                                                                .descricao(r.getDescricao())
                                                                .tratamento(r.getTratamentoRealizado())
                                                                .evolucao(r.getEvolucao())
                                                                .build())
                                                .collect(Collectors.toList()) : null)
                                .build();
        }
}
