import java.util.List;
import java.util.ArrayList;

public class Ambiente {
  private int tamanho;
  private int quantidadeLixeiras;
  private int quantidadeRecargas;
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

  /* Construtor da classe */
  public Ambiente(int n, int quantidadeLixeiras, int quantidadeRecargas) {
    agentes = new ArrayList<Agente>();
    this.tamanho = n;
    this.quantidadeLixeiras = quantidadeLixeiras;
    this.quantidadeRecargas = quantidadeRecargas;
    matriz = new char[tamanho][tamanho];
    carregar();
  }

  /* TODO Coloca elementos (lixeiras, lixo e carregadores) de forma randomica no ambiente  */
  private void carregar(int n_lixeiras, int n_carregadores, int n_lixo) {
    for (int i = 0; i < tamanho; i++)
      for (int j = 0; j < tamanho; j++)
        matriz[i][j] = LIMPO;
  }

  /* Coloca elementos no ambiente - estatico */
  private void carregar() {
    for (int i = 0; i < tamanho; i++) {
      for (int j = 0; j < tamanho; j++) {
        matriz[i][j] = LIMPO;
      }
    }

    inserirParedes();
    inserirLixeiras();
    inserirSujeiras();
  }

  /* Insere agente no ambiente */
  public void inserirAgente(Agente agente) {
    agentes.add(agente);
  }

  /* Insere paredes no ambiente */
  public void inserirParedes() {
    for (int i = 0; i < tamanho; i++) {
      for (int j = 0; j < tamanho; j++) {
        // realizando o tratamento lateral
        this.matriz[i][this.tamanho - 3] = PAREDE;
        //this.matriz[i][0 - 3] = PAREDE;
        
        //this.matriz[i][this.tamanho + 2] = PAREDE;
      }
    }
  }

  //TODO: adicionar lixeiras randomicas considerando as paredes
  public void inserirLixeiras() {
    int auxiliar = this.quantidadeLixeiras;
    for (int i = 0; i < tamanho; i++) {
      for (int j = 0; j < tamanho; j++) {
        if(this.matriz[i][j] != PAREDE || this.matriz[i][j] != SUJEIRA || this.matriz[i][j] != RECARGA) {
          if(auxiliar > 0) {
            //this.matriz[i][j] = LIXEIRA;
          }
        }
      }
    }
  }

  //TODO: adicionar sujeiras randomicas considerando as paredes
  public void inserirSujeiras() {

  }

  /* Imprime o estado atual do ambiente com seus agentes */
  public void print() {
    for (int i = 0; i < tamanho; i++) {
      for (int j = 0; j < tamanho; j++) {

        // Verifica se algum agente esta nesta Posicao
        for (Agente ag : agentes) {
          if (ag.getX() == j && ag.getY() == i)
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
    while (true) {
      print();
      try {
        Thread.sleep(500);
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();

        for (Agente ag : agentes) {
          char atual, esq, dir, cima, baixo;
          atual = matriz[ag.getY()][ag.getX()];

          if ((ag.getX() - 1) < 0)
            esq = NULO;
          else
            esq = matriz[ag.getY()][ag.getX() - 1];

          if ((ag.getX() + 1) >= tamanho)
            dir = NULO;
          else
            dir = matriz[ag.getY()][ag.getX() + 1];

          if ((ag.getY() - 1) < 0)
            cima = NULO;
          else
            cima = matriz[ag.getY() - 1][ag.getX()];

          if ((ag.getY() + 1) >= tamanho)
            baixo = NULO;
          else
            baixo = matriz[ag.getY() + 1][ag.getX()];

          ag.atualizar(atual, esq, dir, cima, baixo);
        }

      } catch (Exception e) {
        e.printStackTrace();
        System.exit(1);
      }
    }
  }

}
