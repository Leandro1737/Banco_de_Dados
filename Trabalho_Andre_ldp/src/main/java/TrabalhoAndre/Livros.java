package TrabalhoAndre;

import java.sql.*;

public class Livros {

    private int id;
    private String titulo;
    private String autores;
    private String anoPublicacao;
    private int numExemplares;
    private int exemplaresDisponiveis;

    // Construtor
    public Livros(int id, String titulo, String autores, String anoPublicacao, int numExemplares) {
        this.id = id;
        this.titulo = titulo;
        this.autores = autores;
        this.anoPublicacao = anoPublicacao;
        this.numExemplares = numExemplares;
        this.exemplaresDisponiveis = numExemplares;
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

    public int getNumExemplares() {
        return numExemplares;
    }

    public int getExemplaresDisponiveis() {
        return exemplaresDisponiveis;
    }

    // Métodos para empréstimo e devolução
    public boolean emprestarExemplar() {
        if (exemplaresDisponiveis > 0) {
            exemplaresDisponiveis--;
            return true;
        }
        return false;
    }

    public void devolverExemplar() {
        if (exemplaresDisponiveis < numExemplares) {
            exemplaresDisponiveis++;
        }
    }

    // Método estático para listar os livros
    public static void listarLivros() {
        String query = "SELECT id, titulo, autores, ano_publicacao, exemplares_disponiveis FROM livros";
        try (Connection connection = Conexao.getConnection(); Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Livros cadastrados:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String autores = rs.getString("autores");
                String anoPublicacao = rs.getString("ano_publicacao");
                int exemplaresDisponiveis = rs.getInt("exemplares_disponiveis");
                System.out.println("ID: " + id + ", Título: " + titulo + ", Autores: " + autores + ", Ano: " + anoPublicacao + ", Exemplares Disponíveis: " + exemplaresDisponiveis);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar livros: " + e.getMessage());
        }
    }

    // Método para cadastrar livro no banco de dados
    public static void cadastrarLivro(Livros livro) {
        String query = "INSERT INTO livros (titulo, autores, ano_publicacao, num_exemplares, exemplares_disponiveis) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = Conexao.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutores());
            stmt.setString(3, livro.getAnoPublicacao());
            stmt.setInt(4, livro.getNumExemplares());
            stmt.setInt(5, livro.getExemplaresDisponiveis());
            stmt.executeUpdate();
            System.out.println("Livro cadastrado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar livro: " + e.getMessage());
        }
    }

    // Método para atualizar livro no banco de dados
    public static void atualizarLivro(int id, String titulo, String autores, String anoPublicacao, int numExemplares) {
        String query = "UPDATE livros SET titulo = ?, autores = ?, ano_publicacao = ?, num_exemplares = ?, exemplares_disponiveis = ? WHERE id = ?";
        try (Connection connection = Conexao.getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, titulo);
            stmt.setString(2, autores);
            stmt.setString(3, anoPublicacao);
            stmt.setInt(4, numExemplares);
            stmt.setInt(5, numExemplares);
            stmt.setInt(6, id);
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

    // Método para apagar livro do banco de dados
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
                int numExemplares = rs.getInt("num_exemplares");
                int exemplaresDisponiveis = rs.getInt("exemplares_disponiveis");

                // Retorna um objeto da classe Livros com os dados do livro
                return new Livros(id, titulo, autores, anoPublicacao, numExemplares);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar livro: " + e.getMessage());
        }
        return null;  // Retorna null se o livro não for encontrado
    }

}
