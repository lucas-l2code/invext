package com.invext.invext.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "time_tipo_solicitacao")
public class TimeTipoSolicitacaoEntity {

    @EmbeddedId
    private TimeTipoSolicitacaoKey id;

    @ManyToOne
    @JoinColumn(name = "time_id", insertable = false, updatable = false)
    private TimeEntity time;

    @ManyToOne
    @JoinColumn(name = "tipo_solicitacao_id", insertable = false, updatable = false)
    private TipoSolicitacaoEntity tipoSolicitacao;

}