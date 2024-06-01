public class PrincipalBanco {

    public static void main(String[] args) {

        GerenciadorDeTransacao tx = new GerenciadorDeTransacao();
        PoolDeConexao pool = new PoolDeConexao();

        new Thread(new TarefaAccessaBanco(pool, tx)).start();
        new Thread(new TarefaAccessaBancoProcedimento(pool, tx)).start();

    }

}
