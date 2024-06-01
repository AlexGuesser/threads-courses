package listanativa;

import java.util.List;
import java.util.Vector;
import java.util.stream.IntStream;

public class Principal {
    public static void main(String[] args) throws InterruptedException {
        // ArrayList por padrão não é sincronizado
        //List<String> lista = Collections.synchronizedList(new ArrayList<>());
        List<String> lista = new Vector<>();
        IntStream.range(0, 10).forEach(i -> {
            new Thread(new TarefaAdicionarElemento(lista, i)).start();
        });

        Thread.sleep(2000);

        IntStream.range(0, lista.size()).forEach(i -> {
            System.out.println(i + " - " + lista.get(i));
        });
    }
}