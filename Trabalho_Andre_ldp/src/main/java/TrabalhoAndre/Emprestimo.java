package TrabalhoAndre;

import java.sql.*;

/**
 * Classe responsável por gerenciar os empréstimos de livros para os clientes.
 * Fornece métodos para cadastrar, atualizar, listar e registrar devoluções de empréstimos.
 * 
 * @author Giovana
 */
public class Emprestimo {
    private Livros livro;
    private Cliente cliente;
    private String dataEmprestimo;

    /**
     * Construtor para criar uma instância de Emprestimo.
     * 
     * @param livro           O livro que foi emprestado.
     * @param cliente         O cliente que realizou o empréstimo.
     * @param dataEmprestimo  A data do empréstimo.
     */
    public Emprestimo(Livros livro, Cliente cliente, String dataEmprestimo) {
        this.livro = livro;
        this.cliente = cliente;
        this.dataEmprestimo = dataEmprestimo;
    }

    /**
     * Método para cadastrar um empréstimo no banco de dados.
     * Insere as informações de um empréstimo (livro, cliente e data) na tabela de empréstimos.
     * 
     * @param livro           O livro que foi emprestado.
     * @param cliente         O cliente que pegou o livro emprestado.
     * @param dataEmprestimo  A data em que o empréstimo foi realizado.
     */
    public static void cadastrarEmprestimo(Livros livro, Cliente cliente, String dataEmprestimo) {
        String query = "INSERT INTO emprestimos (cliente_id, livro_id, data_emprestimo) VALUES (?, ?, ?)";
        try (Connection connection = Conexao.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cliente.getId());  // Define o ID do cliente
            stmt.setInt(2, livro.getId());     // Define o ID do livro
            stmt.setString(3, dataEmprestimo); // Define a data do empréstimo
            stmt.executeUpdate(); // Executa a inserção no banco
            System.out.println("Empréstimo cadastrado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar empréstimo: " + e.getMessage());
        }
    }
    
    /**
     * Método para atualizar a data de um empréstimo existente no banco de dados.
     * Atualiza a data de empréstimo de um cliente para um livro específico.
     * 
     * @param clienteId        O ID do cliente que fez o empréstimo.
     * @param livroId          O ID do livro que foi emprestado.
     * @param novaDataEmprestimo A nova data do empréstimo.
     */
    public static void atualizarEmprestimo(int clienteId, int livroId, String novaDataEmprestimo) {
        String query = "UPDATE emprestimos SET data_emprestimo = ? WHERE cliente_id = ? AND livro_id = ?";
        try (Connection connection = Conexao.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, novaDataEmprestimo);  // Define a nova data de empréstimo
            stmt.setInt(2, clienteId);               // Define o ID do cliente
            stmt.setInt(3, livroId);                 // Define o ID do livro
            int rowsUpdated = stmt.executeUpdate();  // Executa a atualização no banco
            if (rowsUpdated > 0) {
                System.out.println("Empréstimo atualizado com sucesso.");
            } else {
                System.out.println("Empréstimo não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar empréstimo: " + e.getMessage());
        }
    }
    
    /**
     * Método para listar todos os empréstimos cadastrados no banco de dados.
     * Exibe todos os empréstimos, incluindo o nome do cliente, o título do livro e a data do empréstimo.
     */
    public static void listaEmprestimos() {
        String query = "SELECT e.data_emprestimo, l.titulo, c.nome FROM emprestimos e " +
                       "JOIN livros l ON e.livro_id = l.id " +
                       "JOIN clientes c ON e.cliente_id = c.id";
        try (Connection connection = Conexao.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            System.out.println("Empréstimos cadastrados:");
            while (rs.next()) {
                String titulo = rs.getString("titulo");        // Obtém o título do livro
                String nomeCliente = rs.getString("nome");     // Obtém o nome do cliente
                String dataEmprestimo = rs.getString("data_emprestimo"); // Obtém a data do empréstimo
                System.out.println("Livro: " + titulo + ", Cliente: " + nomeCliente + ", Data: " + dataEmprestimo);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar empréstimos: " + e.getMessage());
        }
    }

    /**
     * Método para registrar a devolução de um livro emprestado.
     * Exclui o registro de empréstimo da tabela de empréstimos, efetivamente "devolvendo" o livro.
     * 
     * @param livro   O livro que foi devolvido.
     * @param cliente O cliente que está devolvendo o livro.
     */
    public static void registrarDevolucao(Livros livro, Cliente cliente) {
        String query = "DELETE FROM emprestimos WHERE cliente_id = ? AND livro_id = ?";
        try (Connection connection = Conexao.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cliente.getId());  // Define o ID do cliente
            stmt.setInt(2, livro.getId());     // Define o ID do livro
            stmt.executeUpdate(); // Executa a exclusão do empréstimo do banco
            System.out.println("Devolução registrada com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao registrar devolução: " + e.getMessage());
        }
    }
}