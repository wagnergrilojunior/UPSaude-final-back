package com.upsaude.service.impl.api.farmacia;

import com.upsaude.api.request.farmacia.FarmaciaRequest;
import com.upsaude.api.response.farmacia.FarmaciaResponse;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.farmacia.Farmacia;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.repository.farmacia.FarmaciaRepository;
import com.upsaude.service.api.farmacia.FarmaciaService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FarmaciaServiceImpl implements FarmaciaService {

    private final FarmaciaRepository farmaciaRepository;
    private final EstabelecimentosRepository estabelecimentosRepository;
    private final TenantService tenantService;

    @Override
    @Transactional
    public FarmaciaResponse criar(FarmaciaRequest request) {
        log.debug("Criando nova farmácia. Request: {}", request);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            
            if (tenant == null || tenant.getId() == null || !tenant.getId().equals(tenantId)) {
                throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
            }

            Farmacia entity = new Farmacia();
            entity.setNome(request.getNome());
            entity.setCodigoCnes(request.getCodigoCnes());
            entity.setCodigoFarmaciaInterno(request.getCodigoFarmaciaInterno());
            entity.setResponsavelTecnico(request.getResponsavelTecnico());
            entity.setCrfResponsavel(request.getCrfResponsavel());
            entity.setTelefone(request.getTelefone());
            entity.setEmail(request.getEmail());
            entity.setObservacoes(request.getObservacoes());
            entity.setActive(true);
            entity.setTenant(tenant);

            // Processar estabelecimento se fornecido
            if (request.getEstabelecimentoId() != null) {
                Estabelecimentos estabelecimento = estabelecimentosRepository
                    .findById(request.getEstabelecimentoId())
                    .orElseThrow(() -> new BadRequestException("Estabelecimento não encontrado com ID: " + request.getEstabelecimentoId()));
                
                // Validar que o estabelecimento pertence ao mesmo tenant
                if (!estabelecimento.getTenant().getId().equals(tenantId)) {
                    throw new BadRequestException("Estabelecimento não pertence ao tenant atual");
                }
                
                entity.setEstabelecimento(estabelecimento);
            }

            Farmacia saved = farmaciaRepository.save(Objects.requireNonNull(entity));
            log.info("Farmácia criada com sucesso. ID: {}, tenant: {}", saved.getId(), tenantId);
            
            return buildResponse(saved);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar farmácia. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar farmácia. Request: {}", request, e);
            throw new InternalServerErrorException("Erro ao persistir farmácia", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar farmácia. Request: {}", request, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public FarmaciaResponse obterPorId(UUID id) {
        log.debug("Buscando farmácia por ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para busca de farmácia");
            throw new BadRequestException("ID da farmácia é obrigatório");
        }

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Farmacia farmacia = farmaciaRepository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> {
                    log.warn("Farmácia não encontrada. ID: {}, tenant: {}", id, tenantId);
                    return new NotFoundException("Farmácia não encontrada com ID: " + id);
                });

            log.debug("Farmácia encontrada. ID: {}", id);
            return buildResponse(farmacia);
        } catch (NotFoundException e) {
            log.warn("Farmácia não encontrada. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao buscar farmácia. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao buscar farmácia", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao buscar farmácia. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FarmaciaResponse> listar(Pageable pageable) {
        log.debug("Listando farmácias paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Page<Farmacia> farmacias = farmaciaRepository.findAllByTenant(tenantId, pageable);
            log.debug("Listagem de farmácias concluída. Total de elementos: {}", farmacias.getTotalElements());
            return farmacias.map(this::buildResponse);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar farmácias. Pageable: {}", pageable, e);
            throw new InternalServerErrorException("Erro ao listar farmácias", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar farmácias. Pageable: {}", pageable, e);
            throw e;
        }
    }

    @Override
    @Transactional
    public FarmaciaResponse atualizar(UUID id, FarmaciaRequest request) {
        log.debug("Atualizando farmácia. ID: {}, Request: {}", id, request);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de farmácia");
            throw new BadRequestException("ID da farmácia é obrigatório");
        }
        if (request == null) {
            log.warn("Request nulo recebido para atualização de farmácia. ID: {}", id);
            throw new BadRequestException("Dados da farmácia são obrigatórios");
        }

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            
            if (tenant == null || tenant.getId() == null || !tenant.getId().equals(tenantId)) {
                throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
            }

            Farmacia entity = farmaciaRepository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> {
                    log.warn("Farmácia não encontrada para atualização. ID: {}, tenant: {}", id, tenantId);
                    return new NotFoundException("Farmácia não encontrada com ID: " + id);
                });

            // Atualizar campos
            entity.setNome(request.getNome());
            entity.setCodigoCnes(request.getCodigoCnes());
            entity.setCodigoFarmaciaInterno(request.getCodigoFarmaciaInterno());
            entity.setResponsavelTecnico(request.getResponsavelTecnico());
            entity.setCrfResponsavel(request.getCrfResponsavel());
            entity.setTelefone(request.getTelefone());
            entity.setEmail(request.getEmail());
            entity.setObservacoes(request.getObservacoes());
            entity.setTenant(tenant);

            // Processar estabelecimento se fornecido
            if (request.getEstabelecimentoId() != null) {
                Estabelecimentos estabelecimento = estabelecimentosRepository
                    .findById(request.getEstabelecimentoId())
                    .orElseThrow(() -> new BadRequestException("Estabelecimento não encontrado com ID: " + request.getEstabelecimentoId()));
                
                // Validar que o estabelecimento pertence ao mesmo tenant
                if (!estabelecimento.getTenant().getId().equals(tenantId)) {
                    throw new BadRequestException("Estabelecimento não pertence ao tenant atual");
                }
                
                entity.setEstabelecimento(estabelecimento);
            } else {
                entity.setEstabelecimento(null);
            }

            Farmacia saved = farmaciaRepository.save(Objects.requireNonNull(entity));
            log.info("Farmácia atualizada com sucesso. ID: {}", saved.getId());
            return buildResponse(saved);
        } catch (NotFoundException e) {
            log.warn("Tentativa de atualizar farmácia não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao atualizar farmácia. ID: {}, Request: {}. Erro: {}", id, request, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao atualizar farmácia. ID: {}, Request: {}", id, request, e);
            throw new InternalServerErrorException("Erro ao atualizar farmácia", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao atualizar farmácia. ID: {}, Request: {}", id, request, e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo farmácia permanentemente. ID: {}", id);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            Farmacia entity = farmaciaRepository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> {
                    log.warn("Farmácia não encontrada para exclusão. ID: {}, tenant: {}", id, tenantId);
                    return new NotFoundException("Farmácia não encontrada com ID: " + id);
                });
            
            farmaciaRepository.delete(Objects.requireNonNull(entity));
            log.info("Farmácia excluída permanentemente com sucesso. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de excluir farmácia não existente. ID: {}", id);
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir farmácia. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao excluir farmácia", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao excluir farmácia. ID: {}", id, e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        log.debug("Inativando farmácia. ID: {}", id);

        try {
            UUID tenantId = tenantService.validarTenantAtual();
            
            if (id == null) {
                log.warn("ID nulo recebido para inativação de farmácia");
                throw new BadRequestException("ID da farmácia é obrigatório");
            }

            Farmacia entity = farmaciaRepository.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> {
                    log.warn("Farmácia não encontrada para inativação. ID: {}, tenant: {}", id, tenantId);
                    return new NotFoundException("Farmácia não encontrada com ID: " + id);
                });
            
            if (!entity.getActive()) {
                throw new BadRequestException("Farmácia já está inativa");
            }
            
            entity.setActive(false);
            farmaciaRepository.save(Objects.requireNonNull(entity));
            log.info("Farmácia inativada com sucesso. ID: {}", id);
        } catch (NotFoundException e) {
            log.warn("Tentativa de inativar farmácia não existente. ID: {}", id);
            throw e;
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao inativar farmácia. ID: {}. Erro: {}", id, e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar farmácia. ID: {}", id, e);
            throw new InternalServerErrorException("Erro ao inativar farmácia", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao inativar farmácia. ID: {}", id, e);
            throw e;
        }
    }

    private FarmaciaResponse buildResponse(Farmacia entity) {
        if (entity == null) {
            return null;
        }

        UUID tenantId = entity.getTenantId();
        if (tenantId == null && entity.getTenant() != null) {
            tenantId = entity.getTenant().getId();
        }

        UUID estabelecimentoId = entity.getEstabelecimentoId();
        if (estabelecimentoId == null && entity.getEstabelecimento() != null) {
            estabelecimentoId = entity.getEstabelecimento().getId();
        }

        return FarmaciaResponse.builder()
            .id(entity.getId())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .active(entity.getActive())
            .nome(entity.getNome())
            .codigoCnes(entity.getCodigoCnes())
            .codigoFarmaciaInterno(entity.getCodigoFarmaciaInterno())
            .responsavelTecnico(entity.getResponsavelTecnico())
            .crfResponsavel(entity.getCrfResponsavel())
            .telefone(entity.getTelefone())
            .email(entity.getEmail())
            .observacoes(entity.getObservacoes())
            .tenantId(tenantId)
            .estabelecimentoId(estabelecimentoId)
            .build();
    }
}

