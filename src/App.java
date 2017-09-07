public class App {

	public static void main(String[] args) {
		if(args.length == 0) {
			System.err.println("Uso: java App <tamanho_matriz>");
			System.exit(1);
		}

		int n = 0;
		try {
			n = Integer.parseInt(args[0]);
		} catch(NumberFormatException e) {
			System.err.println("Tamanho de matriz informado invalido!");
			System.exit(1);
		}

		if(n < 12) {
			System.err.println("Tamanho minimo da matriz = 12");
			System.exit(1);
		}

		Ambiente ambiente = new Ambiente(n, 3, 3);
		ambiente.inserirAgente(new Agente(0, 0, 'd', 0));
		ambiente.simular();
	}
}
