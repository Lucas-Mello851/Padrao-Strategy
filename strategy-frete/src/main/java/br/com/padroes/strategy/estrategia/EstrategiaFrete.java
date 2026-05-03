package br.com.padroes.strategy.estrategia;

import br.com.padroes.strategy.model.Pedido;

public interface EstrategiaFrete {

    double calcularFrete(Pedido pedido);

    String getNomeTransportadora();
}
