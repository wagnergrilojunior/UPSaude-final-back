package com.upsaude.service.impl;

import com.upsaude.api.request.MedicacoesContinuasPacienteRequest;
import com.upsaude.api.response.MedicacoesContinuasPacienteResponse;
import com.upsaude.entity.MedicacoesContinuasPaciente;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.MedicacoesContinuasPacienteMapper;
import com.upsaude.repository.MedicacoesContinuasPacienteRepository;
import com.upsaude.service.MedicacoesContinuasPacienteService;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicacoesContinuasPacienteServiceImpl implements MedicacoesContinuasPacienteService {

    private final MedicacoesContinuasPacienteRepository medicacoesContinuasPacienteRepository;
    private final MedicacoesContinuasPacienteMapper medicacoesContinuasPacienteMapper;

    @Override
    @Transactional
    @CacheEvict(value = "medicacoescontinuaspaciente", allEntries = true)
    public MedicacoesContinuasPacienteResponse criar(MedicacoesContinuasPacienteRequest request) {
        log.debug("Criando novo medicacoescontinuaspaciente");

        MedicacoesContinuasPaciente medicacoesContinuasPaciente = medicacoesContinuasPacienteMapper.fromRequest(request);
        medicacoesContinuasPaciente.setActive(true);

        MedicacoesContinuasPaciente medicacoesContinuasPacienteSalvo = medicacoesContinuasPacienteRepository.save(medicacoesContinuasPaciente);
        log.info("MedicacoesContinuasPaciente criado com sucesso. ID: {}", medicacoesContinuasPacienteSalvo.getId());

        return medicacoesContinuasPacienteMapper.toResponse(medicacoesContinuasPacienteSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "medicacoescontinuaspaciente", key = "#id")
    public MedicacoesContinuasPacienteResponse obterPorId(UUID id) {
        log.debug("Buscando medicacoescontinuaspaciente por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do medicacoescontinuaspaciente é obrigatório");
        }

        MedicacoesContinuasPaciente medicacoesContinuasPaciente = medicacoesContinuasPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MedicacoesContinuasPaciente não encontrado com ID: " + id));

        return medicacoesContinuasPacienteMapper.toResponse(medicacoesContinuasPaciente);
    }

    @Override
    public Page<MedicacoesContinuasPacienteResponse> listar(Pageable pageable) {
        log.debug("Listando MedicacoesContinuasPacientes paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<MedicacoesContinuasPaciente> medicacoesContinuasPacientes = medicacoesContinuasPacienteRepository.findAll(pageable);
        return medicacoesContinuasPacientes.map(medicacoesContinuasPacienteMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "medicacoescontinuaspaciente", key = "#id")
    public MedicacoesContinuasPacienteResponse atualizar(UUID id, MedicacoesContinuasPacienteRequest request) {
        log.debug("Atualizando medicacoescontinuaspaciente. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do medicacoescontinuaspaciente é obrigatório");
        }

        MedicacoesContinuasPaciente medicacoesContinuasPacienteExistente = medicacoesContinuasPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MedicacoesContinuasPaciente não encontrado com ID: " + id));

        atualizarDadosMedicacoesContinuasPaciente(medicacoesContinuasPacienteExistente, request);

        MedicacoesContinuasPaciente medicacoesContinuasPacienteAtualizado = medicacoesContinuasPacienteRepository.save(medicacoesContinuasPacienteExistente);
        log.info("MedicacoesContinuasPaciente atualizado com sucesso. ID: {}", medicacoesContinuasPacienteAtualizado.getId());

        return medicacoesContinuasPacienteMapper.toResponse(medicacoesContinuasPacienteAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "medicacoescontinuaspaciente", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo medicacoescontinuaspaciente. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do medicacoescontinuaspaciente é obrigatório");
        }

        MedicacoesContinuasPaciente medicacoesContinuasPaciente = medicacoesContinuasPacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MedicacoesContinuasPaciente não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(medicacoesContinuasPaciente.getActive())) {
            throw new BadRequestException("MedicacoesContinuasPaciente já está inativo");
        }

        medicacoesContinuasPaciente.setActive(false);
        medicacoesContinuasPacienteRepository.save(medicacoesContinuasPaciente);
        log.info("MedicacoesContinuasPaciente excluído (desativado) com sucesso. ID: {}", id);
    }

        private void atualizarDadosMedicacoesContinuasPaciente(MedicacoesContinuasPaciente medicacoesContinuasPaciente, MedicacoesContinuasPacienteRequest request) {
        MedicacoesContinuasPaciente medicacoesContinuasPacienteAtualizado = medicacoesContinuasPacienteMapper.fromRequest(request);

        java.util.UUID idOriginal = medicacoesContinuasPaciente.getId();
        com.upsaude.entity.Tenant tenantOriginal = medicacoesContinuasPaciente.getTenant();
        Boolean activeOriginal = medicacoesContinuasPaciente.getActive();
        java.time.OffsetDateTime createdAtOriginal = medicacoesContinuasPaciente.getCreatedAt();

        BeanUtils.copyProperties(medicacoesContinuasPacienteAtualizado, medicacoesContinuasPaciente);

        medicacoesContinuasPaciente.setId(idOriginal);
        medicacoesContinuasPaciente.setTenant(tenantOriginal);
        medicacoesContinuasPaciente.setActive(activeOriginal);
        medicacoesContinuasPaciente.setCreatedAt(createdAtOriginal);
    }
}
