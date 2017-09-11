
import java.util.ArrayList;

public class Agente {

    private char objetoExcecaoNaBuscaAtual;

    private Posicao lixeiraMaisProxima;
    private int distanciaDaLixeiraMaisProxima;
    private Posicao recargaMaisProxima;
    private int distanciaDaRecargaMaisProxima;

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
    private char elementoObservadoNaDiagonalEsquerdaSuperior;
    private char elementoObservadoNaDiagonalEsquerdaInferior;
    private char elementoObservadoNaDiagonalDireitaSuperior;
    private char elementoObservadoNaDiagonalDireitaInferior;

    public static final char NULO = 'N';
    public static final char SUJEIRA = 'S';
    public static final char LIXEIRA = 'L';
    public static final char RECARGA = 'R';
    public static final char PAREDE = 'P';
    public static final char LIMPO = '.';

    public static final char DIREITA = 'D';
    public static final char ESQUERDA = 'E';
    public static final char ACIMA = 'C';
    public static final char ABAIXO = 'B';
    public static final char DIAGONALESQUERDASUPERIOR = 'Q';
    public static final char DIAGONALESQUERDAINFERIOR = 'A';
    public static final char DIAGONALDIREITASUPERIOR = 'W';
    public static final char DIAGONALDIREITAINFERIOR = 'S';

    Agente agenteParaBACKUPDeDados;

    /* Construtor */
    public Agente(int px, int py, char direcao, int estado, Ambiente ambiente, int capacidadeLixo, int capacidadeEnergia) {

        lixeiraMaisProxima = new Posicao();
        distanciaDaLixeiraMaisProxima = 0;
        recargaMaisProxima = new Posicao();
        distanciaDaRecargaMaisProxima = 0;

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

        elementoObservadoNaPosicaoDoAgente = NULO;
        elementoObservadoNaEsquerdaDoAgente = NULO;
        elementoObservadoNaDireitaDoAgente = NULO;
        elementoObservadoEmCimaDoAgente = NULO;
        elementoObservadoEmBaixoDoAgente = NULO;
        elementoObservadoNaDiagonalEsquerdaSuperior = NULO;
        elementoObservadoNaDiagonalEsquerdaInferior = NULO;
        elementoObservadoNaDiagonalDireitaSuperior = NULO;
        elementoObservadoNaDiagonalDireitaInferior = NULO;

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
        direcao = DIREITA;
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

        elementoObservadoNaPosicaoDoAgente = NULO;
        elementoObservadoNaEsquerdaDoAgente = NULO;
        elementoObservadoNaDireitaDoAgente = NULO;
        elementoObservadoEmCimaDoAgente = NULO;
        elementoObservadoEmBaixoDoAgente = NULO;
    }

    public ArrayList<Character> aStarBuscandoLixeira(Posicao posicaoDoAgente, Posicao posicaoDoDestino) {
        objetoExcecaoNaBuscaAtual = LIXEIRA;

        return aStar(posicaoDoAgente, posicaoDoDestino);
    }

    public ArrayList<Character> aStarBuscandoRecarga(Posicao posicaoDoAgente, Posicao posicaoDoDestino) {
        objetoExcecaoNaBuscaAtual = RECARGA;

        return aStar(posicaoDoAgente, posicaoDoDestino);
    }

    public Posicao getLixeiraMaisProxima() {
        return lixeiraMaisProxima;
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

    public int solicitarCalculoDeHeuristicaAoAmbiente(Posicao posicaoOrigem, Posicao posicaoDestino) {
        return ambiente.distanciaManhattan(posicaoOrigem, posicaoDestino);
    }

    public void solicitarDadosDaLixeiraMaisProxima() {
        ambiente.lixeiraMaisProximaDoAgente(this);
    }

    public void solicitarDadosDaRecargaMaisProxima() {
        ambiente.recargaMaisProximaDoAgente(this);
    }

    public void atualizarDadosDaLixeiraMaisProxima(int coordenadaX, int coordenadaY, int distancia) {
        lixeiraMaisProxima.setX(coordenadaX);
        lixeiraMaisProxima.setY(coordenadaY);
        distanciaDaLixeiraMaisProxima = distancia;
    }

    public void atualizarDadosDaRecargaMaisProxima(int coordenadaX, int coordenadaY, int distancia) {
        recargaMaisProxima.setX(coordenadaX);
        recargaMaisProxima.setY(coordenadaY);
        distanciaDaRecargaMaisProxima = distancia;
    }

    public Posicao posicaoDoAgente() {
        return new Posicao(px, py);
    }

    public void setLixeiraMaisProxima(Posicao lixeiraMaisProxima) {
        this.lixeiraMaisProxima = lixeiraMaisProxima;
    }

    public int getDistanciaDaLixeiraMaisProxima() {
        return distanciaDaLixeiraMaisProxima;
    }

    public void setDistanciaDaLixeiraMaisProxima(int distanciaDaLixeiraMaisProxima) {
        this.distanciaDaLixeiraMaisProxima = distanciaDaLixeiraMaisProxima;
    }

    public Posicao getRecargaMaisProxima() {
        return recargaMaisProxima;
    }

    public void setRecargaMaisProxima(Posicao recargaMaisProxima) {
        this.recargaMaisProxima = recargaMaisProxima;
    }

    public int getDistanciaDaRecargaMaisProxima() {
        return distanciaDaRecargaMaisProxima;
    }

    public void setDistanciaDaRecargaMaisProxima(int distanciaDaRecargaMaisProxima) {
        this.distanciaDaRecargaMaisProxima = distanciaDaRecargaMaisProxima;
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

    public char getElementoObservadoNaDiagonalEsquerdaSuperior() {
        return elementoObservadoNaDiagonalEsquerdaSuperior;
    }

    public void setElementoObservadoNaDiagonalEsquerdaSuperior(char elementoObservadoNaDiagonalEsquerdaSuperior) {
        this.elementoObservadoNaDiagonalEsquerdaSuperior = elementoObservadoNaDiagonalEsquerdaSuperior;
    }

    public char getElementoObservadoNaDiagonalEsquerdaInferior() {
        return elementoObservadoNaDiagonalEsquerdaInferior;
    }

    public void setElementoObservadoNaDiagonalEsquerdaInferior(char elementoObservadoNaDiagonalEsquerdaInferior) {
        this.elementoObservadoNaDiagonalEsquerdaInferior = elementoObservadoNaDiagonalEsquerdaInferior;
    }

    public char getElementoObservadoNaDiagonalDireitaSuperior() {
        return elementoObservadoNaDiagonalDireitaSuperior;
    }

    public void setElementoObservadoNaDiagonalDireitaSuperior(char elementoObservadoNaDiagonalDireitaSuperior) {
        this.elementoObservadoNaDiagonalDireitaSuperior = elementoObservadoNaDiagonalDireitaSuperior;
    }

    public char getElementoObservadoNaDiagonalDireitaInferior() {
        return elementoObservadoNaDiagonalDireitaInferior;
    }

    public void setElementoObservadoNaDiagonalDireitaInferior(char elementoObservadoNaDiagonalDireitaInferior) {
        this.elementoObservadoNaDiagonalDireitaInferior = elementoObservadoNaDiagonalDireitaInferior;
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

    public void moverParaDiagonalSuperiorEsquerda() {
        setPx(getX() - 1);
        setPy(getY() - 1);
        this.consumirEnergia();
    }

    public void moveraParaDiagonalInferiorEsquerda() {
        setPx(getX() - 1);
        setPy(getY() + 1);
        this.consumirEnergia();
    }

    public void moverParaDireita() {
        setPx(getX() + 1);
        this.consumirEnergia();
    }

    public void moverParaDiagonalSuperiorDireita() {
        setPx(getX() + 1);
        setPy(getY() - 1);
        this.consumirEnergia();
    }

    public void moveraParaDiagonalInferiorDireita() {
        setPx(getX() + 1);
        setPy(getY() + 1);
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

    private boolean getMatrizReversaAStar(boolean matriz[][], int x, int y) {
        return matriz[y][x];
    }

    private void setTrueAtXYNaMatrizReversaAStar(boolean matriz[][], int x, int y) {
        matriz[y][x] = true;
    }

    /*  Algoritmo A* */
    public ArrayList<Character> aStar(Posicao posicaoDoAgente, Posicao posicaoDoDestino) {
        posicaoDoAgente = posicaoDoAgente.inverterPonto();
        posicaoDoDestino = posicaoDoDestino.inverterPonto();
        System.out.println("POSICAO ORIGEM = " + posicaoDoAgente.toString());
        System.out.println("POSICAO DESTINO = " + posicaoDoDestino.toString());

        int n = ambiente.getTamanhoMatriz();

        boolean[][] marked_matrix = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                marked_matrix[i][j] = false;
            }
        }

        FilaDePrioridades priority_queue = new FilaDePrioridades();

        //se a posicao inicial e igual a posicao final, encerra algoritmo        
        if (solicitarCalculoDeHeuristicaAoAmbiente(posicaoDoAgente, posicaoDoDestino) == 0) {
            //System.out.println("ACABEI ANTES DE COMECAR!");
            return new ArrayList<Character>();
        }

        //cria o nodo com a posicao inicial e o adiciona na fila de prioridades         
        Nodo start_node = new Nodo();
        start_node.setPosition(posicaoDoAgente.getPosicao());
        priority_queue.addToQueue(start_node);

        //System.out.println("NODO INICIAL= " + start_node.toString());
        //este procedimento encerra quando remove o nodo objetivo da fila, ou quando nao existem mais nodos na mesma
        while (priority_queue.getSize() > 0) {
            //remove o nodo de maior prioridade da fila
            Nodo node_removed = priority_queue.removeFromQueue();
            int node_remove_x = node_removed.getPositionX();
            int node_remove_y = node_removed.getPositionY();

            //se o retorno da heuristica para esse nodo for zero, entao o algoritmo ja pode encerrar
            if (solicitarCalculoDeHeuristicaAoAmbiente(node_removed.getPosition(), posicaoDoDestino) == 0) {
                return node_removed.getPath();
            }

            //verifica quais sao os sucessores do nodo removido da fila
            ArrayList<Sucessor> sucessores = calcularSucessores(node_removed.getPosition());
            //System.out.println("PONTO=" + node_removed.getPosition() + " SUCESSORES" + sucessores.toString());  

            //itera sobre os sucessores do nodo removido
            for (Sucessor sucessor : sucessores) {
                int sucessor_x = sucessor.getPosicao().getX();
                int sucessor_y = sucessor.getPosicao().getY();

                //executa o codigo a seguir somente se esta posicao do mapa ainda nao foi explorada
                if (getMatrizReversaAStar(marked_matrix, sucessor_x, sucessor_y) == false) {
                    //&& ambiente.elementoDaPosicaoXY(sucessor_x, sucessor_y) != LIXEIRA
                    //&& ambiente.elementoDaPosicaoXY(sucessor_x, sucessor_y) != RECARGA
                    //&& ambiente.elementoDaPosicaoXY(sucessor_x, sucessor_y) != PAREDE) {

                    //cria um nodo para essa posicao do mapa
                    Nodo sucessor_nodo = new Nodo();
                    sucessor_nodo.setPosition(new Posicao(sucessor_x, sucessor_y));

                    //determina a prioridade deste nodo, calculando g(n) + h(n)
                    sucessor_nodo.setDistance_until_here(sucessor_nodo.getDistance_until_here() + 1);
                    sucessor_nodo.setPriority(sucessor_nodo.getDistance_until_here()
                            + solicitarCalculoDeHeuristicaAoAmbiente(sucessor_nodo.getPosition(),
                                    posicaoDoDestino));

                    //armazena o caminho nodo
                    for (int i = 0; i < node_removed.getPath().size(); i++) {
                        sucessor_nodo.getPath().add(node_removed.getPath().get(i));
                    }
                    sucessor_nodo.getPath().add(sucessor.getDirecao());

                    //System.out.println("CAMINHO > " + sucessor_nodo.getPath().toString());
                    //armazena o numero de movimentos do nodo
                    sucessor_nodo.setNumber_of_moves(sucessor_nodo.getNumber_of_moves() + 1);

                    //adiciona o nodo na fila de prioridades
                    priority_queue.addToQueue(sucessor_nodo);

                    //se esta nao e a posicao objetivo, ela e marcada para que nao seja explorada novamente
                    if (solicitarCalculoDeHeuristicaAoAmbiente(sucessor.getPosicao(), posicaoDoDestino) != 0) {
                        setTrueAtXYNaMatrizReversaAStar(marked_matrix, sucessor_x, sucessor_y);
                    }
                }
            }
        }

        //System.out.println("VOLTEI NULO, POIS NAO ACHEI NINGUEM!!! =[");
        return null;
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
        if (((getX() - 1) < 0) || ((getY() - 1) < 0)) {
            setElementoObservadoNaDiagonalEsquerdaSuperior(NULO);
        } else {
            setElementoObservadoNaDiagonalEsquerdaSuperior(ambiente.elementoDaPosicaoXY(getX() - 1, getY() - 1));
        }
        if (((getX() - 1) < 0) || ((getY() + 1) >= ambiente.getTamanhoMatriz())) {
            setElementoObservadoNaDiagonalEsquerdaInferior(NULO);
        } else {
            setElementoObservadoNaDiagonalEsquerdaInferior(ambiente.elementoDaPosicaoXY(getX() - 1, getY() + 1));
        }
        if (((getX() + 1) >= ambiente.getTamanhoMatriz()) || ((getY() - 1) < 0)) {
            setElementoObservadoNaDiagonalDireitaSuperior(NULO);
        } else {
            setElementoObservadoNaDiagonalDireitaSuperior(ambiente.elementoDaPosicaoXY(getX() + 1, getY() - 1));
        }
        if (((getX() + 1) >= ambiente.getTamanhoMatriz()) || ((getY() + 1) >= ambiente.getTamanhoMatriz())) {
            setElementoObservadoNaDiagonalDireitaInferior(NULO);
        } else {
            setElementoObservadoNaDiagonalDireitaInferior(ambiente.elementoDaPosicaoXY(getX() + 1, getY() + 1));
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

        if (direcao == ESQUERDA) {
            if (desviandoDeObjetos) {
                if ((lastColumn != px && py + 1 == lastLine) && py + 1 == lastLine && baixo != Ambiente.RECARGA && baixo != Ambiente.LIXEIRA && baixo != Ambiente.PAREDE) {
                    py += 1;
                } else if (py + 1 < lastLine && px != lastColumn && baixo != Ambiente.RECARGA && baixo != Ambiente.LIXEIRA && baixo != Ambiente.PAREDE) {
                    py += 1;
                } else if (esq != Ambiente.NULO && esq != Ambiente.RECARGA && esq != Ambiente.LIXEIRA && esq != Ambiente.PAREDE) {
                    px -= 1;
                } else if (esq == Ambiente.NULO) {
                    direcao = ESQUERDA;
                } else if (esq == Ambiente.RECARGA || esq == Ambiente.LIXEIRA || esq == Ambiente.PAREDE) {
                    if (cima != Ambiente.RECARGA && cima != Ambiente.LIXEIRA && cima != Ambiente.PAREDE) {
                        py -= 1;
                        lastColumn = px;
                    } else if (dir != Ambiente.RECARGA && dir != Ambiente.LIXEIRA && dir != Ambiente.PAREDE) {
                        px += 1;
                        lastColumn = px;
                    } else {
                        py += 1;
                        direcao = DIREITA;
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
                        direcao = DIREITA;
                    } else {
                        lastColumn = px;
                        desviandoDeObjetos = true;
                        direcao = DIREITA;
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
        } else if (direcao == DIREITA) {
            if (desviandoDeObjetos) {
                if ((lastColumn != px && py + 1 == lastLine) && py + 1 == lastLine && baixo != Ambiente.RECARGA && baixo != Ambiente.LIXEIRA && baixo != Ambiente.PAREDE) {
                    py += 1;
                } else if (py + 1 < lastLine && px != lastColumn && baixo != Ambiente.RECARGA && baixo != Ambiente.LIXEIRA && baixo != Ambiente.PAREDE) {
                    py += 1;
                } else if (dir != Ambiente.NULO && dir != Ambiente.RECARGA && dir != Ambiente.LIXEIRA && dir != Ambiente.PAREDE) {
                    px += 1;
                } else if (dir == Ambiente.NULO) {
                    direcao = ESQUERDA;
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
                        direcao = ESQUERDA;
                    } else {
                        lastColumn = px;
                        desviandoDeObjetos = true;
                        direcao = ESQUERDA;
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

        //Se estou indo para a direita DIREITA
        if (direcao == DIREITA && indo == true) {
            //Se esta é a última posição da linha
            if (this.getX() == ambiente.getTamanhoMatriz() - 1) {
                direcao = ESQUERDA;
                voltando = true;
                indo = false;
            } //Se não existe uma parede na direita, se move para a direita
            else if (elementoObservadoNaDireitaDoAgente != PAREDE) {
                this.moverParaDireita();
            } else if (elementoObservadoNaDireitaDoAgente == PAREDE) {
                direcao = ESQUERDA;
                voltando = true;
                indo = false;
            }
        } else if (direcao == ESQUERDA && voltando == true) {
            //Se esta é a primeira posição da linha
            if (this.getX() == 0) {
                if (this.getX() == 0 && this.getY() == ambiente.getTamanhoMatriz() - 1) {
                    varreduraPorLinhaNaEsquerdaConcluida = true;
                    efetuarTransicao = true;
                    System.out.println("FINAL DA VARREDURA PELA ESQUERDA!");
                    //return;
                } else {
                    this.moverParaBaixo();
                    direcao = DIREITA;
                    voltando = false;
                    indo = true;
                }
            } //Se não existe uma parede na esquerda, se move para a esquerda
            else if (elementoObservadoNaEsquerdaDoAgente != PAREDE) {
                this.moverParaEsquerda();
            } else if (elementoObservadoNaDireitaDoAgente == PAREDE) {
                direcao = DIREITA;
                voltando = false;
                indo = true;
            }
        }
    }

    public void varreduraDeLinhasPelaDireita() {
        this.observarAmbiente();
        this.log();

        //Se estou indo para a esquerda
        if (direcao == ESQUERDA && indo == true) {
            //Se esta é a primeira posição da linha
            if (this.getX() == 0) {
                direcao = DIREITA;
                voltando = true;
                indo = false;
            } //Se não existe uma parede na esquerda, se move para a esquerda
            else if (elementoObservadoNaEsquerdaDoAgente != PAREDE) {
                this.moverParaEsquerda();
            } else if (elementoObservadoNaEsquerdaDoAgente == PAREDE) {
                direcao = DIREITA;
                voltando = true;
                indo = false;
            }
        } else if (direcao == DIREITA && voltando == true) {
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
                    direcao = ESQUERDA;
                    voltando = false;
                    indo = true;
                }
            } //Se não existe uma parede na esquerda, se move para a esquerda
            else if (elementoObservadoNaDireitaDoAgente != PAREDE) {
                this.moverParaDireita();
            } else if (elementoObservadoNaDireitaDoAgente == PAREDE) {
                direcao = ESQUERDA;
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
                direcao = ESQUERDA;
                //return;
            } else {
                this.moverParaCima();
            }
        }
    }

    public boolean atualizar2() {
        solicitarDadosDaLixeiraMaisProxima();
        solicitarDadosDaRecargaMaisProxima();

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
        System.out.println("posicao da recarga mais proxima = " + recargaMaisProxima.toString());
        System.out.println("distancia da recarga mais proxima = " + distanciaDaRecargaMaisProxima);
        System.out.println("energia restante = " + this.getEnergiaRestante());
        System.out.println("posicao da lixeira mais proxima = " + lixeiraMaisProxima.toString());
        System.out.println("distancia da lixeira mais proxima = " + distanciaDaLixeiraMaisProxima);
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

    public ArrayList<Sucessor> calcularSucessores(Posicao posicaoAlvo) {
        ArrayList<Sucessor> sucessores = new ArrayList<Sucessor>();

        //ESQUERDA
        if (!((posicaoAlvo.getX() - 1) < 0)) {
            int posX = posicaoAlvo.getX() - 1;
            int posY = posicaoAlvo.getY();

            char elemento = ambiente.elementoDaPosicaoXY(posX, posY);

            if (elemento == LIMPO || elemento == SUJEIRA || elemento == objetoExcecaoNaBuscaAtual) {
                Sucessor sucessorAux = new Sucessor();
                sucessorAux.setDirecao(ESQUERDA);
                Posicao posicaoAux = new Posicao(posX, posY);
                sucessorAux.setPosicao(posicaoAux.getPosicao());
                sucessores.add(sucessorAux);
            }
        }

        //DIREITA
        if (!((posicaoAlvo.getX() + 1) >= ambiente.getTamanhoMatriz())) {
            int posX = posicaoAlvo.getX() + 1;
            int posY = posicaoAlvo.getY();

            char elemento = ambiente.elementoDaPosicaoXY(posX, posY);

            if (elemento == LIMPO || elemento == SUJEIRA || elemento == objetoExcecaoNaBuscaAtual) {
                Sucessor sucessorAux = new Sucessor();
                sucessorAux.setDirecao(DIREITA);
                Posicao posicaoAux = new Posicao(posX, posY);
                sucessorAux.setPosicao(posicaoAux.getPosicao());
                sucessores.add(sucessorAux);
            }
        }

        //ACIMA
        if (!((posicaoAlvo.getY() - 1) < 0)) {
            int posX = posicaoAlvo.getX();
            int posY = posicaoAlvo.getY() - 1;

            char elemento = ambiente.elementoDaPosicaoXY(posX, posY);

            if (elemento == LIMPO || elemento == SUJEIRA || elemento == objetoExcecaoNaBuscaAtual) {
                Sucessor sucessorAux = new Sucessor();
                sucessorAux.setDirecao(ACIMA);
                Posicao posicaoAux = new Posicao(posX, posY);
                sucessorAux.setPosicao(posicaoAux.getPosicao());
                sucessores.add(sucessorAux);
            }
        }

        //ABAIXO
        if (!((posicaoAlvo.getY() + 1) >= ambiente.getTamanhoMatriz())) {
            int posX = posicaoAlvo.getX();
            int posY = posicaoAlvo.getY() + 1;

            char elemento = ambiente.elementoDaPosicaoXY(posX, posY);

            if (elemento == LIMPO || elemento == SUJEIRA || elemento == objetoExcecaoNaBuscaAtual) {
                Sucessor sucessorAux = new Sucessor();
                sucessorAux.setDirecao(ABAIXO);
                Posicao posicaoAux = new Posicao(posX, posY);
                sucessorAux.setPosicao(posicaoAux.getPosicao());
                sucessores.add(sucessorAux);
            }
        }

        //DIAGONAL ESQUERDA SUPERIOR
        if (!(((posicaoAlvo.getX() - 1) < 0) || ((posicaoAlvo.getY() - 1) < 0))) {
            int posX = posicaoAlvo.getX() - 1;
            int posY = posicaoAlvo.getY() - 1;

            char elemento = ambiente.elementoDaPosicaoXY(posX, posY);

            if (elemento == LIMPO || elemento == SUJEIRA || elemento == objetoExcecaoNaBuscaAtual) {
                Sucessor sucessorAux = new Sucessor();
                sucessorAux.setDirecao(DIAGONALESQUERDASUPERIOR);
                Posicao posicaoAux = new Posicao(posX, posY);
                sucessorAux.setPosicao(posicaoAux.getPosicao());
                sucessores.add(sucessorAux);
            }
        }

        //DIAGONAL ESQUERDA INFERIOR
        if (!(((posicaoAlvo.getX() - 1) < 0) || ((posicaoAlvo.getY() + 1) >= ambiente.getTamanhoMatriz()))) {
            int posX = posicaoAlvo.getX() - 1;
            int posY = posicaoAlvo.getY() + 1;

            char elemento = ambiente.elementoDaPosicaoXY(posX, posY);

            if (elemento == LIMPO || elemento == SUJEIRA || elemento == objetoExcecaoNaBuscaAtual) {
                Sucessor sucessorAux = new Sucessor();
                sucessorAux.setDirecao(DIAGONALESQUERDAINFERIOR);
                Posicao posicaoAux = new Posicao(posX, posY);
                sucessorAux.setPosicao(posicaoAux.getPosicao());
                sucessores.add(sucessorAux);
            }
        }

        //DIAGONAL DIREITA SUPERIOR
        if (!(((posicaoAlvo.getX() + 1) >= ambiente.getTamanhoMatriz()) || ((posicaoAlvo.getY() - 1) < 0))) {
            int posX = posicaoAlvo.getX() + 1;
            int posY = posicaoAlvo.getY() - 1;

            char elemento = ambiente.elementoDaPosicaoXY(posX, posY);

            if (elemento == LIMPO || elemento == SUJEIRA || elemento == objetoExcecaoNaBuscaAtual) {
                Sucessor sucessorAux = new Sucessor();
                sucessorAux.setDirecao(DIAGONALDIREITASUPERIOR);
                Posicao posicaoAux = new Posicao(posX, posY);
                sucessorAux.setPosicao(posicaoAux.getPosicao());
                sucessores.add(sucessorAux);
            }
        }

        //DIAGONAL DIREITA INFERIOR
        if (!(((posicaoAlvo.getX() + 1) >= ambiente.getTamanhoMatriz()) || ((posicaoAlvo.getY() + 1) >= ambiente.getTamanhoMatriz()))) {
            int posX = posicaoAlvo.getX() + 1;
            int posY = posicaoAlvo.getY() + 1;

            char elemento = ambiente.elementoDaPosicaoXY(posX, posY);

            if (elemento == LIMPO || elemento == SUJEIRA || elemento == objetoExcecaoNaBuscaAtual) {
                Sucessor sucessorAux = new Sucessor();
                sucessorAux.setDirecao(DIAGONALDIREITAINFERIOR);
                Posicao posicaoAux = new Posicao(posX, posY);
                sucessorAux.setPosicao(posicaoAux.getPosicao());
                sucessores.add(sucessorAux);
            }
        }

        return sucessores;
    }

    public class Sucessor {

        private Posicao posicao;
        private char direcao;

        public Sucessor() {
            posicao = new Posicao();
            direcao = NULO;
        }

        public Posicao getPosicao() {
            return posicao;
        }

        public void setPosicao(Posicao posicao) {
            this.posicao = posicao;
        }

        public char getDirecao() {
            return direcao;
        }

        public void setDirecao(char direcao) {
            this.direcao = direcao;
        }

        public String toString() {
            return direcao + posicao.toString();
        }
    }
}
