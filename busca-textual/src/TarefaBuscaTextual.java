import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TarefaBuscaTextual implements Runnable {

    private final String fileName;
    private final String nome;

    public TarefaBuscaTextual(String fileName, String nome) {
        this.fileName = fileName;
        this.nome = nome;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            int numeroLinha = 1;

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();

                if (linha.toLowerCase().contains(nome.toLowerCase())) {
                    System.out.println(fileName + " na linha:" + numeroLinha + " - " + linha);
                }

                numeroLinha++;
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
