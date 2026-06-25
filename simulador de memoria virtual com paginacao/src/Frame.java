public class Frame {

    private int numero;
    private int pagina;
    private int processo;

    public Frame(int numero) {
        this.numero = numero;
        this.pagina = -1;
        this.processo = -1;
    }

    public int getNumero() {
        return numero;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getProcesso() {
        return processo;
    }

    public void setProcesso(int processo) {
        this.processo = processo;
    }

    public boolean livre() {
        return pagina == -1;
    }
}
