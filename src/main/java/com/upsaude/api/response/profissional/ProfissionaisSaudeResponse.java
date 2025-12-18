package com.upsaude.api.response.profissional;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.upsaude.api.response.geral.EnderecoResponse;
import com.upsaude.enums.EscolaridadeEnum;
import com.upsaude.enums.EstadoCivilEnum;
import com.upsaude.enums.IdentidadeGeneroEnum;
import com.upsaude.enums.NacionalidadeEnum;
import com.upsaude.enums.RacaCorEnum;
import com.upsaude.enums.SexoEnum;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoDeficienciaEnum;
import com.upsaude.enums.TipoProfissionalEnum;

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
    private String nomeCompleto;
    private String cpf;
    private LocalDate dataNascimento;
    private SexoEnum sexo;
    private EstadoCivilEnum estadoCivil;
    private EscolaridadeEnum escolaridade;
    private IdentidadeGeneroEnum identidadeGenero;
    private RacaCorEnum racaCor;
    private TipoDeficienciaEnum tipoDeficiencia;
    private String rg;
    private String orgaoEmissorRg;
    private String ufEmissaoRg;
    private LocalDate dataEmissaoRg;
    private NacionalidadeEnum nacionalidade;
    private String naturalidade;
    private String registroProfissional;
    private ConselhosProfissionaisResponse conselho;
    private String ufRegistro;
    private OffsetDateTime dataEmissaoRegistro;
    private OffsetDateTime dataValidadeRegistro;
    private StatusAtivoEnum statusRegistro;
    private TipoProfissionalEnum tipoProfissional;
    private String cns;
    private String codigoCbo;
    private String descricaoCbo;
    private String codigoOcupacional;
    private String telefone;
    private String email;
    private String telefoneInstitucional;
    private String emailInstitucional;
    private EnderecoResponse enderecoProfissional;

    @Builder.Default
    private List<EspecialidadesMedicasResponse> especialidades = new ArrayList<>();

    @Builder.Default
    private Set<HistoricoHabilitacaoProfissionalResponse> historicoHabilitacao = new HashSet<>();

    private String observacoes;
}
