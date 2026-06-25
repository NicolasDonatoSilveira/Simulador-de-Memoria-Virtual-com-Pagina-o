public class PaginaInfo {

    private int frame;
    private boolean presente;

    public PaginaInfo() {
        this.frame = -1;
        this.presente = false;
    }

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }
}
