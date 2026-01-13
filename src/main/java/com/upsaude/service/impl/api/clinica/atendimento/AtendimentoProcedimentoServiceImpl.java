package com.upsaude.service.impl.api.clinica.atendimento;

import com.upsaude.api.request.clinica.atendimento.AtendimentoProcedimentoRequest;
import com.upsaude.api.response.clinica.atendimento.AtendimentoProcedimentoResponse;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.clinica.atendimento.AtendimentoProcedimento;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimento;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.clinica.atendimento.AtendimentoProcedimentoMapper;
import com.upsaude.repository.clinica.atendimento.AtendimentoProcedimentoRepository;
import com.upsaude.repository.clinica.atendimento.AtendimentoRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.clinica.atendimento.AtendimentoProcedimentoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtendimentoProcedimentoServiceImpl implements AtendimentoProcedimentoService {

    private final AtendimentoProcedimentoRepository repository;
    private final AtendimentoRepository atendimentoRepository;
    private final SigtapProcedimentoRepository sigtapProcedimentoRepository;
    private final AtendimentoProcedimentoMapper mapper;
    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    @Override
    @Transactional
    public AtendimentoProcedimentoResponse criar(AtendimentoProcedimentoRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) tenant = tenantRepository.findById(tenantId).orElse(null);

        Atendimento atendimento = atendimentoRepository.findByIdAndTenant(request.getAtendimento(), tenantId)
                .orElseThrow(() -> new BadRequestException("Atendimento não encontrado com ID: " + request.getAtendimento()));

        AtendimentoProcedimento entity = mapper.fromRequest(request);
        entity.setActive(true);
        entity.setTenant(tenant);
        entity.setAtendimento(atendimento);

        if (request.getSigtapProcedimento() != null) {
            SigtapProcedimento procedimento = sigtapProcedimentoRepository.findById(request.getSigtapProcedimento())
                    .orElseThrow(() -> new BadRequestException("Procedimento SIGTAP não encontrado com ID: " + request.getSigtapProcedimento()));
            entity.setSigtapProcedimento(procedimento);
        }

        AtendimentoProcedimento saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AtendimentoProcedimentoResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        AtendimentoProcedimento entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("AtendimentoProcedimento não encontrado com ID: " + id));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AtendimentoProcedimentoResponse> listar(Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return repository.findAllByTenant(tenantId, pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public AtendimentoProcedimentoResponse atualizar(UUID id, AtendimentoProcedimentoRequest request) {
        UUID tenantId = tenantService.validarTenantAtual();
        AtendimentoProcedimento entity = repository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("AtendimentoProcedimento não encontrado com ID: " + id));
        mapper.updateFromRequest(request, entity);
        AtendimentoProcedimento saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            AtendimentoProcedimento entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("AtendimentoProcedimento não encontrado com ID: " + id));
            repository.delete(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao excluir AtendimentoProcedimento", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        try {
            AtendimentoProcedimento entity = repository.findByIdAndTenant(id, tenantId)
                    .orElseThrow(() -> new NotFoundException("AtendimentoProcedimento não encontrado com ID: " + id));
            if (Boolean.FALSE.equals(entity.getActive())) {
                throw new BadRequestException("AtendimentoProcedimento já está inativo");
            }
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            throw new InternalServerErrorException("Erro ao inativar AtendimentoProcedimento", e);
        }
    }
}

