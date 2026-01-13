package com.upsaude.api.response.clinica.prontuario;

import com.upsaude.api.response.referencia.cid.Cid10SubcategoriaResponse;
import com.upsaude.dto.alergia.AlergenoResponse;
import com.upsaude.dto.alergia.CategoriaAgenteAlergiaResponse;
import com.upsaude.dto.alergia.CriticidadeAlergiaResponse;
import com.upsaude.dto.alergia.ReacaoAdversaResponse;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
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
public class AlergiaPacienteResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String tipoAlergia;
    private String descricao;
    private LocalDate dataDiagnostico;
    private String gravidade;
    private String observacoes;
    private Cid10SubcategoriaResponse diagnosticoRelacionado;
    private AlergenoResponse alergeno;
    private ReacaoAdversaResponse reacaoAdversaCatalogo;
    private CriticidadeAlergiaResponse criticidade;
    private CategoriaAgenteAlergiaResponse categoriaAgente;
    private String clinicalStatus;
    private String verificationStatus;
    private String grauCerteza;
}
