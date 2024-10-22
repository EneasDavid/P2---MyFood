package br.ufal.ic.p2.myfood.persistence;

import br.ufal.ic.p2.myfood.models.Pedido;
import br.ufal.ic.p2.myfood.utils.Persistencia;

import java.util.ArrayList;
import java.util.List;

public class PersistenciaPedido implements Persistencia<Pedido> {

    private List<Pedido> pedidos = new ArrayList<>();
    private ManipuladorXML controle = new ManipuladorXML();
    private final String arquivo = "xml/pedidos.xml";

    @Override
    public void iniciar() {
        pedidos = controle.lerObjetoDeXML(pedidos, arquivo);
    }

    @Override
    public void salvar(Pedido modelo) {
        pedidos.add(modelo);
        controle.gravarObjetoComoXML(pedidos, arquivo);
    }

    @Override
    public void remover(int id) {
        pedidos.removeIf(pedido -> pedido.getNumero() == id);
        controle.gravarObjetoComoXML(pedidos, arquivo);
    }

    @Override
    public void limpar() {
        if (pedidos != null) {
            pedidos.clear();
        }
        controle.ApagarDadosXML(arquivo);
    }

    @Override
    public void editar(Pedido novo_modelo) {
        controle.gravarObjetoComoXML(pedidos, arquivo);
    }

    @Override
    public Pedido buscar(int id) {
        for (Pedido pedido : pedidos) {
            if (pedido.getNumero() == id) {
                return pedido;
            }
        }
        return null;
    }

    @Override
    public List<Pedido> listar() {
        return pedidos;
    }

    @Override
    public void atualizar() {
        controle.gravarObjetoComoXML(pedidos, arquivo);
    }
}
