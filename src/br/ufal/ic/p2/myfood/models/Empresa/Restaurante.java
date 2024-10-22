package br.ufal.ic.p2.myfood.models.Empresa;

import br.ufal.ic.p2.myfood.models.Usuario.Dono;

public class Restaurante extends Empresa {
    private String tipoCozinha;

    public Restaurante() {
    }

    public Restaurante(String nome, String endereco, Dono dono, String tipoCozinha) {
        super(nome, endereco, dono);
        this.tipoCozinha = tipoCozinha;
    }


    public String getTipoCozinha() {
        return tipoCozinha;
    }

    public void setTipoCozinha(String tipoCozinha) {
        this.tipoCozinha = tipoCozinha;
    }

    @Override
    public String getAtributo(String atributo) {
        if (atributo.equals("tipoCozinha")) {
            return tipoCozinha;
        }
        return super.getAtributo(atributo);
    }
}
