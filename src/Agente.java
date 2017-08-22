public class Agente {
  private int px, py;     // Posicao do agente
  private char direcao;   // Direcao de varredura {d, e}
  private int estado;
  private boolean baixou;
  private boolean baixarUrgentemente;

  /* Construtor */
  public Agente(int px, int py, char direcao, int estado) {
    this.px = px;
    this.py = py;
    this.direcao = direcao;
    this.estado = estado;
    this.baixou = false;
    this.baixarUrgentemente = false;
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

    0 1 2
    3 4 5
    6 7 8
  */
  public void atualizar(char atual, char esq, char dir, char cima, char baixo) {
    if (direcao == 'e') {

        if (baixarUrgentemente) {
            while((baixo == Ambiente.RECARGA) || (baixo == Ambiente.LIXEIRA))
                px-=1;
            py+=1;
            baixarUrgentemente = false;
        }

        if (esq == Ambiente.NULO && !baixou) {
            if((baixo != Ambiente.RECARGA) && (baixo != Ambiente.LIXEIRA)) {
                py+=1;
                direcao = 'd';
                baixou = true;
            }
            else {
                baixarUrgentemente = true;
                direcao = 'd';
            }
        }
        else {
            // ir para a esquerda
            px-=1;
            baixou = false;
        }
    }

    else if (direcao == 'd') {

        if (baixarUrgentemente) {
            while((baixo == Ambiente.RECARGA) || (baixo == Ambiente.LIXEIRA))
                px+=1;
            py+=1;
        }

        if (dir == Ambiente.NULO && !baixou) {
            if((baixo != Ambiente.RECARGA) && (baixo != Ambiente.LIXEIRA)) {
                py+=1;
                direcao = 'e';
                baixou = true;
            }
            else {
                baixarUrgentemente = true;
                direcao = 'e';
            }

        }
        else {
            // ir para a direita
            px+=1;
            baixou = false;
        }
    }
    

  }

}
