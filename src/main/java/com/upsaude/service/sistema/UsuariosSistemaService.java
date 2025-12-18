package com.upsaude.service.sistema;

import com.upsaude.api.request.sistema.UsuariosSistemaRequest;
import com.upsaude.api.response.sistema.UsuariosSistemaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UsuariosSistemaService {

    UsuariosSistemaResponse criar(UsuariosSistemaRequest request);

    UsuariosSistemaResponse obterPorId(UUID id);

    Page<UsuariosSistemaResponse> listar(Pageable pageable);

    UsuariosSistemaResponse atualizar(UUID id, UsuariosSistemaRequest request);

    void excluir(UUID id);

    String uploadFoto(UUID id, MultipartFile file);

    String obterFotoUrl(UUID id);

    void deletarFoto(UUID id);
}
