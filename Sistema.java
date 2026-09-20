
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Sistema {

    private Scanner scanner;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Departamento> departamentos;
    private ArrayList<Pedido> pedidos;
    private Usuario usuarioAtual;

    public Sistema() {
        scanner = new Scanner(System.in);
        usuarios = new ArrayList<>();
        departamentos = new ArrayList<>();
        pedidos = new ArrayList<>();

        carregarDadosIniciais();
    }

    public void exibirMenu() {

        int opcao;

        do {
            System.out.println("\n=== SISTEMA DE PEDIDOS ===");

            if (usuarioAtual != null) {
                System.out.println(
                        "Usuário: " + usuarioAtual.getNome()
                        + " (" + usuarioAtual.getIniciais() + ")"
                );
            }

            System.out.println("\n1 - Alterar usuário");
            System.out.println("2 - Criar pedido");
            System.out.println("3 - Listar pedidos");
            System.out.println("4 - Aprovar pedido");
            System.out.println("5 - Reprovar pedido");
            System.out.println("6 - Buscar por funcionário");
            System.out.println("7 - Buscar por item");
            System.out.println("8 - Listar por período");
            System.out.println("9 - Concluir pedido");
            System.out.println("10 - Excluir pedido");
            System.out.println("11 - Estatísticas");
            System.out.println("0 - Sair");

            System.out.print("Opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {

                case 1:
                    selecionarUsuario();
                    break;

                case 2:
                    criarPedido();
                    break;

                case 3:
                    listarPedidos();
                    break;

                case 4:
                    aprovarPedido();
                    break;

                case 5:
                    reprovarPedido();
                    break;

                case 6:
                    buscarPorFuncionario();
                    break;

                case 7:
                    buscarPorItem();
                    break;

                case 8:
                    listarPorPeriodo();
                    break;

                case 9:
                    concluirPedido();
                    break;

                case 10:
                    excluirPedido();
                    break;

                case 11:
                    obterEstatisticas();
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public void adicionarDepartamento(Departamento departamento) {
        departamentos.add(departamento);
    }

    public void adicionarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public void definirUsuarioAtual(Usuario usuario) {
        usuarioAtual = usuario;
    }

    public Usuario getUsuarioAtual() {
        return usuarioAtual;
    }

    private void selecionarUsuario() {

        System.out.println("\n=== USUÁRIOS ===");

        for (Usuario usuario : usuarios) {
            System.out.println(
                    usuario.getId() + " - "
                    + usuario.getNome() + " - "
                    + usuario.getClass().getSimpleName()
            );
        }

        System.out.print("ID do usuário: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (Usuario usuario : usuarios) {

            if (usuario.getId() == id) {
                usuarioAtual = usuario;
                System.out.println(
                        "Usuário alterado para "
                        + usuario.getNome()
                );
                return;
            }
        }

        System.out.println("Usuário não encontrado.");
    }

    private void criarPedido() {

        if (usuarioAtual == null) {
            System.out.println("Selecione um usuário primeiro.");
            return;
        }

        Funcionario funcionario;

        if (usuarioAtual instanceof Funcionario) {

            funcionario = (Funcionario) usuarioAtual;

        } else {

            System.out.print("ID do funcionário solicitante: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            funcionario = encontrarFuncionario(id);

            if (funcionario == null) {
                System.out.println("Funcionário não encontrado.");
                return;
            }
        }

        Departamento departamento
                = funcionario.getDepartamento();

        System.out.println(
                "\nFuncionário: " + funcionario.getNome()
        );

        System.out.println(
                "Departamento: " + departamento.getNome()
        );

        System.out.print("ID do pedido: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (encontrarPedido(id) != null) {
            System.out.println("ID de pedido já utilizado.");
            return;
        }

        Pedido pedido = new Pedido(
                id,
                funcionario,
                departamento,
                LocalDate.now()
        );

        int continuar;

        do {

            System.out.print("Descrição: ");
            String descricao = scanner.nextLine();

            System.out.print("Valor unitário: ");
            double valor = scanner.nextDouble();

            System.out.print("Quantidade: ");
            int quantidade = scanner.nextInt();
            scanner.nextLine();

            Item item = new Item(
                    descricao,
                    valor,
                    quantidade
            );

            if (pedido.getValorTotal() + item.getTotal()
                    > departamento.getLimitePorPedido()) {

                System.out.println(
                        "O limite do departamento foi ultrapassado."
                );
                return;
            }

            pedido.adicionarItem(item);

            System.out.print(
                    "Adicionar outro item? (1-Sim / 0-Não): "
            );

            continuar = scanner.nextInt();
            scanner.nextLine();

        } while (continuar == 1);

        if (pedido.getItens().isEmpty()) {
            System.out.println("O pedido não possui itens.");
            return;
        }

        pedidos.add(pedido);

        System.out.println(
                "Pedido criado! Valor: R$ "
                + pedido.getValorTotal()
        );
    }

    private void listarPedidos() {

        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido.");
            return;
        }

        for (Pedido pedido : pedidos) {
            mostrarPedido(pedido);
        }
    }

    private void aprovarPedido() {

        if (!(usuarioAtual instanceof Administrador)) {
            System.out.println(
                    "Somente administradores podem aprovar pedidos."
            );
            return;
        }

        Pedido pedido = pedirPedido();

        if (pedido == null) {
            return;
        }

        if (!pedido.getStatus().equals("ABERTO")) {
            System.out.println("O pedido não está aberto.");
            return;
        }

        pedido.aprovar();

        System.out.println("Pedido aprovado.");
    }

    private void reprovarPedido() {

        if (!(usuarioAtual instanceof Administrador)) {
            System.out.println(
                    "Somente administradores podem reprovar pedidos."
            );
            return;
        }

        Pedido pedido = pedirPedido();

        if (pedido == null) {
            return;
        }

        if (!pedido.getStatus().equals("ABERTO")) {
            System.out.println("O pedido não está aberto.");
            return;
        }

        pedido.reprovar();

        System.out.println("Pedido reprovado.");
    }

    private void concluirPedido() {

        if (!(usuarioAtual instanceof Administrador)) {
            System.out.println(
                    "Somente administradores podem concluir pedidos."
            );
            return;
        }

        Pedido pedido = pedirPedido();

        if (pedido == null) {
            return;
        }

        if (!pedido.getStatus().equals("APROVADO")) {
            System.out.println(
                    "Somente pedidos aprovados podem ser concluídos."
            );
            return;
        }

        pedido.concluir(LocalDate.now());

        System.out.println("Pedido concluído.");
    }

    private void excluirPedido() {

        if (!(usuarioAtual instanceof Funcionario)) {
            System.out.println(
                    "Somente o funcionário solicitante pode excluir."
            );
            return;
        }

        Pedido pedido = pedirPedido();

        if (pedido == null) {
            return;
        }

        if (pedido.podeExcluir(usuarioAtual)) {

            pedidos.remove(pedido);
            System.out.println("Pedido excluído.");

        } else {

            System.out.println(
                    "Você não pode excluir esse pedido."
            );
        }
    }

    public ArrayList<Pedido> buscarPorFuncionario(
            Funcionario funcionario) {

        ArrayList<Pedido> resultado = new ArrayList<>();

        for (Pedido pedido : pedidos) {

            if (pedido.getFuncionarioSolicitante().getId()
                    == funcionario.getId()) {

                resultado.add(pedido);
            }
        }

        return resultado;
    }

    private void buscarPorFuncionario() {

        if (!(usuarioAtual instanceof Administrador)) {
            System.out.println("Apenas administradores.");
            return;
        }

        System.out.print("ID do funcionário: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Funcionario funcionario
                = encontrarFuncionario(id);

        if (funcionario == null) {
            System.out.println("Funcionário não encontrado.");
            return;
        }

        ArrayList<Pedido> resultado
                = buscarPorFuncionario(funcionario);

        for (Pedido pedido : resultado) {
            mostrarPedido(pedido);
        }
    }

    public ArrayList<Pedido> buscarPorItem(String descricao) {

        ArrayList<Pedido> resultado = new ArrayList<>();

        for (Pedido pedido : pedidos) {

            for (Item item : pedido.getItens()) {

                if (item.getDescricao()
                        .toLowerCase()
                        .contains(descricao.toLowerCase())) {

                    resultado.add(pedido);
                    break;
                }
            }
        }

        return resultado;
    }

    private void buscarPorItem() {

        if (!(usuarioAtual instanceof Administrador)) {
            System.out.println("Apenas administradores.");
            return;
        }

        System.out.print("Descrição do item: ");
        String descricao = scanner.nextLine();

        ArrayList<Pedido> resultado
                = buscarPorItem(descricao);

        for (Pedido pedido : resultado) {
            mostrarPedido(pedido);
        }
    }

    public ArrayList<Pedido> listarPorPeriodo(
            LocalDate inicio,
            LocalDate fim) {

        ArrayList<Pedido> resultado = new ArrayList<>();

        for (Pedido pedido : pedidos) {

            LocalDate data = pedido.getDataPedido();

            if (!data.isBefore(inicio)
                    && !data.isAfter(fim)) {

                resultado.add(pedido);
            }
        }

        return resultado;
    }

    private void listarPorPeriodo() {

        if (!(usuarioAtual instanceof Administrador)) {
            System.out.println("Apenas administradores.");
            return;
        }

        System.out.print("Data inicial (yyyy-MM-dd): ");
        LocalDate inicio
                = LocalDate.parse(scanner.nextLine());

        System.out.print("Data final (yyyy-MM-dd): ");
        LocalDate fim
                = LocalDate.parse(scanner.nextLine());

        ArrayList<Pedido> resultado
                = listarPorPeriodo(inicio, fim);

        for (Pedido pedido : resultado) {
            mostrarPedido(pedido);
        }
    }

    public void obterEstatisticas() {

        if (!(usuarioAtual instanceof Administrador)) {
            System.out.println("Apenas administradores.");
            return;
        }

        int total = pedidos.size();
        int aprovados = 0;
        int reprovados = 0;

        for (Pedido pedido : pedidos) {

            if (pedido.getStatus().equals("APROVADO")
                    || pedido.getStatus().equals("CONCLUIDO")) {

                aprovados++;

            } else if (pedido.getStatus().equals("REPROVADO")) {

                reprovados++;
            }
        }

        System.out.println("\n=== ESTATÍSTICAS ===");

        System.out.println("Total: " + total);

        if (total > 0) {

            System.out.printf(
                    "Aprovados: %d (%.2f%%)%n",
                    aprovados,
                    aprovados * 100.0 / total
            );

            System.out.printf(
                    "Reprovados: %d (%.2f%%)%n",
                    reprovados,
                    reprovados * 100.0 / total
            );
        }

        LocalDate limite
                = LocalDate.now().minusDays(30);

        int quantidade30 = 0;
        double soma = 0;

        Pedido maiorAberto = null;

        for (Pedido pedido : pedidos) {

            if (!pedido.getDataPedido().isBefore(limite)) {

                quantidade30++;
                soma += pedido.getValorTotal();
            }

            if (pedido.getStatus().equals("ABERTO")) {

                if (maiorAberto == null
                        || pedido.getValorTotal()
                        > maiorAberto.getValorTotal()) {

                    maiorAberto = pedido;
                }
            }
        }

        System.out.println(
                "Pedidos nos últimos 30 dias: "
                + quantidade30
        );

        if (quantidade30 > 0) {

            System.out.printf(
                    "Valor médio: R$ %.2f%n",
                    soma / quantidade30
            );
        }

        if (maiorAberto != null) {

            System.out.println(
                    "\nMaior pedido ainda aberto:"
            );

            mostrarPedido(maiorAberto);
        }
    }

    private Pedido pedirPedido() {

        System.out.print("ID do pedido: ");

        int id = scanner.nextInt();
        scanner.nextLine();

        Pedido pedido = encontrarPedido(id);

        if (pedido == null) {
            System.out.println("Pedido não encontrado.");
        }

        return pedido;
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

    private Pedido encontrarPedido(int id) {

        for (Pedido pedido : pedidos) {

            if (pedido.getId() == id) {
                return pedido;
            }
        }

        return null;
    }

    private void mostrarPedido(Pedido pedido) {

        System.out.println("\n------------------------");

        System.out.println(
                "ID: " + pedido.getId()
        );

        System.out.println(
                "Funcionário: "
                + pedido.getFuncionarioSolicitante().getNome()
        );

        System.out.println(
                "Departamento: "
                + pedido.getDepartamentoSolicitante().getNome()
        );

        System.out.println(
                "Data: " + pedido.getDataPedido()
        );

        System.out.println(
                "Status: " + pedido.getStatus()
        );

        System.out.printf(
                "Valor: R$ %.2f%n",
                pedido.getValorTotal()
        );

        System.out.println("Itens:");

        for (Item item : pedido.getItens()) {

            System.out.println(
                    " - " + item.getDescricao()
                    + " | R$ " + item.getTotal()
            );
        }
    }

    public void carregarDadosIniciais() {

        Departamento financeiro
                = new Departamento("Financeiro", 5000);

        Departamento rh
                = new Departamento("RH", 3000);

        Departamento engenharia
                = new Departamento("Engenharia", 10000);

        Departamento manutencao
                = new Departamento("Manutenção", 7000);

        Departamento compras
                = new Departamento("Compras", 15000);

        adicionarDepartamento(financeiro);
        adicionarDepartamento(rh);
        adicionarDepartamento(engenharia);
        adicionarDepartamento(manutencao);
        adicionarDepartamento(compras);

        adicionarUsuario(
                new Administrador(1, "Carlos Administrador")
        );

        adicionarUsuario(
                new Administrador(2, "Ana Administradora")
        );

        adicionarUsuario(
                new Funcionario(101, "João Silva", financeiro)
        );

        adicionarUsuario(
                new Funcionario(102, "Maria Santos", financeiro)
        );

        adicionarUsuario(
                new Funcionario(103, "Pedro Oliveira", financeiro)
        );

        adicionarUsuario(
                new Funcionario(104, "Lucas Souza", rh)
        );

        adicionarUsuario(
                new Funcionario(105, "Julia Costa", rh)
        );

        adicionarUsuario(
                new Funcionario(106, "Marcos Lima", rh)
        );

        adicionarUsuario(
                new Funcionario(107, "Gabriel Alves", engenharia)
        );

        adicionarUsuario(
                new Funcionario(108, "Rafael Pereira", engenharia)
        );

        adicionarUsuario(
                new Funcionario(109, "Beatriz Rocha", engenharia)
        );

        adicionarUsuario(
                new Funcionario(110, "Fernanda Martins", manutencao)
        );

        adicionarUsuario(
                new Funcionario(111, "Ricardo Gomes", manutencao)
        );

        adicionarUsuario(
                new Funcionario(112, "Bruno Ferreira", manutencao)
        );

        adicionarUsuario(
                new Funcionario(113, "Larissa Dias", compras)
        );

        adicionarUsuario(
                new Funcionario(114, "Diego Ramos", compras)
        );

        adicionarUsuario(
                new Funcionario(115, "Camila Barbosa", compras)
        );

        usuarioAtual = usuarios.get(0);
    }
}
