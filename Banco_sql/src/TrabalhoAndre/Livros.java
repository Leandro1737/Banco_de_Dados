package TrabalhoAndre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Livros {
    public String titulo;
    public String autores;
    public String anoPublicacao;
    private int numExemplares;
    private int id; // Atributo para armazenar o ID do livro

    public Livros(String titulo, String autores, String anoPublicacao, int numExemplares) {
        this.titulo = titulo;
        this.autores = autores;
        this.anoPublicacao = anoPublicacao;
        this.numExemplares = numExemplares;
    }

    public Livros(int id, String titulo, String autores, String anoPublicacao, int numExemplares) {
        this.id = id;
        this.titulo = titulo;
        this.autores = autores;
        this.anoPublicacao = anoPublicacao;
        this.numExemplares = numExemplares;
    }

    // Getter para o ID do livro
    public int getId() {
        return this.id;
    }

    public int getNumExemplares() {
        return this.numExemplares;
    }

    public void setNumExemplares(int numExemplares) {
        this.numExemplares = numExemplares;
    }

    /**
     * Método para inserir um livro no banco de dados.
     */
    public void inserir() {
        Connection conexao = new Conexao().getConexao();
        String sql = "INSERT INTO tb_livros (titulo, autores, anoPublicacao, numExemplares) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, this.titulo);
            stmt.setString(2, this.autores);
            stmt.setString(3, this.anoPublicacao);
            stmt.setInt(4, this.numExemplares);

            stmt.execute();

            // Obtém o ID gerado pelo banco de dados
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }

            System.out.println("Livro inserido com sucesso. ID gerado: " + this.id);
        } catch (SQLException e) {
            System.err.println("Erro ao inserir livro: " + e.getMessage());
        }
    }

    /**
     * Método para listar todos os livros no banco de dados.
     */
    public static void listar() {
        Connection conexao = new Conexao().getConexao();
        String sql = "SELECT id, titulo, autores, anoPublicacao, numExemplares FROM tb_livros";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            System.out.println("Lista de Livros:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String autores = rs.getString("autores");
                String anoPublicacao = rs.getString("anoPublicacao");
                int numExemplares = rs.getInt("numExemplares");

                System.out.println("ID: " + id + " | Título: " + titulo + " | Autores: " + autores 
                                 + " | Ano: " + anoPublicacao + " | Exemplares: " + numExemplares);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar livros: " + e.getMessage());
        }
    }

    /**
     * Método para atualizar informações de um livro no banco de dados.
     */
    public void atualizar() {
        Connection conexao = new Conexao().getConexao();
        String sql = "UPDATE tb_livros SET titulo = ?, autores = ?, anoPublicacao = ?, numExemplares = ? WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, this.titulo);
            stmt.setString(2, this.autores);
            stmt.setString(3, this.anoPublicacao);
            stmt.setInt(4, this.numExemplares);
            stmt.setInt(5, this.id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Livro atualizado com sucesso!");
            } else {
                System.out.println("Livro não encontrado para atualização.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar livro: " + e.getMessage());
        }
    }

    /**
     * Método para apagar um livro do banco de dados.
     */
    public void apagar() {
        Connection conexao = new Conexao().getConexao();
        String sql = "DELETE FROM tb_livros WHERE id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, this.id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Livro apagado com sucesso!");
            } else {
                System.out.println("Livro não encontrado para exclusão.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao apagar livro: " + e.getMessage());
        }
    }
}
