package TrabalhoAndre;

import java.sql.*;
import java.util.Scanner;

public class Cliente {

    private String nome;
    private String email;
    private int id;

    // Construtor
    public Cliente(String nome, String email, int id) {
        this.nome = nome;
        this.email = email;
        this.id = id;
    }

    // Métodos de acesso
    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    // Método para obter a conexão com o banco de dados
    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/biblioteca";  // URL do banco de dados
        String user = "root";  // Usuário do banco de dados
        String password = "";  // Senha do banco de dados
        return DriverManager.getConnection(url, user, password);
    }

    // Lista os clientes cadastrados no banco de dados
    public static void listarClientes() {
        String query = "SELECT * FROM clientes";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            System.out.println("Clientes cadastrados:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                System.out.println("ID: " + id + ", Nome: " + nome + ", Email: " + email);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }
    }

    // Cadastrar cliente no banco de dados
    public static void cadastrarCliente(String nome, String email) {
        String query = "INSERT INTO clientes (nome, email) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.executeUpdate();
            System.out.println("Cliente cadastrado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    // Editar cliente no banco de dados
    public static void editarCliente(int id, String novoNome, String novoEmail) {
        String query = "UPDATE clientes SET nome = ?, email = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, novoNome);
            stmt.setString(2, novoEmail);
            stmt.setInt(3, id);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Cliente atualizado com sucesso.");
            } else {
                System.out.println("Cliente não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao editar cliente: " + e.getMessage());
        }
    }

    // Apagar cliente do banco de dados
    public static void apagarCliente(int id) {
        String query = "DELETE FROM clientes WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Cliente apagado com sucesso.");
            } else {
                System.out.println("Cliente não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao apagar cliente: " + e.getMessage());
        }
    }
}
