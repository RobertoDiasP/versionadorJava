package versionamento;
import javax.swing.*;
import java.awt.event.*;

public class Main extends JFrame {
    public Main() {
        // Configuração da janela principal
        setTitle("Versionador F5");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela na tela

        // Criando a barra de menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Opções");
        JMenuItem abrirJanela = new JMenuItem("Abrir Janela Secundária");

        // Adicionando ação ao item de menu
        abrirJanela.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new JanelaSecundaria(); // Abre a nova janela
            }
        });

        // Adicionando itens ao menu
        menu.add(abrirJanela);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}
