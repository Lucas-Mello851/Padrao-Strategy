package br.com.padroes.strategy;

import br.com.padroes.strategy.estrategia.*;
import br.com.padroes.strategy.model.Pedido;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println(" ");
        System.out.println("   PADRÃO DE PROJETO: STRATEGY - CÁLCULO DE FRETE");
        System.out.println("\n");

        Pedido pedido1 = new Pedido("PED-001", 3.5, 120.00, "01310-100", "30140-071", false);

        Pedido pedido2 = new Pedido("PED-002", 1.2, 350.00, "01310-100", "40020-020", true);

        List<EstrategiaFrete> estrategias = List.of(
                new FreteCorreiosPac(),
                new FreteCorreiosSedex(),
                new FreteJadlog(),
                new FreteTotalExpress()
        );

        System.out.println(" Comparativo de fretes para: " + pedido1);
        System.out.println("-".repeat(60));
        System.out.printf("%-20s | %-10s | %s%n", "Transportadora", "Pedido", "Valor Frete");
        System.out.println("-".repeat(60));

        CalculadorFrete calculador = new CalculadorFrete(estrategias.get(0));
        for (EstrategiaFrete estrategia : estrategias) {
            calculador.setEstrategia(estrategia);
            calculador.exibirResumo(pedido1);
        }

        System.out.println("\n Comparativo de fretes para: " + pedido2);
        System.out.println("-".repeat(60));
        System.out.printf("%-20s | %-10s | %s%n", "Transportadora", "Pedido", "Valor Frete");
        System.out.println("-".repeat(60));

        for (EstrategiaFrete estrategia : estrategias) {
            calculador.setEstrategia(estrategia);
            calculador.exibirResumo(pedido2);
        }

        System.out.println("\n[STRATEGY] Estratégia trocada em tempo de execução sem alterar o contexto!");
    }
}
