
public class App {

    public static void main(String[] args) {
        Ambiente ambiente = new Ambiente(Integer.parseInt(args[0]), 3, 3);
        ambiente.inserirAgente(new Agente(0, 0, 'd', 0, ambiente));
        ambiente.simular();
    }
}
