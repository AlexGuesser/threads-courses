package src.br.com.alura.threads.desafios;

public class ImprimeStringRunnable implements Runnable {

    private final String texto;

    public ImprimeStringRunnable(String texto) {
        this.texto = texto;
    }

    @Override
    public void run() {
        System.out.println(texto);
    }

}
