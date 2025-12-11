package com.upsaude.api.request.embeddable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.SeveridadeAlergiaEnum;
import com.upsaude.enums.TipoReacaoAlergicaEnum;
import com.upsaude.util.converter.SeveridadeAlergiaEnumDeserializer;
import com.upsaude.util.converter.TipoReacaoAlergicaEnumDeserializer;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticoAlergiaPacienteRequest {
    private LocalDate dataDiagnostico;
    private LocalDate dataPrimeiraReacao;
    @JsonDeserialize(using = SeveridadeAlergiaEnumDeserializer.class)
    private SeveridadeAlergiaEnum severidade;
    
    @JsonDeserialize(using = TipoReacaoAlergicaEnumDeserializer.class)
    private TipoReacaoAlergicaEnum tipoReacaoObservada;
    
    @Size(max = 255, message = "Método diagnóstico deve ter no máximo 255 caracteres")
    private String metodoDiagnostico;
    
    @Size(max = 255, message = "Local diagnóstico deve ter no máximo 255 caracteres")
    private String localDiagnostico;
    
    @Size(max = 255, message = "Profissional diagnóstico deve ter no máximo 255 caracteres")
    private String profissionalDiagnostico;
}
