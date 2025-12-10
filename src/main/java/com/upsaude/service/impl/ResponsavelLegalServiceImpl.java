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
import com.upsaude.service.ResponsavelLegalService;
import com.upsaude.service.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import jakarta.validation.ConstraintViolation;
import org.hibernate.Hibernate;
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
    private final TenantService tenantService;

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

        // Limpa CPF e telefone removendo caracteres não numéricos
        String cpfLimpo = StringUtils.hasText(request.getCpf()) ? limparNumeros(request.getCpf()) : null;
        String telefoneLimpo = StringUtils.hasText(request.getTelefone()) ? limparNumeros(request.getTelefone()) : null;

        // Valida CPF após limpeza
        if (StringUtils.hasText(cpfLimpo)) {
            if (cpfLimpo.length() != 11) {
            throw new BadRequestException("CPF deve conter exatamente 11 dígitos numéricos");
            }
        }

        // Valida telefone após limpeza
        if (StringUtils.hasText(telefoneLimpo)) {
            if (telefoneLimpo.length() != 10 && telefoneLimpo.length() != 11) {
                throw new BadRequestException("Telefone deve conter 10 ou 11 dígitos numéricos");
            }
        }

        try {
            // Carrega o paciente para definir o relacionamento
            // Verifica primeiro se o paciente existe antes de outras validações
            UUID pacienteId = request.getPaciente();
            Paciente paciente = pacienteRepository.findById(pacienteId)
                    .orElseThrow(() -> {
                        log.warn("Tentativa de criar responsável legal para paciente inexistente. Paciente ID: {}", pacienteId);
                        return new NotFoundException("Paciente não encontrado com ID: " + pacienteId + ". Verifique se o ID está correto e se o paciente foi cadastrado anteriormente.");
                    });

            // Verifica se o paciente está ativo
            if (Boolean.FALSE.equals(paciente.getActive())) {
                throw new BadRequestException("Não é possível criar responsável legal para um paciente inativo. ID do paciente: " + pacienteId);
            }

            // Verifica se já existe responsável legal para este paciente
            repository.findByPacienteId(pacienteId)
                    .ifPresent(d -> {
                        throw new ConflictException("Responsável legal já existe para este paciente. ID do responsável existente: " + d.getId());
                    });

            ResponsavelLegal entity = mapper.fromRequest(request);
            entity.setActive(true);
            entity.setPaciente(paciente); // Define o relacionamento manualmente
            
            // Aplica os valores limpos
            entity.setCpf(cpfLimpo);
            entity.setTelefone(telefoneLimpo);
            
            // Obtém o tenant do usuário autenticado (obrigatório para ResponsavelLegal que estende BaseEntity)
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
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
        } catch (org.springframework.transaction.TransactionSystemException e) {
            // Captura erros de validação do JPA/Hibernate que vêm dentro de TransactionSystemException
            Throwable cause = e.getCause();
            if (cause instanceof jakarta.persistence.RollbackException) {
                Throwable rootCause = cause.getCause();
                if (rootCause instanceof jakarta.validation.ConstraintViolationException) {
                    jakarta.validation.ConstraintViolationException cve = (jakarta.validation.ConstraintViolationException) rootCause;
                    StringBuilder mensagens = new StringBuilder("Erro de validação: ");
                    for (ConstraintViolation<?> violation : cve.getConstraintViolations()) {
                        mensagens.append(violation.getPropertyPath()).append(" - ").append(violation.getMessage()).append("; ");
                    }
                    log.warn("Erro de validação ao criar responsável legal. Request: {}. Erro: {}", request, mensagens.toString());
                    throw new BadRequestException(mensagens.toString().trim());
                }
            }
            log.error("Erro de transação ao criar responsável legal. Request: {}, Exception: {}", request, e.getClass().getName(), e);
            throw new InternalServerErrorException("Erro ao persistir responsável legal", e);
        } catch (jakarta.validation.ConstraintViolationException e) {
            // Captura erros de validação do JPA/Hibernate diretamente
            StringBuilder mensagens = new StringBuilder("Erro de validação: ");
            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                mensagens.append(violation.getPropertyPath()).append(" - ").append(violation.getMessage()).append("; ");
            }
            log.warn("Erro de validação ao criar responsável legal. Request: {}. Erro: {}", request, mensagens.toString());
            throw new BadRequestException(mensagens.toString().trim());
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar ResponsavelLegal. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao persistir ResponsavelLegal", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar ResponsavelLegal. Exception: {}", e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponsavelLegalResponse obterPorId(UUID id) {
        log.debug("Buscando responsável legal por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID é obrigatório");
        }

        ResponsavelLegal entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Responsável legal não encontrado com ID: " + id));

        // Inicializa o relacionamento lazy do paciente dentro da transação
        if (entity.getPaciente() != null) {
            Hibernate.initialize(entity.getPaciente());
        }

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponsavelLegalResponse obterPorPacienteId(UUID pacienteId) {
        log.debug("Buscando responsável legal por paciente ID: {}", pacienteId);

        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        ResponsavelLegal entity = repository.findByPacienteId(pacienteId)
                .orElseThrow(() -> new NotFoundException("Responsável legal não encontrado para o paciente: " + pacienteId));

        // Inicializa o relacionamento lazy do paciente dentro da transação
        if (entity.getPaciente() != null) {
            Hibernate.initialize(entity.getPaciente());
        }

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponsavelLegalResponse> listar(Pageable pageable) {
        log.debug("Listando responsáveis legais paginados");

        Page<ResponsavelLegal> entities = repository.findAll(pageable);
        
        // Inicializa o relacionamento lazy do paciente dentro da transação
        // para evitar LazyInitializationException ao mapear para response
        entities.getContent().forEach(entity -> {
            if (entity.getPaciente() != null) {
                Hibernate.initialize(entity.getPaciente());
            }
        });
        
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

        // Limpa CPF e telefone removendo caracteres não numéricos
        String cpfLimpo = StringUtils.hasText(request.getCpf()) ? limparNumeros(request.getCpf()) : null;
        String telefoneLimpo = StringUtils.hasText(request.getTelefone()) ? limparNumeros(request.getTelefone()) : null;

        // Valida CPF após limpeza
        if (StringUtils.hasText(cpfLimpo)) {
            if (cpfLimpo.length() != 11) {
            throw new BadRequestException("CPF deve conter exatamente 11 dígitos numéricos");
        }
        }

        // Valida telefone após limpeza
        if (StringUtils.hasText(telefoneLimpo)) {
            if (telefoneLimpo.length() != 10 && telefoneLimpo.length() != 11) {
                throw new BadRequestException("Telefone deve conter 10 ou 11 dígitos numéricos");
            }
        }

        atualizarDados(entity, request, cpfLimpo, telefoneLimpo);
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

    private void atualizarDados(ResponsavelLegal entity, ResponsavelLegalRequest request, String cpfLimpo, String telefoneLimpo) {
        // Usar mapper para atualizar campos básicos
        mapper.updateFromRequest(request, entity);
        
        // Aplica valores limpos de CPF e telefone após o mapeamento
        entity.setCpf(cpfLimpo);
        entity.setTelefone(telefoneLimpo);
    }

    /**
     * Remove todos os caracteres não numéricos de uma string.
     * Útil para limpar CPF e telefone que podem vir com máscara.
     *
     * @param valor String com possível máscara
     * @return String contendo apenas dígitos numéricos
     */
    private String limparNumeros(String valor) {
        if (valor == null) {
                    return null;
                }
        return valor.replaceAll("\\D", "");
                }

}

