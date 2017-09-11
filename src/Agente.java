import java.util.PriorityQueue;

public class Agente {
 private int px, py, currentLine; // Posicao do agente, linha corrente sendo limpa
 private char direcao, lastMove;  // Direcao de varredura {d, e}
 private boolean isAvoiding, isCurrentLineClean;  // Agente esta contornando algum obstaculo (troca de linha)
 //private PriorityQueue<PQueueItem> moves;

 public static final char UP_LEFT = 0;
 public static final char UP = 1;
 public static final char UP_RIGHT = 2;
 public static final char LEFT = 3;
 public static final char AGENTE = 4;
 public static final char RIGHT = 5;
 public static final char DOWN_LEFT = 6;
 public static final char DOWN = 7;
 public static final char DOWN_RIGHT = 8;
 public static final char NONE = 10;

 public static final char DIREITA = 'd';
 public static final char ESQUERDA = 'e';

 /* Construtor */
 public Agente(int px, int py, char direcao, int estado) {
  this.px = px;
  this.py = py;
  this.direcao = direcao;
  this.currentLine = py;
  this.isAvoiding = false;
  this.isCurrentLineClean = false;
  //this.moves = new PriorityQueue<PQueueItem>();
 }

 public int getX() { return px; }
 public int getY() { return py; }


 private void mover(char direcao) {
   switch(direcao) {
     case UP_LEFT : lastMove = UP_LEFT; px--; py--; break;
     case UP : lastMove = UP; py--; break;
     case UP_RIGHT : lastMove = UP_RIGHT; px++; py--; break;
     case LEFT : lastMove = LEFT; px--; break;
     case RIGHT : lastMove = RIGHT; px++; break;
     case DOWN_LEFT : lastMove = DOWN_LEFT; px--; py++; break;
     case DOWN : lastMove = DOWN; py++; break;
     case DOWN_RIGHT : lastMove = DOWN_RIGHT; px++; py++; break;
   }
 }


 /*
 Algoritmo A*
 */
 private void aStar(int x, int y) {

 }

 private boolean isValidMove(char celula) {
   if(celula == Ambiente.PAREDE || celula == Ambiente.NULO ||
   celula == Ambiente.LIXEIRA || celula == Ambiente.RECARGA) {
     return false;
   }
   return true;
 }


 /* Atualiza estado do agente com base no valor das adjacencias
 > Requer: informacoes da celula atual e adjacentes

   0 1 2
   3 4 5
   6 7 8
 */

 public boolean atualizar(char entorno[]) {
   char prefMove = (direcao == DIREITA) ? RIGHT : LEFT;
   char noPrefMove = (direcao == DIREITA) ? LEFT : RIGHT;
   char prefMoveUp = (direcao == DIREITA) ? UP_RIGHT : UP_LEFT;
   char noPrefMoveUp = (direcao == DIREITA) ? UP_LEFT : UP_RIGHT;

   char prefMoveDown = (direcao == DIREITA) ? DOWN_RIGHT : DOWN_LEFT;
   char noPrefMoveDown = (direcao == DIREITA) ? DOWN_LEFT : DOWN_RIGHT;

   if(entorno[AGENTE] == Ambiente.SUJEIRA) {
     Ambiente.limpar(px, py);
   } else if(isCurrentLineClean) { // Obstaculo nas bordas, contornando
      if(isValidMove(entorno[noPrefMoveDown]) &&
      !isValidMove(entorno[prefMove]) && !isValidMove(entorno[DOWN]) &&
      !isValidMove(entorno[prefMoveDown]) && currentLine > py) {
        mover(noPrefMoveDown);
      } else if(isValidMove(entorno[prefMoveDown]) && currentLine > py) {
          mover(prefMoveDown);
      } else if(isValidMove(entorno[DOWN]) && !isValidMove(entorno[prefMove]) &&
      currentLine > py) {
        mover(DOWN);
      } else if(isValidMove(entorno[noPrefMove])) {
        mover(noPrefMove);
      } else if(isValidMove(entorno[prefMove]) && currentLine == py) {
        mover(prefMove);
      } else {
        System.out.println("Situacao nao prevista!");
        System.exit(1);
      }

      if(currentLine == py && !isValidMove(entorno[prefMove])) {
        isCurrentLineClean = false;
        isAvoiding = false;
        direcao = (direcao == DIREITA) ? ESQUERDA : DIREITA;
      }

      if(entorno[prefMove] == Ambiente.NULO && entorno[DOWN] == Ambiente.NULO) {
        return false;
      }

   } else if(isAvoiding) {
     if(isValidMove(entorno[prefMoveUp]) && !isValidMove(entorno[prefMove]) &&
     lastMove != noPrefMoveDown) {
       mover(prefMoveUp);
     } else if(isValidMove(entorno[noPrefMoveUp]) && !isValidMove(entorno[prefMove]) &&
     !isValidMove(entorno[prefMoveUp]) && !isValidMove(entorno[UP]) &&
     lastMove != prefMoveDown && lastMove != noPrefMoveDown) {
       mover(noPrefMoveUp);
     } else if(isValidMove(entorno[prefMove]) &&
     !isValidMove(entorno[DOWN])&& !isValidMove(entorno[prefMoveDown]) &&
     lastMove != noPrefMove && currentLine > py)  {
       mover(prefMove);
     } else if(isValidMove(entorno[prefMoveDown]) && lastMove != noPrefMoveUp &&
       !isValidMove(entorno[DOWN]) && currentLine > py) {
       mover(prefMoveDown);
     } else if(isValidMove(entorno[noPrefMove]) && lastMove != prefMove &&
     (lastMove == DOWN || lastMove == prefMoveDown || lastMove == noPrefMoveDown)) {
       mover(noPrefMove);
     } else if(isValidMove(entorno[noPrefMoveDown]) &&
     !isValidMove(entorno[noPrefMove]) && lastMove != UP && lastMove != prefMoveUp
     && currentLine > py) {
        mover(noPrefMoveDown);
     } else if(isValidMove(entorno[DOWN]) && lastMove != UP &&
     lastMove != prefMoveUp && lastMove != noPrefMoveUp &&
     currentLine > py) {
       mover(DOWN);
     } else if(isValidMove(entorno[UP]) && !isValidMove(entorno[prefMove]) &&
     lastMove != DOWN && entorno[prefMove] != Ambiente.NULO) {
       mover(UP);
     } else if(isValidMove(entorno[prefMove]) && currentLine == py &&
     entorno[prefMove] != Ambiente.NULO) {
       isAvoiding = false;

     // Tratamento das bordas da matriz
     } else if(entorno[prefMove] == Ambiente.NULO && !isCurrentLineClean) {
       isCurrentLineClean = true;
       currentLine++;

     // Acao nao prevista (util para debug e evitar loop infinito)
     } else {
       System.out.println("Acao nao prevista!");
       System.exit(1);
     }

   } else {
      // Movimentos normais - preferencial e tratamento de bordas
      if(isValidMove(entorno[prefMove])) {
        mover(prefMove);
      } else if(entorno[prefMove] == Ambiente.NULO) { // bordas da matriz
        if(isValidMove(entorno[DOWN])) {
          mover(DOWN);
          direcao = (direcao == DIREITA) ? ESQUERDA : DIREITA;
          currentLine++;
          isCurrentLineClean = false;
        } else if(entorno[DOWN] == Ambiente.NULO && entorno[prefMove] == Ambiente.NULO) {
          // Ultima celula do ambiente, encerra simulacao
          return false;
        } else {
          isAvoiding = true;
        }
      } else {
        isAvoiding = true;
      }
   }
   printEntorno(entorno);
   log();
   return true;
 }


 private void log() {
    System.out.println("------------------------------------------------");
    System.out.println("direcao = " + direcao);
    System.out.println("px = " + px);
    System.out.println("py = " + py);
    System.out.println("currentLine = " + currentLine);
    System.out.println("lastMove = " + (int)lastMove);
    System.out.println("isAvoiding obstacle = " + isAvoiding);
    System.out.println("isCurrentLineClean = " + isCurrentLineClean);
    System.out.println("------------------------------------------------");
 }


 private void printEntorno(char entorno[]) {
     System.out.print(entorno[Agente.UP_LEFT] + " " + entorno[Agente.UP] + " " + entorno[Agente.UP_RIGHT] + "\n" +
                      entorno[Agente.LEFT] + " " + entorno[Agente.AGENTE] + " " + entorno[Agente.RIGHT] + "\n" +
                      entorno[Agente.DOWN_LEFT] + " " + entorno[Agente.DOWN] + " " + entorno[Agente.DOWN_RIGHT] + "\n");
   }
}
