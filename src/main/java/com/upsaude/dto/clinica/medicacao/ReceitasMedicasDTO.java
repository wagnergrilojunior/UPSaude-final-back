package com.upsaude.dto.clinica.medicacao;

import com.upsaude.enums.StatusReceitaEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import com.upsaude.dto.estabelecimento.EstabelecimentosDTO;
import com.upsaude.dto.profissional.MedicosDTO;
import com.upsaude.dto.paciente.PacienteDTO;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceitasMedicasDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private EstabelecimentosDTO estabelecimento;
    private MedicosDTO medico;
    private PacienteDTO paciente;
    private String numeroReceita;
    private OffsetDateTime dataPrescricao;
    private OffsetDateTime dataValidade;
    private Boolean usoContinuo;
    private String observacoes;
    private StatusReceitaEnum status;
    private String origemReceita;
}
