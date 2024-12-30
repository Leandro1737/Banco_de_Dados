package TrabalhoAndre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Emprestimo {
    private Livros livro;
    private String dataEmprestimo;
    private Cliente cliente;

    public Emprestimo(Livros livro, String dataEmprestimo, Cliente cliente) {
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.cliente = cliente;
    }

    public Livros getLivro() {
        return livro;
    }

    public String getDataEmprestimo() {
        return dataEmprestimo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Insere o empréstimo no banco de dados.
     */
    public void inserir() {
        Connection conexao = new Conexao().getConexao();
        String sql = "INSERT INTO tb_emprestimo (livro_id, dataEmprestimo, cliente_id) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, this.livro.getId()); // Supondo que Livros tenha o método getId()
            stmt.setString(2, this.dataEmprestimo);
            stmt.setInt(3, this.cliente.getID()); // Supondo que Cliente tenha o método getID()

            stmt.execute();
            System.out.println("Empréstimo registrado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir empréstimo: " + e.getMessage());
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    /**
     * Remove o empréstimo do banco de dados.
     */
    public void remover() {
        Connection conexao = new Conexao().getConexao();
        String sql = "DELETE FROM tb_emprestimo WHERE livro_id = ? AND cliente_id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, this.livro.getId());
            stmt.setInt(2, this.cliente.getID());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Empréstimo removido com sucesso!");
            } else {
                System.out.println("Nenhum empréstimo encontrado para remover.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao remover empréstimo: " + e.getMessage());
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    /**
     * Atualiza os dados de um empréstimo no banco de dados.
     */
    public void alterar(String novaDataEmprestimo) {
        Connection conexao = new Conexao().getConexao();
        String sql = "UPDATE tb_emprestimo SET dataEmprestimo = ? WHERE livro_id = ? AND cliente_id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, novaDataEmprestimo);
            stmt.setInt(2, this.livro.getId());
            stmt.setInt(3, this.cliente.getID());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                this.dataEmprestimo = novaDataEmprestimo;
                System.out.println("Empréstimo atualizado com sucesso!");
            } else {
                System.out.println("Nenhum empréstimo encontrado para atualizar.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar empréstimo: " + e.getMessage());
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    /**
     * Lista todos os empréstimos registrados no banco de dados.
     */
    public static void listar() {
        Connection conexao = new Conexao().getConexao();
        String sql = "SELECT e.dataEmprestimo, c.nome AS cliente, l.titulo AS livro "
                   + "FROM tb_emprestimo e "
                   + "JOIN tb_cliente c ON e.cliente_id = c.id "
                   + "JOIN tb_livros l ON e.livro_id = l.id";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            System.out.println("Lista de Empréstimos:");
            while (rs.next()) {
                String dataEmprestimo = rs.getString("dataEmprestimo");
                String cliente = rs.getString("cliente");
                String livro = rs.getString("livro");

                System.out.println("Cliente: " + cliente + " | Livro: " + livro + " | Data: " + dataEmprestimo);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar empréstimos: " + e.getMessage());
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}
