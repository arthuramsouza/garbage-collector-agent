import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Ambiente {
	private int tamanhoMatriz;
	private int quantidadeLixeiras;
	private int quantidadeRecargas;
	private char matriz[][];

	List<Agente> agentes;

	private final String ANSI_CLS = "\u001b[2J";
	private final String ANSI_HOME = "\u001b[H";
	public static final char NULO = 'N';
	public static final char SUJEIRA = 'S';
	public static final char LIXEIRA = 'L';
	public static final char RECARGA = 'R';
	public static final char PAREDE = 'P';
	public static final char LIMPO = '.';

	private int gerador;
	Random random = new Random();

	/* Construtor da classe */
	public Ambiente(int tamanhoMatriz, int quantidadeLixeiras, int quantidadeRecargas) {
		agentes = new ArrayList<Agente>();
		this.tamanhoMatriz = tamanhoMatriz;
		this.quantidadeLixeiras = quantidadeLixeiras;
		this.quantidadeRecargas = quantidadeRecargas;
		this.matriz = new char[tamanhoMatriz][tamanhoMatriz];
		carregar();
	}

	/*
	 * TODO Coloca elementos (lixeiras, lixo e carregadores) de forma randomica
	 * no ambiente
	 */
	private void carregar(int n_lixeiras, int n_carregadores, int n_lixo) {
		for (int i = 0; i < tamanhoMatriz; i++)
			for (int j = 0; j < tamanhoMatriz; j++)
				matriz[i][j] = LIMPO;
	}

	/* Coloca elementos no ambiente - estatico */
	private void carregar() {
		for (int i = 0; i < tamanhoMatriz; i++) {
			for (int j = 0; j < tamanhoMatriz; j++) {
				matriz[i][j] = LIMPO;
			}
		}

		inserirParedes();
		inserirLixeiras();
		inserirSujeiras();
	}

	/* Insere paredes no ambiente */
	public void inserirParedes() {
		// auxiliares para adicionar as bordas na parede
		boolean bordaCimaPosicaoDir = false;
		boolean bordaCimaPosicaoEsq = false;
		boolean bordaBaixoPosicaoDir = false;
		boolean bordaBaixoPosicaoEsq = false;
		// auxiliares para adicionar as paredes
		int posicaoDir = this.tamanhoMatriz - 4;
		int posicaoEsq = (this.tamanhoMatriz - 1) - posicaoDir;

		for (int i = 0; i < tamanhoMatriz; i++) {
			for (int j = 0; j < tamanhoMatriz; j++) {
				if (i > 1 && i < this.tamanhoMatriz - 2) {

					this.matriz[i][posicaoDir] = PAREDE;
					this.matriz[i][posicaoEsq] = PAREDE;

					if (!bordaCimaPosicaoDir) {
						this.matriz[i][posicaoDir + 1] = PAREDE;
						bordaCimaPosicaoDir = true;
					}
					if (!bordaCimaPosicaoEsq) {
						this.matriz[i][posicaoEsq - 1] = PAREDE;
						bordaCimaPosicaoEsq = true;
					}
					if (!bordaBaixoPosicaoDir) {
						if (i == (this.tamanhoMatriz - 2) - 1) {
							this.matriz[i][posicaoDir + 1] = PAREDE;
							bordaBaixoPosicaoDir = true;
						}
					}
					if (!bordaBaixoPosicaoEsq) {
						if (i == (this.tamanhoMatriz - 2) - 1) {
							this.matriz[i][posicaoEsq - 1] = PAREDE;
							bordaBaixoPosicaoEsq = true;
						}
					}
				}
			}
		}
	}

	// TODO: adicionar lixeiras randomicas considerando as paredes
	public void inserirLixeiras() {
		int posicaoDir = this.tamanhoMatriz - 4;
		int posicaoEsq = (this.tamanhoMatriz - 1) - posicaoDir;
		int auxQuantidadeLixeiras = this.quantidadeLixeiras;
		boolean adicionou = false;

		for (int i = 0; i < tamanhoMatriz; i++) {
			for (int j = 0; j < tamanhoMatriz; j++) {
				while (auxQuantidadeLixeiras != 0) {
						this.gerador = random.nextInt(tamanhoMatriz);
						System.out.println("gerador: " + this.gerador);
						System.out.println("posicaoDir: " + posicaoDir);
						System.out.println("posicaoLivre: " + posicaoLivre(this.gerador, posicaoDir));
						this.matriz[this.gerador][this.gerador] = LIXEIRA;
						auxQuantidadeLixeiras--;
						break;
				}
			}
		}
	}

	// TODO: adicionar sujeiras randomicas considerando as paredes
	public void inserirSujeiras() {

	}

	public boolean posicaoLivre(int i, int j) {
		return this.matriz[i][j] != PAREDE && this.matriz[i][j] != SUJEIRA && this.matriz[i][j] != RECARGA;
	}

	/* Insere agente no ambiente */
	public void inserirAgente(Agente agente) {
		agentes.add(agente);
	}

	/* Imprime o estado atual do ambiente com seus agentes */
	public void print() {
		for (int i = 0; i < tamanhoMatriz; i++) {
			for (int j = 0; j < tamanhoMatriz; j++) {

				// Verifica se algum agente esta nesta Posicao
				for (Agente ag : agentes) {
					if (ag.getX() == j && ag.getY() == i)
						System.out.print("A ");
					else
						System.out.print(matriz[i][j] + " ");

				}
			}
			System.out.println();
		}
	}

	/* Executa a simulacao dos agentes no ambiente */
	public void simular() {
		System.out.print(ANSI_CLS + ANSI_HOME);
		while (true) {
			print();
			try {
				Thread.sleep(500);
				System.out.print(ANSI_CLS + ANSI_HOME);
				System.out.flush();

				for (Agente ag : agentes) {
					char atual, esq, dir, cima, baixo;
					atual = matriz[ag.getY()][ag.getX()];

					if ((ag.getX() - 1) < 0)
						esq = NULO;
					else
						esq = matriz[ag.getY()][ag.getX() - 1];

					if ((ag.getX() + 1) >= tamanhoMatriz)
						dir = NULO;
					else
						dir = matriz[ag.getY()][ag.getX() + 1];

					if ((ag.getY() - 1) < 0)
						cima = NULO;
					else
						cima = matriz[ag.getY() - 1][ag.getX()];

					if ((ag.getY() + 1) >= tamanhoMatriz)
						baixo = NULO;
					else
						baixo = matriz[ag.getY() + 1][ag.getX()];

					ag.atualizar(atual, esq, dir, cima, baixo);
				}

			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

}
