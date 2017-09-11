//import java.util.Comparable;

public class PQueueItem implements Comparable<PQueueItem> {
  private int priority;
  private char direction;

  public PQueueItem(int p, char d) {
    this.priority = p;
    this.direction = d;
  }

  public int getPriority() { return priority; }
  public int getDirection() { return direction; }

  @Override
  public boolean equals(Object obj) {
    if(!(obj instanceof PQueueItem)) {
      return false;
    }

    PQueueItem p = (PQueueItem)obj;
    if(p.getPriority() == this.priority) {
      return true;
    }
    return false;
  }

  @Override
  public int compareTo(PQueueItem obj) {
    if(obj.getPriority() > this.priority) {
      return -1;
    } else if(obj.getPriority() < this.priority) {
      return 1;
    }
    return 0;
  }

  public String toString() {
    return "Prioridade: " + this.priority + " Direcao: " + (int)this.direction;
  }
}
