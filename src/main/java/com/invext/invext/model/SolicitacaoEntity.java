package com.invext.invext.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "solicitacao")
public class SolicitacaoEntity {

    public enum Status {
        ABERTO, EM_ATENDIMENTO, FECHADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tipo_solicitacao_id")
    private TipoSolicitacaoEntity tipo;

    @ManyToOne
    @JoinColumn(name = "atendente_id")
    private AtendenteEntity atendente;

    private String titulo;

    private String descricao;

    private Status status;

    private LocalDateTime dataAbertura;

    private LocalDateTime dataFechamento;

}
