package com.upsaude.service.impl.api.clinica.prontuario;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.request.clinica.prontuario.ExamePacienteRequest;
import com.upsaude.api.response.clinica.prontuario.ExamePacienteResponse;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.clinica.prontuario.ExamePaciente;
import com.upsaude.entity.clinica.prontuario.Prontuario;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.referencia.cid.Cid10Subcategorias;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimento;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.clinica.prontuario.ExamePacienteMapper;
import com.upsaude.repository.clinica.atendimento.AtendimentoRepository;
import com.upsaude.repository.clinica.prontuario.ExamePacienteRepository;
import com.upsaude.repository.clinica.prontuario.ProntuarioRepository;
import com.upsaude.repository.profissional.ProfissionaisSaudeRepository;
import com.upsaude.repository.referencia.cid.Cid10SubcategoriasRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.service.api.clinica.prontuario.ExamePacienteService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExamePacienteServiceImpl implements ExamePacienteService {

    private final ExamePacienteRepository exameRepository;
    private final ProntuarioRepository prontuarioRepository;
    private final AtendimentoRepository atendimentoRepository;
    private final SigtapProcedimentoRepository procedimentoRepository;
    private final ProfissionaisSaudeRepository profissionalRepository;
    private final Cid10SubcategoriasRepository cidRepository;
    private final TenantRepository tenantRepository;
    private final TenantService tenantService;
    private final ExamePacienteMapper mapper;
    private final com.upsaude.repository.referencia.exame.CatalogoExameRepository catalogoExameRepository;

    @Override
    @Transactional
    public ExamePacienteResponse criar(ExamePacienteRequest request) {
        log.info("Criando solicitação de exame para prontuário: {}", request.getProntuario());
        UUID tenantId = tenantService.validarTenantAtual();
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new NotFoundException("Tenant não encontrado"));

        ExamePaciente entity = mapper.fromRequest(request);
        entity.setTenant(tenant);

        preencherEntidadesRelacionadas(entity, request, tenantId);
        definirStatusFHIR(entity, request);

        ExamePaciente saved = exameRepository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ExamePacienteResponse atualizar(UUID id, ExamePacienteRequest request) {
        log.info("Atualizando exame: {}", id);
        UUID tenantId = tenantService.validarTenantAtual();

        ExamePaciente entity = exameRepository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Exame não encontrado"));

        mapper.updateFromRequest(request, entity);
        preencherEntidadesRelacionadas(entity, request, tenantId);

        // Atualiza status se fornecido, senão mantém ou infere
        if (request.getStatus() != null) {
            entity.setStatus(request.getStatus());
        } else if (entity.getDataResultado() != null && !"completed".equals(entity.getStatus())) {
            entity.setStatus("completed");
        }

        ExamePaciente saved = exameRepository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ExamePacienteResponse obterPorId(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        ExamePaciente entity = exameRepository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Exame não encontrado"));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExamePacienteResponse> listarPorProntuario(UUID prontuarioId, Pageable pageable) {
        UUID tenantId = tenantService.validarTenantAtual();
        return exameRepository.findByProntuarioIdAndTenantId(prontuarioId, tenantId, pageable)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        UUID tenantId = tenantService.validarTenantAtual();
        ExamePaciente entity = exameRepository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Exame não encontrado"));

        entity.setActive(false);
        exameRepository.save(entity);
    }

    private void preencherEntidadesRelacionadas(ExamePaciente entity, ExamePacienteRequest request, UUID tenantId) {
        // Prontuário
        if (request.getProntuario() != null) {
            Prontuario prontuario = prontuarioRepository.findById(request.getProntuario())
                    .orElseThrow(() -> new NotFoundException("Prontuário não encontrado"));
            entity.setProntuario(prontuario);
        }

        // Atendimento
        if (request.getAtendimentoId() != null) {
            Atendimento atendimento = atendimentoRepository.findById(request.getAtendimentoId())
                    .orElseThrow(() -> new NotFoundException("Atendimento não encontrado"));
            entity.setAtendimento(atendimento);
        }

        // Procedimento SIGTAP
        if (request.getProcedimento() != null) {
            SigtapProcedimento procedimento = procedimentoRepository.findById(request.getProcedimento())
                    .orElseThrow(() -> new NotFoundException("Procedimento SIGTAP não encontrado"));
            entity.setProcedimento(procedimento);

            // Auto-fill nome e categoria se vazios
            if (entity.getNomeExame() == null) {
                entity.setNomeExame(procedimento.getNome());
            }
            if (entity.getCategory() == null) {
                entity.setCategory("laboratory"); // Default, poderia ser refinado baseado no grupo do procedimento
            }
        }

        // Profissionais
        if (request.getProfissionalSolicitante() != null) {
            ProfissionaisSaude prof = profissionalRepository.findById(request.getProfissionalSolicitante())
                    .orElseThrow(() -> new NotFoundException("Profissional solicitante não encontrado"));
            entity.setProfissionalSolicitante(prof);
        }

        if (request.getProfissionalResponsavel() != null) {
            ProfissionaisSaude prof = profissionalRepository.findById(request.getProfissionalResponsavel())
                    .orElseThrow(() -> new NotFoundException("Profissional responsável não encontrado"));
            entity.setProfissionalResponsavel(prof);
        }

        // Diagnóstico
        if (request.getDiagnosticoRelacionado() != null) {
            Cid10Subcategorias cid = cidRepository.findById(request.getDiagnosticoRelacionado())
                    .orElseThrow(() -> new NotFoundException("CID10 não encontrado"));
            entity.setDiagnosticoRelacionado(cid);
        }

        // Catálogo de Exames Unificado
        if (request.getCatalogoExameId() != null) {
            com.upsaude.entity.referencia.exame.CatalogoExame catalogo = catalogoExameRepository
                    .findById(request.getCatalogoExameId())
                    .orElseThrow(() -> new NotFoundException("Catálogo de exame não encontrado"));
            entity.setCatalogoExame(catalogo);

            // Auto-fill nome se vazio
            if (entity.getNomeExame() == null) {
                entity.setNomeExame(catalogo.getNome());
            }
        }
    }

    private void definirStatusFHIR(ExamePaciente entity, ExamePacienteRequest request) {
        if (request.getStatus() != null) {
            entity.setStatus(request.getStatus());
        } else {
            // Lógica de inferência de status
            if (entity.getDataResultado() != null || entity.getResultados() != null) {
                entity.setStatus("completed");
            } else if (entity.getDataExame() != null && entity.getDataExame().isBefore(OffsetDateTime.now())) {
                entity.setStatus("active"); // Ou 'preliminary' se tiver resultado parcial
            } else {
                entity.setStatus("active"); // Pedido realizado
            }
        }

        if (request.getIntent() != null) {
            entity.setIntent(request.getIntent());
        } else {
            entity.setIntent("order"); // Default para exames solicitados em atendimento
        }

        if (request.getPriority() == null) {
            entity.setPriority("routine");
        }
    }
}
