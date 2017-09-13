import java.util.PriorityQueue;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


public class Agente {
 private int px, py, currentLine; // Posicao do agente, linha corrente sendo limpa
 private int prev_x, prev_y;
 private char direcao, lastMove;  // Direcao de varredura {d, e}

 private boolean isAvoiding,      // Agente esta contornando algum obstaculo (troca de linha)
                 isCurrentLineClean,
                 isRunningAStar,
                 isDroppingTrash, // Indo para lixeira
                 isRecharging;    // Indo para carregador
 private List<Character> moves, moves_back;
 private List<Ponto> lixeiras, carregadores;

 private int energia, lixeira;

 private static final int CUSTO_MOVIMENTO = 1;
 private static final int CUSTO_ASPIRAR = 1;
 private static final int CUSTO_LIXO = 1;
 private static final int MAX_ENERGIA = 80;
 private static final int MAX_LIXEIRA = 2;

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

 public static final String MOVES_STR[] = {   "up_left", "up", "up_right",
                                              "left", "agente", "right",
                                              "down_left", "down", "down_right",
                                              " ", " "};

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
  this.isRunningAStar = false;
  this.moves = new ArrayList<Character>();

  this.lixeiras = Ambiente.getLixeiras();
  this.carregadores = Ambiente.getCarregadores();

  this.energia = MAX_ENERGIA;
  this.lixeira = 0;
 }

 public int getX() { return px; }
 public int getY() { return py; }

 private void recarregarEnergia() {
   this.energia = MAX_ENERGIA;
 }


 private void mover(char direcao) {
   switch(direcao) {
     case UP_LEFT : lastMove = UP_LEFT; px--; py--; energia -= CUSTO_MOVIMENTO; break;
     case UP : lastMove = UP; py--; energia -= CUSTO_MOVIMENTO; break;
     case UP_RIGHT : lastMove = UP_RIGHT; energia -= CUSTO_MOVIMENTO; px++; py--; break;
     case LEFT : lastMove = LEFT; px--; energia -= CUSTO_MOVIMENTO; break;
     case RIGHT : lastMove = RIGHT; px++; energia -= CUSTO_MOVIMENTO; break;
     case DOWN_LEFT : lastMove = DOWN_LEFT; energia -= CUSTO_MOVIMENTO; px--; py++; break;
     case DOWN : lastMove = DOWN; py++; energia -= CUSTO_MOVIMENTO; break;
     case DOWN_RIGHT : lastMove = DOWN_RIGHT; px++; energia -= CUSTO_MOVIMENTO; py++; break;
   }
 }


 /*
 Algoritmo A*
 */

 // Calculo de heuristica - Manhattan Distance
 private int calculate_heuristic(int x1, int y1, int x2, int y2) {
   return Math.abs(x2 - x1) + Math.abs(y2 - y1);
 }


 public List<Character> aStar(int dest_x, int dest_y, boolean adjacente) {
   char entorno[] = new char[9];
   Queue<Node> nodes = new PriorityQueue<Node>();
   List<Character> result = new ArrayList<Character>();

   int curr_x = this.px;
   int curr_y = this.py;
   // Gera primeiro nodo (posicao atual do agente)
   Node node = new Node(  0,
                          calculate_heuristic(curr_x, curr_y, dest_x, dest_y),
                          NONE, null, curr_x, curr_y);
   nodes.add(node);

   while(!nodes.isEmpty()) {
     node = nodes.remove();

     //System.out.println("Retirando nodo da pqueue " + node.toString());
     curr_x = node.getX();
     curr_y = node.getY();

     // Destino eh adjacente ao ponto de destino (nao para exatamente em cima)
     if(adjacente) {
       if((node.getX() == dest_x - 1 && node.getY() == dest_y - 1) ||   // up_left
          (node.getX() == dest_x && node.getY() == dest_y - 1) ||       // up
          (node.getX() == dest_x + 1 && node.getY() == dest_y - 1) ||   // up_right
          (node.getX() == dest_x - 1 && node.getY() == dest_y) ||       // left
          (node.getX() == dest_x + 1 && node.getY() == dest_y) ||       // right
          (node.getX() == dest_x - 1 && node.getY() == dest_y + 1) ||   // down_left
          (node.getX() == dest_x && node.getY() == dest_y + 1) ||       // down
          (node.getX() == dest_x + 1 && node.getY() == dest_y + 1)) {   // down_right
            break;
       }
     } else { // posicao exata
       if(node.getX() == dest_x && node.getY() == dest_y) {
         break;
       }
     }

     entorno = Ambiente.getEntorno(curr_x, curr_y);

     // Gera sucessores do nodo atual e adiciona na PriorityQueue
     for(int i = 0; i < 9; i++) {
       if(i == AGENTE) {
         continue;
       }

       int x = curr_x;
       int y = curr_y;

       if(isValidMove(entorno[i])) {
         switch(i) {
           case UP_LEFT  : x--; y--; break;
           case UP       : y--; break;
           case UP_RIGHT : x++; y--; break;
           case LEFT     : x--; break;
           case RIGHT    : x++; break;
           case DOWN_LEFT  : x--; y++; break;
           case DOWN       : y++; break;
           case DOWN_RIGHT : x++; y++; break;
         }

         Node node_child = new Node(     node.getG() + 1, calculate_heuristic(x, y, dest_x, dest_y),
                                        (char)i, node, x, y);
         //System.out.println("   Criando node child " + node_child.toString());
         nodes.add(node_child);
       }
     }
   }

   while(node.getParent() != null) {
     result.add(node.getDirection());
     node = node.getParent();
   }
   Collections.reverse(result);
   return result;
 }


 private boolean isValidMove(char celula) {
   if(celula == Ambiente.PAREDE || celula == Ambiente.NULO ||
   celula == Ambiente.LIXEIRA || celula == Ambiente.RECARGA) {
     return false;
   }
   return true;
 }

 // Verifica se tem energia para chegar no ponto de recarga mais proximo
 private boolean isLowEnergy() {
   this.prev_x = this.px;
   this.prev_y = this.py;

   int min_moves = 0;

   if(!carregadores.isEmpty()) {
     Ponto carregadorProximo = carregadores.get(0);

     //List<Character> min_moves = aStar(carregadorProximo.getX(), carregadorProximo.getY(), true);
     min_moves = calculate_heuristic(this.px, this.py, carregadorProximo.getX(), carregadorProximo.getY());

     for(int i = 1; i < carregadores.size(); i++) {
       carregadorProximo = carregadores.get(i);
       int moves_aux = calculate_heuristic(this.px, this.py, carregadorProximo.getX(), carregadorProximo.getY());

       //System.out.println("******* heuristica = " + moves_aux);

       if(moves_aux < min_moves) {
         min_moves = moves_aux;
       }
     }
   }

   //System.out.println("Custo ate carregador mais proximo = " + this.moves.size());

   if(this.energia < (min_moves * CUSTO_MOVIMENTO) * 2) {
     return true;
   }
   return false;
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

   // Verifica se agente ficou sem bateria - encerra simulacao
   // Teoricamente nunca deve executar
   if(this.energia <= 0) {
     System.err.println("Agente sem energia. Simulacao falhou!");
     System.exit(1);
   }

   if(isRunningAStar) {
     //System.out.println("Rodando astar");
     // Realiza movimento de ida
     if(!this.moves.isEmpty()) {
       System.out.println("Movimento de ida");
       mover(this.moves.remove(0));
     } else {
       if(isDroppingTrash) {
          this.lixeira = 0;
          isDroppingTrash = false;

          // Gera movimento para posicao anterior
          this.moves_back = aStar(this.prev_x, this.prev_y, false);
       } else if(isRecharging) {
         this.energia = MAX_ENERGIA;
         isRecharging = false;

         // Gera movimento para posicao anterior
         this.moves_back = aStar(this.prev_x, this.prev_y, false);
       }

       // Realiza movimento de volta
       if(!this.moves_back.isEmpty()) {
         System.out.println("Movimento de volta");
         mover(this.moves_back.remove(0));
       } else {
         isRunningAStar = false;
       }
     }

   } else if(isLowEnergy()) { // Verifica se consegue chegar no carregador mais proximo
     isRunningAStar = true;
     isRecharging = true;

     this.prev_x = this.px;
     this.prev_y = this.py;

     if(!carregadores.isEmpty()) {
       Ponto carregadorProximo = carregadores.get(0);

       List<Character> min_moves = aStar(carregadorProximo.getX(), carregadorProximo.getY(), true);

       for(int i = 1; i < carregadores.size(); i++) {
         carregadorProximo = carregadores.get(i);
         List<Character> moves_aux = aStar(carregadorProximo.getX(), carregadorProximo.getY(), true);

         if(moves_aux.size() < min_moves.size()) {
           min_moves = moves_aux;
         }
       }
       this.moves = min_moves;
       //System.out.print("Movimentos de ida = ");
       //for(int i = 0; i < this.moves.size(); i++) {
      //   System.out.print((int)this.moves.get(i) + " ");
       //}
       //System.out.println();

       this.moves_back = new ArrayList<Character>();
     } else {
        System.err.println("Sem lixeiras por perto!");
        System.exit(1);
     }

   } else if(this.lixeira == MAX_LIXEIRA) { // lixeira cheia, descarregar
     isRunningAStar = true;
     isDroppingTrash = true;

     this.prev_x = this.px;
     this.prev_y = this.py;

     if(!lixeiras.isEmpty()) {
       Ponto lixeiraProxima = lixeiras.get(0);

       List<Character> min_moves = aStar(lixeiraProxima.getX(), lixeiraProxima.getY(), true);

       for(int i = 1; i < lixeiras.size(); i++) {
         lixeiraProxima = lixeiras.get(i);
         List<Character> moves_aux = aStar(lixeiraProxima.getX(), lixeiraProxima.getY(), true);

         if(moves_aux.size() < min_moves.size()) {
           min_moves = moves_aux;
         }
       }
       this.moves = min_moves;
       System.out.print("Movimentos de ida = ");
       for(int i = 0; i < this.moves.size(); i++) {
         System.out.print((int)this.moves.get(i) + " ");
       }
       System.out.println();

       this.moves_back = new ArrayList<Character>();
     } else {
        System.err.println("Sem lixeiras por perto!");
        System.exit(1);
     }


   } else if(entorno[AGENTE] == Ambiente.SUJEIRA) {
     Ambiente.limpar(px, py);
     this.energia -= CUSTO_ASPIRAR;
     this.lixeira += CUSTO_LIXO;
   } else if(isCurrentLineClean) { // Obstaculo nas bordas, contornando
      if(isValidMove(entorno[noPrefMoveDown]) &&
      !isValidMove(entorno[prefMove]) && !isValidMove(entorno[DOWN]) &&
      !isValidMove(entorno[prefMoveDown]) && currentLine > py) {
        mover(noPrefMoveDown);
      } else if(isValidMove(entorno[prefMoveDown]) && currentLine > py) {
          mover(prefMoveDown);
      } else if(isValidMove(entorno[DOWN])  &&
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
    System.out.println("Direcao = " + direcao + "  Posicao = [" + px + "," + py + "]  Prev = [" + prev_x + "," + prev_y + "]");
    System.out.println("Energia = " + energia + "/" + MAX_ENERGIA + "  Lixeira = " + lixeira + "/" + MAX_LIXEIRA);
    System.out.println("currentLine = " + currentLine + "  lastMove = " + MOVES_STR[(int)lastMove]);
    System.out.println("isAvoiding obstacle = " + isAvoiding + "  isCurrentLineClean = " + isCurrentLineClean);
    System.out.println("isRunningAStar = " + isRunningAStar + "  isDroppingTrash = " + isDroppingTrash + "  isRecharging = " + isRecharging);
    System.out.println("------------------------------------------------");
 }


 private void printEntorno(char entorno[]) {
     System.out.print(entorno[Agente.UP_LEFT] + " " + entorno[Agente.UP] + " " + entorno[Agente.UP_RIGHT] + "\n" +
                      entorno[Agente.LEFT] + " " + entorno[Agente.AGENTE] + " " + entorno[Agente.RIGHT] + "\n" +
                      entorno[Agente.DOWN_LEFT] + " " + entorno[Agente.DOWN] + " " + entorno[Agente.DOWN_RIGHT] + "\n");
   }
}
