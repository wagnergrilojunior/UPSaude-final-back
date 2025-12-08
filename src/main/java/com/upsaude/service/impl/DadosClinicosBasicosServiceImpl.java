package com.upsaude.service.impl;

import com.upsaude.api.request.DadosClinicosBasicosRequest;
import com.upsaude.api.response.DadosClinicosBasicosResponse;
import com.upsaude.api.response.PacienteResponse;
import com.upsaude.entity.DadosClinicosBasicos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.DadosClinicosBasicosMapper;
import com.upsaude.repository.DadosClinicosBasicosRepository;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.repository.UsuariosSistemaRepository;
import com.upsaude.service.DadosClinicosBasicosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DadosClinicosBasicosServiceImpl implements DadosClinicosBasicosService {

    private final DadosClinicosBasicosRepository repository;
    private final DadosClinicosBasicosMapper mapper;
    private final PacienteRepository pacienteRepository;
    private final UsuariosSistemaRepository usuariosSistemaRepository;

    @Override
    @Transactional
    @CacheEvict(value = "dadosclinicosbasicos", allEntries = true)
    public DadosClinicosBasicosResponse criar(DadosClinicosBasicosRequest request) {
        log.debug("Criando dados clínicos básicos. Request: {}", request);
        
        if (request == null) {
            log.warn("Tentativa de criar dados clínicos básicos com request nulo");
            throw new BadRequestException("Dados clínicos básicos são obrigatórios");
        }
        if (request.getPaciente() == null) {
            log.warn("ID de paciente nulo recebido para criação de dados clínicos básicos");
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        try {
            repository.findByPacienteId(request.getPaciente())
                    .ifPresent(d -> {
                        log.warn("Tentativa de criar dados clínicos básicos duplicados para paciente. Paciente ID: {}", request.getPaciente());
                        throw new ConflictException("Dados clínicos básicos já existem para este paciente");
                    });

            // Carrega o paciente para definir o relacionamento
            Paciente paciente = pacienteRepository.findById(request.getPaciente())
                    .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));

            DadosClinicosBasicos entity = mapper.fromRequest(request);
            entity.setActive(true);
            entity.setPaciente(paciente); // Define o relacionamento manualmente
            
            // Obtém o tenant do usuário autenticado (obrigatório para DadosClinicosBasicos que estende BaseEntity)
            Tenant tenant = obterTenantDoUsuarioAutenticado();
            if (tenant == null) {
                throw new BadRequestException("Não foi possível obter tenant do usuário autenticado. É necessário estar autenticado para criar dados clínicos básicos.");
            }
            entity.setTenant(tenant);

            DadosClinicosBasicos saved = repository.save(entity);
            log.info("Dados clínicos básicos criados. ID: {}", saved.getId());

            DadosClinicosBasicosResponse response = mapper.toResponse(saved);
            // Mapeia apenas o ID do paciente para evitar referência circular
            response.setPaciente(createPacienteResponseMinimal(saved.getPaciente()));
            return response;
        } catch (BadRequestException | ConflictException | NotFoundException e) {
            log.warn("Erro de validação ao criar dados clínicos básicos. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar dados clínicos básicos. Request: {}, Exception: {}", request, e.getClass().getName(), e);
            throw new InternalServerErrorException("Erro ao persistir dados clínicos básicos", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao criar dados clínicos básicos. Request: {}, Exception: {}", request, e.getClass().getName(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DadosClinicosBasicosResponse obterPorId(UUID id) {
        log.debug("Buscando dados clínicos básicos por ID: {}", id);
        
        if (id == null) {
            log.warn("ID nulo recebido para busca de dados clínicos básicos");
            throw new BadRequestException("ID é obrigatório");
        }

        try {
            DadosClinicosBasicos entity = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Dados clínicos básicos não encontrados com ID: " + id));

            log.debug("Dados clínicos básicos encontrados. ID: {}", id);
            DadosClinicosBasicosResponse response = mapper.toResponse(entity);
            // Mapeia apenas o ID do paciente para evitar referência circular
            response.setPaciente(createPacienteResponseMinimal(entity.getPaciente()));
            return response;
        } catch (NotFoundException e) {
            log.warn("Dados clínicos básicos não encontrados. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar dados clínicos básicos. ID: {}, Exception: {}", id, e.getClass().getName(), e);
            throw new InternalServerErrorException("Erro ao buscar dados clínicos básicos", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar dados clínicos básicos. ID: {}, Exception: {}", id, e.getClass().getName(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DadosClinicosBasicosResponse obterPorPacienteId(UUID pacienteId) {
        log.debug("Buscando dados clínicos básicos por paciente ID: {}", pacienteId);
        
        if (pacienteId == null) {
            log.warn("ID de paciente nulo recebido para busca de dados clínicos básicos");
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        try {
            DadosClinicosBasicos entity = repository.findByPacienteId(pacienteId)
                    .orElseThrow(() -> new NotFoundException("Dados clínicos básicos não encontrados para o paciente: " + pacienteId));

            log.debug("Dados clínicos básicos encontrados para paciente. Paciente ID: {}", pacienteId);
            DadosClinicosBasicosResponse response = mapper.toResponse(entity);
            // Mapeia apenas o ID do paciente para evitar referência circular
            response.setPaciente(createPacienteResponseMinimal(entity.getPaciente()));
            return response;
        } catch (NotFoundException e) {
            log.warn("Dados clínicos básicos não encontrados para paciente. Paciente ID: {}", pacienteId);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar dados clínicos básicos por paciente. Paciente ID: {}, Exception: {}", pacienteId, e.getClass().getName(), e);
            throw new InternalServerErrorException("Erro ao buscar dados clínicos básicos do paciente", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar dados clínicos básicos por paciente. Paciente ID: {}, Exception: {}", pacienteId, e.getClass().getName(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DadosClinicosBasicosResponse> listar(Pageable pageable) {
        log.debug("Listando dados clínicos básicos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<DadosClinicosBasicos> entities = repository.findAll(pageable);
            log.debug("Listagem de dados clínicos básicos concluída. Total de elementos: {}", entities.getTotalElements());
            return entities.map(entity -> {
                DadosClinicosBasicosResponse response = mapper.toResponse(entity);
                // Mapeia apenas o ID do paciente para evitar referência circular
                response.setPaciente(createPacienteResponseMinimal(entity.getPaciente()));
                return response;
            });
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar dados clínicos básicos. Pageable: {}, Exception: {}", pageable, e.getClass().getName(), e);
            throw new InternalServerErrorException("Erro ao listar dados clínicos básicos", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao listar dados clínicos básicos. Pageable: {}, Exception: {}", pageable, e.getClass().getName(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "dadosclinicosbasicos", key = "#id")
    public DadosClinicosBasicosResponse atualizar(UUID id, DadosClinicosBasicosRequest request) {
        log.debug("Atualizando dados clínicos básicos. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de dados clínicos básicos");
            throw new BadRequestException("ID é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de dados clínicos básicos. ID: {}", id);
            throw new BadRequestException("Dados clínicos básicos são obrigatórios");
        }

        try {
            DadosClinicosBasicos entity = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Dados clínicos básicos não encontrados com ID: " + id));

            if (request.getPaciente() != null && !request.getPaciente().equals(entity.getPaciente().getId())) {
                repository.findByPacienteId(request.getPaciente())
                        .ifPresent(d -> {
                            if (!d.getId().equals(id)) {
                                log.warn("Tentativa de atualizar dados clínicos básicos com paciente duplicado. ID: {}, Paciente ID: {}", id, request.getPaciente());
                                throw new ConflictException("Dados clínicos básicos já existem para este paciente");
                            }
                        });
                
                // Carrega o novo paciente para atualizar o relacionamento
                Paciente paciente = pacienteRepository.findById(request.getPaciente())
                        .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));
                entity.setPaciente(paciente);
            }

            // Usa mapper do MapStruct que preserva campos de controle automaticamente
            mapper.updateFromRequest(request, entity);
            
            DadosClinicosBasicos updated = repository.save(entity);
            log.info("Dados clínicos básicos atualizados. ID: {}", updated.getId());

            DadosClinicosBasicosResponse response = mapper.toResponse(updated);
            // Mapeia apenas o ID do paciente para evitar referência circular
            response.setPaciente(createPacienteResponseMinimal(updated.getPaciente()));
            return response;
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar dados clínicos básicos não existentes. ID: {}", id);
            throw e;
        } catch (BadRequestException | ConflictException e) {
            log.warn("Erro de validação ao atualizar dados clínicos básicos. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar dados clínicos básicos. ID: {}, Request: {}, Exception: {}", id, request, e.getClass().getName(), e);
            throw new InternalServerErrorException("Erro ao atualizar dados clínicos básicos", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao atualizar dados clínicos básicos. ID: {}, Request: {}, Exception: {}", id, request, e.getClass().getName(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "dadosclinicosbasicos", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo dados clínicos básicos. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão de dados clínicos básicos");
            throw new BadRequestException("ID é obrigatório");
        }

        try {
            DadosClinicosBasicos entity = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Dados clínicos básicos não encontrados com ID: " + id));

            if (Boolean.FALSE.equals(entity.getActive())) {
                log.warn("Tentativa de excluir dados clínicos básicos já inativos. ID: {}", id);
                throw new BadRequestException("Dados clínicos básicos já estão inativos");
            }

            entity.setActive(false);
            repository.save(entity);
            log.info("Dados clínicos básicos excluídos. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir dados clínicos básicos não existentes. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir dados clínicos básicos. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir dados clínicos básicos. ID: {}, Exception: {}", id, e.getClass().getName(), e);
            throw new InternalServerErrorException("Erro ao excluir dados clínicos básicos", e);
        } catch (Exception e) {
            log.error("Erro inesperado ao excluir dados clínicos básicos. ID: {}, Exception: {}", id, e.getClass().getName(), e);
            throw e;
        }
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

    /**
     * Cria um PacienteResponse mínimo apenas com o ID para evitar referência circular.
     * 
     * @param paciente Entidade Paciente
     * @return PacienteResponse com apenas o ID preenchido
     */
    private PacienteResponse createPacienteResponseMinimal(Paciente paciente) {
        if (paciente == null) {
            return null;
        }
        PacienteResponse response = new PacienteResponse();
        response.setId(paciente.getId());
        return response;
    }
}

