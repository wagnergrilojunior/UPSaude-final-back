package com.upsaude.service;

import com.upsaude.api.request.UsuariosSistemaRequest;
import com.upsaude.api.response.UsuariosSistemaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a UsuariosSistema.
 *
 * @author UPSaúde
 */
public interface UsuariosSistemaService {

    UsuariosSistemaResponse criar(UsuariosSistemaRequest request);

    UsuariosSistemaResponse obterPorId(UUID id);

    Page<UsuariosSistemaResponse> listar(Pageable pageable);

    UsuariosSistemaResponse atualizar(UUID id, UsuariosSistemaRequest request);

    void excluir(UUID id);

    /**
     * Faz upload da foto do usuário para o Supabase Storage.
     *
     * @param id ID do usuário
     * @param file Arquivo de imagem
     * @return URL pública da foto
     */
    String uploadFoto(UUID id, MultipartFile file);

    /**
     * Obtém a URL da foto do usuário.
     *
     * @param id ID do usuário
     * @return URL pública da foto ou null se não houver foto
     */
    String obterFotoUrl(UUID id);

    /**
     * Deleta a foto do usuário do Supabase Storage.
     *
     * @param id ID do usuário
     */
    void deletarFoto(UUID id);
}
