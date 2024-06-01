package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorTarefas {

    private final ServerSocket servidor;
    private final ExecutorService threadPool;
    //private volatile boolean estaRodando;
    private final AtomicBoolean estaRodando;
    private final BlockingQueue<String> filaComandos;

    public ServidorTarefas() throws IOException {
        System.out.println("----Iniciando servidor----");
        servidor = new ServerSocket(12345);

        // Esse comando cria um pool de threads com 2 threads
        // quando uma terceira thread é criada, ela fica aguardando
        //ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Esse comando cria um pool de threads que aumenta e diminui de acordo com a demanda
        threadPool = Executors.newCachedThreadPool(
                runnable -> {
                    Thread t = new Thread(runnable);
                    t.setUncaughtExceptionHandler(
                            new TratadorDeExcecao()
                    );
                    return t;
                }
        );
        this.estaRodando = new AtomicBoolean(true);
        this.filaComandos = new ArrayBlockingQueue<>(2);
        iniciarConsumidores();
    }


    public void rodar() {
        System.out.println("Rodando servidor na porta 12345...");
        while (this.estaRodando.get()) {
            Socket socket = getSocket();
            if (socket == null) break;
            System.out.println("Aceitando novo cliente na porta " + socket.getPort());

            threadPool.execute(new DistribuirTarefas(socket, this, threadPool, filaComandos));
        }
    }


    public void parar() throws IOException {
        System.out.println("Desligando servidor");
        this.estaRodando.set(false);
        servidor.close();
        threadPool.shutdown();
        //System.exit(0);
    }

    private Socket getSocket() {
        Socket socket;
        try {
            socket = servidor.accept();
        } catch (IOException e) {
            if (!this.estaRodando.get()) {
                System.out.println("Servidor foi desligado");
                return null;
            }
            throw new RuntimeException(e);
        }
        return socket;
    }

    private void iniciarConsumidores() {
        int qtdConsumidores = 2;
        for (int i = 0; i < qtdConsumidores; i++) {
            TarefaConsumir tarefa = new TarefaConsumir(filaComandos);
            threadPool.execute(tarefa);
        }
    }

}
