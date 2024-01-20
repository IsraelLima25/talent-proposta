package br.com.zupacademy.israel.proposta.apiExterna.dadosFinanceiro;

import br.com.zupacademy.israel.proposta.criarNovaProposta.StatusProposta;

public enum StatusRetornoAnaliseFinanceira {
    COM_RESTRICAO, SEM_RESTRICAO;

    public StatusProposta normaliza(){
        if(this.equals(COM_RESTRICAO)) {
            return StatusProposta.NAO_ELEGIVEL;
        }
        return StatusProposta.ELEGIVEL;
    }

}
