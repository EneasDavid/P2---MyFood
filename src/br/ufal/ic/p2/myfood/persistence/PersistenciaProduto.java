package br.ufal.ic.p2.myfood.persistence;

import br.ufal.ic.p2.myfood.models.Produto;
import br.ufal.ic.p2.myfood.utils.Persistencia;

import java.util.ArrayList;
import java.util.List;

public class PersistenciaProduto implements Persistencia<Produto> {

    private List<Produto> produtos = new ArrayList<>();
    private ManipuladorXML controle = new ManipuladorXML();
    private final String caminhoArquivos = "xml/produtos.xml";


    @Override
    public void iniciar() {
        produtos = controle.lerObjetoDeXML(produtos, caminhoArquivos);
    }

    @Override
    public void salvar(Produto modelo) {
        produtos.add(modelo);
        controle.gravarObjetoComoXML(produtos, caminhoArquivos);
    }

    @Override
    public void remover(int id) {
        produtos.removeIf(produto -> produto.getId() == id);
        controle.gravarObjetoComoXML(produtos, caminhoArquivos);
    }

    @Override
    public void limpar() {
        if (produtos != null) {
            produtos.clear();
        }
        controle.ApagarDadosXML(caminhoArquivos);
    }


    @Override
    public void editar(Produto novo_produto) {
        for (int i = 0; i < produtos.size(); i++) {
            Produto produtoExistente = produtos.get(i);
            if (produtoExistente.getId() == novo_produto.getId()) {
                produtos.set(i, novo_produto);
                controle.gravarObjetoComoXML(produtos, caminhoArquivos);
                return;
            }
        }
    }

    @Override
    public Produto buscar(int id) {
        for (Produto prod : produtos) {
            if (prod.getId() == id) {
                return prod;
            }
        }
        return null;
    }

    @Override
    public List<Produto> listar() {
        return produtos;
    }

    @Override
    public void atualizar() {
        controle.gravarObjetoComoXML(produtos, caminhoArquivos);
    }
}
