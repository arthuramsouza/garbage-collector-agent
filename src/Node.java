public class Node implements Comparable<Node> {
  private int g, h, f, px, py;
  private char direction;
  private Node parent;

  public Node(int g, int h, char direction, Node parent, int px, int py) {
    this.g = g;         // distancia percorrida ate este nodo
    this.h = h;         // heuristica deste nodo ate o destino
    this.direction = direction;
    this.parent = parent;
    this.f = g + h;
    this.px = px;
    this.py = py;
  }

  public int getF() { return this.f; }
  public int getG() { return this.g; }
  public int getH() { return this.h; }
  public Node getParent() { return this.parent; }
  public char getDirection() { return this.direction; }
  public int getX() { return this.px; }
  public int getY() { return this.py; }


  @Override
  public boolean equals(Object obj) {
    if(!(obj instanceof Node)) {
      return false;
    }

    Node p = (Node)obj;
    if(p.getF() == this.f) {
      return true;
    }
    return false;
  }

  @Override
  public int compareTo(Node obj) {
    if(obj.getF() > this.f) {
      return -1;
    } else if(obj.getF() < this.f) {
      return 1;
    }
    return 0;
  }

  public String toString() {
    String parent_str;

    if(parent == null) {
      parent_str = "nulo";
    } else {
      parent_str = parent.toString();
    }

    return  "f=" + f +
            " g=" + g +
            " h=" + h +
            " direction=" + (int)direction +
            " pos [" + px + "," + py +
            "] parent [" + parent_str + "]\n";
  }
}
