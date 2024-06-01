package servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class DistribuirTarefas implements Runnable {
    private final Socket socket;
    private final ServidorTarefas servidorTarefas;
    private final ExecutorService threadPool;
    private final BlockingQueue<String> filaComandos;

    public DistribuirTarefas(
            Socket socket,
            ServidorTarefas servidorTarefas,
            ExecutorService threadPool,
            BlockingQueue<String> filaComandos
    ) {
        this.socket = socket;
        this.servidorTarefas = servidorTarefas;
        this.threadPool = threadPool;
        this.filaComandos = filaComandos;
    }

    @Override
    public void run() {
        try (
                Scanner entradaCliente = new Scanner(socket.getInputStream());
                PrintStream saidaCliente = new PrintStream(socket.getOutputStream());
        ) {
            System.out.println("Distribuindo tarefas para " + socket);
            while (entradaCliente.hasNextLine()) {
                String comando = entradaCliente.nextLine();
                System.out.println("Comando recebido: " + comando);

                switch (comando) {
                    case "c1" -> {
                        saidaCliente.println("Confirmação do comando c1");
                        ComandoC1 c1 = new ComandoC1(saidaCliente);
                        threadPool.execute(c1);
                    }
                    case "c2" -> {
                        saidaCliente.println("Confirmação do comando c2");
                        ComandoC2ChamaWS c2WS = new ComandoC2ChamaWS(saidaCliente);
                        ComandoC2ChamaBancoDeDados c2Banco = new ComandoC2ChamaBancoDeDados(saidaCliente);
                        Future<String> futureWS = threadPool.submit(c2WS);
                        Future<String> futureBanco = threadPool.submit(c2Banco);
                        threadPool.submit(
                                new JuntaResultadosFuture(futureWS, futureBanco, saidaCliente)
                        );
                    }
                    case "c3" -> {
                        this.filaComandos.put(comando); //blocks the thread if the queue is full
                        saidaCliente.println("Comando c3 adicionado na fila!");
                    }
                    case "fim" -> {
                        saidaCliente.println("Confirmação do comando fim, desligando servidor!");
                        servidorTarefas.parar();
                    }
                    default -> saidaCliente.println("Comando não encontrado");
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
