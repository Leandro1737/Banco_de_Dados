package TrabalhoAndre;

import java.sql.*;

public class Emprestimo {
    private Livros livro;
    private Cliente cliente;
    private String dataEmprestimo;

    // Construtor
    public Emprestimo(Livros livro, String dataEmprestimo, Cliente cliente) {
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.cliente = cliente;
    }

    // Métodos de acesso
    public Livros getLivro() {
        return livro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getDataEmprestimo() {
        return dataEmprestimo;
    }

    // Método para obter a conexão com o banco de dados
    static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/biblioteca";  // URL do banco de dados
        String user = "root";  // Usuário do banco de dados
        String password = "";  // Senha do banco de dados
        return DriverManager.getConnection(url, user, password);
    }

    // Verifica se o cliente já possui um empréstimo
    public static boolean empCadastrado(int clienteId) {
        String query = "SELECT * FROM emprestimos WHERE cliente_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();  // Retorna true se houver um empréstimo ativo
        } catch (SQLException e) {
            System.out.println("Erro ao verificar empréstimo: " + e.getMessage());
        }
        return false; // Cliente não possui empréstimo ativo
    }

    // Lista todos os empréstimos cadastrados no banco de dados
    public static void listaEmprestimos() {
        String query = "SELECT e.data_emprestimo, l.titulo, c.nome FROM emprestimos e JOIN livros l ON e.livro_id = l.id JOIN clientes c ON e.cliente_id = c.id";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            System.out.println("Empréstimos cadastrados:");
            while (rs.next()) {
                String titulo = rs.getString("titulo");
                String nomeCliente = rs.getString("nome");
                String dataEmprestimo = rs.getString("data_emprestimo");
                System.out.println("Livro: " + titulo + ", Cliente: " + nomeCliente + ", Data: " + dataEmprestimo);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar empréstimos: " + e.getMessage());
        }
    }

    // Cadastrar empréstimo no banco de dados
    public static void cadastrarEmprestimo(Livros livro, Cliente cliente, String dataEmprestimo) {
        String query = "INSERT INTO emprestimos (livro_id, cliente_id, data_emprestimo) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, livro.getId());  // Usando o ID do livro (assumindo que cada livro tem um ID único)
            stmt.setInt(2, cliente.getId());  // Usando o ID do cliente
            stmt.setString(3, dataEmprestimo);  // Data do empréstimo
            stmt.executeUpdate();
            System.out.println("Empréstimo cadastrado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar empréstimo: " + e.getMessage());
        }
    }

    // Apagar empréstimo do banco de dados
    public static void apagarEmprestimo(int clienteId, int livroId) {
        String query = "DELETE FROM emprestimos WHERE cliente_id = ? AND livro_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, clienteId);
            stmt.setInt(2, livroId);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Empréstimo apagado com sucesso.");
            } else {
                System.out.println("Empréstimo não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao apagar empréstimo: " + e.getMessage());
        }
    }

    // Atualizar empréstimo no banco de dados
    public static void atualizarEmprestimo(int clienteId, int livroId, String novaDataEmprestimo) {
        String query = "UPDATE emprestimos SET data_emprestimo = ? WHERE cliente_id = ? AND livro_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, novaDataEmprestimo);
            stmt.setInt(2, clienteId);
            stmt.setInt(3, livroId);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Empréstimo atualizado com sucesso.");
            } else {
                System.out.println("Empréstimo não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar empréstimo: " + e.getMessage());
        }
    }

    // Método para devolver um livro
    public static void devolverLivro(int clienteId, int livroId) {
        String query = "DELETE FROM emprestimos WHERE cliente_id = ? AND livro_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, clienteId);
            stmt.setInt(2, livroId);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                // A devolução do livro no banco de dados foi concluída
                String updateLivro = "UPDATE livros SET exemplares_disponiveis = exemplares_disponiveis + 1 WHERE id = ?";
                try (PreparedStatement updateStmt = connection.prepareStatement(updateLivro)) {
                    updateStmt.setInt(1, livroId);
                    updateStmt.executeUpdate();
                    System.out.println("Livro devolvido com sucesso.");
                }
            } else {
                System.out.println("Empréstimo não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao devolver livro: " + e.getMessage());
        }
    }
}
