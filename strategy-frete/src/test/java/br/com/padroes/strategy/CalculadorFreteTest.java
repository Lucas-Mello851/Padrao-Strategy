package br.com.padroes.strategy;

import br.com.padroes.strategy.estrategia.*;
import br.com.padroes.strategy.model.Pedido;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Padrão Strategy - Cálculo de Frete")
class CalculadorFreteTest {

    private Pedido pedidoSimples;
    private Pedido pedidoExpresso;
    private Pedido pedidoAltoValor;

    @BeforeEach
    void setUp() {
        pedidoSimples  = new Pedido("PED-001", 3.5, 120.00, "01310-100", "30140-071", false);
        pedidoExpresso = new Pedido("PED-002", 1.2,  80.00, "01310-100", "40020-020", true);
        pedidoAltoValor = new Pedido("PED-003", 2.0, 350.00, "01310-100", "20040-020", false);
    }

    @Nested
    @DisplayName("Correios PAC")
    class CorreiosPacTest {

        private final EstrategiaFrete pac = new FreteCorreiosPac();

        @Test
        @DisplayName("Deve calcular frete baseado em taxa base + peso")
        void deveCalcularFreteBasico() {
            double esperado = 15.00 + (3.5 * 4.50);
            assertEquals(esperado, pac.calcularFrete(pedidoSimples), 0.001);
        }

        @Test
        @DisplayName("Deve retornar frete grátis para pedidos acima de R$ 300")
        void deveRetornarFreteGratisAcima300() {
            assertEquals(0.0, pac.calcularFrete(pedidoAltoValor), 0.001);
        }

        @Test
        @DisplayName("Deve ignorar flag de entrega expressa")
        void deveIgnorarEntregaExpressa() {
            double semExpresso = pac.calcularFrete(pedidoSimples);
            Pedido pedidoExpressoPac = new Pedido("X", 3.5, 120.00, "0", "0", true);
            double comExpresso = pac.calcularFrete(pedidoExpressoPac);
            assertEquals(semExpresso, comExpresso, 0.001);
        }

        @Test
        @DisplayName("Deve retornar nome correto da transportadora")
        void deveRetornarNomeCorreto() {
            assertEquals("Correios PAC", pac.getNomeTransportadora());
        }
    }

    @Nested
    @DisplayName("Correios SEDEX")
    class CorreiosSedexTest {

        private final EstrategiaFrete sedex = new FreteCorreiosSedex();

        @Test
        @DisplayName("Deve calcular frete padrão sem expressa")
        void deveCalcularFretePadrao() {
            double esperado = 25.00 + (3.5 * 7.00);
            assertEquals(esperado, sedex.calcularFrete(pedidoSimples), 0.001);
        }

        @Test
        @DisplayName("Deve aplicar acréscimo de 30% para entrega expressa")
        void deveAplicarAdicionalExpresso() {
            double base = 25.00 + (1.2 * 7.00);
            double esperado = base * 1.30;
            assertEquals(esperado, sedex.calcularFrete(pedidoExpresso), 0.001);
        }

        @Test
        @DisplayName("Não deve aplicar frete grátis mesmo para pedidos de alto valor")
        void naoDeveAplicarFreteGratis() {
            double frete = sedex.calcularFrete(pedidoAltoValor);
            assertTrue(frete > 0, "SEDEX nunca aplica frete grátis");
        }

        @Test
        @DisplayName("Deve retornar nome correto da transportadora")
        void deveRetornarNomeCorreto() {
            assertEquals("Correios SEDEX", sedex.getNomeTransportadora());
        }
    }

    @Nested
    @DisplayName("Jadlog")
    class JadlogTest {

        private final EstrategiaFrete jadlog = new FreteJadlog();

        @Test
        @DisplayName("Deve calcular frete sem desconto para pedidos até R$ 150")
        void deveCalcularFreteSemDesconto() {
            double esperado = 18.00 + (3.5 * 5.80);
            assertEquals(esperado, jadlog.calcularFrete(pedidoSimples), 0.001);
        }

        @Test
        @DisplayName("Deve aplicar desconto de 10% para pedidos acima de R$ 150")
        void deveAplicarDescontoParaAltoValor() {
            double base = 18.00 + (2.0 * 5.80);
            double esperado = base * 0.90;
            assertEquals(esperado, jadlog.calcularFrete(pedidoAltoValor), 0.001);
        }

        @Test
        @DisplayName("Deve adicionar R$ 20 para entrega expressa")
        void deveAdicionarTaxaExpressa() {
            double base = 18.00 + (1.2 * 5.80);
            double esperado = base + 20.00;
            assertEquals(esperado, jadlog.calcularFrete(pedidoExpresso), 0.001);
        }

        @Test
        @DisplayName("Deve retornar nome correto da transportadora")
        void deveRetornarNomeCorreto() {
            assertEquals("Jadlog", jadlog.getNomeTransportadora());
        }
    }

    @Nested
    @DisplayName("Total Express")
    class TotalExpressTest {

        private final EstrategiaFrete totalExpress = new FreteTotalExpress();

        @Test
        @DisplayName("Deve incluir ad valorem no cálculo")
        void deveIncluirAdValorem() {
            double esperado = 12.00 + (3.5 * 6.20) + (120.00 * 0.02);
            assertEquals(esperado, totalExpress.calcularFrete(pedidoSimples), 0.001);
        }

        @Test
        @DisplayName("Deve aplicar acréscimo de 50% para entrega expressa")
        void deveAplicarAdicionalExpresso() {
            double base = 12.00 + (1.2 * 6.20) + (80.00 * 0.02);
            double esperado = base * 1.50;
            assertEquals(esperado, totalExpress.calcularFrete(pedidoExpresso), 0.001);
        }

        @Test
        @DisplayName("Deve retornar nome correto da transportadora")
        void deveRetornarNomeCorreto() {
            assertEquals("Total Express", totalExpress.getNomeTransportadora());
        }
    }

    @Nested
    @DisplayName("CalculadorFrete (Contexto do Strategy)")
    class CalculadorFreteContextoTest {

        @Test
        @DisplayName("Deve usar a estratégia configurada para calcular")
        void deveUsarEstrategiaConfigurada() {
            EstrategiaFrete pac = new FreteCorreiosPac();
            CalculadorFrete calculador = new CalculadorFrete(pac);
            double esperado = pac.calcularFrete(pedidoSimples);
            assertEquals(esperado, calculador.calcular(pedidoSimples), 0.001);
        }

        @Test
        @DisplayName("Deve trocar estratégia em tempo de execução")
        void deveTrocarEstrategiaEmRuntime() {
            CalculadorFrete calculador = new CalculadorFrete(new FreteCorreiosPac());
            double fretePac = calculador.calcular(pedidoSimples);

            calculador.setEstrategia(new FreteCorreiosSedex());
            double freteSedex = calculador.calcular(pedidoSimples);

            assertNotEquals(fretePac, freteSedex,
                    "Fretes de PAC e SEDEX devem ser diferentes");
        }

        @Test
        @DisplayName("Deve retornar nome da transportadora atual")
        void deveRetornarNomeTransportadoraAtual() {
            CalculadorFrete calculador = new CalculadorFrete(new FreteJadlog());
            assertEquals("Jadlog", calculador.getTransportadora());
        }
    }

    @Nested
    @DisplayName("Validações do Pedido")
    class PedidoValidacaoTest {

        @Test
        @DisplayName("Deve lançar exceção para peso zero ou negativo")
        void deveLancarExcecaoParaPesoInvalido() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Pedido("X", 0, 100.00, "0", "0", false));

            assertThrows(IllegalArgumentException.class,
                    () -> new Pedido("X", -1, 100.00, "0", "0", false));
        }

        @Test
        @DisplayName("Deve lançar exceção para valor de produto negativo")
        void deveLancarExcecaoParaValorNegativo() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Pedido("X", 1.0, -10.00, "0", "0", false));
        }

        @Test
        @DisplayName("Deve aceitar pedido com valor zero")
        void deveAceitarPedidoComValorZero() {
            assertDoesNotThrow(() -> new Pedido("X", 1.0, 0.0, "0", "0", false));
        }
    }

    @ParameterizedTest(name = "Peso {0}kg, Valor R${1} -> PAC = R${2}")
    @CsvSource({
            "1.0, 100.0, 19.50",
            "2.0, 200.0, 24.00",
            "5.0, 50.0,  37.50",
            "0.5, 300.0, 0.00"
    })
    @DisplayName("PAC: deve calcular corretamente para diferentes pesos e valores")
    void pacCalculaCorretamente(double peso, double valor, double esperado) {
        Pedido p = new Pedido("X", peso, valor, "0", "0", false);
        EstrategiaFrete pac = new FreteCorreiosPac();
        assertEquals(esperado, pac.calcularFrete(p), 0.001);
    }
}
