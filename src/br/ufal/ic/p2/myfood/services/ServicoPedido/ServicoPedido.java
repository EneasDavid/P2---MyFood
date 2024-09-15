package br.ufal.ic.p2.myfood.services.ServicoPedido;

import br.ufal.ic.p2.myfood.exceptions.PedidoNaoEncontradoException;
import br.ufal.ic.p2.myfood.models.Pedido;
import br.ufal.ic.p2.myfood.models.Usuario;
import br.ufal.ic.p2.myfood.models.Empresa;
import java.util.ArrayList;
import java.util.List;

public class ServicoPedido {
    private List<Pedido> pedidos = new ArrayList<>();

    public void criarPedido(Usuario usuario, Empresa empresa) {
        pedidos.add(new Pedido(usuario, empresa));
    }

    public int getNumero(Usuario usuario, Empresa empresa, int indice) {
        // L�gica para obter o n�mero do pedido
        return pedidos.stream()
                .filter(pedido -> pedido.getUsuario().equals(usuario) && pedido.getEmpresa().equals(empresa))
                .skip(indice)
                .findFirst()
                .map(Pedido::getNumero)
                .orElseThrow(() -> new PedidoNaoEncontradoException("Pedido n�o encontrado!"));
    }

    public void adicionarProduto(int numero, int produto) {
        Pedido pedido = pedidos.stream()
                .filter(ped -> ped.getNumero() == numero)
                .findFirst()
                .orElseThrow(() -> new PedidoNaoEncontradoException("Pedido n�o encontrado!"));

        pedido.adicionarProduto(produto);
    }

    public String getAtributo(int numero, String atributo) throws PedidoNaoEncontradoException {
        Pedido pedido = pedidos.stream()
                .filter(ped -> ped.getNumero() == numero)
                .findFirst()
                .orElseThrow(() -> new PedidoNaoEncontradoException("Pedido n�o encontrado!"));

        switch (atributo.toLowerCase()) {
            case "usuario":
                return pedido.getUsuario().getNome();
            case "empresa":
                return pedido.getEmpresa().getNome();
            // Adicione outros atributos conforme necess�rio
            default:
                throw new IllegalArgumentException("Atributo inv�lido!");
        }
    }

    public void fecharPedido(int numero) throws PedidoNaoEncontradoException {
        Pedido pedido = pedidos.stream()
                .filter(ped -> ped.getNumero() == numero)
                .findFirst()
                .orElseThrow(() -> new PedidoNaoEncontradoException("Pedido n�o encontrado!"));

        pedido.fechar();
    }

    public void removerProduto(int pedidoNumero, String produto) throws PedidoNaoEncontradoException {
        Pedido pedido = pedidos.stream()
                .filter(ped -> ped.getNumero() == pedidoNumero)
                .findFirst()
                .orElseThrow(() -> new PedidoNaoEncontradoException("Pedido n�o encontrado!"));

        pedido.removerProduto(produto);
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }
}