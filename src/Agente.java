
public class Agente {

    int capacidadeParaCarregarLixo;
    int lixoAspiradoEArmazenado;
    private int px, py; // Posicao do agente
    private char direcao; // Direcao de varredura {d, e}
    private int estado;
    private int lastLine;
    private int lastColumn;
    private boolean urgencia;
    private boolean indo;
    private boolean voltando;
    private boolean varreduraPorLinhaNaEsquerdaConcluida;
    private boolean efetuarTransicaoParaDireita;
    private boolean varreduraPorLinhaNaDireitaConcluida;
    private boolean varrerPorColunas;
    private boolean varreduraCompletamenteConcluida;
    private char elementoObservadoNaEsquerda;
    private char elementoObservadoNaDireita;
    private char elementoObservadoEmCima;
    private char elementoObservadoEmBaixo;
    private Ambiente ambiente;
    public static final char NULO = 'N';
    public static final char SUJEIRA = 'S';
    public static final char LIXEIRA = 'L';
    public static final char RECARGA = 'R';
    public static final char PAREDE = 'P';
    public static final char LIMPO = '.';

    /* Construtor */
    public Agente(int px, int py, char direcao, int estado, Ambiente ambiente) {
        capacidadeParaCarregarLixo = 1000;
        lixoAspiradoEArmazenado = 0;
        this.px = px;
        this.py = py;
        this.direcao = direcao;
        this.estado = estado;
        this.lastLine = 0;
        this.urgencia = false;
        this.ambiente = ambiente;
        indo = true;
        voltando = false;
        varrerPorColunas = false;
        varreduraCompletamenteConcluida = false;
        varreduraPorLinhaNaEsquerdaConcluida = false;
        varreduraPorLinhaNaDireitaConcluida = false;
        efetuarTransicaoParaDireita = false;
        elementoObservadoNaEsquerda = 'N';
        elementoObservadoNaDireita = 'N';
        elementoObservadoEmCima = 'N';
        elementoObservadoEmBaixo = 'N';
    }

    public int getCapacidadeParaCarregarLixo() {
        return capacidadeParaCarregarLixo;
    }

    public int getLixoAspiradoEArmazenado() {
        return lixoAspiradoEArmazenado;
    }

    public void setCapacidadeParaCarregarLixo(int capacidadeParaCarregarLixo) {
        this.capacidadeParaCarregarLixo = capacidadeParaCarregarLixo;
    }

    public void setLixoAspiradoEArmazenado(int lixoAspiradoEArmazenado) {
        this.lixoAspiradoEArmazenado = lixoAspiradoEArmazenado;
    }

    public char getElementoObersvadoNaEsquerda() {
        return elementoObservadoNaEsquerda;
    }

    public char getElementoObersvadoNaDireita() {
        return elementoObservadoNaDireita;
    }

    public char getElementoObersvadoEmCima() {
        return elementoObservadoEmCima;
    }

    public char getElementoObersvadoEmBaixo() {
        return elementoObservadoEmBaixo;
    }

    public void setElementoObersvadoNaEsquerda(char elementoObersvadoNaEsquerda) {
        this.elementoObservadoNaEsquerda = elementoObersvadoNaEsquerda;
    }

    public void setElementoObersvadoNaDireita(char elementoObersvadoNaDireita) {
        this.elementoObservadoNaDireita = elementoObersvadoNaDireita;
    }

    public void setElementoObersvadoEmCima(char elementoObersvadoEmCima) {
        this.elementoObservadoEmCima = elementoObersvadoEmCima;
    }

    public void setElementoObersvadoEmBaixo(char elementoObersvadoEmBaixo) {
        this.elementoObservadoEmBaixo = elementoObersvadoEmBaixo;
    }

    public int getX() {
        return px;
    }

    public int getY() {
        return py;
    }

    public void setPx(int px) {
        this.px = px;
    }

    public void setPy(int py) {
        this.py = py;
    }

    public void moverParaEsquerda() {
        setPx(getX() - 1);
    }

    public void moverParaDireita() {
        setPx(getX() + 1);
    }

    public void moverParaCima() {
        setPy(getY() - 1);
    }

    public void moverParaBaixo() {
        setPy(getY() + 1);
    }

    /*  Algoritmo A* */
    private void aStar(int x, int y) {

    }

    public void recarregar() {

    }

    public void aspirar() {
        ambiente.setElementoDaPosicaoXY(this.getX(), this.getY(), LIMPO);
    }

    public void observarPosicoesAdjacentes() {

        if ((getX() - 1) < 0) {
            setElementoObersvadoNaEsquerda(NULO);
        } else {
            setElementoObersvadoNaEsquerda(ambiente.elementoDaPosicaoXY(getX() - 1, getY()));
        }
        if ((getX() + 1) >= ambiente.getTamanhoMatriz()) {
            setElementoObersvadoNaDireita(NULO);
        } else {
            setElementoObersvadoNaDireita(ambiente.elementoDaPosicaoXY(getX() + 1, getY()));
        }
        if ((getY() - 1) < 0) {
            setElementoObersvadoEmCima(NULO);
        } else {
            setElementoObersvadoEmCima(ambiente.elementoDaPosicaoXY(getX(), getY() - 1));
        }
        if ((getY() + 1) >= ambiente.getTamanhoMatriz()) {
            setElementoObersvadoEmBaixo(NULO);
        } else {
            setElementoObersvadoEmBaixo(ambiente.elementoDaPosicaoXY(getX(), getY() + 1));
        }
    }

    /* Atualiza estado do agente com base no valor das adjacencias
    > Requer: informacoes da celula atual e adjacentes
    0 1 2
    3 4 5
    6 7 8
     */
    public void atualizar(char atual, char esq, char dir, char cima, char baixo, int tamanho) {
        if (px == tamanho - 1 && py == tamanho - 1) {
            System.out.println("BYE BYE");
        }

        log(px, py, urgencia, direcao, lastColumn, lastLine);

        if (direcao == 'e') {
            if (urgencia) {
                if ((lastColumn != px && py + 1 == lastLine) && py + 1 == lastLine && baixo != Ambiente.RECARGA && baixo != Ambiente.LIXEIRA && baixo != Ambiente.PAREDE) {
                    py += 1;
                } else if (py + 1 < lastLine && px != lastColumn && baixo != Ambiente.RECARGA && baixo != Ambiente.LIXEIRA && baixo != Ambiente.PAREDE) {
                    py += 1;
                } else if (esq != Ambiente.NULO && esq != Ambiente.RECARGA && esq != Ambiente.LIXEIRA && esq != Ambiente.PAREDE) {
                    px -= 1;
                } else if (esq == Ambiente.NULO) {
                    direcao = 'e';
                } else if (esq == Ambiente.RECARGA || esq == Ambiente.LIXEIRA || esq == Ambiente.PAREDE) {
                    if (cima != Ambiente.RECARGA && cima != Ambiente.LIXEIRA && cima != Ambiente.PAREDE) {
                        py -= 1;
                        lastColumn = px;
                    } else if (dir != Ambiente.RECARGA && dir != Ambiente.LIXEIRA && dir != Ambiente.PAREDE) {
                        px += 1;
                        lastColumn = px;
                    } else {
                        py += 1;
                        direcao = 'd';
                    }
                }
                if (py == lastLine) {
                    urgencia = false;
                }
            } else {
                if (esq == Ambiente.NULO) {
                    if (baixo == Ambiente.NULO) {
                        System.out.println("FIM");
                    } else if (baixo != Ambiente.RECARGA && baixo != Ambiente.LIXEIRA && baixo != Ambiente.PAREDE) {
                        py += 1;
                        direcao = 'd';
                    } else {
                        lastColumn = px;
                        urgencia = true;
                        direcao = 'd';
                        lastLine = py + 1;
                    }
                } else if (esq != Ambiente.RECARGA && esq != Ambiente.LIXEIRA && baixo != Ambiente.PAREDE) {
                    px -= 1;
                } else {
                    lastColumn = px;
                    urgencia = true;
                    lastLine = py;
                }
            }
        } else if (direcao == 'd') {
            if (urgencia) {
                if ((lastColumn != px && py + 1 == lastLine) && py + 1 == lastLine && baixo != Ambiente.RECARGA && baixo != Ambiente.LIXEIRA && baixo != Ambiente.PAREDE) {
                    py += 1;
                } else if (py + 1 < lastLine && px != lastColumn && baixo != Ambiente.RECARGA && baixo != Ambiente.LIXEIRA && baixo != Ambiente.PAREDE) {
                    py += 1;
                } else if (dir != Ambiente.NULO && dir != Ambiente.RECARGA && dir != Ambiente.LIXEIRA && dir != Ambiente.PAREDE) {
                    px += 1;
                } else if (dir == Ambiente.NULO) {
                    direcao = 'e';
                } else if (dir == Ambiente.RECARGA || dir == Ambiente.LIXEIRA || dir == Ambiente.PAREDE) {
                    if (cima != Ambiente.RECARGA && cima != Ambiente.LIXEIRA && cima != Ambiente.PAREDE) {
                        py -= 1;
                        lastColumn = px;
                    } else if (esq != Ambiente.RECARGA && esq != Ambiente.LIXEIRA && esq != Ambiente.PAREDE) {
                        px -= 1;
                    } else {
                        py += 1;
                    }
                }
                if (py == lastLine) {
                    urgencia = false;
                }
            } else {
                if (dir == Ambiente.NULO) {
                    if (baixo == Ambiente.NULO) {
                        System.out.println("FIM");
                    } else if (baixo != Ambiente.RECARGA && baixo != Ambiente.LIXEIRA && baixo != Ambiente.PAREDE) {
                        py += 1;
                        direcao = 'e';
                    } else {
                        lastColumn = px;
                        urgencia = true;
                        direcao = 'e';
                        lastLine = py + 1;
                    }
                } else if (dir != Ambiente.RECARGA && dir != Ambiente.LIXEIRA && dir != Ambiente.PAREDE) {
                    px += 1;
                } else {
                    lastColumn = px;
                    urgencia = true;
                    lastLine = py;
                }
            }
        }
    }

    public void varreduraDeLinhasPelaEsquerda() {
        this.observarPosicoesAdjacentes();
        this.log(px, py, urgencia, direcao, lastColumn, lastLine);

        //Se estou indo para a direita
        if (direcao == 'd' && indo == true) {
            //Se esta é a última posição da linha
            if (this.getX() == ambiente.getTamanhoMatriz() - 1) {
                direcao = 'e';
                voltando = true;
                indo = false;
            } //Se não existe uma parede na direita, se move para a direita
            else if (elementoObservadoNaDireita != PAREDE) {
                this.moverParaDireita();
            } else if (elementoObservadoNaDireita == PAREDE) {
                direcao = 'e';
                voltando = true;
                indo = false;
            }
        } else if (direcao == 'e' && voltando == true) {
            //Se esta é a primeira posição da linha
            if (this.getX() == 0) {
                if (this.getX() == 0 && this.getY() == ambiente.getTamanhoMatriz() - 1) {
                    varreduraPorLinhaNaEsquerdaConcluida = true;
                    efetuarTransicaoParaDireita = true;
                    System.out.println("FINAL DA VARREDURA PELA ESQUERDA!");
                    return;
                } else {
                    this.moverParaBaixo();
                    direcao = 'd';
                    voltando = false;
                    indo = true;
                }
            } //Se não existe uma parede na esquerda, se move para a esquerda
            else if (elementoObservadoNaEsquerda != PAREDE) {
                this.moverParaEsquerda();
            } else if (elementoObservadoNaDireita == PAREDE) {
                direcao = 'd';
                voltando = false;
                indo = true;
            }
        }
    }

    public void varreduraDeLinhasPelaDireita() {
        this.observarPosicoesAdjacentes();
        this.log(px, py, urgencia, direcao, lastColumn, lastLine);

        //Se estou indo para a esquerda
        if (direcao == 'e' && indo == true) {
            //Se esta é a primeira posição da linha
            if (this.getX() == 0) {
                direcao = 'd';
                voltando = true;
                indo = false;
            } //Se não existe uma parede na esquerda, se move para a esquerda
            else if (elementoObservadoNaEsquerda != PAREDE) {
                this.moverParaEsquerda();
            } else if (elementoObservadoNaEsquerda == PAREDE) {
                direcao = 'd';
                voltando = true;
                indo = false;
            }
        } else if (direcao == 'd' && voltando == true) {
            //Se esta é a última posição da linha
            if (this.getX() == ambiente.getTamanhoMatriz() - 1) {
                if (this.getX() == ambiente.getTamanhoMatriz() - 1 && this.getY() == ambiente.getTamanhoMatriz() - 1) {
                    varreduraPorLinhaNaDireitaConcluida = true;
                    varrerPorColunas = true;
                    indo = true;
                    voltando = false;
                    direcao = 'c';
                    //efetuarTransicaoParaDireita = true;
                    System.out.println("FINAL DA VARREDURA PELA DIREITA!");
                    return;
                } else {
                    this.moverParaBaixo();
                    direcao = 'e';
                    voltando = false;
                    indo = true;
                }
            } //Se não existe uma parede na esquerda, se move para a esquerda
            else if (elementoObservadoNaDireita != PAREDE) {
                this.moverParaDireita();
            } else if (elementoObservadoNaDireita == PAREDE) {
                direcao = 'e';
                voltando = false;
                indo = true;
            }
        }
    }

    public void varreduraPorColunas() {
        this.observarPosicoesAdjacentes();
        this.log(px, py, urgencia, direcao, lastColumn, lastLine);

        //Se estou indo para cima
        if (direcao == 'c' && indo == true) {
            //Se esta é a última posição da coluna
            if (this.getY() == 0) {
                direcao = 'b';
                voltando = true;
                indo = false;
            } //Se não existe uma parede na direita, se move para a direita
            else if (elementoObservadoEmCima != PAREDE) {
                this.moverParaCima();
            } else if (elementoObservadoEmCima == PAREDE) {
                direcao = 'b';
                voltando = true;
                indo = false;
            }
        } else if (direcao == 'b' && voltando == true) {
            //Se esta é a primeira posição da coluna
            if (this.getY() == ambiente.getTamanhoMatriz() - 1) {
                if (this.getX() == 0 && this.getY() == ambiente.getTamanhoMatriz() - 1) {
                    //////////////venci
                    varreduraCompletamenteConcluida = true;
                    //varreduraPorLinhaNaEsquerdaConcluida = true;
                    //efetuarTransicaoParaDireita = true;
                    System.out.println("A VARREDURA FOI CONCLUIDA COM EXITO!");
                    return;
                } else {
                    this.moverParaEsquerda();
                    direcao = 'c';
                    voltando = false;
                    indo = true;
                }
            } //Se não existe uma parede na esquerda, se move para a esquerda
            else if (elementoObservadoEmBaixo != PAREDE) {
                this.moverParaBaixo();
            } else if (elementoObservadoEmBaixo == PAREDE) {
                direcao = 'c';
                voltando = false;
                indo = true;
            }
        }
    }

    public void transicaoParaVarreduraPelaDireita() {
        this.observarPosicoesAdjacentes();
        this.log(px, py, urgencia, direcao, lastColumn, lastLine);

        if (direcao != 'c') {
            if (this.getX() == ambiente.getTamanhoMatriz() - 1) {
                direcao = 'c';
            } else {
                this.moverParaDireita();
            }
        } else if (direcao == 'c') {
            if (this.getY() == 0) {
                efetuarTransicaoParaDireita = false;
                varreduraPorLinhaNaDireitaConcluida = false;
                indo = true;
                voltando = false;
                direcao = 'e';
                return;
            } else {
                this.moverParaCima();
            }
        }
    }

    public boolean atualizar2(char atual, char esq, char dir, char cima, char baixo, int tamanho) {
        if (ambiente.elementoDaPosicaoXY(this.getX(), this.getY()) == SUJEIRA) {
            if ((this.getLixoAspiradoEArmazenado() + 1) < this.getCapacidadeParaCarregarLixo()) {
                this.aspirar();
                this.setLixoAspiradoEArmazenado(this.getLixoAspiradoEArmazenado() + 1);
            }
        }

        if (varreduraPorLinhaNaEsquerdaConcluida == false) {
            varreduraDeLinhasPelaEsquerda();
        } else if (efetuarTransicaoParaDireita == true) {
            System.out.println("DEVO COMECAR A TRANSICAO!");
            transicaoParaVarreduraPelaDireita();
        } else if (varreduraPorLinhaNaDireitaConcluida == false) {
            System.out.println("DEVO VARRER PELA DIREITA!");
            varreduraDeLinhasPelaDireita();
        } else if (varrerPorColunas == true) {
            varreduraPorColunas();
        }

        return varreduraCompletamenteConcluida;
    }

    public void log(int px, int py, boolean urgencia, char direcao, int lastColumn, int lastLine) {
        System.out.println("------------------------------------------------");
        System.out.println("urgencia = " + urgencia);
        System.out.println("lixo coletado = " + this.getLixoAspiradoEArmazenado());
        System.out.println("direcao = " + direcao);
        System.out.println("indo = " + indo);
        System.out.println("voltando = " + voltando);
        System.out.println("px = " + px);
        System.out.println("py = " + py);
        System.out.println("elemento_esquerda = " + elementoObservadoNaEsquerda);
        System.out.println("elemento_direita = " + elementoObservadoNaDireita);
        System.out.println("elemento_acima = " + elementoObservadoEmCima);
        System.out.println("elemento_abaixo = " + elementoObservadoEmBaixo);
        System.out.println("lastColumn = " + lastColumn);
        System.out.println("lastLine = " + lastLine);
        System.out.println("------------------------------------------------");
    }
}
