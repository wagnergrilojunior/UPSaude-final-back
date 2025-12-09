package com.upsaude.service.impl;

import com.upsaude.api.request.EstabelecimentosRequest;
import com.upsaude.api.response.EstabelecimentosResponse;
import com.upsaude.entity.Endereco;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.Tenant;
import com.upsaude.entity.Cidades;
import com.upsaude.entity.Estados;
import com.upsaude.exception.BadRequestException;
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
            validarDadosBasicos(request);
            
            // Valida CNPJ duplicado
            validarCnpjDuplicado(request.getCnpj(), null);

            Estabelecimentos estabelecimento = estabelecimentosMapper.fromRequest(request);
            
            // Define o tenant do usuário autenticado
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            estabelecimento.setTenant(tenant);
            
            // Processa endereço principal usando findOrCreate para evitar duplicação
            processarEnderecoPrincipal(request, estabelecimento, tenant);

            // Carrega e valida responsável técnico
            if (request.getResponsavelTecnico() != null) {
                ProfissionaisSaude responsavelTecnico = profissionaisSaudeRepository.findById(request.getResponsavelTecnico())
                        .orElseThrow(() -> new NotFoundException("Responsável técnico não encontrado com ID: " + request.getResponsavelTecnico()));
                estabelecimento.setResponsavelTecnico(responsavelTecnico);
            }

            // Carrega e valida responsável administrativo
            if (request.getResponsavelAdministrativo() != null) {
                ProfissionaisSaude responsavelAdmin = profissionaisSaudeRepository.findById(request.getResponsavelAdministrativo())
                        .orElseThrow(() -> new NotFoundException("Responsável administrativo não encontrado com ID: " + request.getResponsavelAdministrativo()));
                estabelecimento.setResponsavelAdministrativo(responsavelAdmin);
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
            validarDadosBasicos(request);

            Estabelecimentos estabelecimentoExistente = estabelecimentosRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + id));
            
            // Valida CNPJ duplicado (exceto para o próprio estabelecimento sendo atualizado)
            validarCnpjDuplicado(request.getCnpj(), id);

            // Usa mapper do MapStruct que preserva campos de controle automaticamente
            estabelecimentosMapper.updateFromRequest(request, estabelecimentoExistente);
            
            // Processa endereço principal usando findOrCreate para evitar duplicação
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            processarEnderecoPrincipal(request, estabelecimentoExistente, tenant);

            if (request.getResponsavelTecnico() != null) {
                ProfissionaisSaude responsavelTecnico = profissionaisSaudeRepository.findById(request.getResponsavelTecnico())
                        .orElseThrow(() -> new NotFoundException("Responsável técnico não encontrado com ID: " + request.getResponsavelTecnico()));
                estabelecimentoExistente.setResponsavelTecnico(responsavelTecnico);
            } else {
                estabelecimentoExistente.setResponsavelTecnico(null);
            }

            if (request.getResponsavelAdministrativo() != null) {
                ProfissionaisSaude responsavelAdmin = profissionaisSaudeRepository.findById(request.getResponsavelAdministrativo())
                        .orElseThrow(() -> new NotFoundException("Responsável administrativo não encontrado com ID: " + request.getResponsavelAdministrativo()));
                estabelecimentoExistente.setResponsavelAdministrativo(responsavelAdmin);
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

    private void validarDadosBasicos(EstabelecimentosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do estabelecimento são obrigatórios");
        }
        if (request.getNome() == null || request.getNome().trim().isEmpty()) {
            throw new BadRequestException("Nome do estabelecimento é obrigatório");
        }
        if (request.getTipo() == null) {
            throw new BadRequestException("Tipo do estabelecimento é obrigatório");
        }
    }

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
            
            // Converte EnderecoRequest para Endereco
            Endereco endereco = enderecoMapper.fromRequest(request.getEnderecoPrincipalCompleto());
            endereco.setActive(true);
            endereco.setTenant(tenant);
            
            // Garante valores padrão
            if (endereco.getSemNumero() == null) {
                endereco.setSemNumero(false);
            }
            
            // Processa relacionamentos estado e cidade
            if (request.getEnderecoPrincipalCompleto().getEstado() != null) {
                Estados estado = estadosRepository.findById(request.getEnderecoPrincipalCompleto().getEstado())
                        .orElseThrow(() -> new NotFoundException("Estado não encontrado com ID: " + request.getEnderecoPrincipalCompleto().getEstado()));
                endereco.setEstado(estado);
            }
            
            if (request.getEnderecoPrincipalCompleto().getCidade() != null) {
                Cidades cidade = cidadesRepository.findById(request.getEnderecoPrincipalCompleto().getCidade())
                        .orElseThrow(() -> new NotFoundException("Cidade não encontrada com ID: " + request.getEnderecoPrincipalCompleto().getCidade()));
                endereco.setCidade(cidade);
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
            Endereco enderecoPrincipal = enderecoRepository.findById(request.getEnderecoPrincipal())
                    .orElseThrow(() -> new NotFoundException("Endereço principal não encontrado com ID: " + request.getEnderecoPrincipal()));
            estabelecimento.setEnderecoPrincipal(enderecoPrincipal);
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
     * @throws BadRequestException se o CNPJ já estiver cadastrado
     */
    private void validarCnpjDuplicado(String cnpj, UUID idExcluir) {
        // Se CNPJ não foi informado, não precisa validar
        if (cnpj == null || cnpj.trim().isEmpty()) {
            return;
        }

        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        
        estabelecimentosRepository.findByCnpjAndTenant(cnpj, tenant)
                .ifPresent(estabelecimentoExistente -> {
                    // Se estiver atualizando, permite se for o mesmo estabelecimento
                    if (idExcluir != null && estabelecimentoExistente.getId().equals(idExcluir)) {
                        return;
                    }
                    
                    log.warn("Tentativa de cadastrar estabelecimento com CNPJ já existente. CNPJ: {}, Tenant: {}", 
                            cnpj, tenant.getId());
                    throw new BadRequestException("Já existe um estabelecimento cadastrado com o CNPJ informado: " + cnpj);
                });
    }

    // Método removido - agora usa estabelecimentosMapper.updateFromRequest diretamente
    // O MapStruct já preserva campos de controle automaticamente
    // A lógica de atualização de relacionamentos (endereço principal, responsáveis) foi movida para o método atualizar
}

