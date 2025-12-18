package com.upsaude.repository.profissional.equipe;

import com.upsaude.entity.profissional.equipe.VinculoProfissionalEquipe;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VinculoProfissionalEquipeRepository extends JpaRepository<VinculoProfissionalEquipe, UUID> {
    
    Optional<VinculoProfissionalEquipe> findByProfissionalIdAndEquipeId(UUID profissionalId, UUID equipeId);
    
    Page<VinculoProfissionalEquipe> findByProfissionalIdOrderByDataInicioDesc(UUID profissionalId, Pageable pageable);
    
    Page<VinculoProfissionalEquipe> findByEquipeIdOrderByDataInicioDesc(UUID equipeId, Pageable pageable);
    
    Page<VinculoProfissionalEquipe> findByTipoVinculoAndEquipeId(TipoVinculoProfissionalEnum tipoVinculo, UUID equipeId, Pageable pageable);
    
    Page<VinculoProfissionalEquipe> findByStatusAndEquipeId(StatusAtivoEnum status, UUID equipeId, Pageable pageable);
}
