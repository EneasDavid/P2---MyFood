package br.ufal.ic.p2.myfood.models.Usuario;

public abstract class Usuario {
    protected int id;
    protected String nome;
    protected String email;
    protected String senha;
    protected String endereco;

    public Usuario(int id, String nome, String email, String senha, String endereco) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getSenha(){return senha; }
    public String getEmail() { return email; }
    public String getEndereco() { return endereco; }
}
