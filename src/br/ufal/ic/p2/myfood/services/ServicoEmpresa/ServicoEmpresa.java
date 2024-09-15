package br.ufal.ic.p2.myfood.services.ServicoEmpresa;

import br.ufal.ic.p2.myfood.exceptions.EmpresaJaExisteException;
import br.ufal.ic.p2.myfood.models.Empresa;
import java.util.ArrayList;
import java.util.List;

public class ServicoEmpresa {
    private List<Empresa> empresas = new ArrayList<>();

    public void criarEmpresa(String nome, String endereco, String tipoCozinha, Dono dono) throws EmpresaJaExisteException {
        // Verifica se a empresa j� existe
        if (empresas.stream().anyMatch(empresa -> empresa.getNome().equals(nome))) {
            throw new EmpresaJaExisteException("Empresa j� cadastrada!");
        }
        empresas.add(new Empresa(nome, endereco, tipoCozinha, dono));
    }

    public String getEmpresasDoUsuario(int idDono) {
        return empresas.stream()
                .filter(empresa -> empresa.getDono().getId() == idDono)
                .map(Empresa::getNome)
                .reduce((a, b) -> a + ", " + b)
                .orElse("Nenhuma empresa encontrada.");
    }

    public int getIdEmpresa(int idDono, String nome, int indice) {
        // L�gica para obter o ID da empresa com �ndice
        return empresas.stream()
                .filter(empresa -> empresa.getDono().getId() == idDono && empresa.getNome().equals(nome))
                .skip(indice)
                .findFirst()
                .map(Empresa::getId)
                .orElseThrow(() -> new IllegalArgumentException("Empresa n�o encontrada!"));
    }

    public String getAtributo(int empresaId, String atributo) {
        Empresa empresa = empresas.stream()
                .filter(emp -> emp.getId() == empresaId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Empresa n�o encontrada!"));

        switch (atributo.toLowerCase()) {
            case "nome":
                return empresa.getNome();
            case "endereco":
                return empresa.getEndereco();
            // Adicione outros atributos conforme necess�rio
            default:
                throw new IllegalArgumentException("Atributo inv�lido!");
        }
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }
}