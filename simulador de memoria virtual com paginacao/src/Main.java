public class Main {

    public static void main(String[] args)
            throws Exception {

        MMU mmu = new MMU();

        Processo p1 =
                new Processo(1, mmu);

        Processo p2 =
                new Processo(2, mmu);

        p1.start();
        p2.start();

        p1.join();
        p2.join();

        System.out.println(
                "\nSimulacao encerrada.");
    }
}
