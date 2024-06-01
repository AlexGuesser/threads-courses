package servidor;

import java.io.IOException;

public class Principal {

    public static void main(String[] args) throws IOException {
        ServidorTarefas servidor = new ServidorTarefas();
        servidor.rodar();
    }

}
