
import java.util.ArrayList;

/**
 * ORIENTACAO DO AMBIENTE: MATRIZ[LIINHA][COLUNA]
 */
public class Agente {

    private boolean modoAEstrelaLixeiraAtivado;
    private boolean modoAEstrelaRecargaAtivado;
    private boolean modoAEstrelaAtivado;
    private int iteradorCaminhoAEstrela;
    private boolean aEstrelaIndo;
    private boolean aEstrelaVoltando;
    private ArrayList<Character> caminhoAEstrelaIndo;
    private ArrayList<Character> caminhoAEstrelaVoltando;

    private char objetoExcecaoNaBuscaAtual;

    private Posicao lixeiraMaisProxima;
    private int distanciaDaLixeiraMaisProxima;
    private Posicao recargaMaisProxima;
    private int distanciaDaRecargaMaisProxima;

    int capacidadeTotalDeEnergia;
    int energiaRestante;
    int capacidadeTotalDeLixo;
    int lixoColetado;

    private int posicaoLinha, posicaoColuna; // Posicao do agente
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
    public Agente(int posicaoLinha, int posicaoColuna, char direcao, int estado, Ambiente ambiente, int capacidadeLixo, int capacidadeEnergia) {
        modoAEstrelaLixeiraAtivado = false;
        modoAEstrelaRecargaAtivado = false;
        modoAEstrelaAtivado = false;
        iteradorCaminhoAEstrela = 0;
        aEstrelaIndo = false;
        aEstrelaVoltando = false;
        caminhoAEstrelaIndo = new ArrayList<Character>();
        caminhoAEstrelaVoltando = new ArrayList<Character>();

        lixeiraMaisProxima = new Posicao();
        distanciaDaLixeiraMaisProxima = 0;
        recargaMaisProxima = new Posicao();
        distanciaDaRecargaMaisProxima = 0;

        capacidadeTotalDeEnergia = capacidadeEnergia;
        energiaRestante = capacidadeTotalDeEnergia;
        capacidadeTotalDeLixo = capacidadeLixo;
        lixoColetado = 0;

        this.posicaoLinha = posicaoLinha;
        this.posicaoColuna = posicaoColuna;
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

        posicaoLinha = 0;
        posicaoColuna = 0;
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

    public int getPosicaoLinha() {
        return posicaoLinha;
    }

    public void setPosicaoLinha(int posicaoLinha) {
        this.posicaoLinha = posicaoLinha;
    }

    public int getPosicaoColuna() {
        return posicaoColuna;
    }

    public void setPosicaoColuna(int posicaoColuna) {
        this.posicaoColuna = posicaoColuna;
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
        agenteParaBACKUPDeDados.posicaoLinha = this.posicaoLinha;
        agenteParaBACKUPDeDados.posicaoColuna = this.posicaoColuna;
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
        this.posicaoLinha = agenteParaBACKUPDeDados.posicaoLinha;
        this.posicaoColuna = agenteParaBACKUPDeDados.posicaoColuna;
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

    public void atualizarDadosDaLixeiraMaisProxima(int coordenadaLinha, int coordenadaColuna, int distancia) {
        lixeiraMaisProxima.setLinha(coordenadaLinha);
        lixeiraMaisProxima.setColuna(coordenadaColuna);
        distanciaDaLixeiraMaisProxima = distancia;
    }

    public void atualizarDadosDaRecargaMaisProxima(int coordenadaLinha, int coordenadaColuna, int distancia) {
        recargaMaisProxima.setLinha(coordenadaLinha);
        recargaMaisProxima.setColuna(coordenadaColuna);
        distanciaDaRecargaMaisProxima = distancia;
    }

    public Posicao posicaoDoAgente() {
        return new Posicao(posicaoLinha, posicaoColuna);
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

    public void moverParaEsquerda() {
        setPosicaoColuna(getPosicaoColuna() - 1);
        this.consumirEnergia();
    }

    public void moverParaDiagonalSuperiorEsquerda() {
        setPosicaoLinha(getPosicaoLinha() - 1);
        setPosicaoColuna(getPosicaoColuna() - 1);
        this.consumirEnergia();
    }

    public void moveraParaDiagonalInferiorEsquerda() {
        setPosicaoLinha(getPosicaoLinha() + 1);
        setPosicaoColuna(getPosicaoColuna() - 1);
        this.consumirEnergia();
    }

    public void moverParaDireita() {
        setPosicaoColuna(getPosicaoColuna() + 1);
        this.consumirEnergia();
    }

    public void moverParaDiagonalSuperiorDireita() {
        setPosicaoLinha(getPosicaoLinha() - 1);
        setPosicaoColuna(getPosicaoColuna() + 1);
        this.consumirEnergia();
    }

    public void moveraParaDiagonalInferiorDireita() {
        setPosicaoLinha(getPosicaoLinha() + 1);
        setPosicaoColuna(getPosicaoColuna() + 1);
        this.consumirEnergia();
    }

    public void moverParaCima() {
        setPosicaoLinha(getPosicaoLinha() - 1);
        this.consumirEnergia();
    }

    public void moverParaBaixo() {
        setPosicaoLinha(getPosicaoLinha() + 1);
        this.consumirEnergia();
    }

    public boolean existeSujeiraNaPosicaoDoAgente() {
        this.observarMinhaPosicao();

        return elementoObservadoNaPosicaoDoAgente == SUJEIRA;
    }

    public void observarMinhaPosicao() {
        elementoObservadoNaPosicaoDoAgente = ambiente.elementoDaPosicaoLinhaColuna(this.getPosicaoLinha(), this.getPosicaoColuna());
    }

    public boolean possoColetarMaisLixo() {
        return ((this.getLixoColetado() + 1) <= this.getCapacidadeTotalDeLixo());
    }

    public void consumirEnergia() {
        this.setEnergiaRestante(this.getEnergiaRestante() - 1);
    }

    private boolean getValorAtLinhaColunaNaMatrizAStar(boolean matriz[][], int linhaAux, int colunaAux) {
        return matriz[linhaAux][colunaAux];
    }

    private void setTrueAtLinhaColunaNaMatrizAStar(boolean matriz[][], int linhaAux, int colunaAux) {
        matriz[linhaAux][colunaAux] = true;
    }

    /*  Algoritmo A* */
    public ArrayList<Character> aStar(Posicao posicaoDoAgente, Posicao posicaoDoDestino) {
        //posicaoDoAgente = posicaoDoAgente.inverterPonto();
        //posicaoDoDestino = posicaoDoDestino.inverterPonto();
        //System.out.println("POSICAO ORIGEM = " + posicaoDoAgente.toString());
        //System.out.println("POSICAO DESTINO = " + posicaoDoDestino.toString());

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
            //int node_remove_linha = node_removed.getPosicaoLinha();
            //int node_remove_coluna = node_removed.getPosicaoColuna();

            //se o retorno da heuristica para esse nodo for zero, entao o algoritmo ja pode encerrar
            if (solicitarCalculoDeHeuristicaAoAmbiente(node_removed.getPosition(), posicaoDoDestino) == 0) {
                return node_removed.getPath();
            }

            //verifica quais sao os sucessores do nodo removido da fila
            ArrayList<Sucessor> sucessores = calcularSucessores(node_removed.getPosition());
            //System.out.println("PONTO=" + node_removed.getPosition() + " SUCESSORES" + sucessores.toString());  

            //itera sobre os sucessores do nodo removido
            for (Sucessor sucessor : sucessores) {
                int sucessor_linha = sucessor.getPosicao().getLinha();
                int sucessor_coluna = sucessor.getPosicao().getColuna();

                //executa o codigo a seguir somente se esta posicao do mapa ainda nao foi explorada
                //if (getValorAtLinhaColunaNaMatrizAStar(marked_matrix, sucessor_linha, sucessor_coluna) == false) {
                if (marked_matrix[sucessor_linha][sucessor_coluna] == false) {
                    //&& ambiente.elementoDaPosicaoXY(sucessor_linha, sucessor_coluna) != LIXEIRA
                    //&& ambiente.elementoDaPosicaoXY(sucessor_linha, sucessor_coluna) != RECARGA
                    //&& ambiente.elementoDaPosicaoXY(sucessor_linha, sucessor_coluna) != PAREDE) {

                    //cria um nodo para essa posicao do mapa
                    Nodo sucessor_nodo = new Nodo();
                    sucessor_nodo.setPosition(new Posicao(sucessor_linha, sucessor_coluna));

                    //determina a prioridade deste nodo, calculando g(n) + h(n) 
                    //REVER O CAMINHO ACUMULADO!
                    sucessor_nodo.setDistance_until_here(node_removed.getDistance_until_here() + 1);
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
                    sucessor_nodo.setNumber_of_moves(sucessor_nodo.getPath().size());

                    //adiciona o nodo na fila de prioridades
                    priority_queue.addToQueue(sucessor_nodo);

                    //se esta nao e a posicao objetivo, ela e marcada para que nao seja explorada novamente
                    if (solicitarCalculoDeHeuristicaAoAmbiente(sucessor.getPosicao(), posicaoDoDestino) != 0) {
                        marked_matrix[sucessor_linha][sucessor_coluna] = true;
                    }
                }
            }
        }

        //System.out.println("VOLTEI NULO, POIS NAO ACHEI NINGUEM!!! =[");
        return null;
    }

    public void recarregar() {
        this.setEnergiaRestante(this.getCapacidadeTotalDeEnergia());
    }

    public void aspirar() {
        ambiente.setElementoDaPosicaoLinhaColuna(this.getPosicaoLinha(), this.getPosicaoColuna(), LIMPO);
        this.setLixoColetado(this.getLixoColetado() + 1);
        this.consumirEnergia();
    }

    public void observarAmbiente() {
        if ((this.getPosicaoColuna() - 1) < 0) {
            setElementoObersvadoNaEsquerda(NULO);
        } else {
            setElementoObersvadoNaEsquerda(ambiente.elementoDaPosicaoLinhaColuna(this.getPosicaoLinha(), this.getPosicaoColuna() - 1));
        }
        if ((this.getPosicaoColuna() + 1) >= ambiente.getTamanhoMatriz()) {
            setElementoObersvadoNaDireita(NULO);
        } else {
            setElementoObersvadoNaDireita(ambiente.elementoDaPosicaoLinhaColuna(this.getPosicaoLinha(), this.getPosicaoColuna() + 1));
        }
        if ((this.getPosicaoLinha() - 1) < 0) {
            setElementoObersvadoEmCima(NULO);
        } else {
            setElementoObersvadoEmCima(ambiente.elementoDaPosicaoLinhaColuna(this.getPosicaoLinha() - 1, this.getPosicaoColuna()));
        }
        if ((this.getPosicaoLinha() + 1) >= ambiente.getTamanhoMatriz()) {
            setElementoObersvadoEmBaixo(NULO);
        } else {
            setElementoObersvadoEmBaixo(ambiente.elementoDaPosicaoLinhaColuna(this.getPosicaoLinha() + 1, this.getPosicaoColuna()));
        }
        if (((this.getPosicaoLinha() - 1) < 0) || ((this.getPosicaoColuna() - 1) < 0)) {
            setElementoObservadoNaDiagonalEsquerdaSuperior(NULO);
        } else {
            setElementoObservadoNaDiagonalEsquerdaSuperior(ambiente.elementoDaPosicaoLinhaColuna(this.getPosicaoLinha() - 1, this.getPosicaoColuna() - 1));
        }
        if (((this.getPosicaoLinha() + 1) >= ambiente.getTamanhoMatriz()) || ((this.getPosicaoColuna() - 1) < 0)) {
            setElementoObservadoNaDiagonalEsquerdaInferior(NULO);
        } else {
            setElementoObservadoNaDiagonalEsquerdaInferior(ambiente.elementoDaPosicaoLinhaColuna(this.getPosicaoLinha() + 1, this.getPosicaoColuna() - 1));
        }
        if (((this.getPosicaoLinha() - 1) < 0) || ((this.getPosicaoColuna() + 1) >= (ambiente.getTamanhoMatriz()))) {
            setElementoObservadoNaDiagonalDireitaSuperior(NULO);
        } else {
            setElementoObservadoNaDiagonalDireitaSuperior(ambiente.elementoDaPosicaoLinhaColuna(this.getPosicaoLinha() - 1, this.getPosicaoColuna() + 1));
        }
        if (((this.getPosicaoLinha() + 1) >= ambiente.getTamanhoMatriz()) || ((this.getPosicaoColuna() + 1) >= ambiente.getTamanhoMatriz())) {
            setElementoObservadoNaDiagonalDireitaInferior(NULO);
        } else {
            setElementoObservadoNaDiagonalDireitaInferior(ambiente.elementoDaPosicaoLinhaColuna(this.getPosicaoLinha() + 1, this.getPosicaoColuna() + 1));
        }
    }

    public void varreduraDeLinhasPelaEsquerda() {
        this.observarAmbiente();
        this.log();

        //Se estou indo para a direita DIREITA
        if (direcao == DIREITA && indo == true) {
            //Se esta é a última posição da linha
            if (this.getPosicaoColuna() == ambiente.getTamanhoMatriz() - 1) {
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
            if (this.getPosicaoColuna() == 0) {
                if (this.getPosicaoColuna() == 0 && this.getPosicaoLinha() == ambiente.getTamanhoMatriz() - 1) {
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
            if (this.getPosicaoColuna() == 0) {
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
            if (this.getPosicaoColuna() == ambiente.getTamanhoMatriz() - 1) {
                if (this.getPosicaoColuna() == ambiente.getTamanhoMatriz() - 1 && this.getPosicaoLinha() == ambiente.getTamanhoMatriz() - 1) {
                    varreduraPorLinhaNaDireitaConcluida = true;
                    varrerPorColunas = true;
                    indo = true;
                    voltando = false;
                    direcao = ACIMA;
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
        System.out.println("DEVO VARRER POR COLUNAS!");

        this.observarAmbiente();
        this.log();

        //Se estou indo para cima
        if (direcao == ACIMA && indo == true) {
            //Se esta é a última posição da coluna
            if (this.getPosicaoLinha() == 0) {
                direcao = ABAIXO;
                voltando = true;
                indo = false;
            } //Se não existe parede acima
            else if (elementoObservadoEmCimaDoAgente != PAREDE) {
                //System.out.println("EU DEVERIA SUBIR! O.O");
                this.moverParaCima();
            } else if (elementoObservadoEmCimaDoAgente == PAREDE) {
                direcao = ABAIXO;
                voltando = true;
                indo = false;
            }
        } else if (direcao == ABAIXO && voltando == true) {
            //Se esta é a primeira posição da coluna
            if (this.getPosicaoLinha() == ambiente.getTamanhoMatriz() - 1) {
                if ((this.getPosicaoLinha() == ambiente.getTamanhoMatriz() - 1) && (this.getPosicaoColuna() == 0)) {
                    //////////////venci
                    varreduraCompletamenteConcluida = true;
                    //varreduraPorLinhaNaEsquerdaConcluida = true;
                    //efetuarTransicao = true;
                    System.out.println("A VARREDURA FOI CONCLUIDA COM EXITO!");
                    //return;
                } else {
                    this.moverParaEsquerda();
                    direcao = ACIMA;
                    voltando = false;
                    indo = true;
                }
            } //Se não existe uma parede na esquerda, se move para a esquerda
            else if (elementoObservadoEmBaixoDoAgente != PAREDE) {
                this.moverParaBaixo();
            } else if (elementoObservadoEmBaixoDoAgente == PAREDE) {
                direcao = ACIMA;
                voltando = false;
                indo = true;
            }
        }
    }

    public void transicaoParaVarreduraPelaDireita() {
        this.observarAmbiente();
        this.log();

        if (direcao != ACIMA) {
            if (this.getPosicaoColuna() == ambiente.getTamanhoMatriz() - 1) {
                direcao = ACIMA;
            } else {
                this.moverParaDireita();
            }
        } else if (direcao == ACIMA) {
            if (this.getPosicaoLinha() == 0) {
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

    public void realizarMovimento(char direcaoAux) {
        if (direcaoAux == ESQUERDA) {
            this.moverParaEsquerda();
        } else if (direcaoAux == DIREITA) {
            this.moverParaDireita();
        } else if (direcaoAux == ACIMA) {
            this.moverParaCima();
        } else if (direcaoAux == ABAIXO) {
            this.moverParaBaixo();
        } else if (direcaoAux == DIAGONALESQUERDASUPERIOR) {
            this.moverParaDiagonalSuperiorEsquerda();
        } else if (direcaoAux == DIAGONALESQUERDAINFERIOR) {
            this.moveraParaDiagonalInferiorEsquerda();
        } else if (direcaoAux == DIAGONALDIREITASUPERIOR) {
            this.moverParaDiagonalSuperiorDireita();
        } else if (direcaoAux == DIAGONALDIREITAINFERIOR) {
            this.moveraParaDiagonalInferiorDireita();
        }
    }

    public void descarregarLixo() {
        this.setLixoColetado(0);
    }

    public boolean atualizar2() {
        if (this.getEnergiaRestante() <= 0) {
            System.out.println("A ENERGIA DO AGENTE CHEGOU A ZERO! =[");
            return true;
        }

        //modoAEstrelaLixeiraAtivado = false;
        //modoAEstrelaRecargaAtivado = false;
        //modoAEstrelaAtivado = false;
        //iteradorCaminhoAEstrela = 0;
        //aEstrelaIndo = false;
        //aEstrelaVoltando = false;
        //caminhoAEstrelaIndo = new ArrayList<Character>();
        //caminhoAEstrelaVoltando = new ArrayList<Character>();
        solicitarDadosDaLixeiraMaisProxima();
        solicitarDadosDaRecargaMaisProxima();

        //se o agente precisa deixar o lixo em uma lixeira
        if ((!possoColetarMaisLixo())
                && (modoAEstrelaAtivado == false)
                && (modoAEstrelaLixeiraAtivado == false)
                && (modoAEstrelaRecargaAtivado == false)) {
            //se o agente possui energia o suficiente para ir ate a lixeira
            if (getDistanciaDaLixeiraMaisProxima() < (getEnergiaRestante() - (ambiente.getTamanhoMatriz() * 2))) {
                salvarInformacoesDoAgente();
                caminhoAEstrelaIndo = aStarBuscandoLixeira(this.getPosicao(), lixeiraMaisProxima.getPosicao());
                caminhoAEstrelaVoltando = aStarBuscandoLixeira(lixeiraMaisProxima.getPosicao(), this.getPosicao());
                //System.out.println("CALCULO CONCLUIDO! =]");
                //System.out.println("PASSOS A* A FAZER NA IDA = " + caminhoAEstrelaIndo.toString());
                //System.out.println("PASSOS A* A FAZER NA VOLTA = " + caminhoAEstrelaVoltando.toString());
                iteradorCaminhoAEstrela = 0;
                modoAEstrelaAtivado = true;
                modoAEstrelaLixeiraAtivado = true;
                aEstrelaIndo = true;
            } //o agente deve primeiro recarregar a bateria, pois nao possui energia o suficiente para chegar na lixeira
            else {
                salvarInformacoesDoAgente();
                caminhoAEstrelaIndo = aStarBuscandoRecarga(this.getPosicao(), recargaMaisProxima.getPosicao());
                caminhoAEstrelaVoltando = aStarBuscandoRecarga(recargaMaisProxima.getPosicao(), this.getPosicao());
                //System.out.println("CALCULO CONCLUIDO! =]");
                //System.out.println("PASSOS A* A FAZER NA IDA = " + caminhoAEstrelaIndo.toString());
                //System.out.println("PASSOS A* A FAZER NA VOLTA = " + caminhoAEstrelaVoltando.toString());
                iteradorCaminhoAEstrela = 0;
                modoAEstrelaAtivado = true;
                modoAEstrelaRecargaAtivado = true;
                aEstrelaIndo = true;
            }
        }

        //se o agente precisa recarregar a bateria
        if ((getDistanciaDaRecargaMaisProxima() > (getEnergiaRestante() - (ambiente.getTamanhoMatriz() * 2)))
                && (modoAEstrelaAtivado == false)
                && (modoAEstrelaLixeiraAtivado == false)
                && (modoAEstrelaRecargaAtivado == false)) {
            //System.out.println("ENERGIA RESTANTE = " + getEnergiaRestante());
            //System.out.println("distancia recarga = " + getDistanciaDaRecargaMaisProxima());
            //System.out.println("CALCULO = " + (getEnergiaRestante()-10) );

            salvarInformacoesDoAgente();
            caminhoAEstrelaIndo = aStarBuscandoRecarga(this.getPosicao(), recargaMaisProxima.getPosicao());
            caminhoAEstrelaVoltando = aStarBuscandoRecarga(recargaMaisProxima.getPosicao(), this.getPosicao());
            //System.out.println("CALCULO CONCLUIDO! =]");
            //System.out.println("PASSOS A* A FAZER NA IDA = " + caminhoAEstrelaIndo.toString());
            //System.out.println("PASSOS A* A FAZER NA VOLTA = " + caminhoAEstrelaVoltando.toString());
            iteradorCaminhoAEstrela = 0;
            modoAEstrelaAtivado = true;
            modoAEstrelaRecargaAtivado = true;
            aEstrelaIndo = true;
        }

        if (existeSujeiraNaPosicaoDoAgente()) {
            if (possoColetarMaisLixo()) {
                this.aspirar();
            }
        }

        if (modoAEstrelaLixeiraAtivado == true) {
            System.out.println("ENTREI NO MODO A ESTRELA! =]");
            log();

            if (aEstrelaIndo == true) {
                if ((caminhoAEstrelaIndo != null) && (caminhoAEstrelaIndo.size() > 1) && (iteradorCaminhoAEstrela < caminhoAEstrelaIndo.size() - 1)) {
                    //this.log();
                    realizarMovimento(caminhoAEstrelaIndo.get(iteradorCaminhoAEstrela));
                    iteradorCaminhoAEstrela++;
                } else {
                    //CHEGUEI AO LADO DA LIXEIRA
                    aEstrelaIndo = false;
                    aEstrelaVoltando = true;
                    descarregarLixo();
                    iteradorCaminhoAEstrela = 1;
                    System.out.println("ACABEI DE DESCARREGAR O LIXO! =]");
                }
            } else if (aEstrelaVoltando == true) {
                if ((caminhoAEstrelaVoltando != null) && (caminhoAEstrelaVoltando.size() > 1) && (iteradorCaminhoAEstrela < caminhoAEstrelaVoltando.size())) {
                    //this.log();
                    realizarMovimento(caminhoAEstrelaVoltando.get(iteradorCaminhoAEstrela));
                    iteradorCaminhoAEstrela++;
                } else {
                    aEstrelaIndo = false;
                    aEstrelaVoltando = false;
                    modoAEstrelaLixeiraAtivado = false;
                    modoAEstrelaAtivado = false;
                    iteradorCaminhoAEstrela = 0;
                    restaurarInformacoesDoAgente();
                    System.out.println("ACABEI DE VOLTAR PARA A POSICAO ONDE EU ESTAVA! =]");

                    //log();
                    //return true;
                }
            }
        } else if (modoAEstrelaRecargaAtivado == true) {
            System.out.println("ENTREI NO MODO A ESTRELA! =]");
            log();

            if (aEstrelaIndo == true) {
                if ((caminhoAEstrelaIndo != null) && (caminhoAEstrelaIndo.size() > 1) && (iteradorCaminhoAEstrela < caminhoAEstrelaIndo.size() - 1)) {
                    //this.log();
                    realizarMovimento(caminhoAEstrelaIndo.get(iteradorCaminhoAEstrela));
                    iteradorCaminhoAEstrela++;
                } else {
                    //CHEGUEI AO LADO DA RECARGA
                    aEstrelaIndo = false;
                    aEstrelaVoltando = true;
                    this.recarregar();
                    iteradorCaminhoAEstrela = 1;
                    System.out.println("ACABEI DE RECARREGAR A BATERIA! =]");
                }
            } else if (aEstrelaVoltando == true) {
                if ((caminhoAEstrelaVoltando != null) && (caminhoAEstrelaVoltando.size() > 1) && (iteradorCaminhoAEstrela < caminhoAEstrelaVoltando.size())) {
                    //this.log();
                    realizarMovimento(caminhoAEstrelaVoltando.get(iteradorCaminhoAEstrela));
                    iteradorCaminhoAEstrela++;
                } else {
                    aEstrelaIndo = false;
                    aEstrelaVoltando = false;
                    modoAEstrelaRecargaAtivado = false;
                    modoAEstrelaAtivado = false;
                    iteradorCaminhoAEstrela = 0;
                    restaurarInformacoesDoAgente();
                    System.out.println("ACABEI DE VOLTAR PARA A POSICAO ONDE EU ESTAVA! =]");

                    //log();
                    //return true;
                }
            }
        } else if (varreduraPorLinhaNaEsquerdaConcluida == false) {
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

    public Posicao getPosicao() {
        return new Posicao(this.getPosicaoLinha(), this.getPosicaoColuna());
    }

    public void log() {
        System.out.println("------------------------------------------------");
        System.out.println("modo a* ativado = " + modoAEstrelaAtivado);
        System.out.println("posicao da recarga mais proxima = " + recargaMaisProxima.toString());
        System.out.println("distancia da recarga mais proxima = " + distanciaDaRecargaMaisProxima);
        System.out.println("energia restante = " + this.getEnergiaRestante());
        System.out.println("caminho A* para a recarga mais proxima = " + aStarBuscandoRecarga(this.getPosicao(), recargaMaisProxima.getPosicao()));
        System.out.println("posicao da lixeira mais proxima = " + lixeiraMaisProxima.toString());
        System.out.println("distancia da lixeira mais proxima = " + distanciaDaLixeiraMaisProxima);
        System.out.println("lixo coletado = " + this.getLixoColetado());
        System.out.println("caminho A* para a lixeira mais proxima = " + aStarBuscandoLixeira(this.getPosicao(), lixeiraMaisProxima.getPosicao()));
        System.out.println("direcao = " + direcao);
        System.out.println("indo = " + indo);
        System.out.println("voltando = " + voltando);
        System.out.println("desviandoDeObjetos = " + desviandoDeObjetos);
        System.out.println("posicao = [ " + posicaoLinha + " , " + posicaoColuna + " ]");
        System.out.println("elemento_esquerda = " + elementoObservadoNaEsquerdaDoAgente);
        System.out.println("elemento_direita = " + elementoObservadoNaDireitaDoAgente);
        System.out.println("elemento_acima = " + elementoObservadoEmCimaDoAgente);
        System.out.println("elemento_abaixo = " + elementoObservadoEmBaixoDoAgente);
        System.out.println("------------------------------------------------");
    }

    public ArrayList<Sucessor> calcularSucessores(Posicao posicaoAlvo) {
        ArrayList<Sucessor> sucessores = new ArrayList<Sucessor>();

        //ESQUERDA
        if (!((posicaoAlvo.getColuna() - 1) < 0)) {
            int posX = posicaoAlvo.getLinha();
            int posY = posicaoAlvo.getColuna() - 1;

            char elemento = ambiente.elementoDaPosicaoLinhaColuna(posX, posY);

            if (elemento == LIMPO || elemento == SUJEIRA || elemento == objetoExcecaoNaBuscaAtual) {
                Sucessor sucessorAux = new Sucessor();
                sucessorAux.setDirecao(ESQUERDA);
                Posicao posicaoAux = new Posicao(posX, posY);
                sucessorAux.setPosicao(posicaoAux.getPosicao());
                sucessores.add(sucessorAux);
            }
        }

        //DIREITA
        if (!((posicaoAlvo.getColuna() + 1) >= ambiente.getTamanhoMatriz())) {
            int posX = posicaoAlvo.getLinha();
            int posY = posicaoAlvo.getColuna() + 1;

            char elemento = ambiente.elementoDaPosicaoLinhaColuna(posX, posY);

            if (elemento == LIMPO || elemento == SUJEIRA || elemento == objetoExcecaoNaBuscaAtual) {
                Sucessor sucessorAux = new Sucessor();
                sucessorAux.setDirecao(DIREITA);
                Posicao posicaoAux = new Posicao(posX, posY);
                sucessorAux.setPosicao(posicaoAux.getPosicao());
                sucessores.add(sucessorAux);
            }
        }

        //ACIMA
        if (!((posicaoAlvo.getLinha() - 1) < 0)) {
            int posX = posicaoAlvo.getLinha() - 1;
            int posY = posicaoAlvo.getColuna();

            char elemento = ambiente.elementoDaPosicaoLinhaColuna(posX, posY);

            if (elemento == LIMPO || elemento == SUJEIRA || elemento == objetoExcecaoNaBuscaAtual) {
                Sucessor sucessorAux = new Sucessor();
                sucessorAux.setDirecao(ACIMA);
                Posicao posicaoAux = new Posicao(posX, posY);
                sucessorAux.setPosicao(posicaoAux.getPosicao());
                sucessores.add(sucessorAux);
            }
        }

        //ABAIXO
        if (!((posicaoAlvo.getLinha() + 1) >= ambiente.getTamanhoMatriz())) {
            int posX = posicaoAlvo.getLinha() + 1;
            int posY = posicaoAlvo.getColuna();

            char elemento = ambiente.elementoDaPosicaoLinhaColuna(posX, posY);

            if (elemento == LIMPO || elemento == SUJEIRA || elemento == objetoExcecaoNaBuscaAtual) {
                Sucessor sucessorAux = new Sucessor();
                sucessorAux.setDirecao(ABAIXO);
                Posicao posicaoAux = new Posicao(posX, posY);
                sucessorAux.setPosicao(posicaoAux.getPosicao());
                sucessores.add(sucessorAux);
            }
        }

        //DIAGONAL ESQUERDA SUPERIOR
        if (!(((posicaoAlvo.getLinha() - 1) < 0) || ((posicaoAlvo.getColuna() - 1) < 0))) {
            int posX = posicaoAlvo.getLinha() - 1;
            int posY = posicaoAlvo.getColuna() - 1;

            char elemento = ambiente.elementoDaPosicaoLinhaColuna(posX, posY);

            if (elemento == LIMPO || elemento == SUJEIRA || elemento == objetoExcecaoNaBuscaAtual) {
                Sucessor sucessorAux = new Sucessor();
                sucessorAux.setDirecao(DIAGONALESQUERDASUPERIOR);
                Posicao posicaoAux = new Posicao(posX, posY);
                sucessorAux.setPosicao(posicaoAux.getPosicao());
                sucessores.add(sucessorAux);
            }
        }

        //DIAGONAL ESQUERDA INFERIOR
        if (!(((posicaoAlvo.getLinha() + 1) >= ambiente.getTamanhoMatriz()) || ((posicaoAlvo.getColuna() - 1) < 0))) {
            int posX = posicaoAlvo.getLinha() + 1;
            int posY = posicaoAlvo.getColuna() - 1;

            char elemento = ambiente.elementoDaPosicaoLinhaColuna(posX, posY);

            if (elemento == LIMPO || elemento == SUJEIRA || elemento == objetoExcecaoNaBuscaAtual) {
                Sucessor sucessorAux = new Sucessor();
                sucessorAux.setDirecao(DIAGONALESQUERDAINFERIOR);
                Posicao posicaoAux = new Posicao(posX, posY);
                sucessorAux.setPosicao(posicaoAux.getPosicao());
                sucessores.add(sucessorAux);
            }
        }

        //DIAGONAL DIREITA SUPERIOR
        if (!(((posicaoAlvo.getLinha() - 1) < 0) || ((posicaoAlvo.getColuna() + 1) >= ambiente.getTamanhoMatriz()))) {
            int posX = posicaoAlvo.getLinha() - 1;
            int posY = posicaoAlvo.getColuna() + 1;

            char elemento = ambiente.elementoDaPosicaoLinhaColuna(posX, posY);

            if (elemento == LIMPO || elemento == SUJEIRA || elemento == objetoExcecaoNaBuscaAtual) {
                Sucessor sucessorAux = new Sucessor();
                sucessorAux.setDirecao(DIAGONALDIREITASUPERIOR);
                Posicao posicaoAux = new Posicao(posX, posY);
                sucessorAux.setPosicao(posicaoAux.getPosicao());
                sucessores.add(sucessorAux);
            }
        }

        //DIAGONAL DIREITA INFERIOR
        if (!(((posicaoAlvo.getLinha() + 1) >= ambiente.getTamanhoMatriz()) || ((posicaoAlvo.getColuna() + 1) >= ambiente.getTamanhoMatriz()))) {
            int posX = posicaoAlvo.getLinha() + 1;
            int posY = posicaoAlvo.getColuna() + 1;

            char elemento = ambiente.elementoDaPosicaoLinhaColuna(posX, posY);

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
