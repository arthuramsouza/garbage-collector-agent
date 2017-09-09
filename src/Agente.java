
public class Agente {

    int capacidadeTotalDeEnergia;
    int energiaRestante;
    int capacidadeTotalDeLixo;
    int lixoColetado;

    private int px, py; // Posicao do agente
    private char direcao; // Direcao de varredura {d, e}
    private int estado;
    private int lastLine;
    private int lastColumn;

    private boolean desviandoDeObjetos;
    private boolean indo;
    private boolean voltando;
    private boolean efetuarTransicao;
    private boolean varreduraPorLinhaNaEsquerdaConcluida;
    private boolean varreduraPorLinhaNaDireitaConcluida;
    private boolean varrerPorColunas;
    private boolean varreduraCompletamenteConcluida;

    private Ambiente ambiente;

    private char elementoObservadoNaPosicaoDoAgente;
    private char elementoObservadoNaEsquerdaDoAgente;
    private char elementoObservadoNaDireitaDoAgente;
    private char elementoObservadoEmCimaDoAgente;
    private char elementoObservadoEmBaixoDoAgente;

    public static final char NULO = 'N';
    public static final char SUJEIRA = 'S';
    public static final char LIXEIRA = 'L';
    public static final char RECARGA = 'R';
    public static final char PAREDE = 'P';
    public static final char LIMPO = '.';

    Agente agenteParaBACKUPDeDados;

    /* Construtor */
    public Agente(int px, int py, char direcao, int estado, Ambiente ambiente, int capacidadeLixo, int capacidadeEnergia) {

        capacidadeTotalDeEnergia = capacidadeEnergia;
        energiaRestante = capacidadeTotalDeEnergia;
        capacidadeTotalDeLixo = capacidadeLixo;
        lixoColetado = 0;

        this.px = px;
        this.py = py;
        this.direcao = direcao;
        this.estado = estado;
        lastLine = 0;
        lastColumn = 0;

        this.desviandoDeObjetos = false;
        indo = true;
        voltando = false;
        efetuarTransicao = false;
        varreduraPorLinhaNaEsquerdaConcluida = false;
        varreduraPorLinhaNaDireitaConcluida = false;
        varrerPorColunas = false;
        varreduraCompletamenteConcluida = false;

        this.ambiente = ambiente;

        elementoObservadoNaPosicaoDoAgente = 'N';
        elementoObservadoNaEsquerdaDoAgente = 'N';
        elementoObservadoNaDireitaDoAgente = 'N';
        elementoObservadoEmCimaDoAgente = 'N';
        elementoObservadoEmBaixoDoAgente = 'N';

        agenteParaBACKUPDeDados = new Agente();
    }

    /* Este construtor deve ser usado apenas para o agenteParaBACKUPDeDados!!! */
    public Agente() {

        capacidadeTotalDeEnergia = 100;
        energiaRestante = 100;
        capacidadeTotalDeLixo = 10;
        lixoColetado = 0;

        px = 0;
        py = 0;
        direcao = 'd';
        estado = 0;
        lastLine = 0;
        lastColumn = 0;

        this.desviandoDeObjetos = false;
        indo = true;
        voltando = false;
        efetuarTransicao = false;
        varreduraPorLinhaNaEsquerdaConcluida = false;
        varreduraPorLinhaNaDireitaConcluida = false;
        varrerPorColunas = false;
        varreduraCompletamenteConcluida = false;

        elementoObservadoNaPosicaoDoAgente = 'N';
        elementoObservadoNaEsquerdaDoAgente = 'N';
        elementoObservadoNaDireitaDoAgente = 'N';
        elementoObservadoEmCimaDoAgente = 'N';
        elementoObservadoEmBaixoDoAgente = 'N';
    }

    public void salvarInformacoesDoAgente() {
        agenteParaBACKUPDeDados.px = this.px;
        agenteParaBACKUPDeDados.py = this.py;
        agenteParaBACKUPDeDados.direcao = this.direcao;
        agenteParaBACKUPDeDados.estado = this.estado;
        agenteParaBACKUPDeDados.lastLine = this.lastLine;
        agenteParaBACKUPDeDados.lastColumn = this.lastColumn;
        agenteParaBACKUPDeDados.desviandoDeObjetos = this.desviandoDeObjetos;
        agenteParaBACKUPDeDados.indo = this.indo;
        agenteParaBACKUPDeDados.voltando = this.voltando;
        agenteParaBACKUPDeDados.efetuarTransicao = this.efetuarTransicao;
        agenteParaBACKUPDeDados.varreduraPorLinhaNaEsquerdaConcluida = this.varreduraPorLinhaNaEsquerdaConcluida;
        agenteParaBACKUPDeDados.varreduraPorLinhaNaDireitaConcluida = this.varreduraPorLinhaNaDireitaConcluida;
        agenteParaBACKUPDeDados.varrerPorColunas = this.varrerPorColunas;
        agenteParaBACKUPDeDados.varreduraCompletamenteConcluida = this.varreduraCompletamenteConcluida;
    }

    public void restaurarInformacoesDoAgente() {
        this.px = agenteParaBACKUPDeDados.px;
        this.py = agenteParaBACKUPDeDados.py;
        this.direcao = agenteParaBACKUPDeDados.direcao;
        this.estado = agenteParaBACKUPDeDados.estado;
        this.lastLine = agenteParaBACKUPDeDados.lastLine;
        this.lastColumn = agenteParaBACKUPDeDados.lastColumn;
        this.desviandoDeObjetos = agenteParaBACKUPDeDados.desviandoDeObjetos;
        this.indo = agenteParaBACKUPDeDados.indo;
        this.voltando = agenteParaBACKUPDeDados.voltando;
        this.efetuarTransicao = agenteParaBACKUPDeDados.efetuarTransicao;
        this.varreduraPorLinhaNaEsquerdaConcluida = agenteParaBACKUPDeDados.varreduraPorLinhaNaEsquerdaConcluida;
        this.varreduraPorLinhaNaDireitaConcluida = agenteParaBACKUPDeDados.varreduraPorLinhaNaDireitaConcluida;
        this.varrerPorColunas = agenteParaBACKUPDeDados.varrerPorColunas;
        this.varreduraCompletamenteConcluida = agenteParaBACKUPDeDados.varreduraCompletamenteConcluida;
    }

    public int getCapacidadeTotalDeEnergia() {
        return capacidadeTotalDeEnergia;
    }

    public void setCapacidadeTotalDeEnergia(int capacidadeTotalDeEnergia) {
        this.capacidadeTotalDeEnergia = capacidadeTotalDeEnergia;
    }

    public int getEnergiaRestante() {
        return energiaRestante;
    }

    public void setEnergiaRestante(int energiaRestante) {
        this.energiaRestante = energiaRestante;
    }

    public int getCapacidadeTotalDeLixo() {
        return capacidadeTotalDeLixo;
    }

    public void setCapacidadeTotalDeLixo(int capacidadeTotalDeLixo) {
        this.capacidadeTotalDeLixo = capacidadeTotalDeLixo;
    }

    public int getLixoColetado() {
        return lixoColetado;
    }

    public void setLixoColetado(int lixoColetado) {
        this.lixoColetado = lixoColetado;
    }

    public char getElementoObservadoNaPosicaoDoAgente() {
        return elementoObservadoNaPosicaoDoAgente;
    }

    public void setElementoObservadoNaPosicaoDoAgente(char elementoObservadoNaPosicaoDoAgente) {
        this.elementoObservadoNaPosicaoDoAgente = elementoObservadoNaPosicaoDoAgente;
    }

    public char getElementoObersvadoNaEsquerda() {
        return elementoObservadoNaEsquerdaDoAgente;
    }

    public void setElementoObersvadoNaEsquerda(char elementoObersvadoNaEsquerda) {
        this.elementoObservadoNaEsquerdaDoAgente = elementoObersvadoNaEsquerda;
    }

    public char getElementoObersvadoNaDireita() {
        return elementoObservadoNaDireitaDoAgente;
    }

    public void setElementoObersvadoNaDireita(char elementoObersvadoNaDireita) {
        this.elementoObservadoNaDireitaDoAgente = elementoObersvadoNaDireita;
    }

    public char getElementoObersvadoEmCima() {
        return elementoObservadoEmCimaDoAgente;
    }

    public void setElementoObersvadoEmCima(char elementoObersvadoEmCima) {
        this.elementoObservadoEmCimaDoAgente = elementoObersvadoEmCima;
    }

    public char getElementoObersvadoEmBaixo() {
        return elementoObservadoEmBaixoDoAgente;
    }

    public void setElementoObersvadoEmBaixo(char elementoObersvadoEmBaixo) {
        this.elementoObservadoEmBaixoDoAgente = elementoObersvadoEmBaixo;
    }

    public int getX() {
        return px;
    }

    public void setPx(int px) {
        this.px = px;
    }

    public int getY() {
        return py;
    }

    public void setPy(int py) {
        this.py = py;
    }

    public void moverParaEsquerda() {
        setPx(getX() - 1);
        this.consumirEnergia();
    }

    public void moverParaDireita() {
        setPx(getX() + 1);
        this.consumirEnergia();
    }

    public void moverParaCima() {
        setPy(getY() - 1);
        this.consumirEnergia();
    }

    public void moverParaBaixo() {
        setPy(getY() + 1);
        this.consumirEnergia();
    }

    public boolean existeSujeiraNaPosicaoDoAgente() {
        this.observarMinhaPosicao();

        return elementoObservadoNaPosicaoDoAgente == SUJEIRA;
    }

    public void observarMinhaPosicao() {
        elementoObservadoNaPosicaoDoAgente = ambiente.elementoDaPosicaoXY(this.getX(), this.getY());
    }

    public boolean possoColetarMaisLixo() {
        return ((this.getLixoColetado() + 1) < this.getCapacidadeTotalDeLixo());
    }

    public void consumirEnergia() {
        this.setEnergiaRestante(this.getEnergiaRestante() - 1);
    }

    /*  Algoritmo A* */
    private void aStar(int x, int y) {

    }

    public void recarregar() {

    }

    public void aspirar() {
        ambiente.setElementoDaPosicaoXY(this.getX(), this.getY(), LIMPO);
        this.setLixoColetado(this.getLixoColetado() + 1);
        this.consumirEnergia();
    }

    public void observarAmbiente() {
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

        log();

        if (direcao == 'e') {
            if (desviandoDeObjetos) {
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
                    desviandoDeObjetos = false;
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
                        desviandoDeObjetos = true;
                        direcao = 'd';
                        lastLine = py + 1;
                    }
                } else if (esq != Ambiente.RECARGA && esq != Ambiente.LIXEIRA && baixo != Ambiente.PAREDE) {
                    px -= 1;
                } else {
                    lastColumn = px;
                    desviandoDeObjetos = true;
                    lastLine = py;
                }
            }
        } else if (direcao == 'd') {
            if (desviandoDeObjetos) {
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
                    desviandoDeObjetos = false;
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
                        desviandoDeObjetos = true;
                        direcao = 'e';
                        lastLine = py + 1;
                    }
                } else if (dir != Ambiente.RECARGA && dir != Ambiente.LIXEIRA && dir != Ambiente.PAREDE) {
                    px += 1;
                } else {
                    lastColumn = px;
                    desviandoDeObjetos = true;
                    lastLine = py;
                }
            }
        }
    }

    public void varreduraDeLinhasPelaEsquerda() {
        this.observarAmbiente();
        this.log();

        //Se estou indo para a direita
        if (direcao == 'd' && indo == true) {
            //Se esta é a última posição da linha
            if (this.getX() == ambiente.getTamanhoMatriz() - 1) {
                direcao = 'e';
                voltando = true;
                indo = false;
            } //Se não existe uma parede na direita, se move para a direita
            else if (elementoObservadoNaDireitaDoAgente != PAREDE) {
                this.moverParaDireita();
            } else if (elementoObservadoNaDireitaDoAgente == PAREDE) {
                direcao = 'e';
                voltando = true;
                indo = false;
            }
        } else if (direcao == 'e' && voltando == true) {
            //Se esta é a primeira posição da linha
            if (this.getX() == 0) {
                if (this.getX() == 0 && this.getY() == ambiente.getTamanhoMatriz() - 1) {
                    varreduraPorLinhaNaEsquerdaConcluida = true;
                    efetuarTransicao = true;
                    System.out.println("FINAL DA VARREDURA PELA ESQUERDA!");
                    //return;
                } else {
                    this.moverParaBaixo();
                    direcao = 'd';
                    voltando = false;
                    indo = true;
                }
            } //Se não existe uma parede na esquerda, se move para a esquerda
            else if (elementoObservadoNaEsquerdaDoAgente != PAREDE) {
                this.moverParaEsquerda();
            } else if (elementoObservadoNaDireitaDoAgente == PAREDE) {
                direcao = 'd';
                voltando = false;
                indo = true;
            }
        }
    }

    public void varreduraDeLinhasPelaDireita() {
        this.observarAmbiente();
        this.log();

        //Se estou indo para a esquerda
        if (direcao == 'e' && indo == true) {
            //Se esta é a primeira posição da linha
            if (this.getX() == 0) {
                direcao = 'd';
                voltando = true;
                indo = false;
            } //Se não existe uma parede na esquerda, se move para a esquerda
            else if (elementoObservadoNaEsquerdaDoAgente != PAREDE) {
                this.moverParaEsquerda();
            } else if (elementoObservadoNaEsquerdaDoAgente == PAREDE) {
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
                    //efetuarTransicao = true;
                    System.out.println("FINAL DA VARREDURA PELA DIREITA!");
                    //return;
                } else {
                    this.moverParaBaixo();
                    direcao = 'e';
                    voltando = false;
                    indo = true;
                }
            } //Se não existe uma parede na esquerda, se move para a esquerda
            else if (elementoObservadoNaDireitaDoAgente != PAREDE) {
                this.moverParaDireita();
            } else if (elementoObservadoNaDireitaDoAgente == PAREDE) {
                direcao = 'e';
                voltando = false;
                indo = true;
            }
        }
    }

    public void varreduraPorColunas() {
        this.observarAmbiente();
        this.log();

        //Se estou indo para cima
        if (direcao == 'c' && indo == true) {
            //Se esta é a última posição da coluna
            if (this.getY() == 0) {
                direcao = 'b';
                voltando = true;
                indo = false;
            } //Se não existe uma parede na direita, se move para a direita
            else if (elementoObservadoEmCimaDoAgente != PAREDE) {
                this.moverParaCima();
            } else if (elementoObservadoEmCimaDoAgente == PAREDE) {
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
                    //efetuarTransicao = true;
                    System.out.println("A VARREDURA FOI CONCLUIDA COM EXITO!");
                    //return;
                } else {
                    this.moverParaEsquerda();
                    direcao = 'c';
                    voltando = false;
                    indo = true;
                }
            } //Se não existe uma parede na esquerda, se move para a esquerda
            else if (elementoObservadoEmBaixoDoAgente != PAREDE) {
                this.moverParaBaixo();
            } else if (elementoObservadoEmBaixoDoAgente == PAREDE) {
                direcao = 'c';
                voltando = false;
                indo = true;
            }
        }
    }

    public void transicaoParaVarreduraPelaDireita() {
        this.observarAmbiente();
        this.log();

        if (direcao != 'c') {
            if (this.getX() == ambiente.getTamanhoMatriz() - 1) {
                direcao = 'c';
            } else {
                this.moverParaDireita();
            }
        } else if (direcao == 'c') {
            if (this.getY() == 0) {
                efetuarTransicao = false;
                varreduraPorLinhaNaDireitaConcluida = false;
                indo = true;
                voltando = false;
                direcao = 'e';
                //return;
            } else {
                this.moverParaCima();
            }
        }
    }

    public boolean atualizar2() {
        if (existeSujeiraNaPosicaoDoAgente()) {
            if (possoColetarMaisLixo()) {
                this.aspirar();
            }
        }

        if (varreduraPorLinhaNaEsquerdaConcluida == false) {
            varreduraDeLinhasPelaEsquerda();
        } else if (efetuarTransicao == true) {
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

    public void log() {
        System.out.println("------------------------------------------------");
        System.out.println("energia restante = " + this.getEnergiaRestante());
        System.out.println("lixo coletado = " + this.getLixoColetado());
        System.out.println("direcao = " + direcao);
        System.out.println("indo = " + indo);
        System.out.println("voltando = " + voltando);
        System.out.println("desviandoDeObjetos = " + desviandoDeObjetos);
        System.out.println("posicao = [ " + px + " , " + py + " ]");
        System.out.println("elemento_esquerda = " + elementoObservadoNaEsquerdaDoAgente);
        System.out.println("elemento_direita = " + elementoObservadoNaDireitaDoAgente);
        System.out.println("elemento_acima = " + elementoObservadoEmCimaDoAgente);
        System.out.println("elemento_abaixo = " + elementoObservadoEmBaixoDoAgente);
        System.out.println("------------------------------------------------");
    }
}
