package src.br.com.alura.threads.desafios;

public class ImprimeStringPrincipal {

    public static void main(String[] args) {
        Thread thread = new Thread(
                new ImprimeStringRunnable("Opa, estou sendo imprimido a partir da thread ImprimeStringRunnable!"),
                ImprimeStringRunnable.class.getSimpleName()
        );
        thread.start();
    }

}
