package com.invext.invext.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NovaSolicitacaoDto {

    @NotNull(message = "Tipo da solicitação é obrigatório.")
    private Long tipo;

    @NotBlank(message = "Título é obrigatório.")
    private String titulo;

    private String descricao;

}
