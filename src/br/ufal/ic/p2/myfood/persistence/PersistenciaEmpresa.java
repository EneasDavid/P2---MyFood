package br.ufal.ic.p2.myfood.persistence;

import br.ufal.ic.p2.myfood.models.Empresa.Empresa;
import br.ufal.ic.p2.myfood.utils.Persistencia;

import java.util.ArrayList;
import java.util.List;

public class PersistenciaEmpresa implements Persistencia<Empresa> {

    private List<Empresa> empresas = new ArrayList<>();
    private ManipuladorXML controle = new ManipuladorXML();
    private final String caminhoArquivo = "xml/empresas.xml";


    @Override
    public void iniciar() {
        empresas = controle.lerObjetoDeXML(empresas, caminhoArquivo);
    }

    @Override
    public void salvar(Empresa modelo) {
        empresas.add(modelo);
        controle.gravarObjetoComoXML(empresas, caminhoArquivo);
    }

    @Override
    public void remover(int id){
        empresas.removeIf(comp -> comp.getId() == id);
        controle.gravarObjetoComoXML(empresas, caminhoArquivo);
    }

    @Override
    public void limpar() {
     
        if (empresas != null) {
            empresas.clear();
        }

      
        controle.ApagarDadosXML(caminhoArquivo);
    }

    @Override
    public void editar(Empresa nova_empresa){

    }

    @Override
    public Empresa buscar(int id) {
        for (Empresa comp : empresas) {
            if (comp.getId() == id) {
                return comp;
            }
        }
        return null;
    }

    @Override
    public List<Empresa> listar() {
        return empresas;
    }

    @Override
    public void atualizar() {
        controle.gravarObjetoComoXML(empresas, caminhoArquivo);
    }
}
