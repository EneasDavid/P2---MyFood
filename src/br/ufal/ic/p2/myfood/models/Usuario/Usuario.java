package br.ufal.ic.p2.myfood.models.Usuario;

import java.io.Serializable;

public class Usuario implements Serializable {
    private static int contador = 1; 
    private int id;
    private String nome;
    String email;
    private String senha;
    private String endereco;

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha, String endereco) {
        this.id = contador++;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public boolean isEntregador() {
        return false;
    }

    public String getAtributo(String atributo) {
        return switch (atributo) {
            case "id" -> String.valueOf(id);
            case "nome" -> nome;
            case "email" -> email;
            case "senha" -> senha;
            case "endereco" -> endereco;
            default -> null;
        };
    }
    public String getTipo(){
        return "Cliente";
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + " senha=" + senha + "  endereço=" + endereco + "]";
    }
}
