package br.com.padroes.strategy.estrategia;

import br.com.padroes.strategy.model.Pedido;

public class FreteCorreiosSedex implements EstrategiaFrete {

    private static final double TAXA_BASE = 25.00;
    private static final double TAXA_POR_KG = 7.00;
    private static final double ADICIONAL_EXPRESSO = 1.30;

    @Override
    public double calcularFrete(Pedido pedido) {
        double frete = TAXA_BASE + (pedido.getPesoKg() * TAXA_POR_KG);
        if (pedido.isEntregaExpressa()) {
            frete *= ADICIONAL_EXPRESSO;
        }
        return frete;
    }

    @Override
    public String getNomeTransportadora() {
        return "Correios SEDEX";
    }
}
