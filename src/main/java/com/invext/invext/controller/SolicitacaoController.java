package com.invext.invext.controller;

import com.invext.invext.dto.AtendimentoDto;
import com.invext.invext.dto.NovaSolicitacaoDto;
import com.invext.invext.dto.SolicitacaoDto;
import com.invext.invext.service.DistribuicaoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.invext.invext.service.SolicitacaoService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/solicitacoes")
public class SolicitacaoController {

    private final DistribuicaoService distribuicaoService;
    private SolicitacaoService solicitacaoService;

    @PostMapping
    public SolicitacaoDto criarSolicitacao(@Valid @RequestBody NovaSolicitacaoDto novaSolicitacaoDto) {
        return solicitacaoService.criarSolicitacao(novaSolicitacaoDto);
    }

    @GetMapping()
    public List<SolicitacaoDto> filaSolicitacoes(@RequestParam(value = "status", required = false) String status) {
        return solicitacaoService.listarSolicitacoes(status);
    }

    @GetMapping("/atendimento")
    public AtendimentoDto statusAtendimento() {
        return solicitacaoService.listarStatusAtendimento();
    }

    @PatchMapping("/{id}/concluir")
    public SolicitacaoDto fecharSolicitacao(@PathVariable Long id) {
        return solicitacaoService.fecharSolicitacao(id);
    }

}

