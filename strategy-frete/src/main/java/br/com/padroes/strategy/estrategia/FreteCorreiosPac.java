package br.com.padroes.strategy.estrategia;

import br.com.padroes.strategy.model.Pedido;

public class FreteCorreiosPac implements EstrategiaFrete {

    private static final double TAXA_BASE = 15.00;
    private static final double TAXA_POR_KG = 4.50;
    private static final double VALOR_FRETE_GRATIS = 300.00;

    @Override
    public double calcularFrete(Pedido pedido) {
        if (pedido.getValorProdutos() >= VALOR_FRETE_GRATIS) {
            return 0.0;
        }
        return TAXA_BASE + (pedido.getPesoKg() * TAXA_POR_KG);
    }

    @Override
    public String getNomeTransportadora() {
        return "Correios PAC";
    }
}
