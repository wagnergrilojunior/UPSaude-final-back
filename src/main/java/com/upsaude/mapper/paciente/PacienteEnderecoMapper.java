package com.upsaude.mapper.paciente;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.api.request.paciente.PacienteEnderecoRequest;
import com.upsaude.entity.paciente.PacienteEndereco;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.api.response.geral.EnderecoResponse;
import com.upsaude.mapper.geral.EnderecoMapper;

@Mapper(config = MappingConfig.class, uses = { EnderecoMapper.class })
public interface PacienteEnderecoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "endereco", source = "dadosEndereco")
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    PacienteEndereco fromRequest(PacienteEnderecoRequest request);

    @Mapping(target = "id", source = "endereco.id")
    @Mapping(target = "createdAt", source = "endereco.createdAt")
    @Mapping(target = "updatedAt", source = "endereco.updatedAt")
    @Mapping(target = "active", source = "endereco.active")
    @Mapping(target = "tipoLogradouro", source = "endereco.tipoLogradouro")
    @Mapping(target = "logradouro", source = "endereco.logradouro")
    @Mapping(target = "numero", source = "endereco.numero")
    @Mapping(target = "complemento", source = "endereco.complemento")
    @Mapping(target = "bairro", source = "endereco.bairro")
    @Mapping(target = "cep", source = "endereco.cep")
    @Mapping(target = "pais", source = "endereco.pais")
    @Mapping(target = "distrito", source = "endereco.distrito")
    @Mapping(target = "pontoReferencia", source = "endereco.pontoReferencia")
    @Mapping(target = "latitude", source = "endereco.latitude")
    @Mapping(target = "longitude", source = "endereco.longitude")
    @Mapping(target = "tipoEndereco", source = "endereco.tipoEndereco")
    @Mapping(target = "zona", source = "endereco.zona")
    @Mapping(target = "codigoIbgeMunicipio", source = "endereco.codigoIbgeMunicipio")
    @Mapping(target = "quadra", source = "endereco.quadra")
    @Mapping(target = "lote", source = "endereco.lote")
    @Mapping(target = "zonaRuralDescricao", source = "endereco.zonaRuralDescricao")
    @Mapping(target = "andar", source = "endereco.andar")
    @Mapping(target = "bloco", source = "endereco.bloco")
    @Mapping(target = "semNumero", source = "endereco.semNumero")
    @Mapping(target = "cidade", source = "endereco.cidade")
    @Mapping(target = "estado", source = "endereco.cidade.estado")
    EnderecoResponse toResponse(PacienteEndereco entity);
}
