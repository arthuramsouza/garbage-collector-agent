
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Ambiente {

    private int tamanhoMatriz;
    private int quantidadeLixeiras;
    private int quantidadeRecargas;
    private char matriz[][];

    List<Agente> agentes;

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
        this.tamanhoMatriz = tamanhoMatriz;
        this.quantidadeLixeiras = quantidadeLixeiras;
        this.quantidadeRecargas = quantidadeRecargas;
        this.matriz = new char[tamanhoMatriz][tamanhoMatriz];
        carregar();
    }

    public int getTamanhoMatriz() {
        return tamanhoMatriz;
    }

    public char elementoDaPosicaoXY(int x, int y) {
        return matriz[y][x];
    }

    public void setElementoDaPosicaoXY(int x, int y, char valor) {
        matriz[y][x] = valor;
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

        int numero = random.nextInt(this.tamanhoMatriz);

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
        for (int i = 0; i < tamanhoMatriz; i++) {
            for (int j = 0; j < tamanhoMatriz; j++) {
                // Verifica se algum agente esta nesta Posicao
                for (Agente ag : agentes) {
                    if (ag.getX() == j && ag.getY() == i) {
                        System.out.print("A ");
                    } else {
                        System.out.print(matriz[i][j] + " ");
                    }

                }
            }
            System.out.println();
        }
        System.out.println("\t");
    }

    /* Imprime o estado atual do ambiente com seus agentes */
    public void print2() {
        for (int i = 0; i < tamanhoMatriz; i++) {
            for (int j = 0; j < tamanhoMatriz; j++) {
                // Verifica se algum agente esta nesta Posicao
                for (Agente ag : agentes) {
                    if (ag.getX() == i && ag.getY() == j) {
                        System.out.print("A ");
                    } else {
                        System.out.print(matriz[i][j] + " ");
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
                    char atual, esq, dir, cima, baixo;
                    atual = matriz[ag.getY()][ag.getX()];

                    if ((ag.getX() - 1) < 0) {
                        esq = NULO;
                    } else {
                        esq = matriz[ag.getY()][ag.getX() - 1];
                    }

                    if ((ag.getX() + 1) >= tamanhoMatriz) {
                        dir = NULO;
                    } else {
                        dir = matriz[ag.getY()][ag.getX() + 1];
                    }

                    if ((ag.getY() - 1) < 0) {
                        cima = NULO;
                    } else {
                        cima = matriz[ag.getY() - 1][ag.getX()];
                    }

                    if ((ag.getY() + 1) >= tamanhoMatriz) {
                        baixo = NULO;
                    } else {
                        baixo = matriz[ag.getY() + 1][ag.getX()];
                    }

                    acabou = ag.atualizar2(atual, esq, dir, cima, baixo, this.tamanhoMatriz);
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
}
