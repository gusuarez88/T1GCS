
public abstract class Usuario {

    private int id;
    private String nome;

    public Usuario(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getIniciais() {
        String[] partes = nome.trim().split("\\s+");
        String iniciais = "";

        for (String parte : partes) {
            if (!parte.isEmpty()) {
                iniciais += Character.toUpperCase(parte.charAt(0));
            }
        }

        return iniciais;
    }
}
