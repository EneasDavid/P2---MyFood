package br.ufal.ic.p2.myfood.persistence;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ManipuladorXML {

    public ManipuladorXML() {

    }

    public void gravarObjetoComoXML(Object objeto, String nome_arquivo) {
        try (FileOutputStream fos = new FileOutputStream(nome_arquivo);
             XMLEncoder encoder = new XMLEncoder(fos)) {
            encoder.writeObject(objeto);

        } catch (IOException e) {
            System.out.println("Problemas de Serializacao");
        }

    }

    public <T> T lerObjetoDeXML(T objeto, String nome_arquivo) {
        File file = new File(nome_arquivo);

        if (!file.exists()) {
            return objeto;
        } else if (file.length() == 0) {
            return objeto;
        }

        try (FileInputStream fis = new FileInputStream(nome_arquivo);
             XMLDecoder decoder = new XMLDecoder(fis)) {
            objeto = (T) decoder.readObject();

        } catch (IOException e) {
            System.out.println("Problemas de Desserializacao");
        }
        return objeto;
    }

    public void ApagarDadosXML(String nome_arquivo) {
        try (FileOutputStream fos = new FileOutputStream(nome_arquivo)) {

            fos.write(new byte[0]);

        } catch (IOException e) {
            System.out.println("Problemas ao Apagar Dados");
        }
    }
}
