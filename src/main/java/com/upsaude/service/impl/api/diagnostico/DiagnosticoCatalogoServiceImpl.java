package com.upsaude.service.impl.api.diagnostico;

import com.upsaude.api.response.diagnostico.Ciap2Response;
import com.upsaude.api.response.diagnostico.Cid10Response;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.diagnostico.Ciap2Mapper;
import com.upsaude.mapper.diagnostico.Cid10Mapper;
import com.upsaude.repository.referencia.Ciap2Repository;
import com.upsaude.repository.referencia.cid.Cid10SubcategoriasRepository;
import com.upsaude.service.api.diagnostico.DiagnosticoCatalogoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiagnosticoCatalogoServiceImpl implements DiagnosticoCatalogoService {

    private final Cid10SubcategoriasRepository cid10Repository;
    private final Ciap2Repository ciap2Repository;
    private final Cid10Mapper cid10Mapper;
    private final Ciap2Mapper ciap2Mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<Cid10Response> listarCid10(String termo, @NonNull Pageable pageable) {
        if (termo != null && !termo.isBlank()) {
            return cid10Repository.buscarPorTermo(termo, pageable).map(cid10Mapper::toResponse);
        }
        return cid10Repository.findAll(pageable).map(cid10Mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Ciap2Response> listarCiap2(String termo, @NonNull Pageable pageable) {
        if (termo != null && !termo.isBlank()) {
            return ciap2Repository.buscarPorTermo(termo, pageable).map(ciap2Mapper::toResponse);
        }
        return ciap2Repository.findByAtivoTrue(pageable).map(ciap2Mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Cid10Response buscarCid10PorCodigo(String codigo) {
        return cid10Repository.findBySubcat(codigo)
                .map(cid10Mapper::toResponse)
                .orElseThrow(() -> new NotFoundException("CID-10 não encontrado: " + codigo));
    }

    @Override
    @Transactional(readOnly = true)
    public Ciap2Response buscarCiap2PorCodigo(String codigo) {
        return ciap2Repository.findByCodigo(codigo)
                .map(ciap2Mapper::toResponse)
                .orElseThrow(() -> new NotFoundException("CIAP-2 não encontrado: " + codigo));
    }
}
