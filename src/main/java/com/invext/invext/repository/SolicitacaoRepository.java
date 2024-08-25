package com.invext.invext.repository;

import com.invext.invext.model.AtendenteEntity;
import com.invext.invext.model.SolicitacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SolicitacaoRepository extends JpaRepository<SolicitacaoEntity, Long> {

    List<SolicitacaoEntity> findByStatusInOrderByDataAberturaAsc(Collection<SolicitacaoEntity.Status> status);
    long countByAtendenteAndStatus(AtendenteEntity atendente, SolicitacaoEntity.Status status);

}