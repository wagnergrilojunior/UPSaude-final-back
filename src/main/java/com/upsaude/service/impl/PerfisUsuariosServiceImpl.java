package com.upsaude.service.impl;

import com.upsaude.api.request.PerfisUsuariosRequest;
import com.upsaude.api.response.PerfisUsuariosResponse;
import com.upsaude.entity.PerfisUsuarios;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.PerfisUsuariosMapper;
import com.upsaude.repository.PerfisUsuariosRepository;
import com.upsaude.service.PerfisUsuariosService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de PerfisUsuarios.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PerfisUsuariosServiceImpl implements PerfisUsuariosService {

    private final PerfisUsuariosRepository perfisUsuariosRepository;
    private final PerfisUsuariosMapper perfisUsuariosMapper;

    @Override
    @Transactional
    @CacheEvict(value = "perfisusuarios", allEntries = true)
    public PerfisUsuariosResponse criar(PerfisUsuariosRequest request) {
        log.debug("Criando novo perfisusuarios");

        // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

        PerfisUsuarios perfisUsuarios = perfisUsuariosMapper.fromRequest(request);
        perfisUsuarios.setActive(true);

        PerfisUsuarios perfisUsuariosSalvo = perfisUsuariosRepository.save(perfisUsuarios);
        log.info("PerfisUsuarios criado com sucesso. ID: {}", perfisUsuariosSalvo.getId());

        return perfisUsuariosMapper.toResponse(perfisUsuariosSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "perfisusuarios", key = "#id")
    public PerfisUsuariosResponse obterPorId(UUID id) {
        log.debug("Buscando perfisusuarios por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do perfisusuarios é obrigatório");
        }

        PerfisUsuarios perfisUsuarios = perfisUsuariosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("PerfisUsuarios não encontrado com ID: " + id));

        return perfisUsuariosMapper.toResponse(perfisUsuarios);
    }

    @Override
    public Page<PerfisUsuariosResponse> listar(Pageable pageable) {
        log.debug("Listando PerfisUsuarios paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<PerfisUsuarios> perfisUsuarios = perfisUsuariosRepository.findAll(pageable);
        return perfisUsuarios.map(perfisUsuariosMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "perfisusuarios", key = "#id")
    public PerfisUsuariosResponse atualizar(UUID id, PerfisUsuariosRequest request) {
        log.debug("Atualizando perfisusuarios. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do perfisusuarios é obrigatório");
        }

        // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

        PerfisUsuarios perfisUsuariosExistente = perfisUsuariosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("PerfisUsuarios não encontrado com ID: " + id));

        atualizarDadosPerfisUsuarios(perfisUsuariosExistente, request);

        PerfisUsuarios perfisUsuariosAtualizado = perfisUsuariosRepository.save(perfisUsuariosExistente);
        log.info("PerfisUsuarios atualizado com sucesso. ID: {}", perfisUsuariosAtualizado.getId());

        return perfisUsuariosMapper.toResponse(perfisUsuariosAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "perfisusuarios", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo perfisusuarios. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do perfisusuarios é obrigatório");
        }

        PerfisUsuarios perfisUsuarios = perfisUsuariosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("PerfisUsuarios não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(perfisUsuarios.getActive())) {
            throw new BadRequestException("PerfisUsuarios já está inativo");
        }

        perfisUsuarios.setActive(false);
        perfisUsuariosRepository.save(perfisUsuarios);
        log.info("PerfisUsuarios excluído (desativado) com sucesso. ID: {}", id);
    }

    // Validações de dados básicos foram movidas para o Request usando Bean Validation
    // (@NotNull, @NotBlank, @Pattern, etc). Isso garante validação automática no Controller
    // e retorno de erro 400 padronizado via ApiExceptionHandler.

        private void atualizarDadosPerfisUsuarios(PerfisUsuarios perfisUsuarios, PerfisUsuariosRequest request) {
        PerfisUsuarios perfisUsuariosAtualizado = perfisUsuariosMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = perfisUsuarios.getId();
        com.upsaude.entity.Tenant tenantOriginal = perfisUsuarios.getTenant();
        Boolean activeOriginal = perfisUsuarios.getActive();
        java.time.OffsetDateTime createdAtOriginal = perfisUsuarios.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(perfisUsuariosAtualizado, perfisUsuarios);
        
        // Restaura campos de controle
        perfisUsuarios.setId(idOriginal);
        perfisUsuarios.setTenant(tenantOriginal);
        perfisUsuarios.setActive(activeOriginal);
        perfisUsuarios.setCreatedAt(createdAtOriginal);
    }
}
