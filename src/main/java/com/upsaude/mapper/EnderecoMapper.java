package com.upsaude.mapper;

import com.upsaude.api.request.EnderecoRequest;
import com.upsaude.api.response.EnderecoResponse;
import com.upsaude.dto.EnderecoDTO;
import com.upsaude.entity.Endereco;
import com.upsaude.entity.Cidades;
import com.upsaude.entity.Estados;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de Endereco.
 * Entity ↔ DTO ↔ Request/Response
 * 
 * IMPORTANTE: Este mapper apenas converte dados, NUNCA persiste endereços.
 * Para criar/atualizar endereços, sempre use EnderecoService.findOrCreate() no Service.
 * Nunca use enderecoRepository.save() diretamente após usar este mapper.
 */
@Mapper(config = MappingConfig.class, uses = {CidadesMapper.class, EstadosMapper.class})
public interface EnderecoMapper extends EntityMapper<Endereco, EnderecoDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     * 
     * NOTA: Este método não cria IDs automaticamente. O ID será gerado pelo JPA ao persistir.
     */
    @Mapping(target = "active", ignore = true)
    Endereco toEntity(EnderecoDTO dto);

    /**
     * Converte Entity para DTO.
     */
    EnderecoDTO toDTO(Endereco entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     * 
     * IMPORTANTE: Este método NÃO cria IDs. O ID será null até que o endereço seja persistido.
     * Após usar este método, sempre use EnderecoService.findOrCreate() para evitar duplicados.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cidade", ignore = true)
    @Mapping(target = "estado", ignore = true)
    Endereco fromRequest(EnderecoRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cidade", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void updateFromRequest(EnderecoRequest request, @MappingTarget Endereco entity);

    /**
     * Converte Entity para Response.
     */
    EnderecoResponse toResponse(Endereco entity);
}
