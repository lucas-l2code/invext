package com.invext.invext.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitacaoDto {

    private Long id;
    private TipoSolicitacaoDto tipo;
    private AtendenteDto atendente;
    private String titulo;
    private String descricao;
    private String status;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataFechamento;

}
