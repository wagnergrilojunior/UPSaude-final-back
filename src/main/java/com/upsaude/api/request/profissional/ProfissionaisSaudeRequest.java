package com.upsaude.api.request.profissional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.upsaude.api.request.embeddable.ContatoProfissionalRequest;
import com.upsaude.api.request.embeddable.DadosDeficienciaProfissionalRequest;
import com.upsaude.api.request.embeddable.DadosDemograficosProfissionalRequest;
import com.upsaude.api.request.embeddable.DadosPessoaisBasicosProfissionalRequest;
import com.upsaude.api.request.embeddable.DocumentosBasicosProfissionalRequest;
import com.upsaude.api.request.embeddable.RegistroProfissionalRequest;
import com.upsaude.api.request.geral.EnderecoRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados de profissionais de saúde")
public class ProfissionaisSaudeRequest {

    @Valid
    private DadosPessoaisBasicosProfissionalRequest dadosPessoaisBasicos;

    @Valid
    private DocumentosBasicosProfissionalRequest documentosBasicos;

    @Valid
    private DadosDemograficosProfissionalRequest dadosDemograficos;

    @Valid
    private DadosDeficienciaProfissionalRequest dadosDeficiencia;

    @Valid
    private RegistroProfissionalRequest registroProfissional;

    private UUID sigtapOcupacao;

    @Valid
    private ContatoProfissionalRequest contato;

    private UUID enderecoProfissional;

    @Deprecated
    @Valid
    private EnderecoRequest enderecoProfissionalCompleto;

    @Builder.Default
    private Set<UUID> especialidades = new HashSet<>();

    private String observacoes;
}
