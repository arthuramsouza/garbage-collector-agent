
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * ORIENTACAO DO AMBIENTE: MATRIZ[LIINHA][COLUNA]
 */
public class Ambiente {

    private int tamanhoMatriz;
    private int quantidadeLixeiras;
    private int quantidadeRecargas;
    private char matriz[][];

    List<Agente> agentes;
    List<Posicao> lixeiras;
    List<Posicao> recargas;

    public static final char NULO = 'N';
    public static final char SUJEIRA = 'S';
    public static final char LIXEIRA = 'L';
    public static final char RECARGA = 'R';
    public static final char PAREDE = 'P';
    public static final char LIMPO = '.';

    private int gerador;
    Random random = new Random();

    /* Construtor da classe */
    public Ambiente(int tamanhoMatriz, int quantidadeLixeiras, int quantidadeRecargas) {
        agentes = new ArrayList<Agente>();
        lixeiras = new ArrayList<Posicao>();
        recargas = new ArrayList<Posicao>();
        this.tamanhoMatriz = tamanhoMatriz;
        this.matriz = new char[tamanhoMatriz][tamanhoMatriz];
        if (quantidadeLixeiras < 0) {
            this.quantidadeLixeiras = 5;
        } else {
            this.quantidadeLixeiras = quantidadeLixeiras;
        }
        if (quantidadeRecargas < 0) {
            this.quantidadeRecargas = 5;
        } else {
            this.quantidadeRecargas = quantidadeRecargas;
        }
        carregar();
    }

    public void lixeiraMaisProximaDoAgente(Agente agente) {
        Posicao posicaoDoAgente = new Posicao(agente.getPosicaoLinha(), agente.getPosicaoColuna());
        Posicao posicaoAtual = new Posicao(lixeiras.get(0).getLinha(), lixeiras.get(0).getColuna());
        Posicao posicaoMaisProxima = new Posicao(lixeiras.get(0).getLinha(), lixeiras.get(0).getColuna());
        int menorDistancia = distanciaManhattan(posicaoDoAgente, posicaoAtual);
        int distanciaAtual;

        for (int i = 1; i < lixeiras.size(); i++) {
            posicaoAtual = new Posicao(lixeiras.get(i).getLinha(), lixeiras.get(i).getColuna());

            distanciaAtual = distanciaManhattan(posicaoDoAgente, posicaoAtual);

            if (distanciaAtual < menorDistancia) {
                menorDistancia = distanciaAtual;
                posicaoMaisProxima = new Posicao(lixeiras.get(i).getLinha(), lixeiras.get(i).getColuna());
            }
        }

        agente.atualizarDadosDaLixeiraMaisProxima(posicaoMaisProxima.getLinha(), posicaoMaisProxima.getColuna(), menorDistancia);
    }

    public void recargaMaisProximaDoAgente(Agente agente) {
        Posicao posicaoDoAgente = new Posicao(agente.getPosicaoLinha(), agente.getPosicaoColuna());
        Posicao posicaoAtual = new Posicao(recargas.get(0).getLinha(), recargas.get(0).getColuna());
        Posicao posicaoMaisProxima = new Posicao(recargas.get(0).getLinha(), recargas.get(0).getColuna());
        int menorDistancia = distanciaManhattan(posicaoDoAgente, posicaoAtual);
        int distanciaAtual;

        for (int i = 1; i < recargas.size(); i++) {
            posicaoAtual = new Posicao(recargas.get(i).getLinha(), recargas.get(i).getColuna());

            distanciaAtual = distanciaManhattan(posicaoDoAgente, posicaoAtual);

            if (distanciaAtual < menorDistancia) {
                menorDistancia = distanciaAtual;
                posicaoMaisProxima = new Posicao(recargas.get(i).getLinha(), recargas.get(i).getColuna());
            }
        }

        agente.atualizarDadosDaRecargaMaisProxima(posicaoMaisProxima.getLinha(), posicaoMaisProxima.getColuna(), menorDistancia);
    }

    public int distanciaManhattan(Posicao posicao1, Posicao posicao2) {
        int coordenadaX = posicao1.getLinha() - posicao2.getLinha();
        int coordenadaY = posicao1.getColuna() - posicao2.getColuna();

        coordenadaX = Math.abs(coordenadaX);
        coordenadaY = Math.abs(coordenadaY);

        return coordenadaX + coordenadaY;
    }

    public List<Posicao> getLixeiras() {
        return lixeiras;
    }

    public void setLixeiras(List<Posicao> lixeiras) {
        this.lixeiras = lixeiras;
    }

    public List<Posicao> getRecargas() {
        return recargas;
    }

    public void setRecargas(List<Posicao> recargas) {
        this.recargas = recargas;
    }

    public int getTamanhoMatriz() {
        return tamanhoMatriz;
    }

    public char elementoDaPosicaoLinhaColuna(int linha, int coluna) {
        return matriz[linha][coluna];
    }

    public void setElementoDaPosicaoLinhaColuna(int linha, int coluna, char valor) {
        matriz[linha][coluna] = valor;
    }

    /* Coloca elementos no ambiente - estatico */
    private void carregar() {
        for (int i = 0; i < tamanhoMatriz; i++) {
            for (int j = 0; j < tamanhoMatriz; j++) {
                matriz[i][j] = LIMPO;
            }
        }

        inserirParedes();
        inserirLixeiras();
        inserirRecargas();
        inserirSujeiras();
    }

    /* Insere paredes no ambiente */
    public void inserirParedes() {
        // auxiliares para adicionar as bordas na parede
        boolean bordaCimaPosicaoDir = false;
        boolean bordaCimaPosicaoEsq = false;
        boolean bordaBaixoPosicaoDir = false;
        boolean bordaBaixoPosicaoEsq = false;

        int divisao = this.tamanhoMatriz / 3;
        int inicio = (divisao / 2);
        int fim = this.tamanhoMatriz - (divisao / 2);

        // auxiliares para adicionar as paredes
        int posicaoEsq = divisao - 1;
        int posicaoDir = this.tamanhoMatriz - divisao;

        for (int i = inicio; i < fim; i++) {
            this.matriz[i][posicaoEsq] = PAREDE;
            this.matriz[i][posicaoDir] = PAREDE;

            if (!bordaCimaPosicaoDir) {
                this.matriz[i][posicaoDir + 1] = PAREDE;
                bordaCimaPosicaoDir = true;
            }
            if (!bordaCimaPosicaoEsq) {
                this.matriz[i][posicaoEsq - 1] = PAREDE;
                bordaCimaPosicaoEsq = true;
            }
            if (!bordaBaixoPosicaoDir) {
                if (i == fim - 1) {
                    this.matriz[i][posicaoDir + 1] = PAREDE;
                    bordaBaixoPosicaoDir = true;
                }
            }
            if (!bordaBaixoPosicaoEsq) {
                if (i == fim - 1) {
                    this.matriz[i][posicaoEsq - 1] = PAREDE;
                    bordaBaixoPosicaoEsq = true;
                }
            }
        }
    }

    /* Adiciona Lixeiras ao ambiente */
    public void inserirLixeiras() {
        int divisao = this.tamanhoMatriz / 3;
        int inicio = (divisao / 2);
        int fim = this.tamanhoMatriz - (divisao / 2);

        int posicaoEsq = divisao - 1;
        int posicaoDir = this.tamanhoMatriz - divisao;

        while (this.quantidadeLixeiras > 0) {
            int posicaoLinha = random.nextInt(this.tamanhoMatriz);
            int posicaoColuna = random.nextInt(this.tamanhoMatriz);

            if (posicaoLinha > inicio && posicaoLinha < fim - 1) {
                if (posicaoColuna < posicaoEsq || posicaoColuna > posicaoDir) {
                    if (posicaoLivre(posicaoLinha, posicaoColuna) && posicaoValida(posicaoLinha, posicaoColuna)) {
                        this.matriz[posicaoLinha][posicaoColuna] = LIXEIRA;
                        lixeiras.add(new Posicao(posicaoLinha, posicaoColuna));
                        this.quantidadeLixeiras--;
                        continue;
                    }
                }
            }
        }
    }

    /* Adiciona recargas ao ambiente */
    public void inserirRecargas() {
        int divisao = this.tamanhoMatriz / 3;
        int inicio = (divisao / 2);
        int fim = this.tamanhoMatriz - (divisao / 2);

        int posicaoEsq = divisao - 1;
        int posicaoDir = this.tamanhoMatriz - divisao;

        while (this.quantidadeRecargas > 0) {
            int posicaoLinha = random.nextInt(this.tamanhoMatriz);
            int posicaoColuna = random.nextInt(this.tamanhoMatriz);

            if (posicaoLinha > inicio && posicaoLinha < fim - 1) {
                if (posicaoColuna < posicaoEsq || posicaoColuna > posicaoDir) {
                    if (posicaoLivre(posicaoLinha, posicaoColuna) && posicaoValida(posicaoLinha, posicaoColuna)) {
                        this.matriz[posicaoLinha][posicaoColuna] = RECARGA;
                        recargas.add(new Posicao(posicaoLinha, posicaoColuna));
                        this.quantidadeRecargas--;
                        continue;
                    }
                }
            }
        }
    }

    /* Adiciona sujeiras ao ambiente */
    public void inserirSujeiras() {
        int quantidade = 0;
        double percentual1 = this.tamanhoMatriz + ((40 / 100) * this.tamanhoMatriz);
        double percentual2 = this.tamanhoMatriz + ((80 / 100) * this.tamanhoMatriz);

        int numero = (random.nextInt(this.tamanhoMatriz)) + 10;

        while (numero < percentual1 && numero > percentual2) {
            numero = random.nextInt(this.tamanhoMatriz);
        }

        quantidade = numero;

        while (quantidade > 0) {
            int posicaoLinha = random.nextInt(this.tamanhoMatriz);
            int posicaoColuna = random.nextInt(this.tamanhoMatriz);

            if (posicaoLivre(posicaoLinha, posicaoColuna)) {
                this.matriz[posicaoLinha][posicaoColuna] = SUJEIRA;
                quantidade--;
                continue;
            }
        }
    }

    /* Verifica posições livres */
    public boolean posicaoLivre(int i, int j) {
        return this.matriz[i][j] != PAREDE && this.matriz[i][j] != SUJEIRA && this.matriz[i][j] != RECARGA && this.matriz[i][j] != LIXEIRA;
    }

    /* Verifica se a posição é valida, ou seja, paredes ou bordas ao lado para adicionar lixeiras e pontos de recargas */
    public boolean posicaoValida(int posicaoLinha, int posicaoColuna) {
        if ((posicaoColuna + 1) > this.tamanhoMatriz - 1 || (posicaoColuna - 1) < 0) {
            return true;
        }
        if (this.matriz[posicaoLinha][posicaoColuna + 1] == PAREDE || this.matriz[posicaoLinha][posicaoColuna - 1] == PAREDE) {
            return true;
        } else {
            return false;
        }
    }

    /* Insere agente no ambiente */
    public void inserirAgente(Agente agente) {
        agentes.add(agente);
    }

    /* Imprime o estado atual do ambiente com seus agentes */
    public void print() {
        for (int linha = 0; linha < tamanhoMatriz; linha++) {
            for (int coluna = 0; coluna < tamanhoMatriz; coluna++) {
                // Verifica se algum agente esta nesta Posicao
                for (Agente ag : agentes) {
                    if (ag.getPosicaoLinha() == linha && ag.getPosicaoColuna() == coluna) {
                        System.out.print("A ");
                    } else {
                        System.out.print(matriz[linha][coluna] + " ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println("\t");
    }

    /* Executa a simulacao dos agentes no ambiente */
    public void simular() {
        boolean acabou = false;

        while (true) {
            print();
            try {
                Thread.sleep(300);
                System.out.flush();

                for (Agente ag : agentes) {

                    acabou = ag.atualizar2();
                    if (acabou == true) {
                        break;
                    }
                }

                if (acabou == true) {
                    System.out.println("SIMULACAO ENCERRADA!!!");
                    break;
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    void imprimirCoordenadasDasLixeiras() {
        System.out.println("coordenadas das lixeiras");

        for (int i = 0; i < lixeiras.size(); i++) {
            System.out.println("[ " + lixeiras.get(i).getLinha() + " , " + lixeiras.get(i).getColuna() + "]");
        }

        System.out.println();
    }

    void imprimirCoordenadasDasRecargas() {
        System.out.println("coordenadas das recargas");

        for (int i = 0; i < recargas.size(); i++) {
            System.out.println("[ " + recargas.get(i).getLinha() + " , " + recargas.get(i).getColuna() + "]");
        }

        System.out.println();
    }
}
