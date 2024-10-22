package br.ufal.ic.p2.myfood.utils;

import java.util.List;

// Interface genérica para persistência de dados, permitindo operações em qualquer modelo de dados.
public interface Persistencia<Models> {
    // Método para iniciar a persistência. Pode ser usado para configurar a fonte de dados.
    void iniciar();

    // Método para salvar um novo modelo na fonte de dados.
    // Parâmetro:
    // Model modelo - o objeto a ser salvo.
    void salvar(Models modelo);

    // Método para remover um modelo existente da fonte de dados.
    // Parâmetro:
    // int id - identificador do modelo a ser removido.
    void remover(int id);

    // Método para limpar todos os dados armazenados na fonte de dados.
    void limpar();

    // Método para editar um modelo existente na fonte de dados.
    // Parâmetro:
    // Model novo_modelo - o novo objeto que substitui o modelo existente.
    void editar(Models novo_modelo);

    // Método para buscar um modelo específico na fonte de dados.
    // Parâmetro:
    // int id - identificador do modelo a ser buscado.
    // Retorno:
    // Model - o modelo correspondente ao id, ou null se não encontrado.
    Models buscar(int id);

    // Método para listar todos os modelos armazenados na fonte de dados.
    // Retorno:
    // List<Model> - lista contendo todos os modelos.
    List<Models> listar();

    // Método para atualizar a fonte de dados, se necessário.
    // Pode ser usado para persistir alterações ou fazer operações de manutenção.
    void atualizar();
}