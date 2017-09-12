
import java.util.ArrayList;

/**
 * A classe Nodo é utilizada na classe FilaDePrioridades.
 */
public class Nodo {

    private int priority;
    private int distance_until_here;
    private int number_of_moves;
    private Nodo next;
    private Posicao position;
    private ArrayList<Character> path;

    public Nodo() {
        priority = 0;
        distance_until_here = 0;
        number_of_moves = 0;
        next = null;
        position = null;
        path = new ArrayList<Character>();
    }

    public String toString() {
        return "posicao:" + position.toString() + " prioridade:" + priority + " caminho" + path.toString();
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getDistance_until_here() {
        return distance_until_here;
    }

    public void setDistance_until_here(int distance_until_here) {
        this.distance_until_here = distance_until_here;
    }

    public int getNumber_of_moves() {
        return number_of_moves;
    }

    public void setNumber_of_moves(int number_of_moves) {
        this.number_of_moves = number_of_moves;
    }

    public Nodo getNext() {
        return next;
    }

    public void setNext(Nodo next) {
        this.next = next;
    }

    public Posicao getPosition() {
        return position.getPosicao();
    }

    public void setPosition(Posicao position) {
        this.position = position;
    }

    public ArrayList<Character> getPath() {
        return path;
    }

    public void setPath(ArrayList<Character> path) {
        this.path = path;
    }

    public int getPosicaoLinha() {
        return position.getLinha();
    }

    public int getPosicaoColuna() {
        return position.getColuna();
    }

    public void addAMove(char move) {
        path.add(move);
    }
}
