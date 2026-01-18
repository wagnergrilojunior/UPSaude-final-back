package com.upsaude.service.api.support.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.sistema.usuario.UsuariosSistemaRequest;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.estabelecimento.UsuarioEstabelecimento;
import com.upsaude.entity.sistema.usuario.UsuariosSistema;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.repository.estabelecimento.UsuarioEstabelecimentoRepository;
import com.upsaude.repository.sistema.usuario.UsuariosSistemaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuariosSistemaVinculosHandler {

    private final UsuarioEstabelecimentoRepository usuarioEstabelecimentoRepository;
    private final EstabelecimentosRepository estabelecimentosRepository;
    private final UsuariosSistemaRepository usuariosSistemaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public void criarVinculosComPapel(UsuariosSistema usuario,
            List<UsuariosSistemaRequest.EstabelecimentoVinculoRequest> vinculos) {
        if (vinculos == null || vinculos.isEmpty()) {
            return;
        }

        UUID userId = null;
        if (usuario.getUser() != null) {
            userId = usuario.getUser().getId();
        }

        for (int i = 0; i < vinculos.size(); i++) {
            UsuariosSistemaRequest.EstabelecimentoVinculoRequest vinculoRequest = vinculos.get(i);
            UUID estabelecimentoId = vinculoRequest.getEstabelecimentoId();

            UsuarioEstabelecimento vinculoExistente = usuarioEstabelecimentoRepository
                    .findByUsuarioUserIdAndEstabelecimentoId(userId, estabelecimentoId)
                    .orElse(null);

            if (vinculoExistente != null) {
                if (Boolean.FALSE.equals(vinculoExistente.getActive())) {
                    vinculoExistente.setActive(true);
                    vinculoExistente.setTipoUsuario(vinculoRequest.getTipoUsuario());
                    usuarioEstabelecimentoRepository.save(vinculoExistente);
                    log.debug("Vínculo reativado com papel {} para usuário {} e estabelecimento {}",
                            vinculoRequest.getTipoUsuario(), usuario.getId(), estabelecimentoId);
                }
            } else {
                Estabelecimentos estabelecimento = estabelecimentosRepository.findById(estabelecimentoId)
                        .orElseThrow(() -> new NotFoundException(
                                "Estabelecimento não encontrado com ID: " + estabelecimentoId));

                UsuarioEstabelecimento vinculo = new UsuarioEstabelecimento();
                vinculo.setUsuario(usuario);
                vinculo.setEstabelecimento(estabelecimento);
                vinculo.setTenant(usuario.getTenant());
                vinculo.setTipoUsuario(vinculoRequest.getTipoUsuario());
                vinculo.setActive(true);

                usuarioEstabelecimentoRepository.save(vinculo);
                log.debug("Vínculo criado com papel {} para usuário {} e estabelecimento {}",
                        vinculoRequest.getTipoUsuario(), usuario.getId(), estabelecimentoId);
            }
        }
    }

    public void criarVinculosEstabelecimentos(UsuariosSistema usuario, List<UUID> estabelecimentosIds) {
        if (estabelecimentosIds == null || estabelecimentosIds.isEmpty()) {
            return;
        }

        UUID userId = null;
        if (usuario.getUser() != null) {
            userId = usuario.getUser().getId();
        }

        for (int i = 0; i < estabelecimentosIds.size(); i++) {
            UUID estabelecimentoId = estabelecimentosIds.get(i);

            UsuarioEstabelecimento vinculoExistente = usuarioEstabelecimentoRepository
                    .findByUsuarioUserIdAndEstabelecimentoId(userId, estabelecimentoId)
                    .orElse(null);

            if (vinculoExistente != null) {
                if (Boolean.FALSE.equals(vinculoExistente.getActive())) {
                    vinculoExistente.setActive(true);
                    usuarioEstabelecimentoRepository.save(vinculoExistente);
                    log.debug("Vínculo reativado para usuário {} e estabelecimento {}", usuario.getId(),
                            estabelecimentoId);
                }
            } else {
                Estabelecimentos estabelecimento = estabelecimentosRepository.findById(estabelecimentoId)
                        .orElseThrow(
                                () -> new NotFoundException("Estabelecimento não encontrado com ID: " + estabelecimentoId));

                UsuarioEstabelecimento vinculo = new UsuarioEstabelecimento();
                vinculo.setUsuario(usuario);
                vinculo.setEstabelecimento(estabelecimento);
                vinculo.setTenant(usuario.getTenant());
                vinculo.setActive(true);

                usuarioEstabelecimentoRepository.save(vinculo);
                log.debug("Vínculo criado para usuário {} e estabelecimento {}", usuario.getId(), estabelecimentoId);
            }
        }
    }

    public void atualizarVinculosComPapel(UsuariosSistema usuario,
            List<UsuariosSistemaRequest.EstabelecimentoVinculoRequest> vinculos) {
        UUID usuarioId = usuario.getId();
        UUID userId = null;
        if (usuario.getUser() != null) {
            userId = usuario.getUser().getId();
        }

        if (userId == null) {
            log.warn("Usuário não possui userId, não é possível atualizar vínculos");
            return;
        }

        entityManager.flush();
        entityManager.detach(usuario);

        List<UsuarioEstabelecimento> vinculosExistentes = usuarioEstabelecimentoRepository.findByUsuarioUserId(userId);

        List<UUID> novosIds = new ArrayList<UUID>();
        for (int i = 0; i < vinculos.size(); i++) {
            novosIds.add(vinculos.get(i).getEstabelecimentoId());
        }

        for (int i = 0; i < vinculosExistentes.size(); i++) {
            UsuarioEstabelecimento vinculo = vinculosExistentes.get(i);
            UUID estabelecimentoId = vinculo.getEstabelecimento().getId();
            boolean estaAtivo = Boolean.TRUE.equals(vinculo.getActive());
            boolean naoEstaNaLista = !novosIds.contains(estabelecimentoId);

            if (estaAtivo && naoEstaNaLista) {
                vinculo.setActive(false);
                usuarioEstabelecimentoRepository.save(vinculo);
                log.debug("Vínculo desativado para estabelecimento {}", estabelecimentoId);
            }
        }

        for (int i = 0; i < vinculos.size(); i++) {
            UsuariosSistemaRequest.EstabelecimentoVinculoRequest vinculoRequest = vinculos.get(i);
            UUID estabelecimentoId = vinculoRequest.getEstabelecimentoId();

            UsuarioEstabelecimento vinculoExistente = usuarioEstabelecimentoRepository
                    .findByUsuarioUserIdAndEstabelecimentoId(userId, estabelecimentoId)
                    .orElse(null);

            if (vinculoExistente != null) {
                vinculoExistente.setActive(true);
                vinculoExistente.setTipoUsuario(vinculoRequest.getTipoUsuario());
                usuarioEstabelecimentoRepository.save(vinculoExistente);
                log.debug("Vínculo atualizado com papel {} para estabelecimento {}",
                        vinculoRequest.getTipoUsuario(), estabelecimentoId);
            } else {
                UsuariosSistema usuarioGerenciado = usuariosSistemaRepository.findById(usuarioId)
                        .orElseThrow(() -> new NotFoundException("UsuariosSistema não encontrado com ID: " + usuarioId));

                Estabelecimentos estabelecimento = estabelecimentosRepository.findById(estabelecimentoId)
                        .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado"));

                UsuarioEstabelecimento vinculo = new UsuarioEstabelecimento();
                vinculo.setUsuario(usuarioGerenciado);
                vinculo.setEstabelecimento(estabelecimento);
                vinculo.setTenant(usuarioGerenciado.getTenant());
                vinculo.setTipoUsuario(vinculoRequest.getTipoUsuario());
                vinculo.setActive(true);

                usuarioEstabelecimentoRepository.save(vinculo);
                log.debug("Novo vínculo criado com papel {} para estabelecimento {}",
                        vinculoRequest.getTipoUsuario(), estabelecimentoId);
            }
        }
    }

    public void atualizarVinculosEstabelecimentos(UsuariosSistema usuario, List<UUID> estabelecimentosIds) {
        UUID usuarioId = usuario.getId();
        UUID userId = null;
        if (usuario.getUser() != null) {
            userId = usuario.getUser().getId();
        }

        if (userId == null) {
            log.warn("Usuário não possui userId, não é possível atualizar vínculos");
            return;
        }

        entityManager.flush();
        entityManager.detach(usuario);

        List<UsuarioEstabelecimento> vinculosExistentes = usuarioEstabelecimentoRepository.findByUsuarioUserId(userId);

        List<UUID> idsExistentes = new ArrayList<UUID>();
        for (int i = 0; i < vinculosExistentes.size(); i++) {
            UsuarioEstabelecimento vinculo = vinculosExistentes.get(i);
            if (Boolean.TRUE.equals(vinculo.getActive())) {
                idsExistentes.add(vinculo.getEstabelecimento().getId());
            }
        }

        for (int i = 0; i < vinculosExistentes.size(); i++) {
            UsuarioEstabelecimento vinculo = vinculosExistentes.get(i);
            UUID estabelecimentoId = vinculo.getEstabelecimento().getId();
            boolean estaAtivo = Boolean.TRUE.equals(vinculo.getActive());
            boolean naoEstaNaLista = !estabelecimentosIds.contains(estabelecimentoId);

            if (estaAtivo && naoEstaNaLista) {
                vinculo.setActive(false);
                usuarioEstabelecimentoRepository.save(vinculo);
                log.debug("Vínculo desativado para usuário {} e estabelecimento {}", usuarioId, estabelecimentoId);
            }
        }

        List<UUID> idsParaCriar = new ArrayList<UUID>();
        for (int i = 0; i < estabelecimentosIds.size(); i++) {
            UUID id = estabelecimentosIds.get(i);
            if (!idsExistentes.contains(id)) {
                idsParaCriar.add(id);
            }
        }

        if (!idsParaCriar.isEmpty()) {
            UsuariosSistema usuarioGerenciado = usuariosSistemaRepository.findById(usuarioId)
                    .orElseThrow(() -> new NotFoundException("UsuariosSistema não encontrado com ID: " + usuarioId));
            criarVinculosEstabelecimentos(usuarioGerenciado, idsParaCriar);
        }
    }
}
