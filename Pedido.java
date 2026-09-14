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
        Funcionario funcionario,
        Departamento departamento,
        LocalDate dataPedido
    ) {
    }

    public void adicionarItem(Item item) {
    }

    public double calcularTotal() {
    }

    public void aprovar() {
    }

    public void reprovar() {
    }

    public void concluir(LocalDate data) {
    }

    public boolean podeExcluir(Usuario usuario) {
    }

    public int getId() {
    }

    public Funcionario getFuncionarioSolicitante() {
    }

    public Departamento getDepartamentoSolicitante() {
    }

    public LocalDate getDataPedido() {
    }

    public LocalDate getDataConclusao() {
    }

    public Status getStatus() {
    }

    public ArrayList<Item> getItens() {
    }

    public double getValorTotal() {
    }
}
