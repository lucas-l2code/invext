package com.invext.invext.service;

import com.invext.invext.dto.*;
import com.invext.invext.exception.ResourceNotFoundException;
import com.invext.invext.model.AtendenteEntity;
import com.invext.invext.model.SolicitacaoEntity;
import com.invext.invext.model.TimeEntity;
import com.invext.invext.model.TipoSolicitacaoEntity;
import com.invext.invext.repository.SolicitacaoRepository;
import com.invext.invext.repository.TimeRepository;
import com.invext.invext.repository.TipoSolicitacaoRepository;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class SolicitacaoService {

    private final ModelMapper modelMapper;
    private final SolicitacaoRepository solicitacaoRepository;
    private final TipoSolicitacaoRepository tipoSolicitacaoRepository;
    private final TimeRepository timeRepository;
    private final ModelMapper mapper;

    public SolicitacaoDto criarSolicitacao(NovaSolicitacaoDto solicitacao) {
        TipoSolicitacaoEntity tipo = tipoSolicitacaoRepository.findById(solicitacao.getTipo())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo não encontrado."));

        SolicitacaoEntity solicitacaoEntity = solicitacaoRepository.save(
                SolicitacaoEntity.builder()
                .tipo(tipo)
                .titulo(solicitacao.getTitulo())
                .descricao(solicitacao.getDescricao())
                .status(SolicitacaoEntity.Status.ABERTO)
                .dataAbertura(LocalDateTime.now())
                .build());

        return mapper.map(solicitacaoEntity, SolicitacaoDto.class);
    }

    public List<SolicitacaoDto> listarSolicitacoes(String status) {
        List<SolicitacaoEntity> solicitacoes;
        if(StringUtils.isNotBlank(status)) {
            solicitacoes = solicitacaoRepository.findByStatusInOrderByDataAberturaAsc(Collections.singletonList(SolicitacaoEntity.Status.valueOf(status)));
        } else {
            solicitacoes = solicitacaoRepository.findAll();
        }

        return solicitacoes.stream()
                .map(solicitacao -> modelMapper.map(solicitacao, SolicitacaoDto.class))
                .toList();
    }

    public AtendimentoDto listarStatusAtendimento() {
        List<TimeDto> timesAtendimento = new ArrayList<>();

        List<TimeEntity> times = timeRepository.findAll();
        times.forEach(time -> {
            List<AtendenteDto> atendentesAtivos = new ArrayList<>();
            time.getAtendentes().stream().filter(AtendenteEntity::isAtivo).forEach(
                atendente -> atendentesAtivos.add(AtendenteDto.builder()
                        .id(atendente.getId())
                        .nome(atendente.getNome())
                        .solicitacoesEmAtendimento(solicitacaoRepository.countByAtendenteAndStatus(atendente, SolicitacaoEntity.Status.EM_ATENDIMENTO))
                        .build()));

            timesAtendimento.add(TimeDto.builder()
                    .id(time.getId())
                    .nome(time.getNome())
                    .atendentes(atendentesAtivos)
                    .build());
        });

        return AtendimentoDto.builder()
                .times(timesAtendimento)
                .build();
    }

    public SolicitacaoDto fecharSolicitacao(Long id) {
        SolicitacaoEntity solicitacao = solicitacaoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Solicitação não encontrada."));
        solicitacao.setStatus(SolicitacaoEntity.Status.FECHADO);
        solicitacao.setDataFechamento(LocalDateTime.now());
        solicitacaoRepository.save(solicitacao);
        return mapper.map(solicitacao, SolicitacaoDto.class);
    }
}