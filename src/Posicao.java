
public class Posicao {

    private int x;
    private int y;

    public Posicao(int x, int y) {
        this.x = y;
        this.y = x;
    }

    public Posicao getPosicao() {
        return new Posicao(getX(), getY());
    }

    public Posicao() {
        x = 0;
        y = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String toString() {
        return "[ " + getY() + " , " + getX() + " ]";
    }
}