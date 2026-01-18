package com.upsaude.service.api.support.usuario;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.upsaude.entity.sistema.usuario.UsuariosSistema;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.integration.supabase.SupabaseStorageService;
import com.upsaude.repository.sistema.usuario.UsuariosSistemaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuariosSistemaFotoHandler {

    private final UsuariosSistemaRepository repository;
    private final SupabaseStorageService supabaseStorageService;

    public String uploadFoto(UUID id, MultipartFile file) {
        log.debug("Fazendo upload de foto para usuário: {}", id);

        UsuariosSistema usuario = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado com ID: " + id));

        if (usuario.getUser() == null || usuario.getUser().getId() == null) {
            throw new BadRequestException(
                    "Usuário não possui User vinculado. É necessário criar/atualizar o usuário com email antes de fazer upload de foto.");
        }

        String fotoUrlAntiga = null;
        if (usuario.getDadosExibicao() != null && usuario.getDadosExibicao().getFotoUrl() != null
                && !usuario.getDadosExibicao().getFotoUrl().isEmpty()) {
            fotoUrlAntiga = usuario.getDadosExibicao().getFotoUrl();
            supabaseStorageService.deletarFotoUsuario(fotoUrlAntiga);
        }

        String fotoUrl = supabaseStorageService.uploadFotoUsuario(file, usuario.getUser().getId());

        if (usuario.getDadosExibicao() == null) {
            usuario.setDadosExibicao(new com.upsaude.entity.embeddable.DadosExibicaoUsuario());
        }
        usuario.getDadosExibicao().setFotoUrl(fotoUrl);
        repository.save(usuario);

        log.info("Foto enviada com sucesso para usuário {}. URL: {}", id, fotoUrl);
        return fotoUrl;
    }

    public String obterFotoUrl(UUID id) {
        log.debug("Buscando URL da foto para usuário: {}", id);

        UsuariosSistema usuario = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado com ID: " + id));

        String fotoUrl = null;
        if (usuario.getDadosExibicao() != null) {
            fotoUrl = usuario.getDadosExibicao().getFotoUrl();
        }

        return fotoUrl;
    }

    public void deletarFoto(UUID id) {
        log.debug("Deletando foto do usuário: {}", id);

        UsuariosSistema usuario = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado com ID: " + id));

        boolean temFoto = false;
        String fotoUrl = null;

        if (usuario.getDadosExibicao() != null && usuario.getDadosExibicao().getFotoUrl() != null
                && !usuario.getDadosExibicao().getFotoUrl().isEmpty()) {
            temFoto = true;
            fotoUrl = usuario.getDadosExibicao().getFotoUrl();
        }

        if (temFoto) {
            supabaseStorageService.deletarFotoUsuario(fotoUrl);
            usuario.getDadosExibicao().setFotoUrl(null);
            repository.save(usuario);
            log.info("Foto deletada com sucesso para usuário: {}", id);
        } else {
            log.debug("Usuário {} não possui foto para deletar", id);
        }
    }
}
