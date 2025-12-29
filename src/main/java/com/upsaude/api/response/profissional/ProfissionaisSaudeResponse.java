package com.upsaude.api.response.profissional;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.upsaude.api.response.embeddable.ContatoProfissionalResponse;
import com.upsaude.api.response.embeddable.DadosDeficienciaProfissionalResponse;
import com.upsaude.api.response.embeddable.DadosDemograficosProfissionalResponse;
import com.upsaude.api.response.embeddable.DadosPessoaisBasicosProfissionalResponse;
import com.upsaude.api.response.embeddable.DocumentosBasicosProfissionalResponse;
import com.upsaude.api.response.embeddable.RegistroProfissionalResponse;
import com.upsaude.api.response.geral.EnderecoResponse;

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
public class ProfissionaisSaudeResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private DadosPessoaisBasicosProfissionalResponse dadosPessoaisBasicos;
    private DocumentosBasicosProfissionalResponse documentosBasicos;
    private DadosDemograficosProfissionalResponse dadosDemograficos;
    private DadosDeficienciaProfissionalResponse dadosDeficiencia;
    private RegistroProfissionalResponse registroProfissional;
    private UUID sigtapOcupacao;
    private ContatoProfissionalResponse contato;
    private EnderecoResponse enderecoProfissional;
    private String observacoes;
}
