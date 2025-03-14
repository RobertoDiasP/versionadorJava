package versionamento;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {
	
	//parametros de conexão
	private static final String JDBC_URL = "jdbc:firebirdsql://siform.ddns.net:3050/siform-demo?lc_ctype=UTF8";
	private static final String USER = "SYSDBA";
    private static final String PASSWORD = "F5sistemas";
    
 // Método para obter uma conexão
    public static Connection getConnection() throws SQLException {
        try {
            // Carrega o driver JDBC do Firebird
            Class.forName("org.firebirdsql.jdbc.FBDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver do Firebird não encontrado!", e);
        }

        // Cria e retorna a conexão
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    // Método para fechar a conexão
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
    
}
