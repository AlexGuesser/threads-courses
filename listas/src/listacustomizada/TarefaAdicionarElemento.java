package listacustomizada;

import java.util.stream.IntStream;

public class TarefaAdicionarElemento implements Runnable {
    private final Lista lista;
    private final int numeroDaThread;

    public TarefaAdicionarElemento(Lista lista, int numeroDaThread) {
        this.lista = lista;
        this.numeroDaThread = numeroDaThread;
    }

    @Override
    public void run() {
        IntStream.range(0, 100).forEach(i -> {
            lista.adicionaElementos("Thread: " + numeroDaThread + " - " + i);
        });
    }
}
