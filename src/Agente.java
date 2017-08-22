public class Agente {
  private int px, py;     // Posicao do agente
  private char direcao;   // Direcao de varredura {d, e}
  private int estado;
  private boolean baixou;

  /* Construtor */
  public Agente(int px, int py, char direcao, int estado) {
    this.px = px;
    this.py = py;
    this.direcao = direcao;
    this.estado = estado;
    this.baixou = false;
  }

  public int getX() { return px; }
  public int getY() { return py; }

  /*
  Algoritmo A*
  */
  private void aStar(int x, int y) {

  }


  public void recarregar() {

  }

  public void aspirar() {

  }

  /* Atualiza estado do agente com base no valor das adjacencias
  > Requer: informacoes da celula atual e adjacentes
  */
  public void atualizar(char atual, char esq, char dir, char cima, char baixo) {
    if (direcao == 'e') {
        if (esq == Ambiente.NULO && !baixou) {
            py+=1;
            direcao = 'd';
            baixou = true;
            System.out.println("Dir: " + direcao + "\tBaixou: " + baixou + "\tX: " + px + "\tY: " + py);
            System.out.println("ADJACENCIAS\tEsq: " + esq + "\tDir: " + dir);
        }
        else {
            // ir para a esquerda
            px-=1;
            baixou = false;
            System.out.println("Dir: " + direcao + "\tBaixou: " + baixou + "\tX: " + px + "\tY: " + py);
            System.out.println("ADJACENCIAS\tEsq: " + esq + "\tDir: " + dir);
        }
    }

    else if (direcao == 'd') {
        if (dir == Ambiente.NULO && !baixou) {
            // se possível, ir para baixo
            py+=1;
            direcao = 'e';
            baixou = true;
            System.out.println("Dir: " + direcao + "\tBaixou: " + baixou + "\tX: " + px + "\tY: " + py);
            System.out.println("ADJACENCIAS\tEsq: " + esq + "\tDir: " + dir);
        }
        else {
            // ir para a direita
            px+=1;
            baixou = false;
            System.out.println("Dir: " + direcao + "\tBaixou: " + baixou + "\tX: " + px + "\tY: " + py);
            System.out.println("ADJACENCIAS\tEsq: " + esq + "\tDir: " + dir);
        }
    }
    

  }

}
