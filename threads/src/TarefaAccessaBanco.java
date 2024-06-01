public class TarefaAccessaBanco implements Runnable {
    private final PoolDeConexao pool;
    private final GerenciadorDeTransacao tx;

    public TarefaAccessaBanco(PoolDeConexao pool, GerenciadorDeTransacao tx) {
        this.pool = pool;
        this.tx = tx;
    }

    @Override
    public void run() {
        synchronized (pool) {
            System.out.println("Peguei a chave do pool");
            String connection = pool.getConnection();

            synchronized (tx) {
                System.out.println("Começando a transação");
                tx.begin();
            }
        }
    }
}
