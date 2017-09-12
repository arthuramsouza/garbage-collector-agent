
import java.util.ArrayList;

/**
 * ORIENTACAO DO AMBIENTE: [LIINHA][COLUNA] ORIENTACAO DO AGENTE: [LIINHA][COLUNA]
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
    Posicao posicaoAlvo;
    Posicao posicaoOrigem;

    private char objetoExcecaoNaBuscaAtual;

    private Posicao lixeiraMaisProxima;
    private int distanciaDaLixeiraMaisProxima;
    private Posicao recargaMaisProxima;
    private int distanciaDaRecargaMaisProxima;

    int capacidadeTotalDeEnergia;
    int energiaRestante;
    int capacidadeTotalDeLixo;
    int lixoColetado;

    private int posicaoLinha, posicaoColuna; // Posição do agente
    private char direcao;

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
    public Agente(int posicaoLinha, int posicaoColuna, char direcao, Ambiente ambiente, int capacidadeLixo, int capacidadeEnergia) {
        modoAEstrelaLixeiraAtivado = false;
        modoAEstrelaRecargaAtivado = false;
        modoAEstrelaAtivado = false;
        iteradorCaminhoAEstrela = 0;
        aEstrelaIndo = false;
        aEstrelaVoltando = false;
        caminhoAEstrelaIndo = new ArrayList<Character>();
        caminhoAEstrelaVoltando = new ArrayList<Character>();

        posicaoAlvo = new Posicao();
        posicaoOrigem = new Posicao();

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

    /* Este construtor deve ser usado apenas para o agenteParaBACKUPDeDados! */
    public Agente() {

        capacidadeTotalDeEnergia = 100;
        energiaRestante = 100;
        capacidadeTotalDeLixo = 10;
        lixoColetado = 0;
        posicaoLinha = 0;
        posicaoColuna = 0;
        direcao = DIREITA;
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

    /* Salva estado do agente. */
    public void salvarInformacoesDoAgente() {
        agenteParaBACKUPDeDados.posicaoLinha = this.posicaoLinha;
        agenteParaBACKUPDeDados.posicaoColuna = this.posicaoColuna;
        agenteParaBACKUPDeDados.direcao = this.direcao;
        agenteParaBACKUPDeDados.desviandoDeObjetos = this.desviandoDeObjetos;
        agenteParaBACKUPDeDados.indo = this.indo;
        agenteParaBACKUPDeDados.voltando = this.voltando;
        agenteParaBACKUPDeDados.efetuarTransicao = this.efetuarTransicao;
        agenteParaBACKUPDeDados.varreduraPorLinhaNaEsquerdaConcluida = this.varreduraPorLinhaNaEsquerdaConcluida;
        agenteParaBACKUPDeDados.varreduraPorLinhaNaDireitaConcluida = this.varreduraPorLinhaNaDireitaConcluida;
        agenteParaBACKUPDeDados.varrerPorColunas = this.varrerPorColunas;
        agenteParaBACKUPDeDados.varreduraCompletamenteConcluida = this.varreduraCompletamenteConcluida;
    }

    /* Restaura estado do agente. */
    public void restaurarInformacoesDoAgente() {
        this.posicaoLinha = agenteParaBACKUPDeDados.posicaoLinha;
        this.posicaoColuna = agenteParaBACKUPDeDados.posicaoColuna;
        this.direcao = agenteParaBACKUPDeDados.direcao;
        this.desviandoDeObjetos = agenteParaBACKUPDeDados.desviandoDeObjetos;
        this.indo = agenteParaBACKUPDeDados.indo;
        this.voltando = agenteParaBACKUPDeDados.voltando;
        this.efetuarTransicao = agenteParaBACKUPDeDados.efetuarTransicao;
        this.varreduraPorLinhaNaEsquerdaConcluida = agenteParaBACKUPDeDados.varreduraPorLinhaNaEsquerdaConcluida;
        this.varreduraPorLinhaNaDireitaConcluida = agenteParaBACKUPDeDados.varreduraPorLinhaNaDireitaConcluida;
        this.varrerPorColunas = agenteParaBACKUPDeDados.varrerPorColunas;
        this.varreduraCompletamenteConcluida = agenteParaBACKUPDeDados.varreduraCompletamenteConcluida;
    }

    public Posicao getPosicao() {
        return new Posicao(this.getPosicaoLinha(), this.getPosicaoColuna());
    }

    public int getPosicaoLinha() {
        return posicaoLinha;
    }

    public int getPosicaoColuna() {
        return posicaoColuna;
    }

    public Posicao getLixeiraMaisProxima() {
        return lixeiraMaisProxima;
    }

    public int getDistanciaDaLixeiraMaisProxima() {
        return distanciaDaLixeiraMaisProxima;
    }

    public Posicao getRecargaMaisProxima() {
        return recargaMaisProxima;
    }

    public int getDistanciaDaRecargaMaisProxima() {
        return distanciaDaRecargaMaisProxima;
    }

    public int getCapacidadeTotalDeEnergia() {
        return capacidadeTotalDeEnergia;
    }

    public int getEnergiaRestante() {
        return energiaRestante;
    }

    public int getCapacidadeTotalDeLixo() {
        return capacidadeTotalDeLixo;
    }

    public int getLixoColetado() {
        return lixoColetado;
    }

    public void setPosicaoLinha(int posicaoLinha) {
        this.posicaoLinha = posicaoLinha;
    }

    public void setPosicaoColuna(int posicaoColuna) {
        this.posicaoColuna = posicaoColuna;
    }

    public void setLixeiraMaisProxima(Posicao lixeiraMaisProxima) {
        this.lixeiraMaisProxima = lixeiraMaisProxima;
    }

    public void setDistanciaDaLixeiraMaisProxima(int distanciaDaLixeiraMaisProxima) {
        this.distanciaDaLixeiraMaisProxima = distanciaDaLixeiraMaisProxima;
    }

    public void setRecargaMaisProxima(Posicao recargaMaisProxima) {
        this.recargaMaisProxima = recargaMaisProxima;
    }

    public void setDistanciaDaRecargaMaisProxima(int distanciaDaRecargaMaisProxima) {
        this.distanciaDaRecargaMaisProxima = distanciaDaRecargaMaisProxima;
    }

    public void setCapacidadeTotalDeEnergia(int capacidadeTotalDeEnergia) {
        this.capacidadeTotalDeEnergia = capacidadeTotalDeEnergia;
    }

    public void setEnergiaRestante(int energiaRestante) {
        this.energiaRestante = energiaRestante;
    }

    public void setCapacidadeTotalDeLixo(int capacidadeTotalDeLixo) {
        this.capacidadeTotalDeLixo = capacidadeTotalDeLixo;
    }

    public void setLixoColetado(int lixoColetado) {
        this.lixoColetado = lixoColetado;
    }

    /* Executa o algoritmo A* tendo como alvo a lixeira mais próxima ao agente. */
    public ArrayList<Character> aStarBuscandoLixeira(Posicao posicaoDoAgente, Posicao posicaoDoDestino) {
        objetoExcecaoNaBuscaAtual = LIXEIRA;
        return aStar(posicaoDoAgente, posicaoDoDestino);
    }

    /* Executa o algoritmo A* tendo como alvo a recarga mais próxima ao agente. */
    public ArrayList<Character> aStarBuscandoRecarga(Posicao posicaoDoAgente, Posicao posicaoDoDestino) {
        objetoExcecaoNaBuscaAtual = RECARGA;

        return aStar(posicaoDoAgente, posicaoDoDestino);
    }

    public int solicitarCalculoDeHeuristicaAoAmbiente(Posicao posicaoOrigem, Posicao posicaoDestino) {
        return ambiente.distanciaManhattan(posicaoOrigem, posicaoDestino);
    }

    /* O agente solicita dados da lixeira próxima. */
    public void solicitarDadosDaLixeiraMaisProxima() {
        ambiente.lixeiraMaisProximaDoAgente(this);
    }

    /* O agente solicita dados da recarga próxima. */
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

    public void recarregar() {
        this.setEnergiaRestante(this.getCapacidadeTotalDeEnergia());
    }

    public void aspirar() {
        ambiente.setElementoDaPosicaoLinhaColuna(this.getPosicaoLinha(), this.getPosicaoColuna(), LIMPO);
        this.setLixoColetado(this.getLixoColetado() + 1);
        this.consumirEnergia();
    }

    public void descarregarLixo() {
        this.setLixoColetado(0);
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

    /* Imprime informações relevantes durante a execução do programa. */
    public void log() {
        System.out.println("------------------------------------------------");
        if (modoAEstrelaAtivado) {
            if (modoAEstrelaLixeiraAtivado) {
                System.out.println("MODO A* ATIVADO = " + modoAEstrelaAtivado);
                System.out.println("POSICAO ORIGEM (AGENTE) = " + posicaoOrigem.toString());
                System.out.println("POSICAO DESTINO (LIXEIRA) = " + posicaoAlvo.toString());
                System.out.println("MOVIMENTOS DA IDA = " + caminhoAEstrelaIndo.toString());
                System.out.println("MOVIMENTOS DA VOLTA = " + caminhoAEstrelaVoltando.toString());
            } else if (modoAEstrelaRecargaAtivado) {
                System.out.println("MODO A* ATIVADO = " + modoAEstrelaAtivado);
                System.out.println("POSICAO ORIGEM (AGENTE) = " + posicaoOrigem.toString());
                System.out.println("POSICAO DESTINO (RECARGA) = " + posicaoAlvo.toString());
                System.out.println("MOVIMENTOS DA IDA = " + caminhoAEstrelaIndo.toString());
                System.out.println("MOVIMENTOS DA VOLTA = " + caminhoAEstrelaVoltando.toString());
            }
        } else {
            System.out.println("MODO A* ATIVADO = " + modoAEstrelaAtivado);
            System.out.println("POSICAO ORIGEM = []");
            System.out.println("POSICAO DESTINO  = []");
            System.out.println("MOVIMENTOS DA IDA = []");
            System.out.println("MOVIMENTOS DA VOLTA = []");
        }
        System.out.println("POSICAO DA RECARGA MAIS PROXIMA = " + recargaMaisProxima.toString());
        System.out.println("POSICAO DA LIXEIRA MAIS PROXIMA = " + lixeiraMaisProxima.toString());
        System.out.println("POSICAO DO AGENTE = " + this.getPosicao());
        System.out.println("ENERGIA RESTANTE = " + this.getEnergiaRestante());
        System.out.println("REPOSITORIO INTERNO DE LIXO = " + this.getLixoColetado());
        System.out.println("------------------------------------------------");
    }

    /* Realiza a próxima ação do agente */
    public boolean atualizar() {
        //Se a energia do agente chegou a zero, ele para de funcionar e o programa encerra.
        if (this.getEnergiaRestante() <= 0) {
            System.out.println("A ENERGIA DO AGENTE CHEGOU A ZERO! =[");
            return true;
        }

        //O agente solicita ao ambiente a posição da lixeira mais próxima e da recarga mais próxima.
        solicitarDadosDaLixeiraMaisProxima();
        solicitarDadosDaRecargaMaisProxima();

        //Se existe lixo na posição do agente e ainda existe espaço no repositório, o lixo é coletado.
        if (existeSujeiraNaPosicaoDoAgente()) {
            if (possoColetarMaisLixo()) {
                this.aspirar();
            }
        }

        //se o agente precisa decarregar o lixo
        if ((!possoColetarMaisLixo())
                && (modoAEstrelaAtivado == false)
                && (modoAEstrelaLixeiraAtivado == false)
                && (modoAEstrelaRecargaAtivado == false)) {
            //se o agente possui energia o suficiente para ir até a lixeira
            if (getDistanciaDaLixeiraMaisProxima() < (getEnergiaRestante() - (ambiente.getTamanhoMatriz() * 2))) {
                //Guarda estado do agente
                salvarInformacoesDoAgente();
                //Executa o cálculo A* para determinar a lista de movimentos do agente
                caminhoAEstrelaIndo = aStarBuscandoLixeira(this.getPosicao(), lixeiraMaisProxima.getPosicao());
                caminhoAEstrelaVoltando = aStarBuscandoLixeira(lixeiraMaisProxima.getPosicao(), this.getPosicao());
                //Ativa o modo A*
                iteradorCaminhoAEstrela = 0;
                modoAEstrelaAtivado = true;
                modoAEstrelaLixeiraAtivado = true;
                aEstrelaIndo = true;
                //System.out.println("VOU/ENTREI NO MODO A* ESTRELA! =]");
            } //Se não possui energia o suficiente para chegar até a lixeira, deve primeiro recarregar a bateria 
            else {
                //Guarda o estado do agente
                salvarInformacoesDoAgente();
                //Executa o cálculo A* para determinar a lista de movimentos do agente
                caminhoAEstrelaIndo = aStarBuscandoRecarga(this.getPosicao(), recargaMaisProxima.getPosicao());
                caminhoAEstrelaVoltando = aStarBuscandoRecarga(recargaMaisProxima.getPosicao(), this.getPosicao());
                //Ativa o modo A*
                iteradorCaminhoAEstrela = 0;
                modoAEstrelaAtivado = true;
                modoAEstrelaRecargaAtivado = true;
                aEstrelaIndo = true;
                //System.out.println("VOU/ENTREI NO MODO A* ESTRELA! =]");
            }
        }

        //Se o agente precisa recarregar a bateria
        if ((getDistanciaDaRecargaMaisProxima() > (getEnergiaRestante() - (ambiente.getTamanhoMatriz() * 2)))
                && (modoAEstrelaAtivado == false)
                && (modoAEstrelaLixeiraAtivado == false)
                && (modoAEstrelaRecargaAtivado == false)) {
            //Guarda estado do agente
            salvarInformacoesDoAgente();
            //Executa o cálculo A* para determinar a lista de movimentos do agente
            caminhoAEstrelaIndo = aStarBuscandoRecarga(this.getPosicao(), recargaMaisProxima.getPosicao());
            caminhoAEstrelaVoltando = aStarBuscandoRecarga(recargaMaisProxima.getPosicao(), this.getPosicao());
            //Ativa o modo A*
            iteradorCaminhoAEstrela = 0;
            modoAEstrelaAtivado = true;
            modoAEstrelaRecargaAtivado = true;
            aEstrelaIndo = true;
            //System.out.println("VOU/ENTREI NO MODO A* ESTRELA! =]");
        }

        //ESTADO -> A*_LIXEIRA [O AGENTE ESTÁ EXECUTANDO OS PASSOS DO A* PARA CHEGAR NA LIXEIRA MAIS PRÓXIMA]
        if (modoAEstrelaLixeiraAtivado == true) {
            log();

            //Se o agente está fazendo o caminho de ida
            if (aEstrelaIndo == true) {
                //Executa o próximo movimento da lista de passos do A*
                if ((caminhoAEstrelaIndo != null) && (caminhoAEstrelaIndo.size() > 1) && (iteradorCaminhoAEstrela < caminhoAEstrelaIndo.size() - 1)) {
                    realizarMovimento(caminhoAEstrelaIndo.get(iteradorCaminhoAEstrela));
                    iteradorCaminhoAEstrela++;
                } //Se chegou no final do caminho, descarrega o lixo na lixeira
                else {
                    //Ativa o caminho de volta do A*
                    aEstrelaIndo = false;
                    aEstrelaVoltando = true;
                    //Descarrega o lixo
                    descarregarLixo();
                    iteradorCaminhoAEstrela = 1;
                    //System.out.println("ACABEI DE DESCARREGAR O LIXO! =]");
                }
            } //Se o agente está fazendo o caminho de volta
            else if (aEstrelaVoltando == true) {
                //Executa o próximo movimento da lista de passos do A*
                if ((caminhoAEstrelaVoltando != null) && (caminhoAEstrelaVoltando.size() > 1) && (iteradorCaminhoAEstrela < caminhoAEstrelaVoltando.size())) {
                    realizarMovimento(caminhoAEstrelaVoltando.get(iteradorCaminhoAEstrela));
                    iteradorCaminhoAEstrela++;
                } //Se chegou no final do caminho, restaura os estado anterior do agente
                else {
                    //Desativao modo A*
                    aEstrelaIndo = false;
                    aEstrelaVoltando = false;
                    modoAEstrelaLixeiraAtivado = false;
                    modoAEstrelaAtivado = false;
                    iteradorCaminhoAEstrela = 0;
                    //Restaura estado anterior do agente
                    restaurarInformacoesDoAgente();
                    //System.out.println("ACABEI DE VOLTAR PARA A POSICAO ONDE EU ESTAVA! =]");
                }
            }
        } //ESTADO -> A*_RECARGA [O AGENTE ESTÁ EXECUTANDO OS PASSOS DO A* PARA CHEGAR NA RECARGA MAIS PRÓXIMA]
        else if (modoAEstrelaRecargaAtivado == true) {
            log();

            //Se o agente está fazendo o caminho de ida
            if (aEstrelaIndo == true) {
                //Executa o próximo movimento da lista de passos do A*
                if ((caminhoAEstrelaIndo != null) && (caminhoAEstrelaIndo.size() > 1) && (iteradorCaminhoAEstrela < caminhoAEstrelaIndo.size() - 1)) {
                    realizarMovimento(caminhoAEstrelaIndo.get(iteradorCaminhoAEstrela));
                    iteradorCaminhoAEstrela++;
                } //Se chegou no final do caminho, recarrega a bateria do agente
                else {
                    //Ativa o caminho de volta do A*
                    aEstrelaIndo = false;
                    aEstrelaVoltando = true;
                    //Recarrega a bateria do agente
                    this.recarregar();
                    iteradorCaminhoAEstrela = 1;
                    //System.out.println("ACABEI DE RECARREGAR A BATERIA! =]");
                }
            } //Se o agente está fazendo o caminho de volta
            else if (aEstrelaVoltando == true) {
                //Executa o próximo movimento da lista de passos do A*
                if ((caminhoAEstrelaVoltando != null) && (caminhoAEstrelaVoltando.size() > 1) && (iteradorCaminhoAEstrela < caminhoAEstrelaVoltando.size())) {
                    realizarMovimento(caminhoAEstrelaVoltando.get(iteradorCaminhoAEstrela));
                    iteradorCaminhoAEstrela++;
                } //Se chegou no final do caminho, restaura os estado anterior do agente
                else {
                    //Desativa o modo A*
                    aEstrelaIndo = false;
                    aEstrelaVoltando = false;
                    modoAEstrelaRecargaAtivado = false;
                    modoAEstrelaAtivado = false;
                    iteradorCaminhoAEstrela = 0;
                    //Restaura o estado anterior do agente
                    restaurarInformacoesDoAgente();
                    //System.out.println("ACABEI DE VOLTAR PARA A POSICAO ONDE EU ESTAVA! =]");
                }
            }
        } //ESTADO -> VARRENDO AMBIENTE [O AGENTE ESTÁ VARRENDO O AMBIENTE]
        else if (varreduraPorLinhaNaEsquerdaConcluida == false) {
            varreduraDeLinhasPelaEsquerda();
        } else if (efetuarTransicao == true) {
            //System.out.println("DEVO COMECAR A TRANSICAO!");
            transicaoParaVarreduraPelaDireita();
        } else if (varreduraPorLinhaNaDireitaConcluida == false) {
            //System.out.println("DEVO VARRER PELA DIREITA!");
            varreduraDeLinhasPelaDireita();
        } else if (varrerPorColunas == true) {
            varreduraPorColunas();
        }

        return varreduraCompletamenteConcluida;
    }

    /*  Implementação do algoritmo A* (retorna uma lista de movimentos) */
    public ArrayList<Character> aStar(Posicao posicaoDoAgente, Posicao posicaoDoDestino) {
        posicaoAlvo = posicaoDoDestino.getPosicao();
        posicaoOrigem = posicaoDoAgente.getPosicao();
        int n = ambiente.getTamanhoMatriz();

        //Matriz que indica o algoritmo quais estados já foram visitados
        boolean[][] marked_matrix = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                marked_matrix[i][j] = false;
            }
        }

        //Fila de Prioridades usada no algoritmo
        FilaDePrioridades priority_queue = new FilaDePrioridades();

        //Se a posição inicial é igual a posição final, encerra algoritmo        
        if (solicitarCalculoDeHeuristicaAoAmbiente(posicaoDoAgente, posicaoDoDestino) == 0) {
            return new ArrayList<Character>();
        }

        //Cria o nodo com a posição inicial e o adiciona na fila de prioridades         
        Nodo start_node = new Nodo();
        start_node.setPosition(posicaoDoAgente.getPosicao());
        priority_queue.addToQueue(start_node);

        //Este procedimento encerra quando remove o nodo objetivo da fila, ou quando não existem mais nodos na mesma
        while (priority_queue.getSize() > 0) {
            //Remove o nodo de maior prioridade da fila
            Nodo node_removed = priority_queue.removeFromQueue();

            //Se o retorno da heurística para esse nodo for zero, então o algoritmo já pode encerrar
            if (solicitarCalculoDeHeuristicaAoAmbiente(node_removed.getPosition(), posicaoDoDestino) == 0) {
                return node_removed.getPath();
            }

            //Verifica quais são os sucessores do nodo removido da fila
            ArrayList<Sucessor> sucessores = calcularSucessores(node_removed.getPosition());

            //Itera sobre os sucessores do nodo removido
            for (Sucessor sucessor : sucessores) {
                int sucessor_linha = sucessor.getPosicao().getLinha();
                int sucessor_coluna = sucessor.getPosicao().getColuna();

                //Executa o código a seguir somente se esta posição do mapa ainda não foi explorada
                if (marked_matrix[sucessor_linha][sucessor_coluna] == false) {
                    //Cria um nodo para essa posicao do mapa
                    Nodo sucessor_nodo = new Nodo();
                    sucessor_nodo.setPosition(new Posicao(sucessor_linha, sucessor_coluna));

                    //Determina a prioridade deste nodo, calculando g(n) + h(n)
                    sucessor_nodo.setDistance_until_here(node_removed.getDistance_until_here() + 1);
                    sucessor_nodo.setPriority(sucessor_nodo.getDistance_until_here()
                            + solicitarCalculoDeHeuristicaAoAmbiente(sucessor_nodo.getPosition(),
                                    posicaoDoDestino));

                    //Armazena o caminho nodo
                    for (int i = 0; i < node_removed.getPath().size(); i++) {
                        sucessor_nodo.getPath().add(node_removed.getPath().get(i));
                    }
                    sucessor_nodo.getPath().add(sucessor.getDirecao());

                    //Armazena o número de passos do caminho do nodo
                    sucessor_nodo.setNumber_of_moves(sucessor_nodo.getPath().size());

                    //Adiciona o nodo na fila de prioridades
                    priority_queue.addToQueue(sucessor_nodo);

                    //se esta não é a posição objetivo, ela é marcada para que não seja explorada novamente
                    if (solicitarCalculoDeHeuristicaAoAmbiente(sucessor.getPosicao(), posicaoDoDestino) != 0) {
                        marked_matrix[sucessor_linha][sucessor_coluna] = true;
                    }
                }
            }
        }

        return null;
    }

    /* Varre o ambiente por linhas, da esuqerda para a direita. */
    public void varreduraDeLinhasPelaEsquerda() {
        this.observarAmbiente();
        this.log();

        //Se estou indo para a direita
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
                    //System.out.println("FINAL DA VARREDURA PELA ESQUERDA!");
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

    /* Varre o ambiente por linhas, da direita para a esquerda. */
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
                    //System.out.println("FINAL DA VARREDURA PELA DIREITA!");
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

    /* Varre o abiente por colunas. */
    public void varreduraPorColunas() {
        //System.out.println("VARRENDO POR COLUNAS!");

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
                //System.out.println("REALIZAR MOVIMENTO PARA CIMA!");
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

                    //na organização do algoritmo está é a condição de encerramento da varredura
                    varreduraCompletamenteConcluida = true;
                    //System.out.println("A VARREDURA FOI CONCLUIDA COM EXITO!");
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

    /* Desloca o agente até o canto superior direito. */
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

    /* Realiza um movimento de acordo o valor da variável direção. */
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

    /* Calcula e retorna uma lista sucessores de uma posição alvo. */
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

    /* Esta classe interna representa um sucessor */
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
