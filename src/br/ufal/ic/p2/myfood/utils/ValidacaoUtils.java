package br.ufal.ic.p2.myfood.utils;

import br.ufal.ic.p2.myfood.exceptions.AcaoBloqueadaParaUsuarioException;
import br.ufal.ic.p2.myfood.exceptions.CriacaoEmpressaException;
import br.ufal.ic.p2.myfood.exceptions.CriacaoUsuarioException;


public class ValidacaoUtils {
    public static void validarUsuario(String nome, String email, String senha, String endereco) throws CriacaoUsuarioException {
        if (nome == null || nome.isEmpty()) {
            throw new CriacaoUsuarioException("Nome invalido");
        }

        if (email == null || !(email.contains("@"))) {
            throw new CriacaoUsuarioException("Email invalido");
        }

        if (senha == null || senha.isEmpty()) {
            throw new CriacaoUsuarioException("Senha invalido");
        }

        if (endereco == null || endereco.isEmpty()) {
            throw new CriacaoUsuarioException("Endereco invalido");
        }
    }

    public static void validarUsuarioCPF(String cpf) throws CriacaoUsuarioException {
        if (cpf == null || cpf.length() != 14) throw new CriacaoUsuarioException("CPF invalido");
    }

    public static void validarPlaca(String placa) throws CriacaoUsuarioException {
        if (placa == null || placa.isEmpty()) throw new CriacaoUsuarioException("Placa invalido");
    }

    public static void validarVeiculo(String veiculo) throws CriacaoUsuarioException {
        if (veiculo == null || veiculo.isEmpty()) throw new CriacaoUsuarioException("Veiculo invalido");
    }

    public static void validaTipoEmpresa(String tipoEmpresa) throws CriacaoEmpressaException {
        if (tipoEmpresa == null || tipoEmpresa.isEmpty()) throw new CriacaoEmpressaException("Tipo de empresa invalido");
    }
    public static void nomeEmpresaUnico(String nome, String endereco) throws CriacaoEmpressaException {
        if (nome == null || nome.isEmpty()) {
            throw new CriacaoEmpressaException("Nome invalido");
        }
        if (endereco == null || endereco.isEmpty()) {
            throw new CriacaoEmpressaException("Endereco da empresa invalido");
        }
    }
    // Método para validar empresa completa (inclui horários e tipo de mercado)
    public static void validarEmpresa(String tipoEmpresa, String abre, String fecha, String tipoMercado) throws CriacaoEmpressaException, AcaoBloqueadaParaUsuarioException {

        validaTipoEmpresa(tipoEmpresa);

        if (abre == null || fecha == null) throw new CriacaoEmpressaException("Horarios invalido");

        // Verificar o formato das horas
        if (!abre.matches("^\\d{2}:\\d{2}$") || !fecha.matches("^\\d{2}:\\d{2}$")) throw new CriacaoEmpressaException("Formato de hora invalido");


        if (tipoMercado == null || tipoMercado.isEmpty()) throw new CriacaoEmpressaException("Tipo de mercado invalido");
        String[] abreParts = abre.split(":");
        String[] fechaParts = fecha.split(":");

        int abreHora = Integer.parseInt(abreParts[0]);
        int abreMinuto = Integer.parseInt(abreParts[1]);

        int fechaHora = Integer.parseInt(fechaParts[0]);
        int fechaMinuto = Integer.parseInt(fechaParts[1]);

        if (abreHora > 23 || fechaHora > 23 || abreMinuto > 59 || fechaMinuto > 59) {
            throw new CriacaoEmpressaException("Horarios invalidos");
        }

        if (fechaHora < abreHora || (fechaHora == abreHora && fechaMinuto < abreMinuto)) {
            throw new CriacaoEmpressaException("Horarios invalidos");
        }
    }
}
