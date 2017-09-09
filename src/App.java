
public class App {

    public static void main(String[] args) {
        int tamanhoDaMatriz = Integer.parseInt(args[0]);
        int quantidadeDeLixeiras = 3;
        int quantidadeDeRecargas = 3;

        Ambiente ambiente = new Ambiente(tamanhoDaMatriz, quantidadeDeLixeiras,
                quantidadeDeRecargas);

        int xInicial = 0;
        int yInicial = 0;
        char direcaoInicial = 'd';
        int estadoInicial = 0;
        int capacidadeDeColeta = 15;
        int capacidadeDeEnergia = 100;

        ambiente.imprimirCoordenadasDasLixeiras();
        ambiente.imprimirCoordenadasDasRecargas();

        Agente agente = new Agente(xInicial, yInicial, direcaoInicial,
                estadoInicial, ambiente, capacidadeDeColeta, capacidadeDeEnergia);

        ambiente.inserirAgente(agente);
        ambiente.simular2();
    }
}
