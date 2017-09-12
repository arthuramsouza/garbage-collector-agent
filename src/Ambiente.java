import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Ambiente {
	private static int tamanhoMatriz;
	private int quantidadeLixeiras;
	private int quantidadeRecargas;
	private static char matriz[][];
	private static List<Ponto> lixeiras, carregadores;

	List<Agente> agentes;

	public static final char NULO = 'N';
	public static final char SUJEIRA = 's';
	public static final char LIXEIRA = 'L';
	public static final char RECARGA = 'R';
	public static final char PAREDE = 'P';
	public static final char LIMPO = '.';

	public static final int WAIT_TIME = 50;

	private int gerador;
	Random random = new Random();

	/* Construtor da classe */
	public Ambiente(int tamanhoMatriz, int quantidadeLixeiras, int quantidadeRecargas) {
		agentes = new ArrayList<Agente>();
		this.tamanhoMatriz = tamanhoMatriz;
		this.quantidadeLixeiras = quantidadeLixeiras;
		this.quantidadeRecargas = quantidadeRecargas;
		this.matriz = new char[tamanhoMatriz][tamanhoMatriz];
		lixeiras = new ArrayList<Ponto>();
		carregadores = new ArrayList<Ponto>();
		carregar();
	}

	/* Coloca elementos no ambiente - estatico */
	private void carregar() {
		for (int i = 0; i < tamanhoMatriz; i++) {
			for (int j = 0; j < tamanhoMatriz; j++) {
				matriz[i][j] = LIMPO;	// linha de producao
				//matriz[i][j] = SUJEIRA; // dev only
			}
		}

		inserirParedes();
		//Desenvolvimento - apagar depois
		/*
		matriz[6][0] = LIXEIRA;
		matriz[3][2] = LIXEIRA;
		matriz[4][2] = LIXEIRA;
		matriz[7][0] = LIXEIRA;
		matriz[7][3] = LIXEIRA;
		matriz[6][11] = LIXEIRA;
		matriz[3][0] = LIXEIRA;
		matriz[3][3] = LIXEIRA;
		*/
		inserirLixeiras();
		inserirRecargas();
		inserirSujeiras();
	}

	/* Insere paredes no ambiente */
	public void inserirParedes() {
		// auxiliares para adicionar as bordas na parede
		boolean bordaCimaPosicaoDir = false;
		boolean bordaCimaPosicaoEsq = false;
		boolean bordaBaixoPosicaoDir = false;
		boolean bordaBaixoPosicaoEsq = false;

		int divisao = this.tamanhoMatriz / 3;
		int inicio = (divisao / 2);
		int fim = this.tamanhoMatriz - (divisao / 2);

		// auxiliares para adicionar as paredes
		int posicaoEsq = divisao - 1;
		int posicaoDir = this.tamanhoMatriz - divisao;

		for (int i = inicio; i < fim; i++) {
			this.matriz[i][posicaoEsq] = PAREDE;
			this.matriz[i][posicaoDir] = PAREDE;

			if (!bordaCimaPosicaoDir) {
				this.matriz[i][posicaoDir + 1] = PAREDE;
				bordaCimaPosicaoDir = true;
			}
			if (!bordaCimaPosicaoEsq) {
				this.matriz[i][posicaoEsq - 1] = PAREDE;
				bordaCimaPosicaoEsq = true;
			}
			if (!bordaBaixoPosicaoDir) {
				if (i == fim - 1) {
					this.matriz[i][posicaoDir + 1] = PAREDE;
					bordaBaixoPosicaoDir = true;
				}
			}
			if (!bordaBaixoPosicaoEsq) {
				if (i == fim - 1) {
					this.matriz[i][posicaoEsq - 1] = PAREDE;
					bordaBaixoPosicaoEsq = true;
				}
			}
		}
	}

	/* Adiciona Lixeiras ao ambiente */
	public void inserirLixeiras() {
		int divisao = this.tamanhoMatriz / 3;
		int inicio = (divisao / 2);
		int fim = this.tamanhoMatriz - (divisao / 2);

		int posicaoEsq = divisao - 1;
		int posicaoDir = this.tamanhoMatriz - divisao;

		while (this.quantidadeLixeiras > 0) {
			int posicaoLinha = random.nextInt(this.tamanhoMatriz);
			int posicaoColuna = random.nextInt(this.tamanhoMatriz);

			if (posicaoLinha > inicio && posicaoLinha < fim - 1) {
				if (posicaoColuna < posicaoEsq || posicaoColuna > posicaoDir) {
					if (posicaoLivre(posicaoLinha, posicaoColuna) && posicaoValida(posicaoLinha, posicaoColuna)) {
						this.matriz[posicaoLinha][posicaoColuna] = LIXEIRA;
						this.quantidadeLixeiras--;
						lixeiras.add(new Ponto(posicaoColuna, posicaoLinha));
						continue;
					}
				}
			}
		}
	}

	/* Adiciona recargas ao ambiente */
	public void inserirRecargas() {
		int divisao = this.tamanhoMatriz / 3;
		int inicio = (divisao / 2);
		int fim = this.tamanhoMatriz - (divisao / 2);

		int posicaoEsq = divisao - 1;
		int posicaoDir = this.tamanhoMatriz - divisao;

		while (this.quantidadeRecargas > 0) {
			int posicaoLinha = random.nextInt(this.tamanhoMatriz);
			int posicaoColuna = random.nextInt(this.tamanhoMatriz);

			if (posicaoLinha > inicio && posicaoLinha < fim - 1) {
				if (posicaoColuna < posicaoEsq || posicaoColuna > posicaoDir) {
					if (posicaoLivre(posicaoLinha, posicaoColuna) && posicaoValida(posicaoLinha, posicaoColuna)) {
						this.matriz[posicaoLinha][posicaoColuna] = RECARGA;
						this.quantidadeRecargas--;
						carregadores.add(new Ponto(posicaoColuna, posicaoLinha));
						continue;
					}
				}
			}
		}
	}

	/* Adiciona sujeiras ao ambiente */
	public void inserirSujeiras() {
		int quantidade = 0;
		double percentual1 = this.tamanhoMatriz + ((40 / 100) * this.tamanhoMatriz);
		double percentual2 = this.tamanhoMatriz + ((80 / 100) * this.tamanhoMatriz);

		int numero = random.nextInt(this.tamanhoMatriz);

		while (numero < percentual1 && numero > percentual2) {
			numero = random.nextInt(this.tamanhoMatriz);
		}

		quantidade = numero;

		while (quantidade > 0) {
			int posicaoLinha = random.nextInt(this.tamanhoMatriz);
			int posicaoColuna = random.nextInt(this.tamanhoMatriz);

			if (posicaoLivre(posicaoLinha, posicaoColuna)) {
				this.matriz[posicaoLinha][posicaoColuna] = SUJEIRA;
				quantidade--;
				continue;
			}
		}
	}

	/* Verifica posições livres */
	public boolean posicaoLivre(int i, int j) {
		return this.matriz[i][j] != PAREDE && this.matriz[i][j] != SUJEIRA && this.matriz[i][j] != RECARGA && this.matriz[i][j] != LIXEIRA;
	}

	/* Verifica se a posição é valida, ou seja, paredes ou bordas ao lado para adicionar lixeiras e pontos de recargas */
	public boolean posicaoValida(int posicaoLinha, int posicaoColuna) {
		if((posicaoColuna+1) > this.tamanhoMatriz-1 || (posicaoColuna-1) < 0)
		 	return true;
		if(this.matriz[posicaoLinha][posicaoColuna+1] == PAREDE || this.matriz[posicaoLinha][posicaoColuna-1] == PAREDE)
			return true;
		else
			return false;
	}

	/* Retorna lista com posicao das lixeiras e carregadores */
	public static List<Ponto> getLixeiras() { return lixeiras; }
	public static List<Ponto> geCarregadores() { return carregadores; }


	/* Insere agente no ambiente */
	public void inserirAgente(Agente agente) {
		agentes.add(agente);
	}

	/* Imprime o estado atual do ambiente com seus agentes */
	public void print() {
		for (int i = 0; i < tamanhoMatriz; i++) {
			System.out.print(i + "\t");
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
		System.out.println("\t");
	}

	/* Limpa uma celula passada por parametro */
	public static void limpar(int px, int py) {
		if(px < tamanhoMatriz && py < tamanhoMatriz && px >= 0 && py >= 0) {
			matriz[py][px] = LIMPO;
		}
	}

	public static char[] getEntorno(int px, int py) {
		char[] entorno = new char[9];

		for(int i = 0; i < 9; i++)
			entorno[i] = NULO;

		// Posicao corrente do agente (centro da matriz)
		entorno[Agente.AGENTE] = matriz[py][px];

		if(px > 0 && px < tamanhoMatriz - 1) {
			entorno[Agente.LEFT] = matriz[py][px - 1];
			entorno[Agente.RIGHT] = matriz[py][px + 1];

			if(py > 0 && py < tamanhoMatriz - 1) {
				entorno[Agente.UP_LEFT] = matriz[py - 1][px - 1];
				entorno[Agente.UP] = matriz[py - 1][px];
				entorno[Agente.UP_RIGHT] = matriz[py - 1][px + 1];

				entorno[Agente.DOWN_LEFT] = matriz[py + 1][px - 1];
				entorno[Agente.DOWN] = matriz[py + 1][px];
				entorno[Agente.DOWN_RIGHT] = matriz[py + 1][px + 1];
			} else if(py == 0) { // Primeira linha
				entorno[Agente.DOWN_LEFT] = matriz[py + 1][px - 1];
				entorno[Agente.DOWN] = matriz[py + 1][px];
				entorno[Agente.DOWN_RIGHT] = matriz[py + 1][px + 1];
			} else { // Ultima linha
				entorno[Agente.UP_LEFT] = matriz[py - 1][px - 1];
				entorno[Agente.UP] = matriz[py - 1][px];
				entorno[Agente.UP_RIGHT] = matriz[py - 1][px + 1];
			}
		} else if(px == 0) { // Primeira coluna
			entorno[Agente.RIGHT] = matriz[py][px + 1];

			if(py > 0 && py < tamanhoMatriz - 1) {
				entorno[Agente.UP] = matriz[py - 1][px];
				entorno[Agente.UP_RIGHT] = matriz[py - 1][px + 1];

				entorno[Agente.DOWN] = matriz[py + 1][px];
				entorno[Agente.DOWN_RIGHT] = matriz[py + 1][px + 1];
			} else if(py == 0) { // Primeira linha
				entorno[Agente.DOWN] = matriz[py + 1][px];
				entorno[Agente.DOWN_RIGHT] = matriz[py + 1][px + 1];
			} else { // Ultima linha
				entorno[Agente.UP] = matriz[py - 1][px];
				entorno[Agente.UP_RIGHT] = matriz[py - 1][px + 1];
			}
		} else { // Ultima coluna
			entorno[Agente.LEFT] = matriz[py][px - 1];

			if(py > 0 && py < tamanhoMatriz - 1) {
				entorno[Agente.UP_LEFT] = matriz[py - 1][px - 1];
				entorno[Agente.UP] = matriz[py - 1][px];

				entorno[Agente.DOWN_LEFT] = matriz[py + 1][px - 1];
				entorno[Agente.DOWN] = matriz[py + 1][px];
			} else if(py == 0) { // Primeira linha
				entorno[Agente.DOWN] = matriz[py + 1][px];
				entorno[Agente.DOWN_LEFT] = matriz[py + 1][px - 1];
			} else { // Ultima linha
				entorno[Agente.UP] = matriz[py - 1][px];
				entorno[Agente.UP_LEFT] = matriz[py - 1][px - 1];
			}
		}
		return entorno;
	}

	/* Executa a simulacao dos agentes no ambiente */
	public void simular() {
		boolean isRunning = true;

		while (isRunning) {
			try {
				print();
				Thread.sleep(WAIT_TIME);
				System.out.flush();

				for (Agente ag : agentes) {
					// Percepcoes do agente em relacao ao ambiente
					char[] entorno = new char[9];

					for(int i = 0; i < 9; i++)
						entorno[i] = NULO;

					entorno = getEntorno(ag.getX(), ag.getY());
					isRunning = ag.atualizar(entorno);
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		System.out.println("Simulacao concluida com sucesso!");
	}

}
