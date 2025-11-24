package com.upsaude.service.impl;

import com.upsaude.api.request.ReceitasMedicasRequest;
import com.upsaude.api.response.ReceitasMedicasResponse;
import com.upsaude.entity.ReceitasMedicas;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.ReceitasMedicasMapper;
import com.upsaude.repository.ReceitasMedicasRepository;
import com.upsaude.service.ReceitasMedicasService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de ReceitasMedicas.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReceitasMedicasServiceImpl implements ReceitasMedicasService {

    private final ReceitasMedicasRepository receitasMedicasRepository;
    private final ReceitasMedicasMapper receitasMedicasMapper;

    @Override
    @Transactional
    public ReceitasMedicasResponse criar(ReceitasMedicasRequest request) {
        log.debug("Criando novo receitasmedicas");

        validarDadosBasicos(request);

        ReceitasMedicas receitasMedicas = receitasMedicasMapper.fromRequest(request);
        receitasMedicas.setActive(true);

        ReceitasMedicas receitasMedicasSalvo = receitasMedicasRepository.save(receitasMedicas);
        log.info("ReceitasMedicas criado com sucesso. ID: {}", receitasMedicasSalvo.getId());

        return receitasMedicasMapper.toResponse(receitasMedicasSalvo);
    }

    @Override
    @Transactional
    public ReceitasMedicasResponse obterPorId(UUID id) {
        log.debug("Buscando receitasmedicas por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do receitasmedicas é obrigatório");
        }

        ReceitasMedicas receitasMedicas = receitasMedicasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ReceitasMedicas não encontrado com ID: " + id));

        return receitasMedicasMapper.toResponse(receitasMedicas);
    }

    @Override
    public Page<ReceitasMedicasResponse> listar(Pageable pageable) {
        log.debug("Listando ReceitasMedicas paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<ReceitasMedicas> receitasMedicas = receitasMedicasRepository.findAll(pageable);
        return receitasMedicas.map(receitasMedicasMapper::toResponse);
    }

    @Override
    @Transactional
    public ReceitasMedicasResponse atualizar(UUID id, ReceitasMedicasRequest request) {
        log.debug("Atualizando receitasmedicas. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do receitasmedicas é obrigatório");
        }

        validarDadosBasicos(request);

        ReceitasMedicas receitasMedicasExistente = receitasMedicasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ReceitasMedicas não encontrado com ID: " + id));

        atualizarDadosReceitasMedicas(receitasMedicasExistente, request);

        ReceitasMedicas receitasMedicasAtualizado = receitasMedicasRepository.save(receitasMedicasExistente);
        log.info("ReceitasMedicas atualizado com sucesso. ID: {}", receitasMedicasAtualizado.getId());

        return receitasMedicasMapper.toResponse(receitasMedicasAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo receitasmedicas. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do receitasmedicas é obrigatório");
        }

        ReceitasMedicas receitasMedicas = receitasMedicasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ReceitasMedicas não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(receitasMedicas.getActive())) {
            throw new BadRequestException("ReceitasMedicas já está inativo");
        }

        receitasMedicas.setActive(false);
        receitasMedicasRepository.save(receitasMedicas);
        log.info("ReceitasMedicas excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(ReceitasMedicasRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do receitasmedicas são obrigatórios");
        }
    }

        private void atualizarDadosReceitasMedicas(ReceitasMedicas receitasMedicas, ReceitasMedicasRequest request) {
        ReceitasMedicas receitasMedicasAtualizado = receitasMedicasMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = receitasMedicas.getId();
        com.upsaude.entity.Tenant tenantOriginal = receitasMedicas.getTenant();
        Boolean activeOriginal = receitasMedicas.getActive();
        java.time.OffsetDateTime createdAtOriginal = receitasMedicas.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(receitasMedicasAtualizado, receitasMedicas);
        
        // Restaura campos de controle
        receitasMedicas.setId(idOriginal);
        receitasMedicas.setTenant(tenantOriginal);
        receitasMedicas.setActive(activeOriginal);
        receitasMedicas.setCreatedAt(createdAtOriginal);
    }
}
