package br.com.padroes.strategy.estrategia;

import br.com.padroes.strategy.model.Pedido;

public class FreteJadlog implements EstrategiaFrete {

    private static final double TAXA_BASE = 18.00;
    private static final double TAXA_POR_KG = 5.80;
    private static final double VALOR_DESCONTO = 150.00;
    private static final double PERCENTUAL_DESCONTO = 0.90;
    private static final double TAXA_EXPRESSA = 20.00;

    @Override
    public double calcularFrete(Pedido pedido) {
        double frete = TAXA_BASE + (pedido.getPesoKg() * TAXA_POR_KG);

        if (pedido.getValorProdutos() > VALOR_DESCONTO) {
            frete *= PERCENTUAL_DESCONTO;
        }

        if (pedido.isEntregaExpressa()) {
            frete += TAXA_EXPRESSA;
        }

        return frete;
    }

    @Override
    public String getNomeTransportadora() {
        return "Jadlog";
    }
}
