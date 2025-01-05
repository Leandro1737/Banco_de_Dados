package TrabalhoAndre;

import java.sql.*;

/**
 * Classe responsável pelo gerenciamento de clientes.
 * Permite cadastrar, listar, editar e apagar clientes no banco de dados.
 * 
 * @author Kaiky
 */
public class Cliente {

    private String nome;
    private String email;
    private int id;

    /**
     * Construtor da classe Cliente.
     * 
     * @param nome  Nome do cliente.
     * @param email Email do cliente.
     * @param id    ID do cliente.
     */
    public Cliente(String nome, String email, int id) {
        this.nome = nome;
        this.email = email;
        this.id = id;
    }

    /**
     * Obtém o nome do cliente.
     * 
     * @return O nome do cliente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Obtém o email do cliente.
     * 
     * @return O email do cliente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Obtém o ID do cliente.
     * 
     * @return O ID do cliente.
     */
    public int getId() {
        return id;
    }

    /**
     * Lista todos os clientes cadastrados no banco de dados.
     */
    public static void listarClientes() {
        String query = "SELECT * FROM clientes";
        try (Connection connection = Conexao.getConnection(); 
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

    /**
     * Cadastra um novo cliente no banco de dados.
     * 
     * @param nome  Nome do cliente.
     * @param email Email do cliente.
     */
    public static void cadastrarCliente(String nome, String email) {
        String query = "INSERT INTO clientes (nome, email) VALUES (?, ?)";
        try (Connection connection = Conexao.getConnection(); 
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.executeUpdate();
            System.out.println("Cliente cadastrado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    /**
     * Edita as informações de um cliente existente no banco de dados.
     * 
     * @param id        ID do cliente a ser editado.
     * @param novoNome  Novo nome do cliente.
     * @param novoEmail Novo email do cliente.
     */
    public static void editarCliente(int id, String novoNome, String novoEmail) {
        String query = "UPDATE clientes SET nome = ?, email = ? WHERE id = ?";
        try (Connection connection = Conexao.getConnection(); 
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

    /**
     * Apaga um cliente do banco de dados.
     * 
     * @param id ID do cliente a ser apagado.
     */
    public static void apagarCliente(int id) {
        String query = "DELETE FROM clientes WHERE id = ?";
        try (Connection connection = Conexao.getConnection(); 
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

    /**
     * Busca um cliente pelo ID no banco de dados.
     * 
     * @param clienteId ID do cliente a ser buscado.
     * @return O cliente encontrado ou null se não for encontrado.
     */
    public static Cliente buscarClientePorId(int clienteId) {
        String query = "SELECT * FROM clientes WHERE id = ?";
        try (Connection connection = Conexao.getConnection(); 
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                return new Cliente(nome, email, clienteId);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
        }
        return null;  // Retorna null se o cliente não for encontrado
    }
}