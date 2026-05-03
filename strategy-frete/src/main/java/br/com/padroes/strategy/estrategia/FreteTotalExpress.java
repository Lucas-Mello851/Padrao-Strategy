package br.com.padroes.strategy.estrategia;

import br.com.padroes.strategy.model.Pedido;

public class FreteTotalExpress implements EstrategiaFrete {

    private static final double TAXA_BASE = 12.00;
    private static final double TAXA_POR_KG = 6.20;
    private static final double PERCENTUAL_AD_VALOREM = 0.02;
    private static final double ADICIONAL_EXPRESSO = 1.50;

    @Override
    public double calcularFrete(Pedido pedido) {
        double frete = TAXA_BASE
                + (pedido.getPesoKg() * TAXA_POR_KG)
                + (pedido.getValorProdutos() * PERCENTUAL_AD_VALOREM);

        if (pedido.isEntregaExpressa()) {
            frete *= ADICIONAL_EXPRESSO;
        }

        return frete;
    }

    @Override
    public String getNomeTransportadora() {
        return "Total Express";
    }
}
