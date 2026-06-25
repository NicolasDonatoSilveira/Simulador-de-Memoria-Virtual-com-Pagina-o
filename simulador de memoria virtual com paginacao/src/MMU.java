import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MMU {

    public static final int TAM_PAGINA = 8192;

    private Frame[] memoriaFisica;

    private Queue<String> fifo;

    private Map<Integer, PaginaInfo[]> tabelas;

    public MMU() {

        memoriaFisica = new Frame[8];

        for(int i=0;i<8;i++) {
            memoriaFisica[i] = new Frame(i);
        }

        fifo = new LinkedList<>();

        tabelas = new HashMap<>();

        tabelas.put(1, criarTabela());
        tabelas.put(2, criarTabela());
    }

    private PaginaInfo[] criarTabela() {

        PaginaInfo[] tabela = new PaginaInfo[128];

        for(int i=0;i<128;i++) {
            tabela[i] = new PaginaInfo();
        }

        return tabela;
    }

    public synchronized void acessarMemoria(int processo, int enderecoVirtual) {

        int pagina = enderecoVirtual / TAM_PAGINA;
        int offset = enderecoVirtual % TAM_PAGINA;

        System.out.println("\n=====================================");
        System.out.println("PROCESSO " + processo);
        System.out.println("Endereco Virtual: " + enderecoVirtual);
        System.out.println("Pagina: " + pagina);
        System.out.println("Offset: " + offset);

        PaginaInfo info = tabelas.get(processo)[pagina];

        if(info.isPresente()) {

            int enderecoFisico =
                    info.getFrame() * TAM_PAGINA + offset;

            System.out.println("PAGE HIT");
            System.out.println("Frame: " + info.getFrame());
            System.out.println("Endereco Fisico: " + enderecoFisico);

            return;
        }

        System.out.println("PAGE FAULT");

        carregarPagina(processo, pagina);

        info = tabelas.get(processo)[pagina];

        int enderecoFisico =
                info.getFrame() * TAM_PAGINA + offset;

        System.out.println("Endereco Fisico: " + enderecoFisico);

        mostrarFrames();
    }

    private void carregarPagina(int processo, int pagina) {

        int frameLivre = procurarFrameLivre();

        if(frameLivre != -1) {

            usarFrame(frameLivre, processo, pagina);

            return;
        }

        substituirFIFO(processo, pagina);
    }

    private int procurarFrameLivre() {

        for(Frame frame : memoriaFisica) {

            if(frame.livre()) {
                return frame.getNumero();
            }
        }

        return -1;
    }

    private void usarFrame(int frame,
                           int processo,
                           int pagina) {

        memoriaFisica[frame].setPagina(pagina);
        memoriaFisica[frame].setProcesso(processo);

        tabelas.get(processo)[pagina]
                .setFrame(frame);

        tabelas.get(processo)[pagina]
                .setPresente(true);

        fifo.add(processo + "-" + pagina);

        carregarDadosArquivo(processo,pagina);

        System.out.println("Pagina carregada no Frame "
                + frame);
    }

    private void substituirFIFO(int processo,
                                int pagina) {

        String vitima = fifo.poll();

        String[] dados = vitima.split("-");

        int processoVitima =
                Integer.parseInt(dados[0]);

        int paginaVitima =
                Integer.parseInt(dados[1]);

        int frameVitima =
                tabelas.get(processoVitima)
                        [paginaVitima]
                        .getFrame();

        tabelas.get(processoVitima)
                [paginaVitima]
                .setPresente(false);

        tabelas.get(processoVitima)
                [paginaVitima]
                .setFrame(-1);

        System.out.println(
                "Substituindo pagina "
                + paginaVitima
                + " do processo "
                + processoVitima);

        usarFrame(frameVitima,
                processo,
                pagina);
    }

    private void carregarDadosArquivo(int processo,
                                      int pagina) {

        try {

            String arquivo = "../data/processo" + processo + ".txt";

            String conteudo =
                    Files.readString(
                            Paths.get(arquivo));

            int inicio =
                    pagina * 100;

            if(inicio >= conteudo.length())
                return;

            int fim =
                    Math.min(
                            inicio + 100,
                            conteudo.length());

            String bloco =
                    conteudo.substring(
                            inicio,
                            fim);

            System.out.println(
                    "Dados carregados: "
                            + bloco.substring(
                            0,
                            Math.min(
                                    30,
                                    bloco.length()))
                            + "...");

        }
        catch(IOException e) {

                System.out.println("Erro lendo arquivo.");
                e.printStackTrace();
        }
    }

    private void mostrarFrames() {

        System.out.println("\nFrames:");

        for(Frame frame : memoriaFisica()) {

            System.out.println(
                    "Frame "
                            + frame.getNumero()
                            + " -> Processo "
                            + frame.getProcesso()
                            + " Pagina "
                            + frame.getPagina());
        }
    }

    private Frame[] memoriaFisica() {
        return memoriaFisica;
    }
}
