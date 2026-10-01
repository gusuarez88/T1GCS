import java.time.LocalDate;
import java.util.ArrayList;

public class Pedido {

    private int id;
    private Funcionario funcionarioSolicitante;
    private Departamento departamentoSolicitante;
    private LocalDate dataPedido;
    private LocalDate dataConclusao;
    private Status status;
    private ArrayList<Item> itens;
    private double valorTotal;

    public enum Status {
    ABERTO, APROVADO, REPROVADO, CONCLUIDO}


    public Pedido(int id, Funcionario funcionario, Departamento departamento, LocalDate dataPedido) {
        this.id = id;
        this.funcionarioSolicitante = funcionario;
        this.departamentoSolicitante = departamento;
        this.dataPedido = dataPedido;
        this.status = Status.ABERTO;
        this.itens = new ArrayList<>();
        this.valorTotal = 0;
    }

    public void adicionarItem(Item item) {
    }
    public double calcularTotal() {
        valorTotal = 0;
        for (Item item : itens) valorTotal += item.calcularTotal();
        return valorTotal;
    }
    public void aprovar() {
        if (status != Status.ABERTO) throw new IllegalStateException("O pedido nao esta aberto.");
        status = Status.APROVADO;
    }
    public void reprovar() { 
        if (status != Status.ABERTO) throw new IllegalStateException("O pedido não está aberto.");
    }
    public void concluir(LocalDate data) {
        if (status != Status.APROVADO) throw new IllegalStateException("Somente pedidos aprovados podem ser concluídos.");
        if (data.isBefore(dataPedido)) throw new IllegalArgumentException("A conclusão não pode anteceder o pedido.");
        dataConclusao = data;
        status = Status.CONCLUIDO;
    }
    public boolean podeExcluir(Usuario usuario) {
        return status == Status.ABERTO && usuario != null
                && usuario.getId() == funcionarioSolicitante.getId()
                && usuario instanceof Funcionario;
    }

    public int getId() { return id; }
    public Funcionario getFuncionarioSolicitante() { return funcionarioSolicitante; }
    public Departamento getDepartamentoSolicitante() { return departamentoSolicitante; }
    public LocalDate getDataPedido() { return dataPedido; }
    public LocalDate getDataConclusao() { return dataConclusao; }
    public Status getStatus() { return status; }
    public ArrayList<Item> getItens() { return new ArrayList<>(itens); }
    public double getValorTotal() { return valorTotal; }

}
