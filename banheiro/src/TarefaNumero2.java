public class TarefaNumero2 implements Runnable {

    private final Banheiro banheiro;

    public TarefaNumero2(Banheiro banheiro) {
        this.banheiro = banheiro;
    }

    @Override
    public void run() {
        banheiro.fazerNumero2();

    }
}
