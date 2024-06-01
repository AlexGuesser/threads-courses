public class TarefaNumero1 implements Runnable {

    private final Banheiro banheiro;

    public TarefaNumero1(Banheiro banheiro) {
        this.banheiro = banheiro;
    }

    @Override
    public void run() {
        banheiro.fazerNumero1();
    }
}
