package br.ufal.ic.p2.myfood.models.Usuario;

public class Dono extends Usuario {
    private String cpf;

    public Dono(int id, String nome, String email, String senha, String endereco, String cpf) {
        super(id, nome, email, senha, endereco);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }
}
