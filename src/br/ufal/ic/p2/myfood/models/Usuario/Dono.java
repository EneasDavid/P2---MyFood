package br.ufal.ic.p2.myfood.models.Usuario;

import br.ufal.ic.p2.myfood.models.Empresa.Empresa;

import java.util.ArrayList;
import java.util.List;

public class Dono extends Usuario {
    private String cpf;
    private List<Empresa> comp_list;


    public Dono() {
    }

    public Dono(String nome, String email, String senha, String endereco, String cpf) {
        super(nome, email, senha, endereco);
        this.cpf = cpf;
        this.comp_list = new ArrayList<Empresa>();
    }


    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

   
    @Override
    public String getAtributo(String atributo) {
        if (atributo.equals("cpf")) {
            return cpf;
        }
        return super.getAtributo(atributo);
    }

    public List<Empresa> getComp_list() {
        return comp_list;
    }

    public void setComp_list(List<Empresa> comp_list) {
        this.comp_list = comp_list;
    }

    public void addComp_list(Empresa empresa) {
        this.comp_list.add(empresa);
    }

    public String getTipo(){
        return "Dono";
    }
}
