package br.ufal.ic.p2.myfood.exceptions;

public class PedidoException extends Exception {
    public PedidoException(){
        super("Nao e permitido ter dois pedidos em aberto para a mesma empresa");
    }
    public PedidoException(String message){
        super(message);
    }
}
