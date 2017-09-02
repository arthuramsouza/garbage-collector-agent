public class App {

	public static void main(String[] args) {
		Ambiente ambiente = new Ambiente(12, 3, 3);
		ambiente.inserirAgente(new Agente(0, 0, 'd', 0));
		ambiente.simular();
		// System.out.println("Hello World");
	}

}
