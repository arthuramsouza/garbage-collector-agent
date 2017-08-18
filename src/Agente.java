public class Agente {
  private int px, py;     // Posicao do agente
  private char direcao;   // Direcao de varredura {d, e}
  private int estado;

  /* Construtor */
  public Agente(int px, int py, char direcao, int estado) {
    this.px = px;
    this.py = py;
    this.direcao = direcao;
    this.estado = estado;
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
    if (atual == Ambiente.NULO) {}
  }

}
