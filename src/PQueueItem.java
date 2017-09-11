import java.util.Comparator;

public class PQueueItem implements Comparator<Integer> {
  private int priority;
  private char direction;

  public PQueueItem(int p, char d) {
    this.priority = p;
    this.direction = d;
  }

  public int getPriority() { return priority; }
  public int getDirection() { return direction; }

  //@Override
  public int compare(int x, int y) {
    if(x > y) {
      return 1;
    } else if(x < y) {
      return -1;
    }
    return 0;
  }
}
