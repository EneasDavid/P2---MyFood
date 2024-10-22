package br.ufal.ic.p2.myfood.models;

import br.ufal.ic.p2.myfood.exceptions.StatusException;
import br.ufal.ic.p2.myfood.exceptions.SemRegistroException;
import br.ufal.ic.p2.myfood.models.Empresa.Empresa;
import br.ufal.ic.p2.myfood.models.Usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private static int contador = 1;
    private int numero;
    private Usuario cliente;
    private Empresa empresa;
    private String estado;
    private List<Produto> prod_list = new ArrayList<>();
    private float valor_total;


    public Pedido() {
    }

    public Pedido(Usuario cliente, Empresa empresa) {
        this.numero = contador++;
        this.cliente = cliente;
        this.empresa = empresa;
        this.estado = "aberto";
    }


    public int getNumero() {

        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    public void mudarEstado() throws SemRegistroException {
        if (this.estado.equals("aberto")) {
            this.estado = "preparando";
        } else {
            throw new SemRegistroException("Este pedido nao esta aberto");
        }
    }

    public void mudarEstadoNovamente() throws SemRegistroException, StatusException {
        if (this.estado.equals("preparando")) {
            this.estado = "pronto";
        } else if (this.estado.equals("pronto")) {
            throw new StatusException("Pedido ja liberado");
        } else {
            throw new SemRegistroException("Nao e possivel liberar um produto que nao esta sendo preparado");
        }
    }

    public void mudarParaEntregando() throws SemRegistroException {
        if (this.estado.equals("pronto")) {
            this.estado = "entregando";
        } else {
            throw new SemRegistroException("Nao e possivel liberar um produto que nao esta sendo preparado");
        }
    }

    public void getEntregar() throws SemRegistroException {
        if (this.estado.equals("entregando")) {
            this.estado = "entregue";
        } else {
            throw new SemRegistroException("Nao e possivel entregar um produto que nao esta entregando");
        }
    }

    public List<Produto> getProd_list() {
        return prod_list;
    }

    public void setProd_list(List<Produto> prod_list) {
        this.prod_list = prod_list;
    }

    public float getValor_total() {
        return valor_total;
    }

    public void setValor_total(float valor_total) {
        this.valor_total = valor_total;
    }


    public void addProductToList(Produto produto) {
        prod_list.add(produto);
        this.valor_total += produto.getValor();
    }

    public void removeProductFromList(Produto produto) {
        if (prod_list.remove(produto)) {
            this.valor_total -= produto.getValor();
        }
    }

    public String toString() {
        return "id = " + numero + ", Cliente = " + cliente.getNome() + ", Empresa = " + empresa.getNome() + ", Estado = " + estado + "\n"
                + "Produtos de pedido:\n" + prod_list + "\n\n";
    }
}

