public class Agente {
  private int px, py;     // Posicao do agente
  private char direcao;   // Direcao de varredura {d, e}
  private int estado;
  private int lastLine;
  private int lastColumn;
  private boolean urgencia;

  /* Construtor */
  public Agente(int px, int py, char direcao, int estado) {
    this.px = px;
    this.py = py;
    this.direcao = direcao;
    this.estado = estado;
    this.lastLine = 0;
    this.urgencia = false;
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
  public void atualizar(char atual, char esq, char dir, char cima, char baixo, int tamanho) {

    if(px == tamanho-1 && py == tamanho-1)
        System.out.println("BYE BYE");

    System.out.println("------------------------------------------------"); 
    System.out.println("urgencia = " + urgencia);
    System.out.println("direcao = " + direcao);
    System.out.println("px = " + px);
    System.out.println("py = " + py);
    System.out.println("lastColumn = " + lastColumn);
    System.out.println("lastLine = " + lastLine);
    System.out.println("------------------------------------------------");
    if (direcao == 'e') {

        if (urgencia) {

            if ((lastColumn != px && py+1 == lastLine) && py+1 == lastLine && baixo != Ambiente.RECARGA && baixo != Ambiente.LIXEIRA) {
                    py+=1;
            }

            else if (py+1 < lastLine && px != lastColumn && baixo != Ambiente.RECARGA && baixo != Ambiente.LIXEIRA) {
                py+=1;
            }

            else if (esq != Ambiente.NULO && esq != Ambiente.RECARGA && esq != Ambiente.LIXEIRA) {
                px-=1;
            }

            else if (esq == Ambiente.NULO) {             
                direcao = 'e';

            }

            else if (esq == Ambiente.RECARGA || esq == Ambiente.LIXEIRA) {
                if (cima != Ambiente.RECARGA && cima != Ambiente.LIXEIRA) {
                    py-=1;
                    lastColumn = px;
                }
                else if (dir != Ambiente.RECARGA && dir != Ambiente.LIXEIRA) {
                    px+=1;
                    lastColumn = px;
                }
                else {
                    py+=1;
                    direcao = 'd';
                }

            }

            if (py == lastLine)
                urgencia = false;
        }

        else {
            if (esq == Ambiente.NULO) {
                if (baixo == Ambiente.NULO)
                    System.out.println("FIM");
                else if (baixo != Ambiente.RECARGA && baixo != Ambiente.LIXEIRA) {
                    py+=1;
                    direcao = 'd';
                }
                else {
                    lastColumn = px;
                    urgencia = true;
                    direcao = 'd';
                    lastLine = py+1;
                }
            }

            else if (esq != Ambiente.RECARGA && esq != Ambiente.LIXEIRA) {
                px-=1;
            }
            else {
                lastColumn = px;
                urgencia = true;
                lastLine = py;
            }
        
        }
    
    }

    else if (direcao == 'd') {

        if (urgencia) {

            if ((lastColumn != px && py+1 == lastLine) && py+1 == lastLine && baixo != Ambiente.RECARGA && baixo != Ambiente.LIXEIRA) {
                    py+=1;
            }

            else if (py+1 < lastLine && px != lastColumn && baixo != Ambiente.RECARGA && baixo != Ambiente.LIXEIRA) {
                py+=1;
            }

            else if (dir != Ambiente.NULO && dir != Ambiente.RECARGA && dir != Ambiente.LIXEIRA)
                px+=1;

            else if (dir == Ambiente.NULO) {             
                direcao = 'e';

            }

            else if (dir == Ambiente.RECARGA || dir == Ambiente.LIXEIRA) {
                if (cima != Ambiente.RECARGA && cima != Ambiente.LIXEIRA) {
                    py-=1;
                    lastColumn = px;
                }
                else if (esq != Ambiente.RECARGA && esq != Ambiente.LIXEIRA) {
                    px-=1;
                }
                else
                    py+=1;

            }

            if (py == lastLine)
                urgencia = false;
        }

        else {
            if (dir == Ambiente.NULO) {
                if (baixo == Ambiente.NULO)
                    System.out.println("FIM");
                else if (baixo != Ambiente.RECARGA && baixo != Ambiente.LIXEIRA) {
                    py+=1;
                    direcao = 'e';
                }
                else {
                    lastColumn = px;
                    urgencia = true;
                    direcao = 'e';
                    lastLine = py+1;
                }
            }

            else if (dir != Ambiente.RECARGA && dir != Ambiente.LIXEIRA)
                px+=1;
            else {
                lastColumn = px;
                urgencia = true;
                lastLine = py;

            }
        
        }
    
    }
    

  }

}
