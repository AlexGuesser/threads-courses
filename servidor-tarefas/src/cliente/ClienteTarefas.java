package cliente;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefas {

    public static void main(String[] args) throws Exception {

        try (
                Socket socket = new Socket("localhost", 12345)
        ) {
            System.out.println("Conexão estabelecida");

            Thread threadEnviaComandos = enviarComandosParaServidor(socket);
            Thread threadRecebeComandos = receberComandosDoServidor(socket);

            threadEnviaComandos.join();
        }

    }

    private static Thread receberComandosDoServidor(Socket socket) {
        Thread thread = new Thread(() -> {
            try (
                    Scanner entrada = new Scanner(socket.getInputStream())
            ) {
                System.out.println("Pronto para receber dados do servidor");
                while (entrada.hasNextLine()) {
                    String linha = entrada.nextLine();
                    System.out.println(linha);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
        return thread;
    }

    private static Thread enviarComandosParaServidor(Socket socket) {
        Thread thread = new Thread(() -> {
            try (
                    PrintStream saida = new PrintStream(socket.getOutputStream());
                    Scanner teclado = new Scanner(System.in);
            ) {
                System.out.println("Pronto para enviar dados para o servidor");
                while (teclado.hasNextLine()) {
                    String comando = teclado.nextLine().trim().toLowerCase();

                    if (comando.equals("")) {
                        break;
                    }

                    saida.println(comando);
                    if(comando.equals("fim")) {
                        break;
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
        return thread;
    }

}
