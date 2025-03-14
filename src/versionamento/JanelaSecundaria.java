package versionamento;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JanelaSecundaria extends JFrame {
    private JTextField campoVersao, nome, arquivoOrigem, diretorioDestino;
    private JComboBox<String> comboSistema;
    private JButton botaoEnviar, btnSelArqOrigem, btnSelDiretorio, btnBanco;
    public String dataUltimaModificacao;

    public JanelaSecundaria() {
        // Configuração da janela
        setTitle("Versionador F5");
        setSize(400, 400);
        setLocationRelativeTo(null); // Centraliza a janela
        setResizable(false); // Impede redimensionamento
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha apenas essa janela
        setLayout(null); // Desativa o layout padrão
        
        
        
        // versão
        JLabel labelVersao = new JLabel("Versão:");
        labelVersao.setBounds(20, 30, 80, 25);

        campoVersao = new JTextField();
        campoVersao.setBounds(100, 30, 200, 25);
        
        //sistema
        JLabel labelSistema = new JLabel("Sistema:");
        labelSistema.setBounds(20, 70, 80, 25);

        String[] sistemas = {"SiEmpresarial", "SiOfi", "SiEventos", "SiForm"};;
        comboSistema = new JComboBox<>(sistemas);
        comboSistema.setBounds(100, 70, 200, 25);
        
        //nome
        JLabel labelNome = new JLabel("Nome:");
        labelNome.setBounds(20, 120, 80, 25);
        
        nome = new JTextField();
        nome.setBounds(100, 120, 200, 25);
       
        //seleção arquivo origem
        JLabel labelArqOrigem = new JLabel("Arquivo Origem:");
        labelArqOrigem.setBounds(20, 160, 80, 25);        
        
        arquivoOrigem = new JTextField();
        arquivoOrigem.setBounds(100, 160, 200, 25);
        arquivoOrigem.setEditable(false);
        
        btnSelArqOrigem = new JButton("...");
        btnSelArqOrigem.setBounds(310, 160, 25, 25);   
        btnSelArqOrigem.addActionListener( e -> selecionarArquivoOrigem());
        
        //destino btnSelDiretorio diretorioDestino
        
        JLabel labelDiretorio = new JLabel("Destino: ");
        labelDiretorio.setBounds(20, 200, 80 , 25); 
        
        diretorioDestino = new JTextField();
        diretorioDestino.setBounds(100, 200, 200, 25);
        diretorioDestino.setEditable(false);
        
        btnSelDiretorio = new JButton("...");
        btnSelDiretorio.setBounds(310,200, 25, 25);
        btnSelDiretorio.addActionListener( e -> selecionarDiretorio());
        
        //botão executar
        botaoEnviar = new JButton("Mostrar Dados");
        botaoEnviar.setBounds(100, 240, 200, 30);
        

        botaoEnviar.addActionListener(e -> {
            String versao = campoVersao.getText();
            String sistema = (String) comboSistema.getSelectedItem();
            String arquivo = arquivoOrigem.getText();
            String destino = diretorioDestino.getText();
            String nomeArquivo = nome.getText();
            Compactador compactador = new Compactador();
            compactador.compactarZip(arquivo, destino, sistema, nomeArquivo);
            
        });
        
        btnBanco = new JButton("Chamar Conexão");
        btnBanco.setBounds(100,280, 200, 25);
        btnBanco.addActionListener( e -> chamarBanco());

        // Adicionando componentes à janela
        add(labelVersao);
        add(campoVersao);
        add(labelSistema);
        add(comboSistema);
        add(labelNome);
        add(nome);
        
        add(labelArqOrigem);
        add(arquivoOrigem);
        add(btnSelArqOrigem);
        
        add(labelDiretorio);
        add(diretorioDestino);
        add(btnSelDiretorio);
        
        add(botaoEnviar);
        add(btnBanco);

        setVisible(true);
    }
    
    public void selecionarArquivoOrigem(){
    	
    	JFileChooser fileOrigem = new JFileChooser();
    	int resposta = fileOrigem.showOpenDialog(this);
    	if (resposta == fileOrigem.APPROVE_OPTION) {
    		File arquivo = fileOrigem.getSelectedFile();
    		arquivoOrigem.setText(arquivo.getAbsolutePath());
    		
    		long ultimaModificacao = arquivo.lastModified();
    		
    		Date dataModificacao = new Date(ultimaModificacao);
    		SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		dataUltimaModificacao = formatoData.format(dataModificacao);
    	
    	}
    	
    }
    
    public void selecionarDiretorio() {
    	JFileChooser diretorio = new JFileChooser();
    	
    	diretorio.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    	
    	int resposta = diretorio.showOpenDialog(this);
    	
    	if(resposta == diretorio.APPROVE_OPTION) {
    		File arquivo = diretorio.getSelectedFile();
    		diretorioDestino.setText(arquivo.getAbsolutePath());
    	}
    }
    
    public void chamarBanco() {
    	Date dataAtual = new Date();
    	
    	CriarVersao criar = new CriarVersao();
    	String versao = campoVersao.getText();
        String sistema = (String) comboSistema.getSelectedItem();
        String nomeArquivo = nome.getText();
        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataFormatada = formatoData.format(dataAtual);
    	criar.inserirVersao(sistema, versao, nomeArquivo, dataUltimaModificacao, dataFormatada);
    	
    }

    public static void main(String[] args) {
        new JanelaSecundaria();
    }
}