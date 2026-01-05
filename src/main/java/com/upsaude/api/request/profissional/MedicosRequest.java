package com.upsaude.api.request.profissional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.upsaude.api.request.embeddable.ContatoMedicoRequest;
import com.upsaude.api.request.embeddable.DadosDemograficosMedicoRequest;
import com.upsaude.api.request.embeddable.DadosPessoaisBasicosMedicoRequest;
import com.upsaude.api.request.embeddable.DocumentosBasicosMedicoRequest;
import com.upsaude.api.request.embeddable.RegistroProfissionalMedicoRequest;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de médicos")
public class MedicosRequest {

    @Schema(description = "Lista de IDs de especialidades (CBO) para associar ao médico no cadastro inicial. " +
            "Os IDs devem corresponder a CBOs válidos da tabela sigtap_ocupacao. " +
            "Exemplo: [\"ee0e8400-e29b-41d4-a716-446655440009\", \"ee0e8400-e29b-41d4-a716-446655440010\"]")
    @Builder.Default
    private Set<UUID> especialidades = new HashSet<>();

    @Valid
    private DadosPessoaisBasicosMedicoRequest dadosPessoaisBasicos;

    @Valid
    private DocumentosBasicosMedicoRequest documentosBasicos;

    @Valid
    private DadosDemograficosMedicoRequest dadosDemograficos;

    @Valid
    private RegistroProfissionalMedicoRequest registroProfissional;

    @Valid
    private ContatoMedicoRequest contato;

    private UUID enderecoMedico;

    @Schema(description = "ID do estabelecimento para vincular o médico no cadastro inicial")
    private UUID estabelecimentoId;

    @Schema(description = "Tipo de vínculo profissional (1=EFETIVO, 2=CONTRATO, 3=TEMPORARIO, etc.). Padrão: 1 (EFETIVO)")
    private Integer tipoVinculo;

    private String observacoes;
}
