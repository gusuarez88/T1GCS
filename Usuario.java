
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
        StringBuilder iniciais = new StringBuilder();

        for (String parte : nome.trim().split("\\s+")) {
            if (!parte.isEmpty()) {
                iniciais.append(
                        Character.toUpperCase(parte.charAt(0))
                );
            }
        }

        return iniciais.toString();
    }

    public abstract String getTipo();
}
