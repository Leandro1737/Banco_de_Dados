package TrabalhoAndre;

import java.sql.*;

public class Emprestimo {
    private Livros livro;
    private Cliente cliente;
    private String dataEmprestimo;

    // Construtor
    public Emprestimo(Livros livro, Cliente cliente, String dataEmprestimo) {
        this.livro = livro;
        this.cliente = cliente;
        this.dataEmprestimo = dataEmprestimo;
    }

    // Método para cadastrar o empréstimo
    public static void cadastrarEmprestimo(Livros livro, Cliente cliente, String dataEmprestimo) {
        String query = "INSERT INTO emprestimos (cliente_id, livro_id, data_emprestimo) VALUES (?, ?, ?)";
        try (Connection connection = Conexao.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cliente.getId());
            stmt.setInt(2, livro.getId());
            stmt.setString(3, dataEmprestimo);
            stmt.executeUpdate();
            System.out.println("Empréstimo cadastrado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar empréstimo: " + e.getMessage());
        }
    }
    
    // Atualizar empréstimo no banco de dados
    public static void atualizarEmprestimo(int clienteId, int livroId, String novaDataEmprestimo) {
        String query = "UPDATE emprestimos SET data_emprestimo = ? WHERE cliente_id = ? AND livro_id = ?";
        try (Connection connection = Conexao.getConnection();
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
    
    // Lista todos os empréstimos cadastrados no banco de dados
    public static void listaEmprestimos() {
        String query = "SELECT e.data_emprestimo, l.titulo, c.nome FROM emprestimos e JOIN livros l ON e.livro_id = l.id JOIN clientes c ON e.cliente_id = c.id";
        try (Connection connection = Conexao.getConnection();
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

    // Método para registrar devolução
    public static void registrarDevolucao(Livros livro, Cliente cliente) {
        String query = "DELETE FROM emprestimos WHERE cliente_id = ? AND livro_id = ?";
        try (Connection connection = Conexao.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cliente.getId());
            stmt.setInt(2, livro.getId());
            stmt.executeUpdate();
            System.out.println("Devolução registrada com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao registrar devolução: " + e.getMessage());
        }
    }
}