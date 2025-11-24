package com.upsaude.service.impl;

import com.upsaude.api.request.VacinasRequest;
import com.upsaude.api.response.VacinasResponse;
import com.upsaude.entity.Vacinas;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.VacinasMapper;
import com.upsaude.repository.VacinasRepository;
import com.upsaude.service.VacinasService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Vacinas.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VacinasServiceImpl implements VacinasService {

    private final VacinasRepository vacinasRepository;
    private final VacinasMapper vacinasMapper;

    @Override
    @Transactional
    public VacinasResponse criar(VacinasRequest request) {
        log.debug("Criando novo vacinas");

        validarDadosBasicos(request);

        Vacinas vacinas = vacinasMapper.fromRequest(request);
        vacinas.setActive(true);

        Vacinas vacinasSalvo = vacinasRepository.save(vacinas);
        log.info("Vacinas criado com sucesso. ID: {}", vacinasSalvo.getId());

        return vacinasMapper.toResponse(vacinasSalvo);
    }

    @Override
    @Transactional
    public VacinasResponse obterPorId(UUID id) {
        log.debug("Buscando vacinas por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do vacinas é obrigatório");
        }

        Vacinas vacinas = vacinasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacinas não encontrado com ID: " + id));

        return vacinasMapper.toResponse(vacinas);
    }

    @Override
    public Page<VacinasResponse> listar(Pageable pageable) {
        log.debug("Listando Vacinas paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Vacinas> vacinas = vacinasRepository.findAll(pageable);
        return vacinas.map(vacinasMapper::toResponse);
    }

    @Override
    @Transactional
    public VacinasResponse atualizar(UUID id, VacinasRequest request) {
        log.debug("Atualizando vacinas. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do vacinas é obrigatório");
        }

        validarDadosBasicos(request);

        Vacinas vacinasExistente = vacinasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacinas não encontrado com ID: " + id));

        atualizarDadosVacinas(vacinasExistente, request);

        Vacinas vacinasAtualizado = vacinasRepository.save(vacinasExistente);
        log.info("Vacinas atualizado com sucesso. ID: {}", vacinasAtualizado.getId());

        return vacinasMapper.toResponse(vacinasAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo vacinas. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do vacinas é obrigatório");
        }

        Vacinas vacinas = vacinasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacinas não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(vacinas.getActive())) {
            throw new BadRequestException("Vacinas já está inativo");
        }

        vacinas.setActive(false);
        vacinasRepository.save(vacinas);
        log.info("Vacinas excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(VacinasRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do vacinas são obrigatórios");
        }
    }

        private void atualizarDadosVacinas(Vacinas vacinas, VacinasRequest request) {
        Vacinas vacinasAtualizado = vacinasMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = vacinas.getId();
        com.upsaude.entity.Tenant tenantOriginal = vacinas.getTenant();
        Boolean activeOriginal = vacinas.getActive();
        java.time.OffsetDateTime createdAtOriginal = vacinas.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(vacinasAtualizado, vacinas);
        
        // Restaura campos de controle
        vacinas.setId(idOriginal);
        vacinas.setTenant(tenantOriginal);
        vacinas.setActive(activeOriginal);
        vacinas.setCreatedAt(createdAtOriginal);
    }
}
