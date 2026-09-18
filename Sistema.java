
import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;

public class Sistema {

    private Scanner scanner;

    private ArrayList<Usuario> usuarios;
    private ArrayList<Departamento> departamentos;
    private ArrayList<Pedido> pedidos;
    private Usuario usuarioAtual;
    //public static final int LIMITE_PEDIDO = 1000;

    public Sistema() {
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu(Scanner scanner) {
        int opcao;
        do {
            System.out.println("=== Sistema de Gerenciamento de Pedidos ===");
            System.out.println("1. Criar Pedido");
            System.out.println("2. Listar Pedidos");
            System.out.println("3. Aprovar Pedido");
            System.out.println("4. Reprovar Pedido");
            System.out.println("5. Buscar Por Funcionario");
            System.out.println("6. Buscar Por Item");
            System.out.println("7. Listar Por Periodo");
            System.out.println("8. Obter Estatisticas");
            System.out.println("0. Sair");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    criarPedido();
                    break;
                case 2:
                    //listarPedidos();
                    break;
                case 3:
                    //aprovarPedido();
                    break;
                case 4:
                    //reprovarPedido();
                    break;
                case 5:
                    //buscarPorFuncionario();
                    break;
                case 6:
                    //buscarPorItem();
                    break;
                case 7:
                    //listarPorPeriodo();
                    break;
                case 8:
                    //obterEstatisticas();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);

    }

    public void adicionarUsuario(Usuario usuario) {
    }

    public void adicionarDepartamento(Departamento departamento) {
    }

    public void adicionarPedido(Pedido pedido) {
    }

    public void definirUsuarioAtual(Usuario usuario) {
    }

    private void selecionarUsuario() {

        System.out.println("\n=== Usuários ===");

        for (Usuario usuario : usuarios) {

            System.out.println(
                    "ID: " + usuario.getId()
                    + " | Nome: " + usuario.getNome()
                    + " | Iniciais: " + usuario.getIniciais()
                    + " | Tipo: " + usuario.getClass().getSimpleName()
            );
        }

        System.out.print("Digite o ID do usuário: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (Usuario usuario : usuarios) {

            if (usuario.getId() == id) {
                definirUsuarioAtual(usuario);

                System.out.println(
                        "Usuário atual alterado para: "
                        + usuario.getNome()
                );

                return;
            }
        }

        System.out.println("Usuário não encontrado.");
    }

    private void criarPedido() {

        if (usuarioAtual == null) {
            System.out.println("Nenhum usuário selecionado.");
            return;
        }

        Funcionario funcionario;

        if (usuarioAtual instanceof Funcionario) {
            funcionario = (Funcionario) usuarioAtual;
        } else {
            System.out.println("Digite o ID do funcionário solicitante:");
            int idFuncionario = scanner.nextInt();
            scanner.nextLine();

            funcionario = encontrarFuncionario(idFuncionario);

            if (funcionario == null) {
                System.out.println("Funcionário não encontrado.");
                return;
            }
        }

        Departamento departamento = funcionario.getDepartamento();

        System.out.println("\n=== Novo Pedido ===");
        System.out.println("Funcionário: " + funcionario.getNome());
        System.out.println("Departamento: " + departamento.getNome());
        System.out.println("Limite do departamento: R$ "
                + departamento.getLimitePorPedido());

        System.out.print("Digite o ID do pedido: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Pedido pedido = new Pedido(
                id,
                funcionario,
                departamento,
                LocalDate.now()
        );

        int adicionarMais;

        do {

            System.out.print("Descrição do item: ");
            String descricao = scanner.nextLine();

            System.out.print("Valor unitário: ");
            double valor = scanner.nextDouble();

            System.out.print("Quantidade: ");
            int quantidade = scanner.nextInt();
            scanner.nextLine();

            Item item = new Item(descricao, valor, quantidade);

            pedido.adicionarItem(item);

            System.out.println("Total do item: R$ " + item.getTotal());

            if (pedido.getValorTotal() > departamento.getLimitePorPedido()) {

                System.out.println(
                        "O valor do pedido ultrapassou o limite do departamento."
                );

                return;
            }

            System.out.print("Adicionar outro item? (1-Sim / 0-Não): ");
            adicionarMais = scanner.nextInt();
            scanner.nextLine();

        } while (adicionarMais == 1);

        adicionarPedido(pedido);

        System.out.println("\nPedido criado com sucesso!");
        System.out.println("Valor total: R$ " + pedido.getValorTotal());
        System.out.println("Status: " + pedido.getStatus());
    }

    public boolean excluirPedido(Pedido pedido) {
    }

    public void aprovarPedido(Pedido pedido) {
    }

    public void reprovarPedido(Pedido pedido) {
    }

    private Funcionario encontrarFuncionario(int id) {

        for (Usuario usuario : usuarios) {

            if (usuario instanceof Funcionario
                    && usuario.getId() == id) {

                return (Funcionario) usuario;
            }
        }

        return null;
    }

    public ArrayList<Pedido> buscarPorItem(String descricao) {
    }

    public ArrayList<Pedido> listarPorPeriodo(
            LocalDate inicio,
            LocalDate fim
    ) {
    }

    public void obterEstatisticas() {
    }

    public void carregarDadosIniciais() {
    }
}
