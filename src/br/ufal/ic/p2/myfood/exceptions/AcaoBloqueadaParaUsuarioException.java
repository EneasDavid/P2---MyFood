package br.ufal.ic.p2.myfood.exceptions;

public class AcaoBloqueadaParaUsuarioException extends Exception {
    public AcaoBloqueadaParaUsuarioException() {
        super("Usuario nao pode criar uma empresa");
    }

    public AcaoBloqueadaParaUsuarioException(String message) {
        super(message);
    }
}