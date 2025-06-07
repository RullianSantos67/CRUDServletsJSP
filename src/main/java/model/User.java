package model;

public class User {
    private int id;
    private String nome;
    private String sexo;
    private String email;
    private String password;

    
    public User(int id) {
        this.id = id;
    }

    public User(int id, String nome, String sexo, String email, String password) {
        this.id = id;
        this.nome = nome;
        this.sexo = sexo;
        this.email = email;
        this.password = password;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
