package br.ufal.ic.p2.myfood.exceptions;

public class EmpresaJaExisteException extends Exception{
    public EmpresaJaExisteException() {
        super("Empresa com esse nome e endereço já existe.");
    }
}
