package com.upsaude.service.impl.api.financeiro;

import com.upsaude.entity.faturamento.DocumentoFaturamento;
import com.upsaude.entity.financeiro.CompetenciaFinanceiraTenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.faturamento.DocumentoFaturamentoRepository;
import com.upsaude.repository.financeiro.CompetenciaFinanceiraTenantRepository;
import com.upsaude.service.api.financeiro.BpaExportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BpaExportServiceImpl implements BpaExportService {

    private final CompetenciaFinanceiraTenantRepository competenciaTenantRepository;
    private final DocumentoFaturamentoRepository documentoFaturamentoRepository;

    @Override
    @Transactional(readOnly = true)
    public Resource exportarBpa(UUID competenciaId, UUID tenantId) {
        log.info("Exportando BPA para competência {} e tenant {}", competenciaId, tenantId);
        
        // Buscar competência tenant para verificar se foi fechada e obter documento BPA
        CompetenciaFinanceiraTenant competenciaTenant = competenciaTenantRepository
                .findByTenantAndCompetencia(tenantId, competenciaId)
                .orElseThrow(() -> new NotFoundException("Competência financeira não encontrada para o tenant"));
        
        if (competenciaTenant.getDocumentoBpaFechamento() == null) {
            throw new NotFoundException("BPA não foi gerado para esta competência. É necessário fechar a competência primeiro.");
        }
        
        DocumentoFaturamento documentoBpa = documentoFaturamentoRepository
                .findByIdAndTenant(competenciaTenant.getDocumentoBpaFechamento().getId(), tenantId)
                .orElseThrow(() -> new NotFoundException("Documento BPA não encontrado"));
        
        // O conteúdo do BPA está armazenado no payloadLayout como texto
        String conteudoBpa = documentoBpa.getPayloadLayout();
        if (conteudoBpa == null || conteudoBpa.isEmpty()) {
            throw new NotFoundException("Conteúdo do arquivo BPA não encontrado no documento");
        }
        
        // Converter para bytes usando ISO-8859-1 (padrão DATASUS)
        byte[] bytes;
        try {
            bytes = conteudoBpa.getBytes("ISO-8859-1");
        } catch (java.io.UnsupportedEncodingException e) {
            log.warn("Erro ao converter para ISO-8859-1, usando UTF-8", e);
            bytes = conteudoBpa.getBytes(StandardCharsets.UTF_8);
        }
        
        log.info("BPA exportado com sucesso. Tamanho: {} bytes", bytes.length);
        return new ByteArrayResource(bytes);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean bpaFoiGerado(UUID competenciaId, UUID tenantId) {
        return competenciaTenantRepository
                .findByTenantAndCompetencia(tenantId, competenciaId)
                .map(CompetenciaFinanceiraTenant::getDocumentoBpaFechamento)
                .isPresent();
    }
}
