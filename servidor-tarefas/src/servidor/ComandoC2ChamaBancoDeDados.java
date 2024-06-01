package servidor;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2ChamaBancoDeDados implements Callable<String> {

    private final PrintStream saidaCliente;

    public ComandoC2ChamaBancoDeDados(PrintStream saidaCliente) {
        this.saidaCliente = saidaCliente;
    }

    @Override
    public String call() throws Exception {
        saidaCliente.println("Executando comando c2 Banco de Dados");

        Thread.sleep(14000);

        int numero = new Random().nextInt(100) + 1;

        saidaCliente.println("Comando c2 Banco de Dados executado com sucesso");
        return Integer.toString(numero);
    }
}
