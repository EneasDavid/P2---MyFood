package br.ufal.ic.p2.myfood.persistence;

import br.ufal.ic.p2.myfood.models.Entrega;
import br.ufal.ic.p2.myfood.utils.Persistencia;

import java.util.ArrayList;
import java.util.List;

public class PersistenciaEntrega implements Persistencia<Entrega> {

    private List<Entrega> entregas = new ArrayList<>();
    private ManipuladorXML controle = new ManipuladorXML();
    private final String caminhoArquivo = "xml/entregas.xml";

    @Override
    public void iniciar() {
        entregas = controle.lerObjetoDeXML(entregas, caminhoArquivo);
    }

    @Override
    public void salvar(Entrega modelo) {
        entregas.add(modelo);
        controle.gravarObjetoComoXML(entregas, caminhoArquivo);
    }

    @Override
    public void remover(int id) {
        if (entregas != null) {
            entregas.removeIf(entrega -> entrega.getId() == id);
            controle.gravarObjetoComoXML(entregas, caminhoArquivo);
        }
    }

    @Override
    public void limpar() {
        if (entregas != null) {
            entregas.clear();
        }
        controle.ApagarDadosXML(caminhoArquivo);
    }

    @Override
    public void editar(Entrega nova_entrega) {
        for (int i = 0; i < entregas.size(); i++) {
            if (entregas.get(i).getId() == nova_entrega.getId()) {
                entregas.set(i, nova_entrega);
                break;
            }
        }
        controle.gravarObjetoComoXML(entregas, caminhoArquivo);
    }
    @Override
    public Entrega buscar(int id) {
        for (Entrega entrega : entregas) {
            if (entrega.getId() == id) {
                return entrega;
            }
        }
        return null;
    }

    @Override
    public List<Entrega> listar() {
        if (entregas == null) {
            entregas = new ArrayList<>();
        }
        return entregas;
    }
    @Override
    public void atualizar() {
        controle.gravarObjetoComoXML(entregas, caminhoArquivo);
    }
}