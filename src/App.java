public class App {

	public static void main(String[] args) {
		if(args.length == 0) {
			System.err.println("Uso: java App <tamanho_matriz> <quantidade_lixeiras> <quantidade_recargas>");
			System.exit(1);
		}

		int tamanhoMatriz = 0;
		int quantidadeLixeiras = 0;
		int quantidadeRecargas = 0;
		try {
			tamanhoMatriz = Integer.parseInt(args[0]);
			quantidadeLixeiras = Integer.parseInt(args[1]);
			quantidadeRecargas = Integer.parseInt(args[2]);
		} catch(NumberFormatException e) {
			System.err.println("quantidade de parametros e(ou) parametros informados invalido!");
			System.exit(1);
		}

		if(tamanhoMatriz < 12) {
			System.err.println("Tamanho minimo da matriz = 12");
			System.exit(1);
		}

		Ambiente ambiente = new Ambiente(tamanhoMatriz, quantidadeLixeiras, quantidadeRecargas);
		ambiente.inserirAgente(new Agente(0, 0, 'd', 0));
		ambiente.simular();
	}
}
