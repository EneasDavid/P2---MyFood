package br.ufal.ic.p2.myfood.services.ServicoUsuario;

import br.ufal.ic.p2.myfood.exceptions.EmailJaExisteException;
import br.ufal.ic.p2.myfood.exceptions.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.myfood.models.Usuario.*;

import java.util.ArrayList;
import java.util.List;

public class ServicoUsuario {
    private List<Usuario> usuarios = new ArrayList<>();

    public void criarUsuario(String nome, String email, String senha, String endereco) throws EmailJaExisteException {
        // Verifica se o email já existe
        if (usuarios.stream().anyMatch(usuario -> usuario.getEmail().equals(email))) {
            throw new EmailJaExisteException("Email já cadastrado!");
        }
        usuarios.add(new Cliente(nome, email, senha, endereco));
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws EmailJaExisteException {
        // Similar ao método acima, mas inclui o CPF
        if (usuarios.stream().anyMatch(usuario -> usuario.getEmail().equals(email))) {
            throw new EmailJaExisteException("Email já cadastrado!");
        }
        usuarios.add(new Dono(nome, email, senha, endereco, cpf));
    }

    public Usuario login(String email, String senha) throws UsuarioNaoCadastradoException {
        return usuarios.stream()
                .filter(usuario -> usuario.getEmail().equals(email) && usuario.getSenha().equals(senha))
                .findFirst()
                .orElseThrow(() -> new UsuarioNaoCadastradoException("Usuário não cadastrado!"));
    }

    public String getAtributoUsuario(int id, String atributo) throws UsuarioNaoCadastradoException {
        Usuario usuario = usuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElseThrow(() -> new UsuarioNaoCadastradoException("Usuário não encontrado!"));

        switch (atributo.toLowerCase()) {
            case "nome":
                return usuario.getNome();
            case "email":
                return usuario.getEmail();
            // Adicione outros atributos conforme necessário
            default:
                throw new IllegalArgumentException("Atributo inválido!");
        }
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
}