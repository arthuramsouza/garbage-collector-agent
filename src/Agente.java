import java.util.List;
import java.util.ArrayList;

public class Agente {
 private int px, py, currentLine; // Posicao do agente, linha corrente sendo limpa
 private char direcao; // Direcao de varredura {d, e}
 private int estado;
 private int lastLine;
 private int lastColumn;
 private boolean urgencia;
 private char lastMove;

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
  this.estado = estado;
  this.lastLine = 0;
  this.urgencia = false;
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
   printEntorno(entorno);

   char pref_move = direcao == DIREITA ? Agente.RIGHT : Agente.LEFT;

   if(isValidMove(entorno[pref_move])) {
     mover(pref_move);
   } else if(entorno[pref_move] == Ambiente.NULO) { // borda da matriz
     if(isValidMove(entorno[Agente.DOWN])) {
       mover(Agente.DOWN);
       direcao = direcao == DIREITA ? ESQUERDA : DIREITA;
     }
   }
 }

 private void log(int px, int py, boolean urgencia, char direcao, int lastColumn, int lastLine) {
    System.out.println("------------------------------------------------");
    System.out.println("urgencia = " + urgencia);
    System.out.println("direcao = " + direcao);
    System.out.println("px = " + px);
    System.out.println("py = " + py);
    System.out.println("lastColumn = " + lastColumn);
    System.out.println("lastLine = " + lastLine);
    System.out.println("------------------------------------------------");
 }

 private void printEntorno(char entorno[]) {
     System.out.print(entorno[Agente.UP_LEFT] + " " + entorno[Agente.UP] + " " + entorno[Agente.UP_RIGHT] + "\n" +
                      entorno[Agente.LEFT] + " " + entorno[Agente.AGENTE] + " " + entorno[Agente.RIGHT] + "\n" +
                      entorno[Agente.DOWN_LEFT] + " " + entorno[Agente.DOWN] + " " + entorno[Agente.DOWN_RIGHT] + "\n");
   }
}
