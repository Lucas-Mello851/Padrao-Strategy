package br.com.padroes.strategy;

import br.com.padroes.strategy.estrategia.EstrategiaFrete;
import br.com.padroes.strategy.model.Pedido;

public class CalculadorFrete {

    private EstrategiaFrete estrategia;

    public CalculadorFrete(EstrategiaFrete estrategia) {
        this.estrategia = estrategia;
    }

    public void setEstrategia(EstrategiaFrete estrategia) {
        this.estrategia = estrategia;
    }

    public double calcular(Pedido pedido) {
        return estrategia.calcularFrete(pedido);
    }

    public String getTransportadora() {
        return estrategia.getNomeTransportadora();
    }

    public void exibirResumo(Pedido pedido) {
        double valor = calcular(pedido);
        System.out.printf("%-20s | %s | Frete: R$ %.2f%n",
                estrategia.getNomeTransportadora(),
                pedido.getNumeroPedido(),
                valor);
    }
}
