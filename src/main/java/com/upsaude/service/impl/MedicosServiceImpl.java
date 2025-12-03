package com.upsaude.service.impl;

import com.upsaude.api.request.MedicosRequest;
import com.upsaude.api.response.MedicosResponse;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.entity.Medicos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.MedicosMapper;
import com.upsaude.repository.EspecialidadesMedicasRepository;
import com.upsaude.repository.MedicosRepository;
import com.upsaude.service.MedicosService;
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
 * Implementação do serviço de gerenciamento de Medicos.
 * Usa anotações JPA para delegar responsabilidades ao framework.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MedicosServiceImpl implements MedicosService {

    private final MedicosRepository medicosRepository;
    private final MedicosMapper medicosMapper;
    private final EspecialidadesMedicasRepository especialidadesMedicasRepository;

    @Override
    @Transactional
    @CacheEvict(value = "medicos", allEntries = true)
    public MedicosResponse criar(MedicosRequest request) {
        log.debug("Criando novo médico");

        validarDadosBasicos(request);

        Medicos medicos = medicosMapper.fromRequest(request);
        medicos.setActive(true);

        // Processar relacionamentos - JPA gerencia a persistência automaticamente
        processarRelacionamentos(medicos, request);

        Medicos medicosSalvo = medicosRepository.save(medicos);
        log.info("Médico criado com sucesso. ID: {}", medicosSalvo.getId());

        return medicosMapper.toResponse(medicosSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "medicos", key = "#id")
    public MedicosResponse obterPorId(UUID id) {
        log.debug("Buscando medicos por ID: {} (cache miss)", id);

        if (id == null) {
            throw new BadRequestException("ID do medicos é obrigatório");
        }

        Medicos medicos = medicosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Medicos não encontrado com ID: " + id));

        return medicosMapper.toResponse(medicos);
    }

    @Override
    public Page<MedicosResponse> listar(Pageable pageable) {
        log.debug("Listando Medicos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Medicos> medicos = medicosRepository.findAll(pageable);
        return medicos.map(medicosMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "medicos", key = "#id")
    public MedicosResponse atualizar(UUID id, MedicosRequest request) {
        log.debug("Atualizando médico. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do médico é obrigatório");
        }

        validarDadosBasicos(request);

        Medicos medicosExistente = medicosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + id));

        atualizarDadosMedicos(medicosExistente, request);
        
        // Processar relacionamentos - JPA gerencia a persistência automaticamente
        processarRelacionamentos(medicosExistente, request);

        Medicos medicosAtualizado = medicosRepository.save(medicosExistente);
        log.info("Médico atualizado com sucesso. ID: {}", medicosAtualizado.getId());

        return medicosMapper.toResponse(medicosAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "medicos", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo medicos. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do medicos é obrigatório");
        }

        Medicos medicos = medicosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Medicos não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(medicos.getActive())) {
            throw new BadRequestException("Medicos já está inativo");
        }

        medicos.setActive(false);
        medicosRepository.save(medicos);
        log.info("Medicos excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(MedicosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do médico são obrigatórios");
        }
    }

    private void atualizarDadosMedicos(Medicos medicos, MedicosRequest request) {
        Medicos medicosAtualizado = medicosMapper.fromRequest(request);
        
        // Preserva campos de controle
        UUID idOriginal = medicos.getId();
        com.upsaude.entity.Tenant tenantOriginal = medicos.getTenant();
        Boolean activeOriginal = medicos.getActive();
        java.time.OffsetDateTime createdAtOriginal = medicos.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado (exceto relacionamentos)
        BeanUtils.copyProperties(medicosAtualizado, medicos, "especialidade", "vinculosEstabelecimentos", "enderecos");
        
        // Restaura campos de controle
        medicos.setId(idOriginal);
        medicos.setTenant(tenantOriginal);
        medicos.setActive(activeOriginal);
        medicos.setCreatedAt(createdAtOriginal);
    }

    /**
     * Processa relacionamentos do médico de forma simplificada.
     * Com as anotações corretas do JPA (cascade, orphanRemoval), o Hibernate 
     * gerencia automaticamente a ordem de salvamento e integridade referencial.
     *
     * Responsabilidades deste método:
     * 1. Buscar entidades relacionadas existentes (apenas validação)
     * 2. Atribuir as referências
     * 3. O JPA/Hibernate cuida do resto (ordem, persistência, cascade)
     *
     * @param medicos entidade Medicos a ser processada
     * @param request dados do request com os UUIDs dos relacionamentos
     */
    private void processarRelacionamentos(Medicos medicos, MedicosRequest request) {
        log.debug("Processando relacionamentos do médico");

        // ESPECIALIDADE (ManyToOne) - Buscar entidade existente
        // Não usa cascade pois especialidades são entidades independentes compartilhadas
        if (request.getEspecialidade() != null) {
            EspecialidadesMedicas especialidade = especialidadesMedicasRepository
                    .findById(request.getEspecialidade())
                    .orElseThrow(() -> new NotFoundException(
                            "Especialidade médica não encontrada com ID: " + request.getEspecialidade()));
            medicos.setEspecialidade(especialidade);
        } else {
            medicos.setEspecialidade(null);
        }

        // VÍNCULOS E ENDEREÇOS são gerenciados com cascade automático pelo JPA
        // Se necessário adicionar/remover vínculos, isso deve ser feito através de endpoints específicos
        
        log.debug("Relacionamentos processados. JPA gerenciará persistência automaticamente.");
    }
}
