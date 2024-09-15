package br.ufal.ic.p2.myfood.models.Empresa;

import br.ufal.ic.p2.myfood.exceptions.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.myfood.models.Produto.Produto;
import br.ufal.ic.p2.myfood.models.Usuario.Dono;
import br.ufal.ic.p2.myfood.models.Usuario.Usuario;

import java.util.List;
import java.util.Map;

public class Empresa {
    private int id;
    private String nome;
    private String endereco;
    private String tipoCozinha;
    private Dono dono;

    public Empresa(int id, String nome, String endereco, String tipoCozinha, Dono dono) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
        this.dono = dono;
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEndereco() { return endereco; }
    public String getTipoCozinha() { return tipoCozinha; }
    public Dono getDono() { return dono; }
}