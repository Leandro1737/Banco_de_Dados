package TrabalhoAndre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
            stmt.setInt(1, this.livro.getId()); // Supondo que Livros tenha o método getId()
            stmt.setInt(2, this.cliente.getID()); // Supondo que Cliente tenha o método getID()

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
     * Lista os empréstimos realizados.
     */
    public static void listaEmprestimos(Emprestimo[] emprestimos) {
        System.out.println("Empréstimos realizados:");
        boolean encontrouEmprestimos = false;
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo != null) {
                System.out.println("Cliente: " + emprestimo.getCliente().getNome() + " - Livro: " 
                                   + emprestimo.getLivro().titulo + " - Data do Empréstimo: " 
                                   + emprestimo.getDataEmprestimo());
                encontrouEmprestimos = true;
            }
        }

        if (!encontrouEmprestimos) {
            System.out.println("Nenhum empréstimo realizado.");
        }
    }

    /**
     * Realiza a devolução do livro.
     */
    public void devolver() {
        livro.devolverExemplar(); // Supondo que Livros tenha um método devolverExemplar()
        System.out.println("Livro devolvido com sucesso!");
    }

    /**
     * Verifica se um cliente possui empréstimo cadastrado.
     */
    public static boolean empCadastrado(Emprestimo[] emprestimos, int clienteId) {
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo != null && emprestimo.getCliente().getID() == clienteId) {
                return true;
            }
        }
        return false;
    }
}
