package com.upsaude.service.impl.api.sistema.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.upsaude.api.request.sistema.usuario.UsuariosSistemaRequest;
import com.upsaude.api.response.sistema.usuario.UsuariosSistemaResponse;
import com.upsaude.entity.sistema.usuario.UsuariosSistema;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.integration.supabase.SupabaseAuthService;
import com.upsaude.repository.sistema.usuario.UsuariosSistemaRepository;
import com.upsaude.service.api.sistema.usuario.UsuariosSistemaService;
import com.upsaude.service.api.support.usuario.UsuariosSistemaCreator;
import com.upsaude.service.api.support.usuario.UsuariosSistemaDomainService;
import com.upsaude.service.api.support.usuario.UsuariosSistemaFotoHandler;
import com.upsaude.service.api.support.usuario.UsuariosSistemaPageableAdjuster;
import com.upsaude.service.api.support.usuario.UsuariosSistemaResponseBuilder;
import com.upsaude.service.api.support.usuario.UsuariosSistemaSenhaHandler;
import com.upsaude.service.api.support.usuario.UsuariosSistemaUpdater;
import com.upsaude.service.api.support.usuario.UsuariosSistemaUserSyncHandler;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuariosSistemaServiceImpl implements UsuariosSistemaService {

    private final UsuariosSistemaRepository repository;
    private final SupabaseAuthService supabaseAuthService;

    private final UsuariosSistemaCreator creator;
    private final UsuariosSistemaUpdater updater;
    private final UsuariosSistemaResponseBuilder responseBuilder;
    private final UsuariosSistemaDomainService domainService;
    private final UsuariosSistemaFotoHandler fotoHandler;
    private final UsuariosSistemaSenhaHandler senhaHandler;
    private final UsuariosSistemaPageableAdjuster pageableAdjuster;
    private final UsuariosSistemaUserSyncHandler userSyncHandler;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public UsuariosSistemaResponse criar(UsuariosSistemaRequest request) {
        log.debug("Criando novo usuariossistema");

        try {
            UsuariosSistema saved = creator.criar(request);
            return responseBuilder.build(saved);
        } catch (BadRequestException e) {
            log.warn("Erro de validação ao criar UsuariosSistema. Erro: {}", e.getMessage());
            throw e;
        } catch (NotFoundException e) {
            log.warn("Erro de validação ao criar UsuariosSistema. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar UsuariosSistema. Exception: {}", e.getClass().getSimpleName(),
                    e);
            throw new InternalServerErrorException("Erro ao persistir UsuariosSistema", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar UsuariosSistema. Exception: {}", e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    public UsuariosSistemaResponse obterPorId(UUID id) {
        log.debug("Buscando usuariossistema por ID: {}", id);
        if (id == null) {
            throw new BadRequestException("ID do usuariossistema é obrigatório");
        }

        UsuariosSistema entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("UsuariosSistema não encontrado com ID: " + id));

        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuariosSistemaResponse> listar(Pageable pageable) {
        log.debug("Listando UsuariosSistemas paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Pageable adjustedPageable = pageableAdjuster.ajustarParaCamposEmbeddados(pageable);

            Specification<UsuariosSistema> spec = new Specification<UsuariosSistema>() {
                @Override
                public Predicate toPredicate(
                        jakarta.persistence.criteria.Root<UsuariosSistema> root,
                        jakarta.persistence.criteria.CriteriaQuery<?> query,
                        jakarta.persistence.criteria.CriteriaBuilder cb) {
                    List<Predicate> predicates = new ArrayList<Predicate>();
                    Predicate[] array = new Predicate[predicates.size()];
                    return cb.and(predicates.toArray(array));
                }
            };

            Page<UsuariosSistema> page = repository.findAll(spec, adjustedPageable);

            List<UsuariosSistema> content = page.getContent();
            for (int i = 0; i < content.size(); i++) {
                UsuariosSistema us = content.get(i);
                Hibernate.initialize(us.getEstabelecimentosVinculados());
            }

            log.debug("Listagem de usuários do sistema concluída. Total de elementos: {}", page.getTotalElements());

            Page<UsuariosSistemaResponse> responsePage = page.map(new java.util.function.Function<UsuariosSistema, UsuariosSistemaResponse>() {
                @Override
                public UsuariosSistemaResponse apply(UsuariosSistema entity) {
                    return responseBuilder.build(entity);
                }
            });

            return responsePage;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar UsuariosSistema. Exception: {}", e.getClass().getSimpleName(),
                    e);
            throw new InternalServerErrorException("Erro ao listar UsuariosSistema", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar UsuariosSistema. Exception: {}", e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    public UsuariosSistemaResponse atualizar(UUID id, UsuariosSistemaRequest request) {
        log.debug("Atualizando usuariossistema. ID: {}", id);

        UsuariosSistema updated = updater.atualizar(id, request);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.info("Excluindo usuário PERMANENTEMENTE em 2 etapas. UsuariosSistema ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do usuariossistema é obrigatório");
        }

        UsuariosSistema entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("UsuariosSistema não encontrado com ID: " + id));

        UUID userId = null;
        if (entity.getUser() != null) {
            userId = entity.getUser().getId();
        }

        repository.delete(entity);
        log.info("ETAPA 1: UsuariosSistema e vínculos deletados PERMANENTEMENTE. ID: {}", id);

        try {
            supabaseAuthService.deleteUser(userId);
            log.info("ETAPA 2: User deletado PERMANENTEMENTE do Supabase Auth. UserID: {}", userId);
        } catch (Exception e) {
            log.error("Erro ao deletar User do Supabase Auth. UserID: {}, Exception: {}", userId,
                    e.getClass().getName(), e);

            log.warn("UsuariosSistema foi deletado mas User não foi removido do Supabase (pode não existir)");
        }
    }

    @Override
    @Transactional
    public String uploadFoto(UUID id, MultipartFile file) {
        return fotoHandler.uploadFoto(id, file);
    }

    @Override
    public String obterFotoUrl(UUID id) {
        return fotoHandler.obterFotoUrl(id);
    }

    @Override
    @Transactional
    public void deletarFoto(UUID id) {
        fotoHandler.deletarFoto(id);
    }

    @Override
    @Transactional
    public void trocarSenha(UUID id, String novaSenha) {
        senhaHandler.trocarSenha(id, novaSenha);
    }

    @Override
    @Transactional
    public int sincronizarUsers() {
        return userSyncHandler.sincronizarUsers();
    }
}
