package listanativa;

import java.util.List;
import java.util.stream.IntStream;

public class TarefaAdicionarElemento implements Runnable {
    private final List<String> lista;
    private final int numeroDaThread;

    public TarefaAdicionarElemento(List<String> lista, int numeroDaThread) {
        this.lista = lista;
        this.numeroDaThread = numeroDaThread;
    }

    @Override
    public void run() {
        IntStream.range(0, 100).forEach(i -> {
            lista.add("Thread: " + numeroDaThread + " - " + i);
        });
    }
}
