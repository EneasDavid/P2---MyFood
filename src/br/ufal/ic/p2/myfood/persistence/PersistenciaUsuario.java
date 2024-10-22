package br.ufal.ic.p2.myfood.persistence;

import br.ufal.ic.p2.myfood.models.Usuario.Usuario;
import br.ufal.ic.p2.myfood.utils.Persistencia;

import java.util.ArrayList;
import java.util.List;

public class PersistenciaUsuario implements Persistencia<Usuario> {

    private List<Usuario> usuarios = new ArrayList<>();
    private ManipuladorXML controle = new ManipuladorXML();
    private final String caminhoArquivo = "xml/usuarios.xml";

    @Override
    public void iniciar() {
        usuarios = controle.lerObjetoDeXML(usuarios, caminhoArquivo);
    }

    @Override
    public void salvar(Usuario user) {
        usuarios.add(user);
        controle.gravarObjetoComoXML(usuarios, caminhoArquivo);
    }

    @Override
    public void remover(int id){
        usuarios.removeIf(user -> user.getId() == id);
        controle.gravarObjetoComoXML(usuarios, caminhoArquivo);
    }

    @Override
    public void limpar() {
        if (usuarios != null) {
            usuarios.clear();
        }
        controle.ApagarDadosXML(caminhoArquivo);
    }

    @Override
    public void editar(Usuario novo_usuario){
    }

    @Override
    public Usuario buscar(int id) {
        for (Usuario user : usuarios) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<Usuario> listar() {
        return usuarios;
    }

    @Override
    public void atualizar() {
        controle.gravarObjetoComoXML(usuarios, caminhoArquivo);
    }
}