
public class Funcionario extends Usuario {

    private Departamento departamento;

    public Funcionario(int id, String nome, Departamento departamento) {
        super(id, nome);
        this.departamento = departamento;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
}
