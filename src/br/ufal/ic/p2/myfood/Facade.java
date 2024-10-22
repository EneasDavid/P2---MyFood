package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.exceptions.*;

public class Facade {
    private final Gerenciamento gerenciamento = new Gerenciamento();

    public void zerarSistema() {
        gerenciamento.zerarSistema();
    }

    public void encerrarSistema() {
        gerenciamento.encerrarSistema();
    }

    public String getAtributoUsuario(int id, String atributo) throws SemRegistroException {
        return gerenciamento.getAtributoUsuario(id, atributo);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws CriacaoUsuarioException {
        gerenciamento.criarUsuario(nome, email, senha, endereco);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws CriacaoUsuarioException {
        gerenciamento.criarUsuario(nome, email, senha, endereco, cpf);
    }

    public int login(String email, String senha) throws CredeciaisInvalidasException {
        return gerenciamento.login(email, senha);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws CriacaoEmpressaException, AcaoBloqueadaParaUsuarioException {
        return gerenciamento.criarEmpresa(tipoEmpresa, dono, nome, endereco, tipoCozinha);
    }

    public String getEmpresasDoUsuario(int dono) throws AcaoBloqueadaParaUsuarioException {
        return gerenciamento.getEmpresasDoUsuario(dono);
    }

    public String getAtributoEmpresa(int empresa, String atributo) throws AtributoInvalidoException, SemRegistroException {
        return gerenciamento.getAtributoEmpresa(empresa, atributo);
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws LimiteEntregaException, AcaoBloqueadaParaUsuarioException, SemRegistroException, CriacaoEmpressaException {
        return gerenciamento.getIdEmpresa(idDono, nome, indice);
    }

    public int criarProduto(int id_empresa, String nome, float valor, String categoria) throws CriacaoProdutoException {
        return gerenciamento.criarProduto(id_empresa, nome, valor, categoria);
    }

    public void editarProduto(int produto, String nome, float valor, String categoria) throws CriacaoProdutoException {
        gerenciamento.editarProduto(produto, nome, valor, categoria);
    }

    public String getProduto(String nome, int empresa, String atributo) throws AtributoInvalidoException, SemRegistroException {
        return gerenciamento.getProduto(nome, empresa, atributo);
    }

    public String listarProdutos(int empresa) throws SemRegistroException {
        return gerenciamento.listarProdutos(empresa);
    }

    public int criarPedido(int cliente, int empresa) throws Exception, AcaoBloqueadaParaUsuarioException {
        return gerenciamento.criarPedido(cliente, empresa);
    }

    public int getNumeroPedido(int cleinte, int empresa, int indice) {
        return gerenciamento.getNumeroPedido(cleinte, empresa, indice);
    }

    public void adicionarProduto(int pedido, int produto) throws SemRegistroException, StatusException {
        gerenciamento.adicionarProduto(pedido, produto);
    }

    public String getPedidos(int pedido, String atributo) throws AtributoInvalidoException, SemRegistroException {
        return gerenciamento.getPedidos(pedido, atributo);
    }

    public void fecharPedido(int pedido) throws SemRegistroException {
        gerenciamento.fecharPedido(pedido);
    }

    public void removerProduto(int pedido, String produto) throws StatusException, SemRegistroException, AtributoInvalidoException {
        gerenciamento.removerProduto(pedido, produto);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String abre, String fecha, String tipoMercado) throws CriacaoEmpressaException, AcaoBloqueadaParaUsuarioException {
        return gerenciamento.criarEmpresa(tipoEmpresa, dono, nome, endereco, abre, fecha, tipoMercado);
    }

    public int alterarFuncionamento(int mercado, String abre, String fecha) throws SemRegistroException {
        return gerenciamento.alterarFuncionamento(mercado, abre, fecha);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, boolean aberto24Horas, int numeroFuncionarios) throws CriacaoEmpressaException, AcaoBloqueadaParaUsuarioException {
        return gerenciamento.criarEmpresa(tipoEmpresa, dono, nome, endereco, aberto24Horas, numeroFuncionarios);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa) throws CriacaoUsuarioException {
        gerenciamento.criarUsuario(nome, email, senha, endereco, veiculo, placa);
    }

    public void cadastrarEntregador(int idEmpresa, int idEntregador) throws SemRegistroException, AcaoBloqueadaParaUsuarioException {
        gerenciamento.cadastrarEntregador(idEmpresa, idEntregador);
    }

    public String getEntregadores(int idEmpresa) throws SemRegistroException {
        return gerenciamento.getEntregadores(idEmpresa);
    }

    public String getEmpresas(int idEntregador) throws AcaoBloqueadaParaUsuarioException {
        return gerenciamento.getEmpresas(idEntregador);
    }

    public void liberarPedido(int idPedido) throws SemRegistroException, StatusException {
        gerenciamento.liberarPedido(idPedido);
    }

    public int obterPedido(int idEntregador) throws AcaoBloqueadaParaUsuarioException, SemRegistroException, StatusException {
        return gerenciamento.obterPedido(idEntregador);
    }

    public int criarEntrega(int pedido, int entregador, String destino) throws SemRegistroException, StatusException {
        return gerenciamento.criarEntrega(pedido, entregador, destino);
    }

    public String getEntrega(int idEntrega, String atributo) throws SemRegistroException, AtributoInvalidoException {
        return gerenciamento.getEntrega(idEntrega, atributo);
    }

    public int getIdEntrega(int pedido) throws SemRegistroException {
        return gerenciamento.getIdEntrega(pedido);
    }

    public void entregar(int entregaId) throws SemRegistroException {
        gerenciamento.entregar(entregaId);
    }

}