package br.ufal.ic.p2.myfood.models.Empresa;

import br.ufal.ic.p2.myfood.models.Usuario.Dono;

public class Farmacia extends Empresa {
    private boolean aberto24Horas;
    private int numeroFuncionarios;

    public Farmacia(){}

    public Farmacia(String nome, String endereco, Dono dono, boolean aberto24Horas, int numeroFuncionarios){

        super(nome, endereco, dono);
        this.aberto24Horas = aberto24Horas;
        this.numeroFuncionarios = numeroFuncionarios;
    }

    public Boolean getAberto24Horas() {
        return aberto24Horas;
    }

    public void setAberto24Horas(Boolean aberto24Horas) {
        this.aberto24Horas = aberto24Horas;
    }

    public int getNumeroFuncionarios(){
        return numeroFuncionarios;
    }

    public void setNumeroFuncionarios(int numeroFuncionarios) {
        this.numeroFuncionarios = numeroFuncionarios;
    }

    @Override
    public String getAtributo(String atributo) {
        return switch (atributo) {
            case "aberto24Horas" -> String.valueOf(aberto24Horas);
            case "numeroFuncionarios" -> String.valueOf(numeroFuncionarios);
            default -> super.getAtributo(atributo);
        };
    }
}
