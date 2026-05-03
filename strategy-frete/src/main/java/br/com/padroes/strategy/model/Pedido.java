package br.com.padroes.strategy.model;

public class Pedido {

    private String numeroPedido;
    private double pesoKg;
    private double valorProdutos;
    private String cepOrigem;
    private String cepDestino;
    private boolean entregaExpressa;

    public Pedido(String numeroPedido, double pesoKg, double valorProdutos,
                  String cepOrigem, String cepDestino, boolean entregaExpressa) {
        if (pesoKg <= 0) {
            throw new IllegalArgumentException("Peso deve ser maior que zero.");
        }
        if (valorProdutos < 0) {
            throw new IllegalArgumentException("Valor dos produtos não pode ser negativo.");
        }
        this.numeroPedido = numeroPedido;
        this.pesoKg = pesoKg;
        this.valorProdutos = valorProdutos;
        this.cepOrigem = cepOrigem;
        this.cepDestino = cepDestino;
        this.entregaExpressa = entregaExpressa;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public double getValorProdutos() {
        return valorProdutos;
    }

    public String getCepOrigem() {
        return cepOrigem;
    }

    public String getCepDestino() {
        return cepDestino;
    }

    public boolean isEntregaExpressa() {
        return entregaExpressa;
    }

    @Override
    public String toString() {
        return String.format("Pedido{numero='%s', peso=%.2f kg, valor=R$ %.2f, expresso=%s}",
                numeroPedido, pesoKg, valorProdutos, entregaExpressa ? "Sim" : "Não");
    }
}
