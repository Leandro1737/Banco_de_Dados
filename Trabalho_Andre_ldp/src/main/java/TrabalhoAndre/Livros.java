package TrabalhoAndre;

import java.sql.*;

/**
 * Classe responsável por representar e manipular dados de livros.
 * Fornece métodos para listar, cadastrar, atualizar e apagar livros no banco de dados.
 * Também permite a busca de livros por ID.
 * 
 * @author Leandro
 */
public class Livros {

    private int id;
    private String titulo;
    private String autores;
    private String anoPublicacao;

    /**
     * Construtor para criar uma instância de Livros.
     * 
     * @param id             O ID do livro.
     * @param titulo         O título do livro.
     * @param autores        Os autores do livro.
     * @param anoPublicacao  O ano de publicação do livro.
     */
    public Livros(int id, String titulo, String autores, String anoPublicacao) {
        this.id = id;
        this.titulo = titulo;
        this.autores = autores;
        this.anoPublicacao = anoPublicacao;
    }

    // Métodos de acesso
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutores() {
        return autores;
    }

    public String getAnoPublicacao() {
        return anoPublicacao;
    }

    /**
     * Lista todos os livros cadastrados no banco de dados.
     */
    public static void listarLivros() {
        String query = "SELECT id, titulo, autores, ano_publicacao FROM livros";
        try (Connection connection = Conexao.getConnection(); Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Livros cadastrados:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String autores = rs.getString("autores");
                String anoPublicacao = rs.getString("ano_publicacao");
                System.out.println("ID: " + id + ", Título: " + titulo + ", Autores: " + autores + ", Ano: " + anoPublicacao);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar livros: " + e.getMessage());
        }
    }

    /**
     * Cadastra um novo livro no banco de dados.
     * 
     * @param livro O objeto Livros a ser cadastrado.
     */
    public static void cadastrarLivro(Livros livro) {
        String query = "INSERT INTO livros (titulo, autores, ano_publicacao) VALUES (?, ?, ?)";
        try (Connection connection = Conexao.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutores());
            stmt.setString(3, livro.getAnoPublicacao());
            stmt.executeUpdate();
            System.out.println("Livro cadastrado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar livro: " + e.getMessage());
        }
    }

    /**
     * Atualiza os dados de um livro no banco de dados.
     * 
     * @param id            O ID do livro a ser atualizado.
     * @param titulo        O novo título do livro.
     * @param autores       Os novos autores do livro.
     * @param anoPublicacao O novo ano de publicação do livro.
     */
    public static void atualizarLivro(int id, String titulo, String autores, String anoPublicacao) {
        String query = "UPDATE livros SET titulo = ?, autores = ?, ano_publicacao = ? WHERE id = ?";
        try (Connection connection = Conexao.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, titulo);
            stmt.setString(2, autores);
            stmt.setString(3, anoPublicacao);
            stmt.setInt(4, id);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Livro atualizado com sucesso.");
            } else {
                System.out.println("Livro não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar livro: " + e.getMessage());
        }
    }

    /**
     * Apaga um livro do banco de dados.
     * 
     * @param id O ID do livro a ser apagado.
     */
    public static void apagarLivro(int id) {
        String query = "DELETE FROM livros WHERE id = ?";
        try (Connection connection = Conexao.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Livro apagado com sucesso.");
            } else {
                System.out.println("Livro não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao apagar livro: " + e.getMessage());
        }
    }

    /**
     * Busca um livro no banco de dados pelo seu ID.
     * 
     * @param livroId O ID do livro a ser buscado.
     * @return Um objeto Livros com os dados do livro, ou null se não for encontrado.
     */
    public static Livros buscarLivroPorId(int livroId) {
        String query = "SELECT * FROM livros WHERE id = ?";
        try (Connection connection = Conexao.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, livroId);  // Definir o ID do livro na consulta
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Mapeia os resultados para as variáveis correspondentes
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String autores = rs.getString("autores");
                String anoPublicacao = rs.getString("ano_publicacao");

                // Retorna um objeto da classe Livros com os dados do livro
                return new Livros(id, titulo, autores, anoPublicacao);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar livro: " + e.getMessage());
        }
        return null;  // Retorna null se o livro não for encontrado
    }
}