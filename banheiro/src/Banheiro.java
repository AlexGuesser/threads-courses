public class Banheiro {

    private boolean estaSujo = true;

    public void fazerNumero1() {
        baterNaPorta();

        synchronized (this) {
            entrarNoBanheiro();

            while (estaSujo) {
                esperarLimpeza();
            }

            log("Fazendo número 1");
            dormir(5000);
            this.estaSujo = true;

            lavarAsMaos();
            sairDoBanheiro();
        }
    }


    public void fazerNumero2() {
        baterNaPorta();

        synchronized (this) {
            entrarNoBanheiro();

            while (estaSujo) {
                esperarLimpeza();
            }

            log("Fazendo número 2");
            dormir(10000);
            this.estaSujo = true;

            lavarAsMaos();
            sairDoBanheiro();
        }
    }

    private void lavarAsMaos() {
        log("Lavando as mãos");
    }

    private void entrarNoBanheiro() {
        log("Entrando no banheiro");
    }

    public void limpar() {
        baterNaPorta();

        synchronized (this) {
            entrarNoBanheiro();

            if (!estaSujo) {
                log("Banheiro está limpo");
                sairDoBanheiro();
                return;
            }

            log("Limpando banheiro...");
            dormir(13000);
            this.estaSujo = false;

            sairDoBanheiro();
            this.notifyAll();
        }
    }

    private static void dormir(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void sairDoBanheiro() {
        log("Saindo do banheiro");
    }

    private void baterNaPorta() {
        String nome = Thread.currentThread().getName();
        log(nome + " batendo na porta");
    }

    private void log(String msg) {
        System.out.println(Thread.currentThread().getName() + ": " + msg);
    }

    private void esperarLimpeza() {
        log("Banheiro está sujo! Vou esperar alguém limpá-lo.");
        try {
            this.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
