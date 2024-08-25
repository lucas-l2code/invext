package com.invext.invext.service;

import com.invext.invext.dto.TimeDto;
import com.invext.invext.model.SolicitacaoEntity;
import com.invext.invext.model.TimeEntity;
import com.invext.invext.model.TimeTipoSolicitacaoEntity;
import com.invext.invext.model.TipoSolicitacaoEntity;
import com.invext.invext.repository.*;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class DistribuicaoService {

    private final TimeRepository timeRepository;
    private final TimeTipoSolicitacaoRepository timeTipoSolicitacaoRepository;
    private final TipoSolicitacaoRepository tipoSolicitacaoRepository;
    private final SolicitacaoService solicitacaoService;
    private final SolicitacaoRepository solicitacaoRepository;
    private final AtendenteRepository atendenteRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void distribuirSolicitacoes() {
        Map<Long, Long> mapaTipos = montarMapaTipos();
        List<TimeDto> times = solicitacaoService.listarStatusAtendimento().getTimes();
        List<SolicitacaoEntity> solicitacoesAbertas = solicitacaoRepository.findByStatusInOrderByDataAberturaAsc(Collections.singletonList(SolicitacaoEntity.Status.ABERTO));
        Set<Long> timesIndisponiveis = new HashSet<>();

        times.forEach(time -> {
            if(time.getAtendentes().stream().noneMatch(atendente -> atendente.getSolicitacoesEmAtendimento() < 3)) {
                timesIndisponiveis.add(time.getId());
            }
        });

        solicitacoesAbertas.forEach(solicitacao -> {
            Long timeId = mapaTipos.get(solicitacao.getTipo().getId());

            if(timesIndisponiveis.contains(timeId)) {
                return;
            }

            TimeDto time = times.stream().filter(t -> t.getId().equals(timeId)).findFirst()
                    .orElseThrow(() -> new RuntimeException(String.format("Time não mapeado para o tipo [%d]", solicitacao.getTipo().getId())));

            time.getAtendentes().stream().filter(atendente -> atendente.getSolicitacoesEmAtendimento() < 3).findFirst().ifPresentOrElse(
                    atendente -> {
                        solicitacao.setAtendente(atendenteRepository.findById(atendente.getId())
                                .orElseThrow(() -> new RuntimeException("Erro ao carregar atendente para vincular a solicitação.")));
                        solicitacao.setStatus(SolicitacaoEntity.Status.EM_ATENDIMENTO);
                        solicitacaoRepository.save(solicitacao);
                        atendente.setSolicitacoesEmAtendimento(atendente.getSolicitacoesEmAtendimento() + 1);
                    },
                    () -> timesIndisponiveis.add(timeId)
            );

        });
    }

    private Map<Long, Long> montarMapaTipos() {
        TimeEntity timePadrao = timeRepository.findByTimePadraoIsTrue()
                .orElseThrow(() -> new RuntimeException("Configuração inválida. Nenhum time padrão encontrado."));

        // Monta mapa que cruza Tipo x Time, preenchendo os tipos sem vinculo na tabela com o time definido como padrão
        List<TimeTipoSolicitacaoEntity> timePorTipo = timeTipoSolicitacaoRepository.findAll();
        List<TipoSolicitacaoEntity> tipos = tipoSolicitacaoRepository.findAll();
        Map<Long, Long> configDistribuicao = new HashMap<>();
        tipos.forEach(
                tipo -> timePorTipo.stream().filter(timeTipoSolicitacao -> timeTipoSolicitacao.getTipoSolicitacao().getId().equals(tipo.getId()))
                .findFirst().ifPresentOrElse(
                    timeTipoSolicitacao -> configDistribuicao.put(tipo.getId(), timeTipoSolicitacao.getTime().getId()),
                    () -> configDistribuicao.put(tipo.getId(), timePadrao.getId())));

        return configDistribuicao;
    }

}
