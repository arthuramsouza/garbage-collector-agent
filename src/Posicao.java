
/**
 * ORIENTACAO DO AMBIENTE: MATRIZ[LIINHA][COLUNA]
 */
public class Posicao {

    private int linha;
    private int coluna;

    public Posicao(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public Posicao getPosicao() {
        return new Posicao(getLinha(), getColuna());
    }

    public Posicao() {
        linha = 0;
        coluna = 0;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    public String toString() {
        return "[ " + getLinha() + " , " + getColuna() + " ]";
    }
}
