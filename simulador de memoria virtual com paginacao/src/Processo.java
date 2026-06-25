import java.util.Random;

public class Processo extends Thread {

    private int id;
    private MMU mmu;

    public Processo(int id, MMU mmu) {
        this.id = id;
        this.mmu = mmu;
    }

    @Override
    public void run() {

        Random random = new Random();

        for(int i=0;i<15;i++) {

            int enderecoVirtual = random.nextInt(65536);

            mmu.acessarMemoria(id, enderecoVirtual);

            try {
                Thread.sleep(1000);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
