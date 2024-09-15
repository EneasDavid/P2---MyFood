package br.ufal.ic.p2.myfood.models.Pedido;

import br.ufal.ic.p2.myfood.models.Empresa.Empresa;
import br.ufal.ic.p2.myfood.models.Produto.Produto;
import br.ufal.ic.p2.myfood.models.Usuario.Cliente;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int numero;
    private Cliente cliente;
    private Empresa empresa;
    private List<Produto> produtos;
    private String estado; // aberto, preparando, fechado

    public Pedido(int numero, Cliente cliente, Empresa empresa) {
        this.numero = numero;
        this.cliente = cliente;
        this.empresa = empresa;
        this.produtos = new ArrayList<>();
        this.estado = "aberto";
    }

    // Métodos para adicionar/remover produtos
    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public void removerProduto(Produto produto) {
        produtos.remove(produto);
    }

    // Getters
    public int getNumero() { return numero; }
    public Cliente getCliente() { return cliente; }
    public Empresa getEmpresa() { return empresa; }
    public List<Produto> getProdutos() { return produtos; }
    public String getEstado() { return estado; }

    public void fecharPedido() {
        this.estado = "fechado";
    }
}