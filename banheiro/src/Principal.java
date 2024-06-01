public class Principal {

    public static void main(String[] args) {
        Banheiro banheiro = new Banheiro();

        Thread convidado1 = new Thread(new TarefaNumero1(banheiro), "João");
        convidado1.setPriority(Thread.MIN_PRIORITY);

        Thread convidado2 = new Thread(new TarefaNumero2(banheiro), "Pedro");
        convidado2.setPriority(Thread.MIN_PRIORITY);

        Thread limpeza = new Thread(new TarefaLimpeza(banheiro), "Limpeza");
        limpeza.setDaemon(true);
        limpeza.setPriority(Thread.MAX_PRIORITY);

        convidado1.start();
        convidado2.start();
        limpeza.start();
    }

}
