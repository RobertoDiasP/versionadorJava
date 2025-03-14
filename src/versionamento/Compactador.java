package versionamento;

import java.io.*;

import javax.swing.JOptionPane;

import org.apache.commons.compress.archivers.zip.*;

public class Compactador {
    public static void compactarZip(String arquivoEntrada, String diretorioDestino, String sistema, String nome) {
        File arquivo = new File(arquivoEntrada);
        
        // Verifica se o arquivo de entrada existe
        if (!arquivo.exists()) {
            System.err.println("Arquivo de entrada não encontrado: " + arquivoEntrada);
            return;
        }

        // Verifica se o diretório de destino existe, caso contrário, tenta criá-lo
        File destinoDir = new File(diretorioDestino);
        if (!destinoDir.exists()) {
            if (!destinoDir.mkdirs()) {
                System.err.println("Não foi possível criar o diretório de destino: " + diretorioDestino);
                return;
            }
        }

        // Define o nome do arquivo ZIP com base no nome do arquivo de entrada
        String nomeArquivoZip = diretorioDestino + File.separator + sistema + "_"+ nome + ".zip";

        // Usando try-with-resources para garantir que os recursos sejam fechados
        try (FileOutputStream fos = new FileOutputStream(nomeArquivoZip);
             ZipArchiveOutputStream zipOutput = new ZipArchiveOutputStream(fos)) {

            // Criando a entrada no arquivo .zip
            ZipArchiveEntry entry = new ZipArchiveEntry(arquivo, arquivo.getName());
            zipOutput.putArchiveEntry(entry);

            // Lendo e escrevendo o conteúdo do arquivo no arquivo .zip
            try (FileInputStream fis = new FileInputStream(arquivo)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    zipOutput.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                System.err.println("Erro ao ler o arquivo de entrada: " + e.getMessage());
                return;
            }

            // Fechando a entrada do arquivo zip
            zipOutput.closeArchiveEntry();

            System.out.println("Arquivo compactado com sucesso para: " + nomeArquivoZip);
            JOptionPane.showMessageDialog(null, "Arquivo compactado com sucesso!");
            
        } catch (IOException e) {
            System.err.println("Erro ao compactar: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Teste da função compactarZip
        compactarZip("caminho/do/arquivo.txt", "caminho/de/destino", "sistema", "nome");
    }
}