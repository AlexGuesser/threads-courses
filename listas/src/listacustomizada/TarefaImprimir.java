package listacustomizada;

import java.util.stream.IntStream;

public class TarefaImprimir implements Runnable {
    private final Lista lista;

    public TarefaImprimir(Lista lista) {
        this.lista = lista;
    }

    @Override
    public void run() {
        synchronized (lista) {
            if (!lista.estaCheia()) {
                try {
                    lista.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            IntStream.range(0, lista.tamanho()).forEach(i -> {
                System.out.println(i + " - " + lista.pegaElemento(i));
            });
        }
    }
}
