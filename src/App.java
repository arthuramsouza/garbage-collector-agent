
/**
 * ORIENTACAO DO AMBIENTE: MATRIZ[LIINHA][COLUNA]
 */
public class App {

    public static void main(String[] args) {
        //Parâmetros do main: <n_matriz> <t_repositorio> <c_energia> <quantidade_lixeiras> <quantidade_recargas>
        int tamanhoDaMatriz = Integer.parseInt(args[0]);
        int capacidadeDeColeta = Integer.parseInt(args[1]);
        int capacidadeDeEnergia = Integer.parseInt(args[2]);
        int quantidadeDeLixeiras = Integer.parseInt(args[3]);
        int quantidadeDeRecargas = Integer.parseInt(args[4]);
        int linhaInicial = 0;
        int colunaInicial = 0;
        char direcaoInicial = 'D';

        Ambiente ambiente = new Ambiente(tamanhoDaMatriz, quantidadeDeLixeiras,
                quantidadeDeRecargas);

        Agente agente = new Agente(linhaInicial, colunaInicial, direcaoInicial,
                ambiente, capacidadeDeColeta, capacidadeDeEnergia);

        ambiente.inserirAgente(agente);
        ambiente.simular();
    }
}
