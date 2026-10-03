
public class Item {

    private String descricao;
    private double valorUnitario;
    private int quantidade;
    private double total;

    public Item(String descricao, double valorUnitario, int quantidade) {
        this.descricao = descricao;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
        this.total = calcularTotal();
    }

    public double calcularTotal() {
        return valorUnitario * quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getTotal() {
        return total;
    }
}
