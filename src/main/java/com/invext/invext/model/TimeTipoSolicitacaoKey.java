package com.invext.invext.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class TimeTipoSolicitacaoKey {

    @Column(name = "time_id")
    private Long timeId;

    @Column(name = "tipo_solicitacao_id")
    private Long tipoSolicitacaoId;

}
