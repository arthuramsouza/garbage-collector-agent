
/**
 * Esta classe é uma fila de prioridades encadeada que é utilizada no algoritmo A*.
 *
 */
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
            int node_aux_priority = node_aux.getPriority();
            Nodo current_node = head;
            int current_priority = current_node.getPriority();
            //adiciona o nodo na primeira posicao se o valor priority e menor que o do head
            if (node_aux_priority <= current_priority) {
                node_aux.setNext(current_node);
                head = node_aux;
                size = size + 1;
                return;
            } else {
                //percorre a fila procurando um nodo com valor de priority maior
                while (current_node.getNext() != null) {
                    current_priority = current_node.getNext().getPriority();
                    //adiciona o nodo na posicao imediatamente anterior ao encontrar um nodo com priority maior
                    if (node_aux_priority <= current_priority) {
                        node_aux.setNext(current_node.getNext());
                        current_node.setNext(node_aux);
                        size = size + 1;
                        return;
                    } else {
                        current_node = current_node.getNext();
                    }
                }
                //adiciona o nodo no final da fila se nao tiver encontrado um nodo com valor de priority maior
                node_aux.setNext(current_node.getNext());
                current_node.setNext(node_aux);
                tail = node_aux;
                size = size + 1;
                return;
            }
        }
    }

    public Nodo removeFromQueue() {
        if (size > 0) {
            Nodo node_to_remove = head;
            head = head.getNext();
            node_to_remove.setNext(null);
            size = size - 1;
            return node_to_remove;
        }
        return null;
    }

    public void printQueuePriorities() {
        System.out.println("\n----queue priorities----");
        Nodo node_aux = head;
        while (node_aux != null) {
            System.out.println(node_aux.getPriority() + " ");
            node_aux = node_aux.getNext();
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
}
