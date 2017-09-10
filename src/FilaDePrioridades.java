
import java.util.ArrayList;

public class FilaDePrioridades {

    private Nodo head;
    private Nodo tail;
    private int size;

    public FilaDePrioridades() {
        head = null;
        tail = null;
        size = 0;
    }

    public void addToQueue(Nodo node_aux) {
        //adiciona o nodo na primeira posicao se a fila esta vazia
        if (size == 0) {
            head = node_aux;
            tail = head;
            size = size + 1;
            return;
        } else {
            int node_aux_priority = node_aux.priority;
            Nodo current_node = head;
            int current_priority = current_node.priority;
            //adiciona o nodo na primeira posicao se o valor priority e menor que o do head
            if (node_aux_priority <= current_priority) {
                node_aux.next = current_node;
                head = node_aux;
                size = size + 1;
                return;
            } else {
                //percorre a fila procurando um nodo com valor de priority maior
                while (current_node.next != null) {
                    current_priority = current_node.next.priority;
                    //adiciona o nodo na posicao imediatamente anterior ao encontrar um nodo com priority maior
                    if (node_aux_priority <= current_priority) {
                        node_aux.next = current_node.next;
                        current_node.next = node_aux;
                        size = size + 1;
                        return;
                    } else {
                        current_node = current_node.next;
                    }
                }
                //adiciona o nodo no final da fila se nao tiver encontrado um nodo com valor de priority maior
                node_aux.next = current_node.next;
                current_node.next = node_aux;
                tail = node_aux;
                size = size + 1;
                return;
            }
        }
    }

    public Nodo removeFromQueue() {
        Nodo node_to_remove = head;
        head = head.next;
        node_to_remove.next = null;
        size = size - 1;
        return node_to_remove;
    }

    public void printQueuePriorities() {
        System.out.println("\n----queue priorities----");
        Nodo node_aux = head;
        while (node_aux != null) {
            System.out.println(node_aux.getPriority() + " ");
            node_aux = node_aux.next;
        }
    }

    public Nodo getHead() {
        return head;
    }

    public void setHead(Nodo head) {
        this.head = head;
    }

    public Nodo getTail() {
        return tail;
    }

    public void setTail(Nodo tail) {
        this.tail = tail;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

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
            return position;
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
    }
}
