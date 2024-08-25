package com.invext.invext.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeDto {
    private Long id;
    private String nome;
    private List<AtendenteDto> atendentes;
}