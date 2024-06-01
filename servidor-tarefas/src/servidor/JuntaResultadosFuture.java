package servidor;

import java.io.PrintStream;
import java.util.concurrent.*;

public class JuntaResultadosFuture implements Callable<Void> {

    private final Future<String> futureWS;
    private final Future<String> futureBanco;
    private final PrintStream saidaCliente;

    public JuntaResultadosFuture(Future<String> futureWS, Future<String> futureBanco, PrintStream saidaCliente) {
        this.futureWS = futureWS;
        this.futureBanco = futureBanco;
        this.saidaCliente = saidaCliente;
    }

    @Override
    public Void call() {
        System.out.println("Aguardando resultados do future WS e Banco");
        try {
            String resultadoFutureWs = this.futureWS.get(15, TimeUnit.SECONDS);
            String resultadoFutureBanco = this.futureBanco.get(15, TimeUnit.SECONDS);
            this.saidaCliente.println("Resultado do comando c2: " + resultadoFutureWs + ", " + resultadoFutureBanco);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            saidaCliente.println("Erro ao esperar resultado do future");
            futureWS.cancel(true);
            futureBanco.cancel(true);
        }
        System.out.println("Finalizou JuntaResultadosFuture");
        return null;
    }
}
