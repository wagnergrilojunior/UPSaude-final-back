package com.upsaude.service.impl;

import com.upsaude.api.request.ResponsavelLegalRequest;
import com.upsaude.api.response.ResponsavelLegalResponse;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ResponsavelLegal;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.ResponsavelLegalMapper;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.repository.ResponsavelLegalRepository;
import com.upsaude.repository.UsuariosSistemaRepository;
import com.upsaude.service.ResponsavelLegalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResponsavelLegalServiceImpl implements ResponsavelLegalService {

    private final ResponsavelLegalRepository repository;
    private final ResponsavelLegalMapper mapper;
    private final PacienteRepository pacienteRepository;
    private final UsuariosSistemaRepository usuariosSistemaRepository;

    @Override
    @Transactional
    @CacheEvict(value = "responsavellegal", allEntries = true)
    public ResponsavelLegalResponse criar(ResponsavelLegalRequest request) {
        log.debug("Criando responsável legal para paciente: {}", request.getPaciente());

        if (request.getPaciente() == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        if (!StringUtils.hasText(request.getNome())) {
            throw new BadRequestException("Nome do responsável legal é obrigatório");
        }

        repository.findByPacienteId(request.getPaciente())
                .ifPresent(d -> {
                    throw new ConflictException("Responsável legal já existe para este paciente");
                });

        if (StringUtils.hasText(request.getCpf()) && !request.getCpf().matches("^\\d{11}$")) {
            throw new BadRequestException("CPF deve conter exatamente 11 dígitos numéricos");
        }

        try {
            // Carrega o paciente para definir o relacionamento
            Paciente paciente = pacienteRepository.findById(request.getPaciente())
                    .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));

            ResponsavelLegal entity = mapper.fromRequest(request);
            entity.setActive(true);
            entity.setPaciente(paciente); // Define o relacionamento manualmente
            
            // Obtém o tenant do usuário autenticado (obrigatório para ResponsavelLegal que estende BaseEntity)
            Tenant tenant = obterTenantDoUsuarioAutenticado();
            if (tenant == null) {
                throw new BadRequestException("Não foi possível obter tenant do usuário autenticado. É necessário estar autenticado para criar responsável legal.");
            }
            entity.setTenant(tenant);

            ResponsavelLegal saved = repository.save(entity);
            log.info("Responsável legal criado. ID: {}", saved.getId());

            return mapper.toResponse(saved);
        } catch (BadRequestException | ConflictException | NotFoundException e) {
            log.warn("Erro de validação ao criar responsável legal. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar responsável legal. Request: {}, Exception: {}", request, e.getClass().getName(), e);
            throw new InternalServerErrorException("Erro ao persistir responsável legal", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao criar responsável legal. Request: {}, Exception: {}", request, e.getClass().getName(), e);
            throw e;
        }
    }

    @Override
    public ResponsavelLegalResponse obterPorId(UUID id) {
        log.debug("Buscando responsável legal por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID é obrigatório");
        }

        ResponsavelLegal entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Responsável legal não encontrado com ID: " + id));

        return mapper.toResponse(entity);
    }

    @Override
    public ResponsavelLegalResponse obterPorPacienteId(UUID pacienteId) {
        log.debug("Buscando responsável legal por paciente ID: {}", pacienteId);

        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        ResponsavelLegal entity = repository.findByPacienteId(pacienteId)
                .orElseThrow(() -> new NotFoundException("Responsável legal não encontrado para o paciente: " + pacienteId));

        return mapper.toResponse(entity);
    }

    @Override
    public Page<ResponsavelLegalResponse> listar(Pageable pageable) {
        log.debug("Listando responsáveis legais paginados");

        Page<ResponsavelLegal> entities = repository.findAll(pageable);
        return entities.map(mapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "responsavellegal", key = "#id")
    public ResponsavelLegalResponse atualizar(UUID id, ResponsavelLegalRequest request) {
        log.debug("Atualizando responsável legal. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID é obrigatório");
        }

        if (!StringUtils.hasText(request.getNome())) {
            throw new BadRequestException("Nome do responsável legal é obrigatório");
        }

        ResponsavelLegal entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Responsável legal não encontrado com ID: " + id));

        if (request.getPaciente() != null && !request.getPaciente().equals(entity.getPaciente().getId())) {
            repository.findByPacienteId(request.getPaciente())
                    .ifPresent(d -> {
                        if (!d.getId().equals(id)) {
                            throw new ConflictException("Responsável legal já existe para este paciente");
                        }
                    });
        }

        if (StringUtils.hasText(request.getCpf()) && !request.getCpf().matches("^\\d{11}$")) {
            throw new BadRequestException("CPF deve conter exatamente 11 dígitos numéricos");
        }

        atualizarDados(entity, request);
        ResponsavelLegal updated = repository.save(entity);
        log.info("Responsável legal atualizado. ID: {}", updated.getId());

        return mapper.toResponse(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = "responsavellegal", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo responsável legal. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID é obrigatório");
        }

        ResponsavelLegal entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Responsável legal não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(entity.getActive())) {
            throw new BadRequestException("Responsável legal já está inativo");
        }

        entity.setActive(false);
        repository.save(entity);
        log.info("Responsável legal excluído. ID: {}", id);
    }

    private void atualizarDados(ResponsavelLegal entity, ResponsavelLegalRequest request) {
        ResponsavelLegal updated = mapper.fromRequest(request);
        
        entity.setNome(updated.getNome());
        entity.setCpf(updated.getCpf());
        entity.setTelefone(updated.getTelefone());
        entity.setTipoResponsavel(updated.getTipoResponsavel());
        entity.setAutorizacaoUsoDadosLGPD(updated.getAutorizacaoUsoDadosLGPD());
        entity.setAutorizacaoResponsavel(updated.getAutorizacaoResponsavel());
    }

    /**
     * Obtém o tenant do usuário autenticado.
     * 
     * @return Tenant do usuário autenticado ou null se não encontrado
     */
    private Tenant obterTenantDoUsuarioAutenticado() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                log.warn("Usuário não autenticado. Não é possível obter tenant.");
                return null;
            }

            // Obtém o userId do token JWT
            UUID userId = null;
            Object details = authentication.getDetails();
            if (details instanceof com.upsaude.integration.supabase.SupabaseAuthResponse.User) {
                com.upsaude.integration.supabase.SupabaseAuthResponse.User user = 
                    (com.upsaude.integration.supabase.SupabaseAuthResponse.User) details;
                userId = user.getId();
                log.debug("UserId obtido do SupabaseAuthResponse.User: {}", userId);
            } else if (authentication.getPrincipal() instanceof String) {
                try {
                    userId = UUID.fromString(authentication.getPrincipal().toString());
                    log.debug("UserId obtido do Principal (String): {}", userId);
                } catch (IllegalArgumentException e) {
                    log.warn("Principal não é um UUID válido: {}", authentication.getPrincipal());
                    return null;
                }
            } else {
                log.warn("Tipo de Principal não reconhecido: {}", authentication.getPrincipal() != null ? authentication.getPrincipal().getClass().getName() : "null");
            }

            if (userId != null) {
                java.util.Optional<com.upsaude.entity.UsuariosSistema> usuarioOpt = usuariosSistemaRepository.findByUserId(userId);
                if (usuarioOpt.isPresent()) {
                    com.upsaude.entity.UsuariosSistema usuario = usuarioOpt.get();
                    Tenant tenant = usuario.getTenant();
                    if (tenant != null) {
                        log.debug("Tenant obtido com sucesso: {} (ID: {})", tenant.getNome(), tenant.getId());
                        return tenant;
                    } else {
                        log.warn("Usuário encontrado mas sem tenant associado. UserId: {}", userId);
                    }
                } else {
                    log.warn("Usuário não encontrado no sistema. UserId: {}", userId);
                }
            } else {
                log.warn("Não foi possível obter userId do contexto de autenticação");
            }
        } catch (Exception e) {
            log.error("Erro ao obter tenant do usuário autenticado, Exception: {}", e.getClass().getName(), e);
        }
        return null;
    }
}

