package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.exceptions.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.myfood.models.Usuario.Dono;
import br.ufal.ic.p2.myfood.services.ServicoEmpresa.ServicoEmpresa;
import br.ufal.ic.p2.myfood.services.ServicoPedido.ServicoPedido;
import br.ufal.ic.p2.myfood.services.ServicoProduto.ServicoProduto;
import br.ufal.ic.p2.myfood.services.ServicoUsuario.ServicoUsuario;
import br.ufal.ic.p2.myfood.exceptions.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class Facade {
    private ServicoUsuario servicoUsuario;
    private ServicoEmpresa servicoEmpresa;
    private ServicoProduto servicoProduto;
    private ServicoPedido servicoPedido;

    public Facade() {
        this.servicoUsuario = new ServicoUsuario();
        this.servicoEmpresa = new ServicoEmpresa();
        this.servicoProduto = new ServicoProduto();
        this.servicoPedido = new ServicoPedido(servicoProduto, servicoUsuario);
    }

    public void zerarSistema() {
        servicoUsuario = new ServicoUsuario();
        servicoEmpresa = new ServicoEmpresa();
        servicoProduto = new ServicoProduto();
        servicoPedido = new ServicoPedido(servicoProduto, servicoUsuario);
    }

    public String getAtributoUsuario(int id, String atributo) throws UsuarioNaoCadastradoException {
        return servicoUsuario.getAtributoUsuario(id, atributo);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws EmailJaExisteException {
        servicoUsuario.criarUsuario(nome, email, senha, endereco);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws EmailJaExisteException {
        servicoUsuario.criarUsuario(nome, email, senha, endereco, cpf);
    }

    public int login(String email, String senha) throws UsuarioNaoCadastradoException {
        return servicoUsuario.login(email, senha).getId();
    }

    public void encerrarSistema() {
        // Finaliza a execução do programa
    }

    // Métodos relacionados à Empresa
    public void criarEmpresa(String nome, String endereco, String tipoCozinha, Dono dono) throws EmpresaJaExisteException {
        servicoEmpresa.criarEmpresa(nome, endereco, tipoCozinha, dono);
    }

    public String getEmpresasDoUsuario(int idDono) {
        return servicoEmpresa.getEmpresasDoUsuario(idDono);
    }

    public int getIdEmpresa(int idDono, String nome, int indice) {
        return servicoEmpresa.getIdEmpresa(idDono, nome, indice);
    }

    public String getAtributoEmpresa(int empresa, String atributo) {
        return servicoEmpresa.getAtributo(empresa, atributo);
    }

    // Métodos relacionados ao Produto
    public void criarProduto(int idEmpresa, String nome, float preco, String descricao) throws ProdutoNaoEncontradoException, EmpresaNaoEncontradaException {
        servicoProduto.adicionarProduto(idEmpresa, nome, preco, descricao);
    }

    public void editarProduto(int produto, String nome, float valor, String categoria) {
        servicoProduto.editarProduto(produto, nome, valor, categoria);
    }

    public String getProduto(String nome, int empresa, String atributo) {
        return servicoProduto.getProduto(nome, empresa, atributo);
    }

    public String listarProdutos(int empresa) {
        return servicoProduto.listarProdutos(empresa);
    }

    // Métodos relacionados ao Pedido
    public void criarPedido(int usuarioId, int empresaId) throws ProdutoNaoEncontradoException, PedidoNaoEncontradoException, UsuarioNaoCadastradoException, EmpresaNaoEncontradaException {
        servicoPedido.criarPedido(servicoUsuario.buscarUsuario(usuarioId), servicoEmpresa.buscarEmpresa(empresaId));
    }

    public int getNumeroPedido(int usuarioId, int empresaId, int indice) throws UsuarioNaoCadastradoException {
        return servicoPedido.getNumero(servicoUsuario.buscarUsuario(usuarioId), servicoEmpresa.buscarEmpresa(empresaId), indice);
    }

    public void adicionarProduto(int numero, int produto) {
        servicoPedido.adicionarProduto(numero, produto);
    }

    public String getPedidos(int numero, String atributo) throws PedidoNaoEncontradoException {
        return servicoPedido.getAtributo(numero, atributo);
    }

    public void fecharPedido(int numero) throws PedidoNaoEncontradoException {
        servicoPedido.fecharPedido(numero);
    }

    public void removerProduto(int pedido, String produto) throws PedidoNaoEncontradoException {
        servicoPedido.removerProduto(pedido, produto);
    }

    // Métodos para salvar e carregar entidades em XML
    public void salvarUsuarios(List<Usuario> usuarios) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(UsuariosWrapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        UsuariosWrapper wrapper = new UsuariosWrapper(usuarios);
        marshaller.marshal(wrapper, new File("usuarios.xml"));
    }

    public List<Usuario> carregarUsuarios() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(UsuariosWrapper.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        UsuariosWrapper wrapper = (UsuariosWrapper) unmarshaller.unmarshal(new File("usuarios.xml"));
        return wrapper.getUsuarios();
    }

    public void salvarEmpresas(List<Empresa> empresas) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(EmpresasWrapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        EmpresasWrapper wrapper = new EmpresasWrapper(empresas);
        marshaller.marshal(wrapper, new File("empresas.xml"));
    }

    public List<Empresa> carregarEmpresas() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(EmpresasWrapper.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        EmpresasWrapper wrapper = (EmpresasWrapper) unmarshaller.unmarshal(new File("empresas.xml"));
        return wrapper.getEmpresas();
    }

    public void salvarProdutos(List<Produto> produtos) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(ProdutosWrapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        ProdutosWrapper wrapper = new ProdutosWrapper(produtos);
        marshaller.marshal(wrapper, new File("produtos.xml"));
    }

    public List<Produto> carregarProdutos() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(ProdutosWrapper.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ProdutosWrapper wrapper = (ProdutosWrapper) unmarshaller.unmarshal(new File("produtos.xml"));
        return wrapper.getProdutos();
    }

    public void salvarPedidos(List<Pedido> pedidos) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(PedidosWrapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        PedidosWrapper wrapper = new PedidosWrapper(pedidos);
        marshaller.marshal(wrapper, new File("pedidos.xml"));
    }

    public List<Pedido> carregarPedidos() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(PedidosWrapper.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        PedidosWrapper wrapper = (PedidosWrapper) unmarshaller.unmarshal(new File("pedidos.xml"));
        return wrapper.getPedidos();
    }
}