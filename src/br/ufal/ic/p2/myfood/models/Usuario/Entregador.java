package br.ufal.ic.p2.myfood.models.Usuario;

import br.ufal.ic.p2.myfood.models.Empresa.Empresa;

import java.util.ArrayList;
import java.util.List;

public class Entregador extends Usuario{
    private String veiculo;
    private String placa;
    private boolean emEntrega = false;
    private List<Empresa> empresas = new ArrayList<>();

    public Entregador(){}

    public Entregador(String nome, String email, String senha, String endereco, String veiculo, String placa){
        super(nome, email, senha, endereco);
        this.veiculo = veiculo;
        this.placa = placa;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void adicionarEmpresa(Empresa empresa) {
        empresas.add(empresa);
    }

    public boolean isEmEntrega() {
        return emEntrega;
    }

    public void setEmEntrega(boolean emEntrega) {
        this.emEntrega = emEntrega;
    }

    @Override
    public boolean isEntregador() {
        return true;
    }

    @Override
    public String getAtributo(String atributo) {
        if (atributo.equals("veiculo")) {
            return veiculo;
        }
        else if(atributo.equals("placa")) {
            return placa;
        }
        return super.getAtributo(atributo);
    }

    public String getTipo(){
        return "Entregador";
    }
}
