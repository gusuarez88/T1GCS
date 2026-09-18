
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

    public Pedido(
            int id,
            Funcionario funcionarioSolicitante,
            Departamento departamentoSolicitante,
            LocalDate dataPedido) {

        this.id = id;
        this.funcionarioSolicitante = funcionarioSolicitante;
        this.departamentoSolicitante = departamentoSolicitante;
        this.dataPedido = dataPedido;
        this.dataConclusao = null;
        this.status = Status.ABERTO;
        this.itens = new ArrayList<>();
        this.valorTotal = 0;
    }

    public void adicionarItem(Item item) {

        if (status != Status.ABERTO) {
            System.out.println("Não é possível adicionar itens a um pedido que não está aberto.");
            return;
        }

        itens.add(item);
        calcularTotal();
    }

    public double calcularTotal() {

        valorTotal = 0;

        for (Item item : itens) {
            valorTotal += item.getTotal();
        }

        return valorTotal;
    }

    public void aprovar() {

        if (status == Status.ABERTO) {
            status = Status.APROVADO;
        }
    }

    public void reprovar() {

        if (status == Status.ABERTO) {
            status = Status.REPROVADO;
        }
    }

    public void concluir(LocalDate data) {

        if (status == Status.APROVADO) {
            status = Status.CONCLUIDO;
            dataConclusao = data;
        }
    }

    public boolean podeExcluir(Usuario usuario) {

        return status == Status.ABERTO
                && usuario instanceof Funcionario
                && ((Funcionario) usuario).getId() == funcionarioSolicitante.getId();
    }

    public int getId() {
        return id;
    }

    public Funcionario getFuncionarioSolicitante() {
        return funcionarioSolicitante;
    }

    public Departamento getDepartamentoSolicitante() {
        return departamentoSolicitante;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public Status getStatus() {
        return status;
    }

    public ArrayList<Item> getItens() {
        return itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }
}
