public class Agente {
 private int px, py, currentLine; // Posicao do agente, linha corrente sendo limpa
 private char direcao, lastMove; // Direcao de varredura {d, e}

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
 public void atualizar(char entorno[]) {
   char prefMove = (direcao == DIREITA) ? Agente.RIGHT : Agente.LEFT;
   char noPrefMove = (direcao == DIREITA) ? Agente.LEFT : Agente.RIGHT;

   if(currentLine == py) { // esta na linha que esta limpando
     if(isValidMove(entorno[prefMove]) && lastMove != noPrefMove) {
       lastMove = prefMove;
       mover(prefMove);
     } else if(entorno[prefMove] == Ambiente.NULO) { // bordas da matriz
       if(isValidMove(entorno[Agente.DOWN])) {
         lastMove = Agente.DOWN;
         mover(Agente.DOWN);
         direcao = (direcao == DIREITA) ? ESQUERDA : DIREITA;
         currentLine++;
       }
     } else if(isValidMove(entorno[Agente.UP]) && lastMove != Agente.DOWN) {
          lastMove = Agente.UP;
          mover(Agente.UP);
      } else if(isValidMove(entorno[noPrefMove])) {
        System.out.println("Aqui");
        lastMove = noPrefMove;
        mover(noPrefMove);
      }
   } else { // tentando voltar para currentLine
     if(isValidMove(entorno[Agente.DOWN]) && lastMove != Agente.UP) {
       lastMove = Agente.DOWN;
       mover(Agente.DOWN);
     } else if(isValidMove(entorno[prefMove]) && lastMove != noPrefMove) {
       lastMove = prefMove;
       mover(prefMove);
     } else if(isValidMove(entorno[Agente.UP]) && lastMove != Agente.DOWN) {
       lastMove = Agente.UP;
       mover(Agente.UP);
     } else if(isValidMove(entorno[noPrefMove]) && lastMove != prefMove) {
       lastMove = noPrefMove;
       mover(noPrefMove);
     }
   }

   printEntorno(entorno);
   log();
 }


 private void log() {
    System.out.println("------------------------------------------------");
    System.out.println("direcao = " + direcao);
    System.out.println("px = " + px);
    System.out.println("py = " + py);
    System.out.println("currentLine = " + currentLine);
    System.out.println("------------------------------------------------");
 }


 private void printEntorno(char entorno[]) {
     System.out.print(entorno[Agente.UP_LEFT] + " " + entorno[Agente.UP] + " " + entorno[Agente.UP_RIGHT] + "\n" +
                      entorno[Agente.LEFT] + " " + entorno[Agente.AGENTE] + " " + entorno[Agente.RIGHT] + "\n" +
                      entorno[Agente.DOWN_LEFT] + " " + entorno[Agente.DOWN] + " " + entorno[Agente.DOWN_RIGHT] + "\n");
   }
}
