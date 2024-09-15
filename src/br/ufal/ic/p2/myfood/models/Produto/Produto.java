package br.ufal.ic.p2.myfood.models.Produto;

import br.ufal.ic.p2.myfood.models.Empresa.Empresa;

public class Produto {
    private int id;
    private Empresa empresa;
    private String nome;
    private float valor;
    private String categoria;

    public Produto(int id, Empresa empresa,String nome, float valor, String categoria) {
        this.id = id;
        this.empresa = empresa;
        this.nome = nome;
        this.valor = valor;
        this.categoria = categoria;
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public float getValor() { return valor; }
    public String getCategoria() { return categoria; }
    public Empresa getEmpresa() { return empresa; }
}