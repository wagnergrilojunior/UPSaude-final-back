package com.upsaude.api.request;

import com.upsaude.enums.TipoLogradouroEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoRequest {
    private TipoLogradouroEnum tipoLogradouro;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String pais;
    private String codigoIbge;
    private Double latitude;
    private Double longitude;
    private UUID estadoId;
    private UUID cidadeId;
}

