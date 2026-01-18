package com.upsaude.service.api.support.usuario;

import java.util.List;
import java.util.UUID;

import org.hibernate.StaleStateException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.upsaude.entity.sistema.auth.User;
import com.upsaude.integration.supabase.SupabaseAuthService;
import com.upsaude.repository.sistema.auth.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuariosSistemaUserSyncHandler {

    private final UserRepository userRepository;
    private final SupabaseAuthService supabaseAuthService;

    @PersistenceContext
    private EntityManager entityManager;

    public int sincronizarUsers() {
        log.info("Iniciando sincronização de users - deletando users órfãos que não estão em usuarios_sistema");

        List<User> usersOrfaos = userRepository.findUsersNotInUsuariosSistema();
        int totalDeletados = 0;

        if (usersOrfaos.isEmpty()) {
            log.info("Nenhum user órfão encontrado. Sincronização concluída.");
            return 0;
        }

        log.info("Encontrados {} users órfãos para deletar", usersOrfaos.size());

        for (int i = 0; i < usersOrfaos.size(); i++) {
            User user = usersOrfaos.get(i);
            boolean deletado = deletarUserOrfao(user);
            if (deletado) {
                totalDeletados = totalDeletados + 1;
            }
        }

        log.info("Sincronização concluída. Total de users deletados: {}", totalDeletados);
        return totalDeletados;
    }

    private boolean deletarUserOrfao(User user) {
        try {
            UUID userId = user.getId();
            if (userId == null) {
                log.warn("User com ID nulo encontrado, pulando. Email: {}", user.getEmail());
                return false;
            }

            log.debug("Deletando user órfão - ID: {}, Email: {}", userId, user.getEmail());

            if (!userRepository.existsById(userId)) {
                log.debug("User já não existe no banco de dados, pulando - ID: {}", userId);
                return false;
            }

            deletarDoSupabaseAuth(userId);

            return deletarDoBanco(userId);

        } catch (Exception e) {
            log.error("Erro ao deletar user órfão - ID: {}, Email: {}, Erro: {}",
                    user.getId(), user.getEmail(), e.getMessage(), e);
            return false;
        }
    }

    private void deletarDoSupabaseAuth(UUID userId) {
        try {
            supabaseAuthService.deleteUser(userId);
            log.debug("User deletado do Supabase Auth - ID: {}", userId);
        } catch (Exception e) {
            log.warn("Erro ao deletar user do Supabase Auth (pode não existir) - ID: {}, Erro: {}",
                    userId, e.getMessage());
        }
    }

    private boolean deletarDoBanco(UUID userId) {
        try {
            userRepository.deleteById(userId);
            try {
                entityManager.flush();
            } catch (StaleStateException e) {
                log.debug("User já foi deletado (detectado no flush), continuando - ID: {}", userId);
                return true;
            } catch (ObjectOptimisticLockingFailureException e) {
                log.debug("User já foi deletado (detectado no flush), continuando - ID: {}", userId);
                return true;
            }

            log.debug("User deletado do banco de dados local - ID: {}", userId);
            return true;

        } catch (EmptyResultDataAccessException e) {
            log.debug("User já foi deletado (EmptyResultDataAccessException), continuando - ID: {}", userId);
            return true;
        } catch (StaleStateException e) {
            log.debug("User já foi deletado (StaleStateException), continuando - ID: {}", userId);
            return true;
        } catch (ObjectOptimisticLockingFailureException e) {
            log.debug("User já foi deletado (ObjectOptimisticLockingFailureException), continuando - ID: {}", userId);
            return true;
        } catch (Exception e) {
            if (!userRepository.existsById(userId)) {
                log.debug("User já foi deletado (verificação pós-exceção), continuando - ID: {}", userId);
                return true;
            } else {
                log.warn("Erro inesperado ao deletar user do banco - ID: {}, Erro: {}", userId, e.getMessage());
                return false;
            }
        }
    }
}
