import java.util.List;
import java.util.ArrayList;

public class Ambiente {
  private int n;
  private char matriz[][];

  List<Agente> agentes;

  private final String ANSI_CLS = "\u001b[2J";
  private final String ANSI_HOME = "\u001b[H";
  public static final char NULO = 'N';
  public static final char SUJEIRA = 'S';
  public static final char LIXEIRA = 'L';
  public static final char RECARGA = 'R';
  public static final char PAREDE = 'P';
  public static final char LIMPO = '.';

  /* TODO Coloca elementos (lixeiras, lixo e carregadores) de forma randomica no ambiente  */
  private void carregar(int n_lixeiras, int n_carregadores, int n_lixo) {
    for(int i = 0; i < n; i++)
      for(int j = 0; j < n; j++)
        matriz[i][j] = LIMPO;
  }

  /* Coloca elementos no ambiente - estatico */
  private void carregar() {
    for(int i = 0; i < n; i++)
      for(int j = 0; j < n; j++)
        matriz[i][j] = LIMPO;

    matriz[4][0] = RECARGA;
    matriz[4][9] = RECARGA;
  }


  /* Construtor da classe */
  public Ambiente(int n) {
    agentes = new ArrayList<Agente>();
    this.n = n;
    matriz = new char[n][n];
    carregar();
  }


  /* Insere agente no ambiente */
  public void inserirAgente(Agente agente) {
    agentes.add(agente);
  }

  /* Imprime o estado atual do ambiente com seus agentes */
  public void print() {
    for(int i = 0; i < n; i++) {
      for(int j = 0; j < n; j++) {

        // Verifica se algum agente esta nesta Posicao
        for(Agente ag : agentes) {
          if(ag.getX() == j && ag.getY() == i)
            System.out.print("A ");
          else
            System.out.print(matriz[i][j] + " ");

        }
      }
      System.out.println();
    }
  }

  /* Executa a simulacao dos agentes no ambiente */
  public void simular() {
    System.out.print(ANSI_CLS + ANSI_HOME);
    while(true) {
      print();
      try {
        Thread.sleep(1000);
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();

        for(Agente ag : agentes) {
          char atual, esq, dir, cima, baixo;
          atual = matriz[ag.getY()][ag.getX()];

          if((ag.getX() - 1) < 0)
            esq = NULO;
          else
            esq = matriz[ag.getY()][ag.getX() - 1];

          if((ag.getX() + 1) >= n)
            dir = NULO;
          else
            dir = matriz[ag.getY()][ag.getX() + 1];

          if((ag.getY() - 1) < 0)
            cima = NULO;
          else cima = matriz[ag.getY() - 1][ag.getX()];

          if((ag.getY() + 1) >= n)
            baixo = NULO;
          else
            baixo = matriz[ag.getY() + 1][ag.getX()];

          ag.atualizar(atual, esq, dir, cima, baixo);
        }

      } catch(Exception e) {
        e.printStackTrace();
        System.exit(1);
      }
    }
  }

}
