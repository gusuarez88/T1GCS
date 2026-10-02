public class Administrador extends Usuario {

    private String cargo;

    public Administrador(int id, String nome) {
        super(id, nome);
        this.cargo = "Administrador";
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void gerenciarSistema() {
        System.out.println("Administrador gerenciando o sistema.");
    }
}