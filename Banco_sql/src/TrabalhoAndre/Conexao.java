package TrabalhoAndre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    // Método para obter a conexão com o banco de dados
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/biblioteca";  // URL do banco de dados
        String user = "root";  // Usuário do banco de dados
        String password = "123456789";  // Senha do banco de dados
        return DriverManager.getConnection(url, user, password);
    }
}