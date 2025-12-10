package com.upsaude.service.impl;

import com.upsaude.api.request.EstabelecimentosRequest;
import com.upsaude.api.request.EnderecoRequest;
import com.upsaude.api.response.EstabelecimentosResponse;
import com.upsaude.entity.Endereco;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.Tenant;
import com.upsaude.entity.Cidades;
import com.upsaude.entity.Estados;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.EstabelecimentosMapper;
import com.upsaude.mapper.EnderecoMapper;
import com.upsaude.repository.EnderecoRepository;
import com.upsaude.repository.EstabelecimentosRepository;
import com.upsaude.repository.ProfissionaisSaudeRepository;
import com.upsaude.repository.CidadesRepository;
import com.upsaude.repository.EstadosRepository;
import com.upsaude.service.EstabelecimentosService;
import com.upsaude.service.EnderecoService;
import com.upsaude.service.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Estabelecimentos.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EstabelecimentosServiceImpl implements EstabelecimentosService {

    private final EstabelecimentosRepository estabelecimentosRepository;
    private final EstabelecimentosMapper estabelecimentosMapper;
    private final EnderecoRepository enderecoRepository;
    private final EnderecoService enderecoService;
    private final EnderecoMapper enderecoMapper;
    private final CidadesRepository cidadesRepository;
    private final EstadosRepository estadosRepository;
    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;
    private final TenantService tenantService;

    @Override
    @Transactional
    @CacheEvict(value = "estabelecimentos", allEntries = true)
    public EstabelecimentosResponse criar(EstabelecimentosRequest request) {
        log.debug("Criando novo estabelecimento. Request: {}", request);
        
        if (request == null) {
            log.warn("Tentativa de criar estabelecimento com request nulo");
            throw new BadRequestException("Dados do estabelecimento são obrigatórios");
        }

        try {
            // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request
            
            // Valida CNPJ duplicado
            validarCnpjDuplicado(request.getCnpj(), null);
            
            // Valida CNES duplicado
            validarCnesDuplicado(request.getCodigoCnes(), null);

            Estabelecimentos estabelecimento = estabelecimentosMapper.fromRequest(request);
            
            // Define o tenant do usuário autenticado
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            if (tenant == null) {
                log.error("Tenant não encontrado para usuário autenticado");
                throw new BadRequestException("Tenant não encontrado. Verifique a autenticação do usuário.");
            }
            estabelecimento.setTenant(tenant);
            
            // Processa endereço principal usando findOrCreate para evitar duplicação
            processarEnderecoPrincipal(request, estabelecimento, tenant);

            // Carrega e valida responsável técnico
            if (request.getResponsavelTecnico() != null) {
                try {
                    ProfissionaisSaude responsavelTecnico = profissionaisSaudeRepository.findById(request.getResponsavelTecnico())
                            .orElseThrow(() -> new NotFoundException("Responsável técnico não encontrado com ID: " + request.getResponsavelTecnico()));
                    // Valida se o profissional pertence ao mesmo tenant
                    if (responsavelTecnico.getTenant() != null && !responsavelTecnico.getTenant().getId().equals(tenant.getId())) {
                        log.warn("Tentativa de associar responsável técnico de outro tenant. ID: {}, Tenant responsável: {}, Tenant atual: {}", 
                                request.getResponsavelTecnico(), responsavelTecnico.getTenant().getId(), tenant.getId());
                        throw new BadRequestException("Responsável técnico não pertence ao mesmo tenant");
                    }
                    estabelecimento.setResponsavelTecnico(responsavelTecnico);
                } catch (NotFoundException e) {
                    log.warn("Responsável técnico não encontrado. ID: {}", request.getResponsavelTecnico());
                    throw e;
                }
            }

            // Carrega e valida responsável administrativo
            if (request.getResponsavelAdministrativo() != null) {
                try {
                    ProfissionaisSaude responsavelAdmin = profissionaisSaudeRepository.findById(request.getResponsavelAdministrativo())
                            .orElseThrow(() -> new NotFoundException("Responsável administrativo não encontrado com ID: " + request.getResponsavelAdministrativo()));
                    // Valida se o profissional pertence ao mesmo tenant
                    if (responsavelAdmin.getTenant() != null && !responsavelAdmin.getTenant().getId().equals(tenant.getId())) {
                        log.warn("Tentativa de associar responsável administrativo de outro tenant. ID: {}, Tenant responsável: {}, Tenant atual: {}", 
                                request.getResponsavelAdministrativo(), responsavelAdmin.getTenant().getId(), tenant.getId());
                        throw new BadRequestException("Responsável administrativo não pertence ao mesmo tenant");
                    }
                    estabelecimento.setResponsavelAdministrativo(responsavelAdmin);
                } catch (NotFoundException e) {
                    log.warn("Responsável administrativo não encontrado. ID: {}", request.getResponsavelAdministrativo());
                    throw e;
                }
            }

            estabelecimento.setActive(true);

            Estabelecimentos estabelecimentoSalvo = estabelecimentosRepository.save(estabelecimento);
            log.info("Estabelecimento criado com sucesso. ID: {}", estabelecimentoSalvo.getId());

            return estabelecimentosMapper.toResponse(estabelecimentoSalvo);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar estabelecimento. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar estabelecimento. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir estabelecimento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar estabelecimento. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "estabelecimentos", key = "#id")
    public EstabelecimentosResponse obterPorId(UUID id) {
        log.debug("Buscando estabelecimento por ID: {} (cache miss)", id);
        
        if (id == null) {
            log.warn("ID nulo recebido para busca de estabelecimento");
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        try {
            Estabelecimentos estabelecimento = estabelecimentosRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + id));

            log.debug("Estabelecimento encontrado. ID: {}", id);
            return estabelecimentosMapper.toResponse(estabelecimento);
        } catch (NotFoundException e) {
            log.warn("Estabelecimento não encontrado. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar estabelecimento. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar estabelecimento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar estabelecimento. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EstabelecimentosResponse> listar(Pageable pageable) {
        log.debug("Listando estabelecimentos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<Estabelecimentos> estabelecimentos = estabelecimentosRepository.findAll(pageable);
            log.debug("Listagem de estabelecimentos concluída. Total de elementos: {}", estabelecimentos.getTotalElements());
            return estabelecimentos.map(estabelecimentosMapper::toResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar estabelecimentos. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar estabelecimentos", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar estabelecimentos. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "estabelecimentos", key = "#id")
    public EstabelecimentosResponse atualizar(UUID id, EstabelecimentosRequest request) {
        log.debug("Atualizando estabelecimento. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de estabelecimento");
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de estabelecimento. ID: {}", id);
            throw new BadRequestException("Dados do estabelecimento são obrigatórios");
        }

        try {
            // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

            Estabelecimentos estabelecimentoExistente = estabelecimentosRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + id));
            
            // Valida CNPJ duplicado (exceto para o próprio estabelecimento sendo atualizado)
            validarCnpjDuplicado(request.getCnpj(), id);
            
            // Valida CNES duplicado (exceto para o próprio estabelecimento sendo atualizado)
            validarCnesDuplicado(request.getCodigoCnes(), id);

            // Usa mapper do MapStruct que preserva campos de controle automaticamente
            estabelecimentosMapper.updateFromRequest(request, estabelecimentoExistente);
            
            // Processa endereço principal usando findOrCreate para evitar duplicação
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            if (tenant == null) {
                log.error("Tenant não encontrado para usuário autenticado durante atualização");
                throw new BadRequestException("Tenant não encontrado. Verifique a autenticação do usuário.");
            }
            processarEnderecoPrincipal(request, estabelecimentoExistente, tenant);

            if (request.getResponsavelTecnico() != null) {
                try {
                    ProfissionaisSaude responsavelTecnico = profissionaisSaudeRepository.findById(request.getResponsavelTecnico())
                            .orElseThrow(() -> new NotFoundException("Responsável técnico não encontrado com ID: " + request.getResponsavelTecnico()));
                    // Valida se o profissional pertence ao mesmo tenant
                    if (responsavelTecnico.getTenant() != null && !responsavelTecnico.getTenant().getId().equals(tenant.getId())) {
                        log.warn("Tentativa de associar responsável técnico de outro tenant. ID: {}, Tenant responsável: {}, Tenant atual: {}", 
                                request.getResponsavelTecnico(), responsavelTecnico.getTenant().getId(), tenant.getId());
                        throw new BadRequestException("Responsável técnico não pertence ao mesmo tenant");
                    }
                    estabelecimentoExistente.setResponsavelTecnico(responsavelTecnico);
                } catch (NotFoundException e) {
                    log.warn("Responsável técnico não encontrado durante atualização. ID: {}", request.getResponsavelTecnico());
                    throw e;
                }
            } else {
                estabelecimentoExistente.setResponsavelTecnico(null);
            }

            if (request.getResponsavelAdministrativo() != null) {
                try {
                    ProfissionaisSaude responsavelAdmin = profissionaisSaudeRepository.findById(request.getResponsavelAdministrativo())
                            .orElseThrow(() -> new NotFoundException("Responsável administrativo não encontrado com ID: " + request.getResponsavelAdministrativo()));
                    // Valida se o profissional pertence ao mesmo tenant
                    if (responsavelAdmin.getTenant() != null && !responsavelAdmin.getTenant().getId().equals(tenant.getId())) {
                        log.warn("Tentativa de associar responsável administrativo de outro tenant. ID: {}, Tenant responsável: {}, Tenant atual: {}", 
                                request.getResponsavelAdministrativo(), responsavelAdmin.getTenant().getId(), tenant.getId());
                        throw new BadRequestException("Responsável administrativo não pertence ao mesmo tenant");
                    }
                    estabelecimentoExistente.setResponsavelAdministrativo(responsavelAdmin);
                } catch (NotFoundException e) {
                    log.warn("Responsável administrativo não encontrado durante atualização. ID: {}", request.getResponsavelAdministrativo());
                    throw e;
                }
            } else {
                estabelecimentoExistente.setResponsavelAdministrativo(null);
            }

            Estabelecimentos estabelecimentoAtualizado = estabelecimentosRepository.save(estabelecimentoExistente);
            log.info("Estabelecimento atualizado com sucesso. ID: {}", estabelecimentoAtualizado.getId());

            return estabelecimentosMapper.toResponse(estabelecimentoAtualizado);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar estabelecimento não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar estabelecimento. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar estabelecimento. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar estabelecimento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar estabelecimento. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "estabelecimentos", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo estabelecimento. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão de estabelecimento");
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        try {
            Estabelecimentos estabelecimento = estabelecimentosRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + id));

            if (Boolean.FALSE.equals(estabelecimento.getActive())) {
                log.warn("Tentativa de excluir estabelecimento já inativo. ID: {}", id);
                throw new BadRequestException("Estabelecimento já está inativo");
            }

            estabelecimento.setActive(false);
            estabelecimentosRepository.save(estabelecimento);
            log.info("Estabelecimento excluído (desativado) com sucesso. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir estabelecimento não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao excluir estabelecimento. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir estabelecimento. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir estabelecimento", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir estabelecimento. ID: {}", id, e);
            throw e;
        }
    }

    // Validações de dados básicos foram movidas para o Request usando Bean Validation
    // (@NotNull, @NotBlank, @Pattern, etc). Isso garante validação automática no Controller
    // e retorno de erro 400 padronizado via ApiExceptionHandler.

    /**
     * Processa o endereço principal do estabelecimento.
     * Se fornecido como UUID, busca o endereço existente.
     * Se fornecido como objeto completo EnderecoRequest, usa findOrCreate para evitar duplicação.
     * 
     * @param request request com dados do estabelecimento
     * @param estabelecimento estabelecimento a ser atualizado
     * @param tenant tenant do estabelecimento
     */
    private void processarEnderecoPrincipal(EstabelecimentosRequest request, Estabelecimentos estabelecimento, Tenant tenant) {
        // Prioriza objeto completo sobre UUID
        if (request.getEnderecoPrincipalCompleto() != null) {
            log.debug("Processando endereço principal como objeto completo. Usando findOrCreate para evitar duplicação");
            
            // Valida se o objeto endereço tem pelo menos algum campo preenchido
            EnderecoRequest enderecoRequest = request.getEnderecoPrincipalCompleto();
            boolean temCamposPreenchidos = (enderecoRequest.getLogradouro() != null && !enderecoRequest.getLogradouro().trim().isEmpty()) ||
                                          (enderecoRequest.getCep() != null && !enderecoRequest.getCep().trim().isEmpty()) ||
                                          (enderecoRequest.getCidade() != null) ||
                                          (enderecoRequest.getEstado() != null);
            
            if (!temCamposPreenchidos) {
                log.warn("Endereço principal completo fornecido mas sem campos preenchidos. Ignorando endereço.");
                estabelecimento.setEnderecoPrincipal(null);
                return;
            }
            
            // Converte EnderecoRequest para Endereco
            Endereco endereco = enderecoMapper.fromRequest(enderecoRequest);
            endereco.setActive(true);
            endereco.setTenant(tenant);
            
            // Garante valores padrão
            if (endereco.getSemNumero() == null) {
                endereco.setSemNumero(false);
            }
            
            // Processa relacionamentos estado e cidade
            if (enderecoRequest.getEstado() != null) {
                Estados estado = estadosRepository.findById(enderecoRequest.getEstado())
                        .orElseThrow(() -> new NotFoundException("Estado não encontrado com ID: " + enderecoRequest.getEstado()));
                endereco.setEstado(estado);
            }
            
            if (enderecoRequest.getCidade() != null) {
                Cidades cidade = cidadesRepository.findById(enderecoRequest.getCidade())
                        .orElseThrow(() -> new NotFoundException("Cidade não encontrada com ID: " + enderecoRequest.getCidade()));
                endereco.setCidade(cidade);
            }
            
            // Garante que pelo menos logradouro ou CEP estejam preenchidos antes de chamar findOrCreate
            if ((endereco.getLogradouro() == null || endereco.getLogradouro().trim().isEmpty()) &&
                (endereco.getCep() == null || endereco.getCep().trim().isEmpty())) {
                log.warn("Endereço sem logradouro nem CEP. Criando novo endereço sem buscar duplicados.");
                Endereco enderecoSalvo = enderecoRepository.save(endereco);
                estabelecimento.setEnderecoPrincipal(enderecoSalvo);
                log.info("Novo endereço criado sem busca de duplicados. ID: {}", enderecoSalvo.getId());
                return;
            }
            
            // Usa findOrCreate para evitar duplicação
            // Salva o ID antes de chamar findOrCreate para verificar se foi criado novo ou encontrado existente
            UUID idAntes = endereco.getId();
            Endereco enderecoProcessado = enderecoService.findOrCreate(endereco);
            estabelecimento.setEnderecoPrincipal(enderecoProcessado);
            
            // Verifica se foi criado novo endereço ou reutilizado existente
            boolean foiCriadoNovo = idAntes == null && enderecoProcessado.getId() != null;
            log.info("Endereço principal processado. ID: {} - {}", 
                    enderecoProcessado.getId(),
                    foiCriadoNovo ? "Novo endereço criado" : "Endereço existente reutilizado");
            
        } else if (request.getEnderecoPrincipal() != null) {
            // Se fornecido apenas UUID, busca endereço existente
            log.debug("Processando endereço principal como UUID: {}", request.getEnderecoPrincipal());
            try {
                Endereco enderecoPrincipal = enderecoRepository.findById(request.getEnderecoPrincipal())
                        .orElseThrow(() -> new NotFoundException("Endereço principal não encontrado com ID: " + request.getEnderecoPrincipal()));
                // Valida se o endereço pertence ao mesmo tenant
                if (enderecoPrincipal.getTenant() != null && !enderecoPrincipal.getTenant().getId().equals(tenant.getId())) {
                    log.warn("Tentativa de associar endereço de outro tenant. ID: {}, Tenant endereço: {}, Tenant atual: {}", 
                            request.getEnderecoPrincipal(), enderecoPrincipal.getTenant().getId(), tenant.getId());
                    throw new BadRequestException("Endereço principal não pertence ao mesmo tenant");
                }
                estabelecimento.setEnderecoPrincipal(enderecoPrincipal);
            } catch (NotFoundException e) {
                log.warn("Endereço principal não encontrado. ID: {}", request.getEnderecoPrincipal());
                throw e;
            }
        } else {
            // Se não fornecido, remove o endereço (apenas na atualização)
            estabelecimento.setEnderecoPrincipal(null);
        }
    }

    /**
     * Valida se o CNPJ já está cadastrado para outro estabelecimento no mesmo tenant.
     * 
     * @param cnpj CNPJ a ser validado
     * @param idExcluir ID do estabelecimento a ser excluído da validação (usado na atualização)
     * @throws ConflictException se o CNPJ já estiver cadastrado
     */
    private void validarCnpjDuplicado(String cnpj, UUID idExcluir) {
        // Se CNPJ não foi informado, não precisa validar
        if (cnpj == null || cnpj.trim().isEmpty()) {
            return;
        }

        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            log.error("Tenant não encontrado durante validação de CNPJ");
            throw new BadRequestException("Tenant não encontrado. Verifique a autenticação do usuário.");
        }
        
        estabelecimentosRepository.findByCnpjAndTenant(cnpj, tenant)
                .ifPresent(estabelecimentoExistente -> {
                    // Se estiver atualizando, permite se for o mesmo estabelecimento
                    if (idExcluir != null && estabelecimentoExistente.getId().equals(idExcluir)) {
                        return;
                    }
                    
                    log.warn("Tentativa de cadastrar estabelecimento com CNPJ já existente. CNPJ: {}, Tenant: {}", 
                            cnpj, tenant.getId());
                    throw new ConflictException("Já existe um estabelecimento cadastrado com o CNPJ informado: " + cnpj);
                });
    }

    /**
     * Valida se o código CNES já está cadastrado para outro estabelecimento no mesmo tenant.
     * 
     * @param codigoCnes código CNES a ser validado
     * @param idExcluir ID do estabelecimento a ser excluído da validação (usado na atualização)
     * @throws ConflictException se o CNES já estiver cadastrado
     */
    private void validarCnesDuplicado(String codigoCnes, UUID idExcluir) {
        // Se CNES não foi informado, não precisa validar
        if (codigoCnes == null || codigoCnes.trim().isEmpty()) {
            return;
        }

        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            log.error("Tenant não encontrado durante validação de CNES");
            throw new BadRequestException("Tenant não encontrado. Verifique a autenticação do usuário.");
        }
        
        estabelecimentosRepository.findByCodigoCnesAndTenant(codigoCnes, tenant)
                .ifPresent(estabelecimentoExistente -> {
                    // Se estiver atualizando, permite se for o mesmo estabelecimento
                    if (idExcluir != null && estabelecimentoExistente.getId().equals(idExcluir)) {
                        return;
                    }
                    
                    log.warn("Tentativa de cadastrar estabelecimento com código CNES já existente. CNES: {}, Tenant: {}", 
                            codigoCnes, tenant.getId());
                    throw new ConflictException("Já existe um estabelecimento cadastrado com o código CNES informado: " + codigoCnes);
                });
    }

    // Método removido - agora usa estabelecimentosMapper.updateFromRequest diretamente
    // O MapStruct já preserva campos de controle automaticamente
    // A lógica de atualização de relacionamentos (endereço principal, responsáveis) foi movida para o método atualizar
}

