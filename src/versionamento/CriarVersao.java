package versionamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class CriarVersao {
	
	Connection connection = null;
	PreparedStatement statement = null;
    ResultSet resultSet = null;
    
	public void ConsultaVersao() {
    	
        
    	try {
    	
    	connection = ConexaoBanco.getConnection();
    	String sql = "Select * from VERSIONAMENTO";
    	statement = connection.prepareStatement(sql);
    	resultSet = statement.executeQuery();
    	
    	while (resultSet.next()) {
    	    System.out.println("ID: " + resultSet.getInt("id"));
    	    System.out.println("Nome: " + resultSet.getString("nome"));
    	}
    	
    	}catch(SQLException e) {
            System.err.println("Erro ao executar a consulta: " + e.getMessage());
            
        } finally {
            // Fecha os recursos
        	
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                ConexaoBanco.closeConnection(connection);
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
            
        }
    }
	public void inserirVersao(String sistema, String versao, String nome, String data_arquivo, String data_registro) {
		
		try {
			connection = ConexaoBanco.getConnection();
			String sql = "INSERT INTO VERSIONAMENTO (SISTEMA,VERSAO, NOME, DATA_ARQUIVO, DATA_REGISTRO) "
					+ "VALUES (?, ?, ?, ?, ?)";
			
			statement = connection.prepareStatement(sql);
			//teste git
			
			// Define os valores dos placeholders
	        statement.setString(1, sistema);    // SISTEMA
	        statement.setString(2, versao);    // VERSAO
	        statement.setString(3, nome);
	        statement.setTimestamp(4, Timestamp.valueOf(data_arquivo));
	        statement.setTimestamp(5, Timestamp.valueOf(data_registro));
	        
	        //linhas afetadas 
	        int linhasAfetadas = statement.executeUpdate();
	        
	        if( linhasAfetadas > 0 ) {
	        	System.out.println("Dados inseridos!");
	        } else {
	        	
	        	System.out.println("Erro ao Inserir!");
	        }
	        
		}
		catch(SQLException e) {
            System.err.println("Erro ao executar o sql: " + e.getMessage());
            
		}finally {
	        // Fecha os recursos
	        try {
	            if (statement != null) statement.close();
	            if (connection != null) connection.close();
	        } catch (SQLException e) {
	            System.err.println("Erro ao fechar recursos: " + e.getMessage());
	        }
		}
	}
		
}