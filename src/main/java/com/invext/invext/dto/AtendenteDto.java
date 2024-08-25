package com.invext.invext.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AtendenteDto {

    private long id;
    private String nome;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long solicitacoesEmAtendimento;

}
