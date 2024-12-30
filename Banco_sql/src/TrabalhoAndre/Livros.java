package TrabalhoAndre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    /**
     * Método que insere o livro no banco de dados.
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
            var rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }

            System.out.println("Livro inserido com sucesso. ID gerado: " + this.id);
        } catch (SQLException e) {
            System.out.println("Erro ao inserir o livro: " + e.getMessage());
        }
    }

    // Getter para o ID do livro
    public int getId() {
        return this.id;
    }

    // Outros métodos
    public int getnumExemplares() {
        return this.numExemplares;
    }

    public void setnumExemplares(int numExemplares) {
        this.numExemplares = numExemplares;
    }

    public static void listarLivros(Livros[] livros) {
        System.out.println("Livros cadastrados:");
        for (Livros livro : livros) {
            if (livro != null) {
                System.out.println("ID: " + livro.getId() + " - Título: " + livro.titulo);
            }
        }
    }

    public static boolean removerLivro(Livros[] livros, String tituloParaRemover) {
        for (int i = 0; i < livros.length; i++) {
            if (livros[i] != null && livros[i].titulo.equalsIgnoreCase(tituloParaRemover)) {
                for (int j = i; j < livros.length - 1; j++) {
                    livros[j] = livros[j + 1];
                }
                livros[livros.length - 1] = null; 
                System.out.println("Livro removido");
                return true;
            }
        }
        System.out.println("Livro não encontrado");
        return false;
    }

    public boolean emprestarExemplar() {
        if (numExemplares > 0) {
            numExemplares--;
            return true;
        } else {
            return false;
        }
    }

    public void devolverExemplar() {
        numExemplares++;
    }
}
