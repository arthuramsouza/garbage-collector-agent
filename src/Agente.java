public class Agente {
 private int px, py, currentLine; // Posicao do agente, linha corrente sendo limpa
 private char direcao, lastMove;  // Direcao de varredura {d, e}
 private boolean isAvoiding;      // Agente esta contornando algum obstaculo (troca de linha)

 public static final char UP_LEFT = 0;
 public static final char UP = 1;
 public static final char UP_RIGHT = 2;
 public static final char LEFT = 3;
 public static final char AGENTE = 4;
 public static final char RIGHT = 5;
 public static final char DOWN_LEFT = 6;
 public static final char DOWN = 7;
 public static final char DOWN_RIGHT = 8;

 public static final char DIREITA = 'd';
 public static final char ESQUERDA = 'e';

 /* Construtor */
 public Agente(int px, int py, char direcao, int estado) {
  this.px = px;
  this.py = py;
  this.direcao = direcao;
  this.currentLine = py;
  this.isAvoiding = false;
 }

 public int getX() {
  return px;
 }
 public int getY() {
  return py;
 }

 private void mover(char direcao) {
   switch(direcao) {
     case UP_LEFT : px--; py--; break;
     case UP : py--; break;
     case UP_RIGHT : px++; py--; break;
     case LEFT : px--; break;
     case RIGHT : px++; break;
     case DOWN_LEFT : px--; py++; break;
     case DOWN : py++; break;
     case DOWN_RIGHT : px++; py++; break;
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

 // TODO - quando o agente chegar em currentLine, varrer para o lado noPrefMove
 // antes de setar isAvoiding = false
 public boolean atualizar(char entorno[]) {
   char prefMove = (direcao == DIREITA) ? RIGHT : LEFT;
   char noPrefMove = (direcao == DIREITA) ? LEFT : RIGHT;

   if(isAvoiding) {
     if((!isValidMove(entorno[prefMove]) || lastMove == noPrefMove) && isValidMove(entorno[UP])) {
       lastMove = UP;
       mover(UP);
     } else if(isValidMove(entorno[DOWN]) && lastMove != UP && currentLine > py) {
       lastMove = DOWN;
       mover(DOWN);
     } else if(!isValidMove(entorno[prefMove]) && !isValidMove(entorno[UP]) && isValidMove(entorno[noPrefMove])) {
       lastMove = noPrefMove;
       mover(noPrefMove);
     } else if(isValidMove(entorno[prefMove]) && lastMove != noPrefMove) {
       lastMove = prefMove;
       mover(prefMove);

       if(currentLine == py) {
         isAvoiding = false;
       }
     }
   } else { // Movimentos normais - preferencial e tratamento de bordas
      if(isValidMove(entorno[prefMove]) && lastMove != noPrefMove) {
        lastMove = prefMove;
        mover(prefMove);
      } else if(entorno[prefMove] == Ambiente.NULO) { // bordas da matriz
        if(isValidMove(entorno[DOWN])) {
          lastMove = DOWN;
          mover(DOWN);
          direcao = (direcao == DIREITA) ? ESQUERDA : DIREITA;
          currentLine++;
        } else if(entorno[DOWN] == Ambiente.NULO && entorno[prefMove] == Ambiente.NULO) {
          return false;
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
    System.out.println("------------------------------------------------");
 }


 private void printEntorno(char entorno[]) {
     System.out.print(entorno[Agente.UP_LEFT] + " " + entorno[Agente.UP] + " " + entorno[Agente.UP_RIGHT] + "\n" +
                      entorno[Agente.LEFT] + " " + entorno[Agente.AGENTE] + " " + entorno[Agente.RIGHT] + "\n" +
                      entorno[Agente.DOWN_LEFT] + " " + entorno[Agente.DOWN] + " " + entorno[Agente.DOWN_RIGHT] + "\n");
   }
}
