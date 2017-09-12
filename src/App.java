
import java.util.ArrayList;

/**
 * ORIENTACAO DO AMBIENTE: MATRIZ[LIINHA][COLUNA]
 */
public class App {

    public static void main(String[] args) {
        int tamanhoDaMatriz = Integer.parseInt(args[0]);
        int quantidadeDeLixeiras = 3;
        int quantidadeDeRecargas = 3;

        Ambiente ambiente = new Ambiente(tamanhoDaMatriz, quantidadeDeLixeiras,
                quantidadeDeRecargas);

        int xInicial = 0;
        int yInicial = 0;
        char direcaoInicial = 'D';
        int estadoInicial = 0;
        int capacidadeDeColeta = 5;
        int capacidadeDeEnergia = 100;

        ambiente.imprimirCoordenadasDasLixeiras();
        ambiente.imprimirCoordenadasDasRecargas();
        Agente agente = new Agente(xInicial, yInicial, direcaoInicial,
                estadoInicial, ambiente, capacidadeDeColeta, capacidadeDeEnergia);

        ambiente.inserirAgente(agente);
        ambiente.simular();

        //ArrayList<Character> lista = new ArrayList<>();
        //lista = null;
        //lista.size();
        //agente.solicitarDadosDaLixeiraMaisProxima();
        //agente.solicitarDadosDaRecargaMaisProxima();
        //ambiente.print();
        //agente.log();
        //ArrayList<Character> caminhoAchadoPeloAStar = agente.aStarBuscandoLixeira(agente.posicaoDoAgente(), ambiente.getLixeiras().get(0));
        //if (caminhoAchadoPeloAStar != null) {
        //    System.out.println("tamanho do caminho= " + caminhoAchadoPeloAStar.size());
        //    for (int i = 0; i < caminhoAchadoPeloAStar.size(); i++) {
        //        System.out.println(caminhoAchadoPeloAStar.get(i) + " ");
        //    }
        //} else {
        //    System.out.println("CAMINHO NAO ENCONTRADO!");
        //}
        //testeComAFilaDePrioridades();
    }

    public static void testeComAFilaDePrioridades() {
        FilaDePrioridades f1 = new FilaDePrioridades();

        f1.printQueuePriorities();

        Nodo nodo1 = new Nodo();
        nodo1.setPriority(10);

        f1.addToQueue(nodo1);
        f1.printQueuePriorities();

        Nodo nodo2 = new Nodo();
        nodo2.setPriority(3);
        f1.addToQueue(nodo2);
        f1.printQueuePriorities();

        Nodo nodo3 = new Nodo();
        nodo3.setPriority(19);

        f1.addToQueue(nodo3);
        f1.printQueuePriorities();

        f1.removeFromQueue();
        f1.printQueuePriorities();

        f1.removeFromQueue();
        f1.printQueuePriorities();

        f1.removeFromQueue();
        f1.printQueuePriorities();

        f1.removeFromQueue();
        f1.printQueuePriorities();
    }
}
