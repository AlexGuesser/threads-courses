package listacustomizada;

import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Lista lista = new Lista();
        IntStream.range(0, 10).forEach(i -> {
            new Thread(new TarefaAdicionarElemento(lista, i)).start();
        });

        new Thread(new TarefaImprimir(lista)).start();
    }
}