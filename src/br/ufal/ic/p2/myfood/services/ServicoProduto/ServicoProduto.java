package br.ufal.ic.p2.myfood.services.ServicoProduto;

import br.ufal.ic.p2.myfood.exceptions.ProdutoNaoEncontradoException;
import br.ufal.ic.p2.myfood.exceptions.EmpresaNaoEncontradaException;
import br.ufal.ic.p2.myfood.models.Produto;
import java.util.ArrayList;
import java.util.List;

public class ServicoProduto {
    private List<Produto> produtos = new ArrayList<>();

    public void adicionarProduto(int idEmpresa, String nome, float preco, String descricao) throws EmpresaNaoEncontradaException {
        // Lógica para adicionar o produto à empresa
        produtos.add(new Produto(idEmpresa, nome, preco, descricao));
    }

    public void editarProduto(int produtoId, String nome, float valor, String categoria) {
        Produto produto = produtos.stream()
                .filter(prod -> prod.getId() == produtoId)
                .findFirst()
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado!"));

        produto.setNome(nome);
        produto.setPreco(valor);
        produto.setCategoria(categoria);
    }

    public String getProduto(String nome, int empresaId, String atributo) {
        Produto produto = produtos.stream()
                .filter(prod -> prod.getNome().equals(nome) && prod.getEmpresaId() == empresaId)
                .findFirst()
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado!"));

        switch (atributo.toLowerCase()) {
            case "preco":
                return String.valueOf(produto.getPreco());
            // Adicione outros atributos conforme necessário
            default:
                throw new IllegalArgumentException("Atributo inválido!");
        }
    }

    public String listarProdutos(int empresaId) {
        return produtos.stream()
                .filter(prod -> prod.getEmpresaId() == empresaId)
                .map(Produto::getNome)
                .reduce((a, b) -> a + ", " + b)
                .orElse("Nenhum produto encontrado.");
    }

    public List<Produto> getProdutos() {
        return produtos;
    }
}