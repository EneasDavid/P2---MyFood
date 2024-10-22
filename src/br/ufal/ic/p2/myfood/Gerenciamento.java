package br.ufal.ic.p2.myfood;

import br.ufal.ic.p2.myfood.exceptions.*;
import br.ufal.ic.p2.myfood.models.*;
import br.ufal.ic.p2.myfood.models.Empresa.Empresa;
import br.ufal.ic.p2.myfood.models.Empresa.Farmacia;
import br.ufal.ic.p2.myfood.models.Empresa.Mercado;
import br.ufal.ic.p2.myfood.models.Empresa.Restaurante;
import br.ufal.ic.p2.myfood.models.Usuario.Dono;
import br.ufal.ic.p2.myfood.models.Usuario.Entregador;
import br.ufal.ic.p2.myfood.models.Usuario.Usuario;
import br.ufal.ic.p2.myfood.persistence.*;
import br.ufal.ic.p2.myfood.utils.Persistencia;
import br.ufal.ic.p2.myfood.utils.ValidacaoUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class Gerenciamento {

    public static Persistencia<Usuario> usuarioP = new PersistenciaUsuario();
    public static Persistencia<Empresa> empresaP = new PersistenciaEmpresa();
    public static Persistencia<Produto> produtoP = new PersistenciaProduto();
    public static Persistencia<Pedido> pedidoP = new PersistenciaPedido();
    public static Persistencia<Entrega> entregaP = new PersistenciaEntrega();

    public Gerenciamento() {
        usuarioP.iniciar();
        empresaP.iniciar();
        produtoP.iniciar();
        pedidoP.iniciar();
        entregaP.iniciar();

        for (Usuario usuario : usuarioP.listar()) {
            if ("Dono".equals(usuario.getTipo())) {
                Dono dono = (Dono) usuario;

                List<Empresa> empresasDoUsuario = empresaP.listar()
                        .stream()
                        .filter(company -> company.getDono().getId() == dono.getId())
                        .toList();

                dono.setComp_list(empresasDoUsuario);
            }
        }


        for (Empresa empresa : empresaP.listar()) {
            List<Produto> produtosEmpresa = produtoP.listar()
                    .stream()
                    .filter(produto -> produto.getId_dono() == empresa.getId())
                    .toList();

            empresa.setProd_list(produtosEmpresa);
        }
    }

    public void zerarSistema() {
        usuarioP.limpar();
        empresaP.limpar();
        produtoP.limpar();
        pedidoP.limpar();
    }


    public void encerrarSistema() {
    }


    public List<Pedido> pedidosClienteEmpresa(int idCliente, int idEmpresa) {
        String nomeCliente = usuarioP.buscar(idCliente).getNome();
        String nomeEmpresa = empresaP.buscar(idEmpresa).getNome();

        return pedidoP.listar()
                .stream()
                .filter(pedido -> pedido.getCliente().getNome().equals(nomeCliente) && pedido.getEmpresa().getNome().equals(nomeEmpresa) && pedido.getEstado().equals("aberto"))
                .toList();
    }
    public void usuarioUnico(String email) throws CriacaoUsuarioException {
        for (Usuario user : usuarioP.listar()) {
            if (user.getEmail().equals(email)) {
                throw new CriacaoUsuarioException("Conta com esse email ja existe");
            }
        }
    }
    public void usuarioPodeSerDono(int id) throws AcaoBloqueadaParaUsuarioException {
        if (!(usuarioP.buscar(id).getClass().getSimpleName().equals("Dono"))) {
            throw new AcaoBloqueadaParaUsuarioException();
        }
    }
    public void empresaUnicoDono(String nome, int dono) throws CriacaoEmpressaException {
        for (Empresa empresa : empresaP.listar()) {
            if (empresa.getNome().equals(nome) && empresa.getDono().getId() == dono) throw new CriacaoEmpressaException("Empresa com esse nome ja existe");
        }
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws CriacaoUsuarioException {

        ValidacaoUtils.validarUsuario(nome, email, senha, endereco);

        usuarioUnico(email);

        Usuario cliente = new Usuario(nome, email, senha, endereco);
        usuarioP.salvar(cliente);
    }


    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws CriacaoUsuarioException {

        ValidacaoUtils.validarUsuario(nome, email, senha, endereco);

        ValidacaoUtils.validarUsuarioCPF(cpf);

        usuarioUnico(email);

        Dono dono = new Dono(nome, email, senha, endereco, cpf);
        usuarioP.salvar(dono);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa) throws CriacaoUsuarioException {
        ValidacaoUtils.validarUsuario(nome, email, senha, endereco);

        ValidacaoUtils.validarVeiculo(veiculo);

        ValidacaoUtils.validarPlaca(placa);

        usuarioUnico(email);

        Entregador entregador = new Entregador(nome, email, senha, endereco, veiculo, placa);
        usuarioP.salvar(entregador);
    }


    public String getAtributoUsuario(int id, String atributo) throws SemRegistroException {
        Usuario usuario = usuarioP.buscar(id);

        if (usuario == null)
            throw new SemRegistroException("Usuario nao cadastrado.");

        return usuario.getAtributo(atributo);
    }


    public int login(String email, String senha) throws CredeciaisInvalidasException {
        for (Usuario user : usuarioP.listar()) {
            if (user.getEmail().equals(email) && user.getSenha().equals(senha)) return user.getId();
        }
        throw new CredeciaisInvalidasException();
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws CriacaoEmpressaException, AcaoBloqueadaParaUsuarioException {


        ValidacaoUtils.nomeEmpresaUnico(nome, endereco);

        usuarioPodeSerDono(dono);

        for (Empresa empresa : empresaP.listar()) {
            if (empresa.getNome().equals(nome) && empresa.getDono().getId() != dono) {
                throw new CriacaoEmpressaException("Empresa com esse nome ja existe");
            }
            if (empresa.getNome().equals(nome) && empresa.getEndereco().equals(endereco)) {
                throw new CriacaoEmpressaException("Proibido cadastrar duas empresas com o mesmo nome e local");
            }
        }

        if (tipoEmpresa.equals("restaurante")) {
            Dono tempDono = (Dono) usuarioP.buscar(dono);
            Restaurante restaurante = new Restaurante(nome, endereco, tempDono, tipoCozinha);
            empresaP.salvar(restaurante);
            tempDono.addComp_list(restaurante);
            return restaurante.getId();
        }

        return -1;
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String abre, String fecha, String tipoMercado) throws CriacaoEmpressaException, AcaoBloqueadaParaUsuarioException {
        ValidacaoUtils.nomeEmpresaUnico(nome, endereco);

        for (Empresa empresa : empresaP.listar()) {
            if (empresa.getNome().equals(nome) && empresa.getDono().getId() != dono) {
                throw new CriacaoEmpressaException("Empresa com esse nome ja existe");
            }
            if (empresa.getNome().equals(nome) && empresa.getEndereco().equals(endereco)) {
                throw new CriacaoEmpressaException("Proibido cadastrar duas empresas com o mesmo nome e local");
            }
        }
        ValidacaoUtils.validarEmpresa(tipoEmpresa, abre, fecha, tipoMercado);

        empresaUnicoDono(nome, dono);

        if (tipoEmpresa.equals("mercado")) {
            Dono tempDono = (Dono) usuarioP.buscar(dono);
            Mercado mercado = new Mercado(nome, endereco, tempDono, abre, fecha, tipoMercado);
            empresaP.salvar(mercado);
            tempDono.addComp_list(mercado);
            return mercado.getId();
        }

        return -1;
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, boolean aberto24Horas, int numeroFuncionarios) throws CriacaoEmpressaException, AcaoBloqueadaParaUsuarioException {
        ValidacaoUtils.nomeEmpresaUnico(nome, endereco);

        empresaUnicoDono(nome, dono);

        ValidacaoUtils.validaTipoEmpresa(tipoEmpresa);

        for (Empresa empresa : empresaP.listar()) {
            if (empresa.getNome().equals(nome) && empresa.getDono().getId() != dono) {
                throw new CriacaoEmpressaException("Empresa com esse nome ja existe");
            }
            if (empresa.getNome().equals(nome) && empresa.getEndereco().equals(endereco)) {
                throw new CriacaoEmpressaException("Proibido cadastrar duas empresas com o mesmo nome e local");
            }
        }
        usuarioPodeSerDono(dono);

        if (tipoEmpresa.equals("farmacia")) {
            Dono tempDono = (Dono) usuarioP.buscar(dono);
            Farmacia farmacia = new Farmacia(nome, endereco, tempDono, aberto24Horas, numeroFuncionarios);
            empresaP.salvar(farmacia);
            tempDono.addComp_list(farmacia);
            return farmacia.getId();
        }

        return -1;
    }


    public String getEmpresasDoUsuario(int idDono) throws AcaoBloqueadaParaUsuarioException {
        if (!(usuarioP.buscar(idDono).getClass().getSimpleName().equals("Dono"))) {
            throw new AcaoBloqueadaParaUsuarioException();
        }

        Dono tempDono = (Dono) usuarioP.buscar(idDono);
        return "{" + tempDono.getComp_list().toString() + "}";
    }


    public String getAtributoEmpresa(int idEmpresa, String atributo) throws AtributoInvalidoException, SemRegistroException {
        Empresa tempEmpresa = empresaP.buscar(idEmpresa);
        if (tempEmpresa == null) {
            throw new SemRegistroException("Empresa nao cadastrada");
        }

        if (atributo == null || atributo.isEmpty()) {
            throw new AtributoInvalidoException();
        }

        if (Objects.equals(atributo, "dono")) {
            Usuario tempUsuario = usuarioP.buscar(tempEmpresa.getDono().getId());
            return tempUsuario.getNome();
        }

        String result = tempEmpresa.getAtributo(atributo);
        if (result == null) {
            throw new AtributoInvalidoException();
        }

        return result;
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws LimiteEntregaException, AcaoBloqueadaParaUsuarioException, SemRegistroException, CriacaoEmpressaException {
        if (nome == null || nome.isEmpty()) {
            throw new CriacaoEmpressaException("Nome invalido");
        }

        if (indice < 0) {
            throw new LimiteEntregaException("Indice invalido");
        }

        // Verificando se o usuário é realmente um dono
        Usuario usuario = usuarioP.buscar(idDono);
        if (usuario == null) {
            throw new SemRegistroException("Usuario nao encontrado");
        }

        if (!usuario.getTipo().equals("Dono")) {
            throw new AcaoBloqueadaParaUsuarioException();
        }

        // Obtendo a lista de empresas do dono com o nome correspondente
        List<Empresa> empresasDoDono = empresaP.listar()
                .stream()
                .filter(empresa -> empresa.getDono().getId() == idDono && empresa.getNome().equals(nome))
                .collect(Collectors.toList());

        if (empresasDoDono.isEmpty()) {
            throw new SemRegistroException("Nao existe empresa com esse nome");
        }

        if (indice >= empresasDoDono.size()) {
            throw new LimiteEntregaException("Indice maior que o esperado");
        }

        // Verificando se o ID da empresa é válido
        Integer empresaId = empresasDoDono.get(indice).getId();
        if (empresaId == null) {
            throw new SemRegistroException("ID da empresa é nulo");
        }

        return empresaId;
    }

    private void testProductInvalid(String nome, float valor, String categoria) throws CriacaoProdutoException {
        if (nome == null || nome.isEmpty()) {
            throw new CriacaoProdutoException("Nome invalido");
        }

        if (valor < 0) {
            throw new CriacaoProdutoException("Valor invalido");
        }

        if (categoria == null || categoria.isEmpty()) {
            throw new CriacaoProdutoException("Categoria invalido");
        }

    }


    public int criarProduto(int idEmpresa, String nome, float valor, String categoria) throws CriacaoProdutoException {
        Empresa empresa = empresaP.buscar(idEmpresa);

        testProductInvalid(nome, valor, categoria);

        for (Produto produto : empresa.getProd_list()) {
            if (produto.getNome().equals(nome)) {
                throw new CriacaoProdutoException("Ja existe um produto com esse nome para essa empresa");
            }
        }

        Produto produto = new Produto(nome, valor, categoria, empresa.getId());
        produtoP.salvar(produto);
        empresa.addProd_list(produto);
        return produto.getId();
    }


    public void editarProduto(int idProduto, String nome, float valor, String categoria) throws CriacaoProdutoException {
        testProductInvalid(nome, valor, categoria);

        Produto prod = produtoP.buscar(idProduto);
        if (prod == null) {
            throw new CriacaoProdutoException("Produto nao cadastrado");
        }

        prod.setNome(nome);
        prod.setValor(valor);
        prod.setCategoria(categoria);

        produtoP.editar(prod);
    }


    public String getProduto(String nome, int idEmpresa, String atributo) throws AtributoInvalidoException, SemRegistroException {
        Empresa empresa = empresaP.buscar(idEmpresa);
        List<Produto> list = empresa.getProd_list();

        if (atributo == null || atributo.isEmpty()) {
            throw new AtributoInvalidoException();
        }

        for (Produto prod : list) {
            if (prod.getNome().equals(nome)) {
                return switch (atributo) {
                    case "nome" -> prod.getNome();
                    case "valor" -> String.format(Locale.US, "%.2f", prod.getValor());
                    case "categoria" -> prod.getCategoria();
                    case "empresa" -> String.valueOf(empresa.getNome());
                    default -> throw new AtributoInvalidoException("Atributo nao existe");
                };
            }
        }
        throw new SemRegistroException("Produto nao encontrado");
    }


    public String listarProdutos(int idEmpresa) throws SemRegistroException {
        Empresa empresa = empresaP.buscar(idEmpresa);

        if (empresa == null) {
            throw new SemRegistroException("Empresa nao encontrada");
        }

        return "{" + empresa.getProd_list() + "}";
    }


    public int criarPedido(int idCliente, int idEmpresa) throws PedidoException, AcaoBloqueadaParaUsuarioException {
        Usuario temp_cliente = usuarioP.buscar(idCliente);
        List<Pedido> pedidosClienteEmpresa = pedidosClienteEmpresa(idCliente, idEmpresa);

        if (temp_cliente.getClass().getSimpleName().equals("Dono")) {
            throw new AcaoBloqueadaParaUsuarioException("Dono de empresa nao pode fazer um pedido");
        } else if (!pedidosClienteEmpresa.isEmpty()) {
            throw new PedidoException();
        } else {
            Empresa temp_comp = empresaP.buscar(idEmpresa);
            Pedido ped = new Pedido(temp_cliente, temp_comp);
            pedidoP.salvar(ped);
            return ped.getNumero();
        }
    }


    public void adicionarProduto(int idPedido, int idProduto) throws SemRegistroException, StatusException {
        Pedido tempPedido = pedidoP.buscar(idPedido);

        if (tempPedido == null) {
            throw new SemRegistroException("Nao existe pedido em aberto");
        }

        if (!(tempPedido.getEstado().equals("aberto"))) {
            throw new StatusException("Nao e possivel adcionar produtos a um pedido fechado");
        }

        List<Produto> prodList = tempPedido.getEmpresa().getProd_list();
        for (Produto prod : prodList) {
            if (prod.getId() == idProduto) {
                tempPedido.addProductToList(prod);
                return;
            }
        }

        throw new SemRegistroException("O produto nao pertence a essa empresa");
    }


    public int getNumeroPedido(int idCliente, int idEmpresa, int indice) {
        String nomeCliente = usuarioP.buscar(idCliente).getNome();
        String nomeEmpresa = empresaP.buscar(idEmpresa).getNome();

        List<Pedido> pedidosClienteEmpresa = pedidoP.listar()
                .stream()
                .filter(pedido -> pedido.getCliente().getNome().equals(nomeCliente) && pedido.getEmpresa().getNome().equals(nomeEmpresa))
                .toList();

        return pedidosClienteEmpresa.get(indice).getNumero();
    }


    public String getPedidos(int idPedido, String atributo) throws SemRegistroException, AtributoInvalidoException {
        if (atributo == null || atributo.isEmpty()) {
            throw new AtributoInvalidoException();
        }

        Pedido tempPedido = pedidoP.buscar(idPedido);

        if (tempPedido.getNumero() == idPedido) {
            return switch (atributo) {
                case "cliente" -> tempPedido.getCliente().getNome();
                case "empresa" -> tempPedido.getEmpresa().getNome();
                case "estado" -> tempPedido.getEstado();
                case "produtos" -> "{" + tempPedido.getProd_list() + "}";
                case "valor" -> String.format(Locale.US, "%.2f", tempPedido.getValor_total());
                default -> throw new AtributoInvalidoException("Atributo nao existe");
            };
        }
        throw new SemRegistroException("Produto nao encontrado");
    }


    public void fecharPedido(int idPedido) throws SemRegistroException {
        Pedido tempPedido = pedidoP.buscar(idPedido);
        if (tempPedido == null) {
            throw new SemRegistroException("Pedido nao encontrado");
        }

        tempPedido.mudarEstado();

    }

    public void liberarPedido(int idPedido) throws SemRegistroException, StatusException {
        if (String.valueOf(idPedido) == null || String.valueOf(idPedido).isEmpty()) {
            throw new SemRegistroException("Nao e possivel liberar um produto que nao esta sendo preparado");
        }

        Pedido tempPedido = pedidoP.buscar(idPedido);
        if (tempPedido == null) {
            throw new SemRegistroException("Pedido nao encontrado");
        }
        tempPedido.mudarEstadoNovamente();
    }

    public void removerProduto(int idPedido, String produto) throws AtributoInvalidoException, SemRegistroException, StatusException {
        if (produto == null || produto.isEmpty()) {
            throw new AtributoInvalidoException("Produto invalido");
        }

        Pedido ped = pedidoP.buscar(idPedido);
        if (ped.getEstado().equals("preparando")) {
            throw new StatusException("Nao e possivel remover produtos de um pedido fechado");
        }

        List<Produto> listProd = ped.getProd_list();
        for (Produto prod : listProd) {
            if (prod.getNome().equals(produto)) {
                ped.removeProductFromList(prod);
                pedidoP.atualizar();
                return;
            }
        }

        throw new SemRegistroException("Produto nao encontrado");
    }

    public int alterarFuncionamento(int mercadoId, String abre, String fecha) throws SemRegistroException {
        if (abre == null) {
            throw new SemRegistroException("Horarios invalidos");
        }
        if (fecha == null) {
            throw new SemRegistroException("Horarios invalidos");
        }

        if (!abre.matches("^\\d{2}:\\d{2}$") || !fecha.matches("^\\d{2}:\\d{2}$")) {
            throw new SemRegistroException("Formato de hora invalido");
        }
        String[] abreParts = abre.split(":");
        String[] fechaParts = fecha.split(":");

        int abreHora = Integer.parseInt(abreParts[0]);
        int abreMinuto = Integer.parseInt(abreParts[1]);

        int fechaHora = Integer.parseInt(fechaParts[0]);
        int fechaMinuto = Integer.parseInt(fechaParts[1]);

        if (abreHora > 23 || fechaHora > 23 || abreMinuto > 59 || fechaMinuto > 59) {
            throw new SemRegistroException("Horarios invalidos");
        }

        if (fechaHora < abreHora || (fechaHora == abreHora && fechaMinuto < abreMinuto)) {
            throw new SemRegistroException("Horarios invalidos");
        }

        Empresa empresa = empresaP.buscar(mercadoId);

        try {
            Mercado mercado = (Mercado) empresa;
            mercado.getTipoMercado();
        } catch (ClassCastException e) {
            throw new SemRegistroException("Nao e um mercado valido");
        }
        Mercado mercado = (Mercado) empresa;

        mercado.setAbre(abre);
        mercado.setFecha(fecha);

        empresaP.atualizar();
        return -1;
    }

    public void cadastrarEntregador(int idEmpresa, int idEntregador) throws AcaoBloqueadaParaUsuarioException, SemRegistroException {
        Empresa empresa = empresaP.buscar(idEmpresa);
        if (empresa == null) {
            throw new SemRegistroException("Empresa nao encontrada");
        }
        if (idEntregador <= 0 || idEmpresa <= 0) {
            throw new SemRegistroException("ID de empresa ou entregador inválido");
        }

        Usuario usuario = usuarioP.buscar(idEntregador);
        if (usuario == null || !usuario.isEntregador()) {
            throw new AcaoBloqueadaParaUsuarioException("Usuario nao e um entregador");
        }

        if (empresa.getListaEntregadores().contains(usuario)) {
            throw new SemRegistroException("Entregador ja cadastrado nesta empresa");
        }

        empresa.addEntregador((Entregador) usuario);

    }

    public String getEntregadores(int idEmpresa) throws SemRegistroException {
        Empresa empresa = empresaP.buscar(idEmpresa);
        if (empresa == null) {
            throw new SemRegistroException("Empresa nao encontrada");
        }

        List<String> emails = empresa.getListaEntregadores()
                .stream()
                .map(Entregador::getEmail)
                .collect(Collectors.toList());


        return "{[" + String.join(", ", emails) + "]}";
    }

    public String getEmpresas(int idEntregador) throws AcaoBloqueadaParaUsuarioException {
        Usuario entregador = usuarioP.buscar(idEntregador);

        if (entregador == null || !entregador.isEntregador()) {
            throw new AcaoBloqueadaParaUsuarioException("Usuario nao e um entregador");
        }

        List<Empresa> empresasDoEntregador = empresaP.listar()
                .stream()
                .filter(empresa -> empresa.getListaEntregadores().contains(entregador))
                .collect(Collectors.toList());

        String empresasFormatadas = empresasDoEntregador.stream()
                .map(empresa -> "[" + empresa.getNome() + ", " + empresa.getEndereco() + "]")
                .collect(Collectors.joining(", "));

        return "{[" + empresasFormatadas + "]}";
    }

    public int obterPedido(int idEntregador) throws AcaoBloqueadaParaUsuarioException, SemRegistroException, StatusException {
        Usuario entregador = usuarioP.buscar(idEntregador);

        if (entregador == null || !entregador.getClass().getSimpleName().equals("Entregador")) {
            throw new AcaoBloqueadaParaUsuarioException("Usuario nao e um entregador.");
        }

        List<Empresa> empresasDoEntregador = empresaP.listar()
                .stream()
                .filter(empresa -> empresa.getListaEntregadores().contains(entregador))
                .collect(Collectors.toList());

        if (empresasDoEntregador.isEmpty()) {
            throw new SemRegistroException("Entregador nao esta em nenhuma empresa.");
        }

        List<Pedido> pedidosProntos = pedidoP.listar()
                .stream()
                .filter(pedido -> empresasDoEntregador.contains(pedido.getEmpresa()) && pedido.getEstado().equals("pronto"))
                .collect(Collectors.toList());

        if (pedidosProntos.isEmpty()) {
            throw new StatusException("Nao existe pedido para entrega");
        }

        Pedido pedidoSelecionado = pedidosProntos.stream()
                .filter(p -> p.getEmpresa().getClass().getSimpleName().equals("Farmacia"))
                .min(Comparator.comparingInt(Pedido::getNumero))
                .orElse(null);

        if (pedidoSelecionado == null) {
            pedidoSelecionado = pedidosProntos.stream()
                    .min(Comparator.comparingInt(Pedido::getNumero))
                    .orElseThrow(() -> new StatusException("Nao foi possivel selecionar um pedido"));
        }


        return pedidoSelecionado.getNumero();
    }

    public int criarEntrega(int idPedido, int idEntregador, String destino) throws SemRegistroException, StatusException {
        Pedido pedido = pedidoP.buscar(idPedido);
        if (pedido == null) {
            throw new SemRegistroException("Pedido nao encontrado");
        }

        Usuario usuario = usuarioP.buscar(idEntregador);
        if (usuario == null || !usuario.getClass().getSimpleName().equals("Entregador")) {
            throw new SemRegistroException("Nao e um entregador valido");
        }

        Entregador entregador = (Entregador) usuario;

        if (entregador.isEmEntrega()) {
            throw new StatusException("Entregador ainda em entrega");
        }
        if (!pedido.getEstado().equals("pronto")) {
            throw new StatusException("Pedido nao esta pronto para entrega");
        }

        if (destino == null) {
            destino = pedido.getCliente().getEndereco();
        }

        entregador.setEmEntrega(true);
        pedido.mudarParaEntregando();

        Entrega novaEntrega = new Entrega(pedido, entregador, destino);
        entregaP.salvar(novaEntrega);
        pedidoP.atualizar();
        usuarioP.atualizar();

        return novaEntrega.getId();
    }

    public String getEntrega(int idEntrega, String atributo) throws SemRegistroException, AtributoInvalidoException {
        Entrega entrega = entregaP.buscar(idEntrega);
        if (entrega == null) {
            throw new SemRegistroException("Entrega não encontrada");
        }

        if (atributo == null || atributo.trim().isEmpty()) {
            throw new SemRegistroException("Atributo invalido");
        }
        return entrega.getAtributo(atributo);
    }

    public int getIdEntrega(int pedidoId) throws SemRegistroException {
        for (Entrega entrega : entregaP.listar()) {
            if (entrega.getPedido().getNumero() == pedidoId) {
                return entrega.getId();
            }
        }

        throw new SemRegistroException("Nao existe entrega com esse id");
    }

    public void entregar(int entregaId) throws SemRegistroException {

        Entrega entrega = entregaP.buscar(entregaId);

        if (entrega == null) {
            throw new SemRegistroException("Nao existe nada para ser entregue com esse id");
        }

        Pedido pedido = entrega.getPedido();
        pedido.getEntregar();

        Entregador entregador = entrega.getEntregador();
        entregador.setEmEntrega(false);

        pedidoP.atualizar();
        entregaP.atualizar();
        usuarioP.atualizar();
    }
}