package com.upsaude.api.request;

import com.upsaude.enums.EscolaridadeEnum;
import com.upsaude.enums.EstadoCivilEnum;
import com.upsaude.enums.IdentidadeGeneroEnum;
import com.upsaude.enums.NacionalidadeEnum;
import com.upsaude.enums.RacaCorEnum;
import com.upsaude.enums.SexoEnum;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoDeficienciaEnum;
import com.upsaude.enums.TipoProfissionalEnum;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfissionaisSaudeRequest {
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
    private UUID conselho;
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
    private UUID enderecoProfissional;
    private String observacoes;
}
