package com.upsaude.entity.embeddable;

import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.util.converter.StatusAtivoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroProfissional {

    @Column(name = "registro_profissional", nullable = false, length = 20)
    private String registroProfissional;

    @Column(name = "uf_registro", length = 2)
    private String ufRegistro;

    @Column(name = "data_emissao_registro")
    private OffsetDateTime dataEmissaoRegistro;

    @Column(name = "data_validade_registro")
    private OffsetDateTime dataValidadeRegistro;

    @Convert(converter = StatusAtivoEnumConverter.class)
    @Column(name = "status_registro")
    private StatusAtivoEnum statusRegistro;
}

