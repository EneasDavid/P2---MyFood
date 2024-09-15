package br.ufal.ic.p2.myfood.exceptions;

public class EmpresaNaoEncontradaException extends Exception {
    public EmpresaNaoEncontradaException() {
        super("Empresa não encontrada.");
    }
}
